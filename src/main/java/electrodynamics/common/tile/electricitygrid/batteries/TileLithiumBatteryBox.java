package electrodynamics.common.tile.electricitygrid.batteries;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsBlockTypes;

public class TileLithiumBatteryBox extends TileBatteryBox {

	public TileLithiumBatteryBox() {
		super(ElectrodynamicsBlockTypes.TILE_LITHIUMBATTERYBOX.get(), SubtypeMachine.lithiumbatterybox, 240, 359.0 * (2 * ElectrodynamicsCapabilities.DEFAULT_VOLTAGE) / 20.0, 40000000);
	}

}