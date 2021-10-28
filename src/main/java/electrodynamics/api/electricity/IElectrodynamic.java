package electrodynamics.api.electricity;

import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IElectrodynamic {
    default void setJoulesStored(double joules) {
    }

    double getJoulesStored();

    double getMaxJoulesStored();

    default double getVoltage() {
	return CapabilityElectrodynamic.DEFAULT_VOLTAGE;
    }

    default TransferPack extractPower(TransferPack transfer, boolean debug) {
	double taken = Math.min(transfer.getJoules(), getJoulesStored());
	if (!debug && taken > 0) {
	    if (transfer.getVoltage() == getVoltage()) {
		setJoulesStored(getJoulesStored() - taken);
	    }
	    if (transfer.getVoltage() > getVoltage()) {
		overVoltage(transfer);
		return TransferPack.EMPTY;
	    }
	}
	return TransferPack.joulesVoltage(taken, transfer.getVoltage());
    }

    default TransferPack receivePower(TransferPack transfer, boolean debug) {
	double received = Math.min(transfer.getJoules(), getMaxJoulesStored() - getJoulesStored());
	if (!debug && received > 0) {
	    if (transfer.getVoltage() == getVoltage() || getVoltage() == -1) {
		setJoulesStored(getJoulesStored() + received);
	    }
	    if (transfer.getVoltage() > getVoltage() && getVoltage() != -1) {
		overVoltage(transfer);
		return TransferPack.EMPTY;
	    }
	}
	return TransferPack.joulesVoltage(received, transfer.getVoltage());
    }

    default void overVoltage(TransferPack transfer) {
	if (this instanceof BlockEntity) {
	    BlockEntity tile = (BlockEntity) this;
	    Level world = tile.getLevel();
	    BlockPos pos = tile.getBlockPos();
	    world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
	    world.explode(null, pos.getX(), pos.getY(), pos.getZ(), (float) Math.log10(10 + transfer.getVoltage() / getVoltage()),
		    BlockInteraction.DESTROY);
	} else if (this instanceof ComponentElectrodynamic) {
	    ComponentElectrodynamic electro = (ComponentElectrodynamic) this;
	    BlockEntity tile = electro.getHolder();
	    if (tile != null) {
		Level world = tile.getLevel();
		BlockPos pos = tile.getBlockPos();
		world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		world.explode(null, pos.getX(), pos.getY(), pos.getZ(), (float) Math.log10(10 + transfer.getVoltage() / getVoltage()),
			BlockInteraction.DESTROY);
	    }
	}
    }
}
