package electrodynamics.api.tile.electric;

import electrodynamics.api.utilities.TransferPack;
import net.minecraft.util.Direction;

public interface IPowerReceiver {

	TransferPack receivePower(TransferPack transfer, Direction dir, boolean debug);

}
