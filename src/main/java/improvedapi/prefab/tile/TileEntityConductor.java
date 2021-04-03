package improvedapi.prefab.tile;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.tile.GenericTile;
import improvedapi.core.capability.CapabilityConductor;
import improvedapi.core.capability.CapabilityConnector;
import improvedapi.core.capability.CapabilityNetworkProvider;
import improvedapi.core.electricity.ElectricityNetwork;
import improvedapi.core.electricity.IElectricityNetwork;
import improvedapi.core.tile.IConductor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

/**
 * This tile entity pre-fabricated for all conductors.
 */
public abstract class TileEntityConductor extends GenericTile implements IConductor {

    private IElectricityNetwork network;

    protected TileEntityConductor(TileEntityType<?> tileEntityTypeIn) {
	super(tileEntityTypeIn);
    }

    @Override
    public void remove() {
	if (!this.world.isRemote) {
	    this.getNetwork().split(this);
	}
	super.remove();
    }

    @Override
    public IElectricityNetwork getNetwork() {
	if (this.network == null) {
	    this.setNetwork(new ElectricityNetwork(this));
	}

	return this.network;
    }

    @Override
    public void setNetwork(IElectricityNetwork network) {
	this.network = network;
    }

    @Override
    public void refresh() {
	if (!this.world.isRemote) {
	    for (Direction side : Direction.values()) {
		TileEntity tileEntity = world.getTileEntity(pos.offset(side));
		if (tileEntity != null && tileEntity.getClass() == this.getClass()) {
		    tileEntity.getCapability(CapabilityNetworkProvider.INSTANCE).ifPresent(capability -> getNetwork().merge(capability.getNetwork()));
		}
	    }
	}
    }

    @Override
    public TileEntity[] getAdjacentConnections() {
	List<TileEntity> adjecentConnections = new ArrayList<>();

	for (Direction side : Direction.values()) {
	    TileEntity tileEntity = world.getTileEntity(pos.offset(side));

	    if (tileEntity != null) {
		tileEntity.getCapability(CapabilityConnector.INSTANCE).ifPresent(capability -> {
		    if (capability.canConnect(side.getOpposite())) {
			adjecentConnections.add(tileEntity);
		    }
		});
	    }
	}

	return adjecentConnections.toArray(new TileEntity[0]);
    }

    @Override
    public boolean canConnect(Direction direction) {
	return true;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap) {
	if (cap == CapabilityNetworkProvider.INSTANCE || cap == CapabilityConductor.INSTANCE || cap == CapabilityConnector.INSTANCE) {
	    return (LazyOptional<T>) LazyOptional.of(() -> this);
	}
	return super.getCapability(cap);
    }

}
