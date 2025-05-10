package electrodynamics.common.tile.machines.charger;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsTiles;

public class TileChargerMV extends GenericTileCharger {

	public TileChargerMV() {
		super(ElectrodynamicsTiles.TILE_CHARGERMV.get(), 2, SubtypeMachine.chargermv);
	}

}
