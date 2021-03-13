package electrodynamics.common.tile.wire;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.tile.generic.component.type.ComponentTickable;

public class TileLogisticalWire extends TileWire {
    public boolean isPowered = false;

    public TileLogisticalWire() {
	super(DeferredRegisters.TILE_LOGISTICALWIRE.get());
	addComponent(new ComponentTickable().addTickServer(this::tickServer));
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
