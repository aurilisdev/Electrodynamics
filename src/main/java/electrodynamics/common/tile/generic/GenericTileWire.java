package electrodynamics.common.tile.generic;

import java.util.ArrayList;
import java.util.HashSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Sets;

import electrodynamics.api.network.conductor.IConductor;
import electrodynamics.api.networks.AbstractNetwork;
import electrodynamics.api.tile.electric.CapabilityElectrodynamic;
import electrodynamics.api.tile.electric.IElectrodynamic;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.network.ElectricNetwork;
import electrodynamics.common.network.NetworkRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public abstract class GenericTileWire extends GenericTileBase implements IConductor {

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
		    if (debug) {
			return TransferPack.EMPTY;
		    }
		    ArrayList<TileEntity> ignored = new ArrayList<>();
		    ignored.add(world.getTileEntity(new BlockPos(pos).offset(dir)));
		    return getNetwork().emit(transfer, ignored);
		}
	    });
	}
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
	    TileEntity facing = world.getTileEntity(new BlockPos(pos).offset(dir));
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
	    if (connectedNets.size() == 0) {
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
	    ArrayList<ElectricNetwork> foundNetworks = new ArrayList<>();
	    for (Direction dir : Direction.values()) {
		TileEntity facing = world.getTileEntity(new BlockPos(pos).offset(dir));
		if (facing instanceof IConductor && ((IConductor) facing).getNetwork() instanceof ElectricNetwork) {
		    foundNetworks.add((ElectricNetwork) ((IConductor) facing).getNetwork());
		}
	    }
	    if (foundNetworks.size() > 0) {
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

    @Override
    public void removeFromNetwork() {
	if (electricNetwork != null) {
	    electricNetwork.removeFromNetwork(this);
	}
    }

    @Override
    public void fixNetwork() {
	getNetwork().fixMessedUpNetwork(this);
    }

    @Override
    public void destroyViolently() {
	world.setBlockState(pos, Blocks.FIRE.getDefaultState());
    }

    @Override
    public void remove() {
	if (!world.isRemote) {
	    if (electricNetwork != null) {
		getNetwork().split(this);
	    }
	}
	super.remove();
    }

    @Override
    public void onChunkUnloaded() {
	remove();
	NetworkRegistry.pruneEmptyNetworks();
    }

}
