package electrodynamics.common.tile.network;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.tile.components.type.ComponentTickable;

public class TileLogisticalWire extends TileWire {
    public boolean isPowered = false;

    public TileLogisticalWire() {
	super(DeferredRegisters.TILE_LOGISTICALWIRE.get());
	addComponent(new ComponentTickable().tickServer(this::tickServer));
    }

    protected void tickServer(ComponentTickable component) {
	if (component.getTicks() % 10 == 0) {
	    boolean shouldPower = getNetwork().getTransmittedLastTick() > 0;
	    if (shouldPower != isPowered) {
		isPowered = shouldPower;
		world.notifyNeighborsOfStateChange(pos, getBlockState().getBlock());
	    }
	}
    }
}
