package electrodynamics.common.tile.network.gas;

import org.jetbrains.annotations.NotNull;

import electrodynamics.common.block.connect.BlockGasPipe;
import electrodynamics.common.block.subtype.SubtypeGasPipe;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.state.BlockState;

public class TileGasPipe extends GenericTileGasPipe {

	public SubtypeGasPipe pipe = null;
	
	public TileGasPipe(BlockPos worldPos, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_GAS_PIPE.get(), worldPos, blockState);
	}

	@Override
	public SubtypeGasPipe getPipeType() {
		if (pipe == null) {
			pipe = ((BlockGasPipe) getBlockState().getBlock()).pipe;
		}
		return pipe;
	}

	@Override
	public void destroyViolently() {
		if(level.isClientSide) {
			return;
		}
		level.explode(null, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), 2.0F, BlockInteraction.DESTROY);
	}
	
	@Override
	public void saveAdditional(@NotNull CompoundTag compound) {
		compound.putInt("ord", getPipeType().ordinal());
		super.saveAdditional(compound);
	}

	@Override
	public void load(@NotNull CompoundTag compound) {
		super.load(compound);
		pipe = SubtypeGasPipe.values()[compound.getInt("ord")];
	}

}
