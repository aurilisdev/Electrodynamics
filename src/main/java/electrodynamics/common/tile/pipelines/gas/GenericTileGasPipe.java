package electrodynamics.common.tile.pipelines.gas;

import java.util.ArrayList;
import java.util.HashSet;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.types.gas.IGasHandler;
import electrodynamics.api.gas.GasAction;
import electrodynamics.api.gas.GasStack;
import electrodynamics.api.network.cable.type.IFluidPipe;
import electrodynamics.api.network.cable.type.IGasPipe;
import electrodynamics.common.network.type.GasNetwork;
import electrodynamics.common.network.utils.GasUtilities;
import electrodynamics.prefab.network.AbstractNetwork;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.tile.types.GenericConnectTile;
import electrodynamics.prefab.utilities.Scheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public abstract class GenericTileGasPipe extends GenericConnectTile implements IGasPipe {

	public GasNetwork gasNetwork;
	private IGasHandler[] capability = new IGasHandler[Direction.values().length];
	private boolean[] connections = new boolean[6];
	private BlockEntity[] tileConnections = new BlockEntity[6];

	public GenericTileGasPipe(BlockEntityType<?> tileEntityTypeIn, BlockPos worldPos, BlockState blockState) {
		super(tileEntityTypeIn, worldPos, blockState);
		addComponent(new ComponentTickable(this));
		for (Direction dir : Direction.values()) {
			capability[dir.ordinal()] = new IGasHandler() {

				@Override
				public boolean isGasValid(int tank, GasStack gas) {
					return gas != null;
				}

				@Override
				public double heat(int tank, double deltaTemperature, GasAction action) {
					return -1;
				}

				@Override
				public int getTanks() {
					return 1;
				}

				@Override
				public double getTankMaxTemperature(int tank) {
					return -1;
				}

				@Override
				public int getTankMaxPressure(int tank) {
					return getNetwork() == null ? 0 : getNetwork().maxPressure;
				}

				@Override
				public double getTankCapacity(int tank) {
					return 0;
				}

				@Override
				public GasStack getGasInTank(int tank) {
					return GasStack.EMPTY;
				}

				@Override
				public double fillTank(int tank, GasStack gas, GasAction action) {
					if (action == GasAction.SIMULATE || getNetwork() == null) {
						return 0;
					}
					return gasNetwork.emit(gas, Lists.newArrayList(level.getBlockEntity(worldPos.relative(dir))), action == GasAction.SIMULATE).getAmount();
				}

				@Override
				public GasStack drainTank(int tank, double maxFill, GasAction action) {
					return GasStack.EMPTY;
				}

				@Override
				public GasStack drainTank(int tank, GasStack gas, GasAction action) {
					return GasStack.EMPTY;
				}

				@Override
				public double bringPressureTo(int tank, int atm, GasAction action) {
					return -1;
				}
			};
		}
	}

	@Override
	public GasNetwork getNetwork() {
		return getNetwork(true);
	}

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction dir) {
		if (cap == ElectrodynamicsCapabilities.GAS_HANDLER) {
			return LazyOptional.of(() -> capability[dir.ordinal()]).cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public GasNetwork getNetwork(boolean createIfNull) {
		if (gasNetwork == null && createIfNull) {
			HashSet<IGasPipe> adjacentPipes = getConnectedPipes();
			HashSet<GasNetwork> connectedNets = new HashSet<>();
			for (IGasPipe wire : adjacentPipes) {
				if (wire.getNetwork(false) != null && wire.getNetwork() instanceof GasNetwork net) {
					connectedNets.add(net);
				}
			}
			if (connectedNets.isEmpty()) {
				gasNetwork = new GasNetwork(Sets.newHashSet(this));
			} else {
				if (connectedNets.size() == 1) {
					gasNetwork = (GasNetwork) connectedNets.toArray()[0];
				} else {
					gasNetwork = new GasNetwork(connectedNets, false);
				}
				gasNetwork.conductorSet.add(this);
			}
		}
		return gasNetwork;
	}

	private HashSet<IGasPipe> getConnectedPipes() {
		HashSet<IGasPipe> set = new HashSet<>();
		for (Direction dir : Direction.values()) {
			BlockEntity facing = level.getBlockEntity(new BlockPos(worldPosition).relative(dir));
			if (facing instanceof IGasPipe p) {
				set.add(p);
			}
		}
		return set;
	}

	@Override
	public void refreshNetwork() {
		if (level.isClientSide) {
			return;
		}
		updateAdjacent();
		ArrayList<GasNetwork> foundNetworks = new ArrayList<>();
		for (Direction dir : Direction.values()) {
			BlockEntity facing = level.getBlockEntity(new BlockPos(worldPosition).relative(dir));
			if (facing instanceof IFluidPipe p && p.getNetwork() instanceof GasNetwork n) {
				foundNetworks.add(n);
			}
		}
		if (!foundNetworks.isEmpty()) {
			foundNetworks.get(0).conductorSet.add(this);
			gasNetwork = foundNetworks.get(0);
			if (foundNetworks.size() > 1) {
				foundNetworks.remove(0);
				for (GasNetwork network : foundNetworks) {
					getNetwork().merge(network);
				}
			}
		}
		getNetwork().refresh();
	}

	@Override
	public void refreshNetworkIfChange() {
		if (updateAdjacent()) {
			refreshNetwork();
		}
	}

	public boolean updateAdjacent() {
		boolean flag = false;
		for (Direction dir : Direction.values()) {
			BlockEntity tile = level.getBlockEntity(worldPosition.relative(dir));
			boolean is = GasUtilities.isGasReciever(tile, dir.getOpposite());
			if (connections[dir.ordinal()] != is) {
				connections[dir.ordinal()] = is;
				tileConnections[dir.ordinal()] = tile;
				flag = true;
			}

		}
		return flag;
	}

	@Override
	public void removeFromNetwork() {
		if (gasNetwork != null) {
			gasNetwork.removeFromNetwork(this);
		}

	}

	@Override
	public void setNetwork(AbstractNetwork<?, ?, ?, ?> network) {
		if (gasNetwork != network && network instanceof GasNetwork g) {
			removeFromNetwork();
			gasNetwork = g;
		}
	}

	@Override
	public AbstractNetwork<?, ?, ?, ?> getAbstractNetwork() {
		return gasNetwork;
	}

	@Override
	public BlockEntity[] getAdjacentConnections() {
		return tileConnections;
	}

	@Override
	public void setRemoved() {
		if (!level.isClientSide && gasNetwork != null) {
			gasNetwork.split(this);
		}
		super.setRemoved();
	}

	@Override
	public void onChunkUnloaded() {
		super.onChunkUnloaded();
		if (!level.isClientSide && gasNetwork != null) {
			gasNetwork.split(this);
		}
	}

	@Override
	public void onLoad() {
		super.onLoad();
		Scheduler.schedule(1, this::refreshNetwork);
	}

}
