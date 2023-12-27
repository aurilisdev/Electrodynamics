package electrodynamics.common.tile.electricitygrid;

import java.util.ArrayList;
import java.util.HashSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Sets;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.types.electrodynamic.ICapabilityElectrodynamic;
import electrodynamics.api.network.cable.type.IConductor;
import electrodynamics.common.network.ElectricNetwork;
import electrodynamics.prefab.network.AbstractNetwork;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.Scheduler;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public abstract class GenericTileWire extends GenericTile implements IConductor {

	public ElectricNetwork electricNetwork;

	private boolean[] connections = new boolean[6];
	private TileEntity[] tileConnections = new TileEntity[6];

	private ArrayList<ICapabilityElectrodynamic> handler = new ArrayList<>();

	private boolean isQueued = false;

	protected GenericTileWire(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
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
					return getNetwork().getVoltage();
				}

				@Override
				public double getMinimumVoltage() {
					return getNetwork().getMinimumVoltage();
				}

				@Override
				public TransferPack receivePower(TransferPack transfer, boolean debug) {
					ArrayList<TileEntity> ignored = new ArrayList<>();
					TileEntity entity = level.getBlockEntity(new BlockPos(worldPosition).relative(dir));
					if (entity == null) {
						return TransferPack.EMPTY;
					}
					boolean isReceiver = entity.getCapability(ElectrodynamicsCapabilities.ELECTRODYNAMIC, dir.getOpposite()).map(ICapabilityElectrodynamic::isEnergyReceiver).orElse(false);
					ignored.add(entity);
					if (!debug) {
						getNetwork().addProducer(ignored.get(0), transfer.getVoltage(), isReceiver);
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

				@Override
				public double getMaximumVoltage() {
					return getNetwork().getActiveVoltage();
				}

				@Override
				public double getAmpacity() {
					return getNetwork().getAmpacity();
				}

				@Override
				public boolean isEnergyReceiver() {
					return getNetwork().isEnergyReceiver();
				}

				@Override
				public boolean isEnergyProducer() {
					return getNetwork().isEnergyProducer();
				}

				@Override
				public void setJoulesStored(double joules) {
					getNetwork().setJoulesStored(joules);
				}

			});
		}
	}

	@Override
	@Nonnull
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
		if (capability == ElectrodynamicsCapabilities.ELECTRODYNAMIC) {
			return LazyOptional.of(() -> handler.get(facing.ordinal())).cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public ElectricNetwork getNetwork() {
		return getNetwork(true);
	}

	private HashSet<IConductor> getConnectedConductors() {
		HashSet<IConductor> set = new HashSet<>();

		for (Direction dir : Direction.values()) {
			TileEntity facing = level.getBlockEntity(worldPosition.relative(dir));
			if (facing instanceof IConductor) {
				set.add((IConductor) facing);
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
				if (wire.getNetwork(false) != null && wire.getNetwork() instanceof ElectricNetwork) {
					connectedNets.add((ElectricNetwork) wire.getNetwork());
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
		if (electricNetwork != network && network instanceof ElectricNetwork) {
			removeFromNetwork();
			electricNetwork = (ElectricNetwork) network;
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
					TileEntity facing = level.getBlockEntity(worldPosition.relative(dir));
					if (facing instanceof IConductor) {
						IConductor conductor = (IConductor) facing;
						if(conductor.getNetwork() instanceof ElectricNetwork) {
							foundNetworks.add((ElectricNetwork) conductor.getNetwork());
						}
						
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
			TileEntity tile = level.getBlockEntity(worldPosition.relative(dir));
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
	public TileEntity[] getAdjacentConnections() {
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

}