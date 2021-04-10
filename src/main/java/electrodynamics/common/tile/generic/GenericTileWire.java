package electrodynamics.common.tile.generic;

import java.util.ArrayList;
import java.util.HashSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Sets;

import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.api.electricity.IElectrodynamic;
import electrodynamics.api.network.AbstractNetwork;
import electrodynamics.api.network.conductor.IConductor;
import electrodynamics.api.utilities.Scheduler;
import electrodynamics.api.utilities.object.TransferPack;
import electrodynamics.common.network.ElectricNetwork;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public abstract class GenericTileWire extends GenericTile implements IConductor {

    public ElectricNetwork electricNetwork;

    protected GenericTileWire(TileEntityType<?> tileEntityTypeIn) {
	super(tileEntityTypeIn);
	for (Direction dir : Direction.values()) {
	    handler.add(new IElectrodynamic() {
		@Override
		public double getMaxJoulesStored() {
		    return 0;
		}

		@Override
		public double getJoulesStored() {
		    return 0;
		}

		@Override
		public TransferPack receivePower(TransferPack transfer, boolean debug) {
		    ArrayList<TileEntity> ignored = new ArrayList<>();
		    ignored.add(world.getTileEntity(new BlockPos(pos).offset(dir)));
		    if (!debug) {
			getNetwork().addProducer(ignored.get(0), transfer.getVoltage());
		    }
		    return getNetwork().receivePower(transfer, debug);
		}
	    });
	}
	addComponent(new ComponentPacketHandler().customPacketReader(this::readCustomPacket).customPacketWriter(this::writeCustomPacket));
    }

    private ArrayList<IElectrodynamic> handler = new ArrayList<>();

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
	if (capability == CapabilityElectrodynamic.ELECTRODYNAMIC) {
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
	    TileEntity facing = world.getTileEntity(pos.offset(dir));
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
	    } else if (connectedNets.size() == 1) {
		electricNetwork = (ElectricNetwork) connectedNets.toArray()[0];
		electricNetwork.conductorSet.add(this);
	    } else {
		electricNetwork = new ElectricNetwork(connectedNets, false);
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
	if (!world.isRemote) {
	    updateAdjacent();
	    ArrayList<ElectricNetwork> foundNetworks = new ArrayList<>();
	    for (Direction dir : Direction.values()) {
		TileEntity facing = world.getTileEntity(pos.offset(dir));
		if (facing instanceof IConductor && ((IConductor) facing).getNetwork() instanceof ElectricNetwork) {
		    foundNetworks.add((ElectricNetwork) ((IConductor) facing).getNetwork());
		}
	    }
	    if (!foundNetworks.isEmpty()) {
		foundNetworks.get(0).conductorSet.add(this);
		electricNetwork = foundNetworks.get(0);
		if (foundNetworks.size() > 1) {
		    foundNetworks.remove(0);
		    for (ElectricNetwork network : foundNetworks) {
			getNetwork().merge(network);
		    }
		}
	    }
	    getNetwork().refresh();
	}
    }

    private boolean[] connections = new boolean[6];
    private TileEntity[] tileConnections = new TileEntity[6];

    public boolean updateAdjacent() {
	boolean flag = false;
	for (Direction dir : Direction.values()) {
	    TileEntity tile = world.getTileEntity(pos.offset(dir));
	    boolean is = ElectricityUtilities.isElectricReceiver(tile, dir.getOpposite());
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
	world.setBlockState(pos, Blocks.FIRE.getDefaultState());
    }

    @Override
    protected void invalidateCaps() {
	super.invalidateCaps();
	if (!world.isRemote && electricNetwork != null) {
	    getNetwork().split(this);
	}
    }

    @Override
    public void onLoad() {
	super.onLoad();
	Scheduler.schedule(1, this::refreshNetwork);
    }

    protected abstract void writeCustomPacket(CompoundNBT nbt);

    protected abstract void readCustomPacket(CompoundNBT nbt);

}
