package electrodynamics.common.network;

import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.api.electricity.IElectrodynamic;
import electrodynamics.api.network.conductor.IConductor;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class ElectricityUtilities {

    public static boolean isElectricReceiver(TileEntity tile) {
	for (Direction dir : Direction.values()) {
	    boolean is = isElectricReceiver(tile, dir);
	    if (is) {
		return true;
	    }
	}
	return false;
    }

    public static boolean isElectricReceiver(TileEntity tile, Direction dir) {
	if (tile != null) {
	    if (tile.getCapability(CapabilityElectrodynamic.ELECTRODYNAMIC, dir).isPresent()
		    || tile.getCapability(CapabilityEnergy.ENERGY, dir).isPresent()) {
		return true;
	    }
	}
	return false;
    }

    public static boolean isConductor(TileEntity acceptor) {
	return acceptor instanceof IConductor;
    }

    public static TransferPack receivePower(TileEntity tile, Direction direction, TransferPack transfer, boolean debug) {
	if (isElectricReceiver(tile, direction)) {
	    LazyOptional<IElectrodynamic> cap = tile.getCapability(CapabilityElectrodynamic.ELECTRODYNAMIC, direction);
	    if (cap.isPresent()) {
		IElectrodynamic handler = cap.resolve().get();
		return handler.receivePower(transfer, debug);
	    }
	    LazyOptional<IEnergyStorage> cap2 = tile.getCapability(CapabilityEnergy.ENERGY, direction);
	    if (cap2.isPresent()) {
		IEnergyStorage handler = cap2.resolve().get();
		TransferPack returner = TransferPack
			.joulesVoltage(handler.receiveEnergy((int) Math.min(Integer.MAX_VALUE, transfer.getJoules()), debug), transfer.getVoltage());
		if (transfer.getVoltage() > CapabilityElectrodynamic.DEFAULT_VOLTAGE) {
		    World world = tile.getWorld();
		    BlockPos pos = tile.getPos();
		    world.setBlockState(pos, Blocks.AIR.getDefaultState());
		    world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(),
			    (float) Math.log10(10 + transfer.getVoltage() / CapabilityElectrodynamic.DEFAULT_VOLTAGE), Mode.DESTROY);
		}
		return returner;
	    }
	}
	return TransferPack.EMPTY;

    }

    public static boolean canInputPower(TileEntity tile, Direction direction) {
	return isElectricReceiver(tile, direction);
    }
}
