package electrodynamics.common.tile.machines.charger;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsBlockTypes;

public class TileChargerLV extends GenericTileCharger {

	public TileChargerLV() {
		super(ElectrodynamicsBlockTypes.TILE_CHARGERLV.get(), 1, SubtypeMachine.chargerlv);
	}

}