package electrodynamics.common.tile.machines.charger;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsTiles;

public class TileChargerLV extends GenericTileCharger {

	public TileChargerLV() {
		super(ElectrodynamicsTiles.TILE_CHARGERLV.get(), 1, SubtypeMachine.chargerlv);
	}

}
