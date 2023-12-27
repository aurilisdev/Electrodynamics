package electrodynamics.common.tile.machines.charger;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsBlockTypes;

public class TileChargerHV extends GenericTileCharger {

	public TileChargerHV() {
		super(ElectrodynamicsBlockTypes.TILE_CHARGERHV.get(), 4, SubtypeMachine.chargerhv);
	}

}