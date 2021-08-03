package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.tile.generic.TileGenericCharger;

public class TileChargerHV extends TileGenericCharger {

    public TileChargerHV() {
	super(DeferredRegisters.TILE_CHARGERHV.get(), 4, "hv");
    }

}
