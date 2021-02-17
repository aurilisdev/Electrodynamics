package electrodynamics.common.electricity;

import electrodynamics.api.network.conductor.IConductor;
import electrodynamics.api.tile.electric.IElectricTile;
import electrodynamics.api.tile.electric.IPowerReceiver;
import electrodynamics.api.utilities.TransferPack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class ElectricityUtilities {

	public static boolean isElectricReceiver(TileEntity tile) {
		if (tile instanceof IPowerReceiver) {
			return true;
		}
		for (Direction dir : Direction.values()) {
			boolean is = isElectricReceiver(tile, dir);
			if (is) {
				return true;
			}
		}
		return false;
	}

	public static boolean isElectricReceiver(TileEntity tile, Direction dir) {
		if (tile instanceof IPowerReceiver || tile instanceof IElectricTile && ((IElectricTile) tile).canConnectElectrically(dir)) {
			return true;
		}
		return isEnergyReceiver(tile, dir);
	}

	public static boolean isEnergyReceiver(TileEntity tile, Direction dir) {
		if (tile != null) {
			if (tile.getCapability(CapabilityEnergy.ENERGY, dir).isPresent()) {
				return true;
			}
		}
		return false;
	}

	public static boolean isConductor(TileEntity acceptor) {
		return acceptor instanceof IConductor;
	}

	public static TransferPack receivePower(TileEntity tile, Direction direction, TransferPack transfer, boolean debug) {
		if (tile instanceof IPowerReceiver) {
			return ((IPowerReceiver) tile).receivePower(transfer, direction, debug);
		} else if (isElectricReceiver(tile)) {
			if (tile != null) {
				LazyOptional<IEnergyStorage> cap = tile.getCapability(CapabilityEnergy.ENERGY, direction);
				if (cap.isPresent()) {
					IEnergyStorage handler = cap.resolve().get();
					return TransferPack.joulesVoltage(handler.receiveEnergy((int) Math.min(Integer.MAX_VALUE, transfer.getJoules()), debug), transfer.getVoltage());
				}
			}
		}
		return TransferPack.EMPTY;

	}

	public static boolean canInputPower(TileEntity tile, Direction direction) {
		if (tile instanceof IPowerReceiver || tile instanceof IElectricTile && ((IElectricTile) tile).canConnectElectrically(direction)) {
			return true;
		} else if (isElectricReceiver(tile)) {
			if (tile != null) {
				LazyOptional<IEnergyStorage> cap = tile.getCapability(CapabilityEnergy.ENERGY, direction);
				if (cap.isPresent()) {
					return true;
				}
			}
		}
		return false;
	}

}
