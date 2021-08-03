package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.tile.generic.TileGenericCharger;

public class TileChargerMV extends TileGenericCharger {

    public TileChargerMV() {
	super(DeferredRegisters.TILE_CHARGERMV.get(), 2, "mv");
    }

}
