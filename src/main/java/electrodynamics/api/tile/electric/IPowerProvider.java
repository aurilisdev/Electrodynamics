package electrodynamics.api.tile.electric;

import electrodynamics.api.utilities.TransferPack;
import net.minecraft.util.Direction;

public interface IPowerProvider {

	double getVoltage(Direction from);

	TransferPack extractPower(TransferPack transfer, Direction from, boolean debug);
}
