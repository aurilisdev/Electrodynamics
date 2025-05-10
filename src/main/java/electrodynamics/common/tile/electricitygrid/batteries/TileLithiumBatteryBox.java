package electrodynamics.common.tile.electricitygrid.batteries;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsTiles;
import voltaic.registers.VoltaicCapabilities;

public class TileLithiumBatteryBox extends TileBatteryBox {

	public TileLithiumBatteryBox() {
		super(ElectrodynamicsTiles.TILE_LITHIUMBATTERYBOX.get(), SubtypeMachine.lithiumbatterybox, 240, 359.0 * (2 * VoltaicCapabilities.DEFAULT_VOLTAGE) / 20.0, 40000000);
	}

}