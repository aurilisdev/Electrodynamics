package electrodynamics.common.tile.machines.charger;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsTiles;

public class TileChargerHV extends GenericTileCharger {

	public TileChargerHV() {
		super(ElectrodynamicsTiles.TILE_CHARGERHV.get(), 4, SubtypeMachine.chargerhv);
	}

}
