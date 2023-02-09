package electrodynamics.api.capability.types.electrodynamic;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface ICapabilityElectrodynamic {
	default void setJoulesStored(double joules) {
	}

	double getJoulesStored();

	double getMaxJoulesStored();

	default double getVoltage() {
		return ElectrodynamicsCapabilities.DEFAULT_VOLTAGE;
	}

	default TransferPack extractPower(TransferPack transfer, boolean debug) {
		double taken = Math.min(transfer.getJoules(), getJoulesStored());
		if (!debug && taken > 0) {
			if (transfer.getVoltage() == getVoltage()) {
				setJoulesStored(getJoulesStored() - taken);
				onChange();
			}
			if (transfer.getVoltage() > getVoltage()) {
				overVoltage(transfer);
				return TransferPack.EMPTY;
			}
		}
		return TransferPack.joulesVoltage(taken, transfer.getVoltage());
	}

	default TransferPack receivePower(TransferPack transfer, boolean debug) {
		double received = Math.max(0, Math.min(transfer.getJoules(), getMaxJoulesStored() - getJoulesStored()));
		if (!debug && received > 0) {
			if (transfer.getVoltage() == getVoltage() || getVoltage() == -1) {
				setJoulesStored(getJoulesStored() + received);
				onChange();
			}
			if (transfer.getVoltage() > getVoltage() && getVoltage() != -1) {
				overVoltage(transfer);
				return TransferPack.EMPTY;
			}
		}
		return TransferPack.joulesVoltage(received, transfer.getVoltage());
	}

	default void overVoltage(TransferPack transfer) {
		if (this instanceof BlockEntity tile) {
			Level world = tile.getLevel();
			BlockPos pos = tile.getBlockPos();
			world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			world.explode(null, pos.getX(), pos.getY(), pos.getZ(), (float) Math.log10(10 + transfer.getVoltage() / getVoltage()), BlockInteraction.DESTROY);
		} else if (this instanceof ComponentElectrodynamic electro) {
			BlockEntity tile = electro.getHolder();
			if (tile != null) {
				Level world = tile.getLevel();
				BlockPos pos = tile.getBlockPos();
				world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
				world.explode(null, pos.getX(), pos.getY(), pos.getZ(), (float) Math.log10(10 + transfer.getVoltage() / getVoltage()), BlockInteraction.DESTROY);
			}
		}
	}
	
	void onChange();
	
}
