package electrodynamics.common.tile;

import electrodynamics.api.tile.ITickableTileBase;
import electrodynamics.api.tile.electric.IElectricTile;
import electrodynamics.api.tile.electric.IPowerProvider;
import electrodynamics.api.tile.electric.IPowerReceiver;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.mod.DeferredRegisters;
import electrodynamics.common.tile.generic.GenericTileBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class TileTransformer extends GenericTileBase implements ITickableTileBase, IPowerProvider, IPowerReceiver, IElectricTile {

	public TileTransformer() {
		super(DeferredRegisters.TILE_TRANSFORMER.get());
	}

	@Override
	public TransferPack extractPower(TransferPack transfer, Direction from, boolean debug) {
		return TransferPack.EMPTY; // TODO: Add support for other mods to extract directly themselves
	}

	@Override
	public TransferPack receivePower(TransferPack transfer, Direction dir, boolean debug) {
		if (dir != getBlockState().get(BlockMachine.FACING).getOpposite()) {
			return TransferPack.EMPTY;
		} else {
			if (!canConnectElectrically(dir)) {
				return TransferPack.EMPTY;
			}
			Direction facing = getBlockState().get(BlockMachine.FACING).getOpposite();
			TileEntity facingTile = world.getTileEntity(new BlockPos(pos).offset(facing.getOpposite()));
			if (facingTile instanceof IPowerReceiver) {
				boolean shouldUpgrade = ((BlockMachine) getBlockState().getBlock()).machine == SubtypeMachine.upgradetransformer;
				boolean shouldTransformEnergy = transfer.getVoltage() != 480.0 && transfer.getVoltage() != 60.0;
				return ((IPowerReceiver) facingTile).receivePower(TransferPack.joulesVoltage(shouldTransformEnergy ? transfer.getJoules() * (shouldUpgrade ? 0.45 : 1.9) : transfer.getJoules(),
						MathHelper.clamp(transfer.getVoltage() * (shouldUpgrade ? 2 : 0.5), 60.0, 480.0)), facing, false);
			}
			return TransferPack.EMPTY;
		}
	}

	@Override
	public boolean canConnectElectrically(Direction direction) {
		return getBlockState().get(BlockMachine.FACING).getOpposite() == direction || getBlockState().get(BlockMachine.FACING) == direction;
	}

	@Override
	public double getVoltage(Direction from) {
		return 120.0;
	}

}
