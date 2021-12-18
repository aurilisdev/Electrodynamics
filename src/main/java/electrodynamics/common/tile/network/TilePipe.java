package electrodynamics.common.tile.network;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.block.connect.BlockPipe;
import electrodynamics.common.block.subtype.SubtypePipe;
import electrodynamics.common.tile.generic.GenericTilePipe;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class TilePipe extends GenericTilePipe {
	public double transmit = 0;

	public TilePipe(BlockPos pos, BlockState state) {
		super(DeferredRegisters.TILE_PIPE.get(), pos, state);
	}

	public SubtypePipe pipe = null;

	@Override
	public SubtypePipe getPipeType() {
		if (pipe == null) {
			pipe = ((BlockPipe) getBlockState().getBlock()).pipe;
		}
		return pipe;
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		compound.putInt("ord", getPipeType().ordinal());
		super.saveAdditional(compound);
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		pipe = SubtypePipe.values()[compound.getInt("ord")];
	}

	@Override
	protected void writeCustomPacket(CompoundTag nbt) {
		nbt.putDouble("transmit", transmit);
	}

	@Override
	protected void readCustomPacket(CompoundTag nbt) {
		transmit = nbt.getDouble("transmit");
	}
}
