package electrodynamics.common.tile.machines.charger;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsBlockTypes;

public class TileChargerMV extends GenericTileCharger {

	public TileChargerMV() {
		super(ElectrodynamicsBlockTypes.TILE_CHARGERMV.get(), 2, SubtypeMachine.chargermv);
	}

}