package electrodynamics.common.tile.network.fluid;

import org.jetbrains.annotations.NotNull;

import electrodynamics.common.block.connect.BlockFluidPipe;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class TileFluidPipe extends GenericTileFluidPipe {
	public Property<Double> transmit = property(new Property<>(PropertyType.Double, "transmit", 0.0));

	public TileFluidPipe(BlockPos pos, BlockState state) {
		super(ElectrodynamicsBlockTypes.TILE_PIPE.get(), pos, state);
	}

	public SubtypeFluidPipe pipe = null;

	@Override
	public SubtypeFluidPipe getPipeType() {
		if (pipe == null) {
			pipe = ((BlockFluidPipe) getBlockState().getBlock()).pipe;
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
		pipe = SubtypeFluidPipe.values()[compound.getInt("ord")];
	}
}
