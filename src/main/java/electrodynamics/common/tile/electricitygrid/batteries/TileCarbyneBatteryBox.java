package electrodynamics.common.tile.electricitygrid.batteries;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsTiles;
import voltaic.registers.VoltaicCapabilities;

public class TileCarbyneBatteryBox extends TileBatteryBox {

	public TileCarbyneBatteryBox() {
		super(ElectrodynamicsTiles.TILE_CARBYNEBATTERYBOX.get(), SubtypeMachine.carbynebatterybox, 480, 359.0 * (4 * VoltaicCapabilities.DEFAULT_VOLTAGE) / 20.0, 80000000);
	}

}