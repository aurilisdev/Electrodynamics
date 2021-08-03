package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.tile.generic.TileGenericCharger;

public class TileChargerLV extends TileGenericCharger{
	
	public TileChargerLV() {
		super(DeferredRegisters.TILE_CHARGERLV.get(),1,"lv");
	}

}
