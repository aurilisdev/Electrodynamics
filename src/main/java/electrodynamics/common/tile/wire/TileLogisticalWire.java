package electrodynamics.common.tile.wire;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.tile.ITickableTileBase;

public class TileLogisticalWire extends TileWire implements ITickableTileBase {
    private int _ticks;
    public boolean isPowered = false;

    public TileLogisticalWire() {
	super(DeferredRegisters.TILE_LOGISTICALWIRE.get());
    }

    @Override
    public void tickServer() {
	_ticks++;
	if (_ticks % 10 == 0) {
	    boolean shouldPower = getNetwork().getTransmittedLastTick() > 0;
	    if (shouldPower != isPowered) {
		isPowered = shouldPower;
		world.notifyNeighborsOfStateChange(pos, getBlockState().getBlock());
	    }
	}
    }
}
