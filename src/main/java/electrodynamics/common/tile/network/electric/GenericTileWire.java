package electrodynamics.common.tile.network.electric;

import java.util.ArrayList;
import java.util.HashSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.Sets;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.types.electrodynamic.ICapabilityElectrodynamic;
import electrodynamics.api.network.cable.type.IConductor;
import electrodynamics.common.network.type.ElectricNetwork;
import electrodynamics.prefab.network.AbstractNetwork;
import electrodynamics.prefab.tile.types.GenericConnectTile;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.Scheduler;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public abstract class GenericTileWire extends GenericConnectTile implements IConductor {

	public ElectricNetwork electricNetwork;

	private boolean[] connections = new boolean[6];
	private BlockEntity[] tileConnections = new BlockEntity[6];

	private ArrayList<ICapabilityElectrodynamic> handler = new ArrayList<>();

	private boolean isQueued = false;

	protected GenericTileWire(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
		super(tileEntityTypeIn, pos, state);
		for (Direction dir : Direction.values()) {
			handler.add(new ICapabilityElectrodynamic() {
				@Override
				public double getMaxJoulesStored() {
					return 0;
				}

				@Override
				public double getJoulesStored() {
					return 0;
				}

				@Override
				public double getVoltage() {
					if (getNetwork() != null) {
						return getNetwork().getVoltage();
					}
					return -1;
				}

				@Override
				public double getMinimumVoltage() {
					if (getNetwork() != null) {
						return getNetwork().getMinimumVoltage();
					}
					return -1;
				}

				@Override
				public TransferPack receivePower(TransferPack transfer, boolean debug) {
					ArrayList<BlockEntity> ignored = new ArrayList<>();
					ignored.add(level.getBlockEntity(new BlockPos(worldPosition).relative(dir)));
					if (!debug) {
						getNetwork().addProducer(ignored.get(0), transfer.getVoltage());
					}
					return getNetwork().receivePower(transfer, debug);
				}

				@Override
				public void onChange() {

				}

				@Override
				public TransferPack getConnectedLoad(LoadProfile loadProfile, Direction dir) {
					return getNetwork().getConnectedLoad(loadProfile, dir);
				}
			});
		}
	}

	@Override
	@Nonnull
	public <T> @NotNull LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
		if (capability == ElectrodynamicsCapabilities.ELECTRODYNAMIC) {
			return LazyOptional.of(() -> handler.get(facing.ordinal())).cast();
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public ElectricNetwork getNetwork() {
		return getNetwork(true);
	}

	private HashSet<IConductor> getConnectedConductors() {
		HashSet<IConductor> set = new HashSet<>();

		for (Direction dir : Direction.values()) {
			BlockEntity facing = level.getBlockEntity(worldPosition.relative(dir));
			if (facing instanceof IConductor conductor && checkColor(conductor)) {
				set.add(conductor);
			}
		}
		return set;
	}

	@Override
	public ElectricNetwork getNetwork(boolean createIfNull) {
		if (electricNetwork == null && createIfNull) {
			HashSet<IConductor> adjacentCables = getConnectedConductors();
			HashSet<ElectricNetwork> connectedNets = new HashSet<>();
			for (IConductor wire : adjacentCables) {
				if (wire.getNetwork(false) != null && wire.getNetwork() instanceof ElectricNetwork el) {
					connectedNets.add(el);
				}
			}
			if (connectedNets.isEmpty()) {
				electricNetwork = new ElectricNetwork(Sets.newHashSet(this));
			} else {
				if (connectedNets.size() == 1) {
					electricNetwork = (ElectricNetwork) connectedNets.toArray()[0];
				} else {
					electricNetwork = new ElectricNetwork(connectedNets, false);
				}
				electricNetwork.conductorSet.add(this);
			}
		}
		return electricNetwork;
	}

	@Override
	public void setNetwork(AbstractNetwork<?, ?, ?, ?> network) {
		if (electricNetwork != network && network instanceof ElectricNetwork el) {
			removeFromNetwork();
			electricNetwork = el;
		}
	}

	@Override
	public void refreshNetwork() {
		if (level != null) {
			isQueued = false;
			if (!level.isClientSide) {
				updateAdjacent();
				ArrayList<ElectricNetwork> foundNetworks = new ArrayList<>();
				for (Direction dir : Direction.values()) {
					BlockEntity facing = level.getBlockEntity(worldPosition.relative(dir));
					if (facing instanceof IConductor conductor && checkColor(conductor) && conductor.getNetwork() instanceof ElectricNetwork network) {
						foundNetworks.add(network);
					}
				}
				if (!foundNetworks.isEmpty() && getNetwork(false) == null) {
					if (foundNetworks.size() > 1) {
						foundNetworks.get(0).conductorSet.add(this);
						electricNetwork = foundNetworks.get(0);
						foundNetworks.remove(0);
						for (ElectricNetwork network : foundNetworks) {
							electricNetwork.merge(network);
						}
					}
				}
				getNetwork().refresh();
			}
		} else if (!isQueued) {
			// For some reason the world was null?
			isQueued = true;
			Scheduler.schedule(20, this::refreshNetwork);
		}
	}

	public boolean updateAdjacent() {
		boolean flag = false;
		for (Direction dir : Direction.values()) {
			BlockEntity tile = level.getBlockEntity(worldPosition.relative(dir));
			boolean isElectricityReciever = ElectricityUtils.isElectricReceiver(tile, dir.getOpposite());
			boolean is = (ElectricityUtils.isConductor(tile, this) && isElectricityReciever) || (!(tile instanceof IConductor) && isElectricityReciever);
			if (connections[dir.ordinal()] != is) {
				connections[dir.ordinal()] = is;
				tileConnections[dir.ordinal()] = tile;
				flag = true;
			}

		}
		return flag;
	}

	@Override
	public BlockEntity[] getAdjacentConnections() {
		return tileConnections;
	}

	@Override
	public void refreshNetworkIfChange() {
		if (updateAdjacent()) {
			refreshNetwork();
		}
	}

	@Override
	public void removeFromNetwork() {
		if (electricNetwork != null) {
			electricNetwork.removeFromNetwork(this);
		}
	}

	@Override
	public AbstractNetwork<?, ?, ?, ?> getAbstractNetwork() {
		return electricNetwork;
	}

	@Override
	public void destroyViolently() {
		level.setBlockAndUpdate(worldPosition, Blocks.FIRE.defaultBlockState());
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		if (!level.isClientSide && electricNetwork != null) {
			getNetwork().split(this);
		}
	}

	@Override
	public void onLoad() {
		super.onLoad();
		Scheduler.schedule(1, this::refreshNetwork);
	}

	private boolean checkColor(IConductor conductor) {
		return conductor.getWireType().isDefaultColor() || getWireType().isDefaultColor() || conductor.getWireColor() == getWireColor();
	}
}
