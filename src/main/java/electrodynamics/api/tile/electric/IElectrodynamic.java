package electrodynamics.api.tile.electric;

import electrodynamics.api.utilities.TransferPack;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;

public interface IElectrodynamic {
    /**
     * Just please use the receivePower
     * 
     * @param joules
     */
    @Deprecated
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
	    if (transfer.getVoltage() == getVoltage()) {
		setJoulesStored(getJoulesStored() + received);
	    }
	    if (transfer.getVoltage() > getVoltage()) {
		overVoltage(transfer);
		return TransferPack.EMPTY;
	    }
	}
	return TransferPack.joulesVoltage(received, transfer.getVoltage());
    }

    default void overVoltage(TransferPack transfer) {
	if (this instanceof TileEntity) {
	    TileEntity tile = (TileEntity) this;
	    World world = tile.getWorld();
	    BlockPos pos = tile.getPos();
	    world.setBlockState(pos, Blocks.AIR.getDefaultState());
	    world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(),
		    (float) Math.log10(10 + transfer.getVoltage() / getVoltage()), Mode.DESTROY);
	}
    }
}
