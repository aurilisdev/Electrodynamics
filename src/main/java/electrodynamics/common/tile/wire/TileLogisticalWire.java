package electrodynamics.common.tile.wire;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.tile.ITickableTileBase;

public class TileLogisticalWire extends TileWire implements ITickableTileBase {
    public boolean isPowered = false;
    private int ticks;

    public TileLogisticalWire() {
	super(DeferredRegisters.TILE_LOGISTICALWIRE.get());
    }

    @Override
    public void tickServer() {
	ticks++;
	if (ticks % 10 == 0) {
	    boolean shouldPower = getNetwork().getTransmittedLastTick() > 0;
	    if (shouldPower != isPowered) {
		isPowered = shouldPower;
		world.notifyNeighborsOfStateChange(pos, getBlockState().getBlock());
	    }
	}
    }
}
