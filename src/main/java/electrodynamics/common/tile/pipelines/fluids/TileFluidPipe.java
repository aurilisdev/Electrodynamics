package electrodynamics.common.tile.pipelines.fluids;

import electrodynamics.common.block.connect.BlockFluidPipe;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;

public class TileFluidPipe extends GenericTileFluidPipe {
	public Property<Double> transmit = property(new Property<>(PropertyType.Double, "transmit", 0.0));

	public TileFluidPipe() {
		super(ElectrodynamicsBlockTypes.TILE_PIPE.get());
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
	public CompoundNBT save(CompoundNBT compound) {
		compound.putInt("ord", getPipeType().ordinal());
		return super.save(compound);
	}

	@Override
	public void load(BlockState state, CompoundNBT compound) {
		super.load(state, compound);
		pipe = SubtypeFluidPipe.values()[compound.getInt("ord")];
	}
}