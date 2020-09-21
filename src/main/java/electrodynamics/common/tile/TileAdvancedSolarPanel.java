package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.utilities.TransferPack;
import net.minecraft.util.Direction;

public class TileAdvancedSolarPanel extends TileSolarPanel {
	public static final TransferPack DEFAULT_OUTPUT_SETTINGS = TransferPack.ampsVoltage(3.5, 240);

	public TileAdvancedSolarPanel() {
		super(DeferredRegisters.TILE_ADVANCEDSOLARPANEL.get());
	}

	@Override
	public TransferPack getOutput() {
		TransferPack def = super.getOutput();
		return TransferPack.ampsVoltage(def.getAmps() * DEFAULT_OUTPUT_SETTINGS.getAmps(), DEFAULT_OUTPUT_SETTINGS.getVoltage());
	}

	@Override
	public double getVoltage(Direction from) {
		return DEFAULT_OUTPUT_SETTINGS.getVoltage();
	}
}
