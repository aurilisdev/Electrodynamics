package electrodynamics.common.tile.network;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.tile.components.ComponentType;
import electrodynamics.api.tile.components.type.ComponentTickable;
import net.minecraft.tileentity.ITickableTileEntity;

public class TileLogisticalWire extends TileWire implements ITickableTileEntity {
    public boolean isPowered = false;

    public TileLogisticalWire() {
	super(DeferredRegisters.TILE_LOGISTICALWIRE.get());
	addComponent(new ComponentTickable().tickServer(this::tickServer));
    }

    /**
     * Copied from {@link #GenericTileTicking.tick}
     */
    @Override
    public void tick() {
	if (hasComponent(ComponentType.Tickable)) {
	    ComponentTickable tickable = getComponent(ComponentType.Tickable);
	    tickable.tickCommon();
	    if (!world.isRemote) {
		tickable.tickServer();
	    } else {
		tickable.tickClient();
	    }
	}
    }

    protected void tickServer(ComponentTickable component) {
	if (component.getTicks() % 10 == 0) {
	    boolean shouldPower = getNetwork().getCurrentTransmission() > 0;
	    if (shouldPower != isPowered) {
		isPowered = shouldPower;
		world.notifyNeighborsOfStateChange(pos, getBlockState().getBlock());
	    }
	}
    }
}
