package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.tile.electric.IElectricTile;
import electrodynamics.api.tile.electric.IPowerProvider;
import electrodynamics.api.tile.electric.IPowerReceiver;
import electrodynamics.api.utilities.CachedTileOutput;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.block.BlockGenericMachine;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.tile.generic.GenericTileBase;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class TileTransformer extends GenericTileBase implements IPowerProvider, IPowerReceiver, IElectricTile {

	private boolean locked = false;
	private CachedTileOutput output;

	public TileTransformer() {
		super(DeferredRegisters.TILE_TRANSFORMER.get());
	}

	@Override
	public TransferPack extractPower(TransferPack transfer, Direction from, boolean debug) {
		return TransferPack.EMPTY;
	}

	@Override
	public TransferPack receivePower(TransferPack transfer, Direction dir, boolean debug) {
		if (locked || dir != getBlockState().get(BlockGenericMachine.FACING).getOpposite()) {
			return TransferPack.EMPTY;
		} else {
			if (!canConnectElectrically(dir)) {
				return TransferPack.EMPTY;
			}
			if (output == null) {
				output = new CachedTileOutput(world, new BlockPos(pos).offset(getFacing()));
			}
			if (output.get() instanceof IPowerReceiver) {
				boolean shouldUpgrade = ((BlockMachine) getBlockState().getBlock()).machine == SubtypeMachine.upgradetransformer;
				double resultVoltage = MathHelper.clamp(transfer.getVoltage() * (shouldUpgrade ? 2 : 0.5), 15.0, 1920.0);
				locked = true;
				TransferPack returner = debug ? TransferPack.ampsVoltage(1, 1)
						: output.<IPowerReceiver>get().receivePower(TransferPack.joulesVoltage(transfer.getJoules() * 0.95, resultVoltage), getFacing().getOpposite(), debug);
				locked = false;
				if (returner.getJoules() > 0) {
					returner = TransferPack.joulesVoltage(returner.getJoules() + transfer.getJoules() * 0.05, resultVoltage);
				}
				return returner;
			}
			return TransferPack.EMPTY;
		}
	}

	@Override
	public boolean canConnectElectrically(Direction direction) {
		return getBlockState().get(BlockGenericMachine.FACING).getOpposite() == direction || getBlockState().get(BlockGenericMachine.FACING) == direction;
	}

	@Override
	public double getVoltage(Direction from) {
		return 120.0;
	}

}
