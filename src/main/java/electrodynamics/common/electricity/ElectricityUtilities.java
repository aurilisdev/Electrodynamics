package electrodynamics.common.electricity;

import electrodynamics.api.network.conductor.IConductor;
import electrodynamics.api.tile.electric.IPowerReceiver;
import electrodynamics.api.utilities.TransferPack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;

public class ElectricityUtilities {

	public static boolean isElectricReceiver(TileEntity acceptor) {
		return acceptor instanceof IPowerReceiver;
	}

	public static boolean isConductor(TileEntity acceptor) {
		return acceptor instanceof IConductor;
	}

	public static TransferPack receivePower(TileEntity receiver, Direction direction, TransferPack transfer, boolean debug) {
		return !isElectricReceiver(receiver) ? TransferPack.EMPTY : ((IPowerReceiver) receiver).receivePower(transfer, direction, debug);
	}

	public static boolean canInputPower(TileEntity acceptor, Direction direction) {
		return receivePower(acceptor, direction, TransferPack.joulesVoltage(Integer.MAX_VALUE, 1), true).getJoules() > 0;
	}

}
