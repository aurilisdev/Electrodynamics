package electrodynamics.common.tile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.tile.electric.CapabilityElectrodynamic;
import electrodynamics.api.tile.electric.IElectrodynamic;
import electrodynamics.api.utilities.CachedTileOutput;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.generic.GenericTileBase;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class TileTransformer extends GenericTileBase implements IElectrodynamic {
    private boolean locked = false;
    private CachedTileOutput output;
    public double lastTransfer = 0;

    public TileTransformer() {
	super(DeferredRegisters.TILE_TRANSFORMER.get());
    }

    @Override
    public TransferPack receivePower(TransferPack transfer, boolean debug) {
	Direction facing = getFacing();
	if (locked || lastDir != facing.getOpposite()) {
	    return TransferPack.EMPTY;
	}
	if (output == null) {
	    output = new CachedTileOutput(world, new BlockPos(pos).offset(facing));
	}
	boolean shouldUpgrade = ((BlockMachine) getBlockState()
		.getBlock()).machine == SubtypeMachine.upgradetransformer;
	double resultVoltage = MathHelper.clamp(transfer.getVoltage() * (shouldUpgrade ? 2 : 0.5), 15.0, 1920.0);
	locked = true;
	TransferPack returner = debug ? TransferPack.ampsVoltage(1, 1)
		: ElectricityUtilities.receivePower(output.get(), lastDir, TransferPack
			.joulesVoltage(transfer.getJoules() * Constants.TRANSFORMER_EFFICIENCY, resultVoltage), debug);
	locked = false;
	if (returner.getJoules() > 0) {
	    returner = TransferPack.joulesVoltage(returner.getJoules() + transfer.getJoules() * 0.05, resultVoltage);
	}
	lastTransfer = returner.getJoules();
	return returner;
    }

    private Direction lastDir;

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
	Direction face = getFacing();
	if (capability == CapabilityElectrodynamic.ELECTRODYNAMIC && (face == facing || face.getOpposite() == facing)) {
	    lastDir = facing;
	    return (LazyOptional<T>) LazyOptional.of(() -> this);
	}
	return super.getCapability(capability, facing);
    }

    @Override
    public double getJoulesStored() {
	return 0;
    }

    @Override
    public double getMaxJoulesStored() {
	return 0;
    }

}
