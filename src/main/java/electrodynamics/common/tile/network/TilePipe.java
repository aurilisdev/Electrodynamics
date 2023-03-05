package electrodynamics.common.tile.network;

import electrodynamics.common.block.connect.BlockPipe;
import electrodynamics.common.block.subtype.SubtypePipe;
import electrodynamics.common.tile.generic.GenericTilePipe;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TilePipe extends GenericTilePipe {
	public Property<Double> transmit = property(new Property<>(PropertyType.Double, "transmit", 0.0));

	public TilePipe(BlockPos pos, BlockState state) {
		super(ElectrodynamicsBlockTypes.TILE_PIPE.get(), pos, state);
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
	public void saveAdditional(@NotNull CompoundTag compound) {
		compound.putInt("ord", getPipeType().ordinal());
		super.saveAdditional(compound);
	}

	@Override
	public void load(@NotNull CompoundTag compound) {
		super.load(compound);
		pipe = SubtypePipe.values()[compound.getInt("ord")];
	}
}
