package electrodynamics.common.tile.network;

import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.tile.generic.GenericTileWire;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileWire extends GenericTileWire {
	public Property<Double> transmit = property(new Property<Double>(PropertyType.Double, "transmit", 0.0));

	public TileWire(BlockPos pos, BlockState state) {
		super(ElectrodynamicsBlockTypes.TILE_WIRE.get(), pos, state);
	}

	public TileWire(BlockEntityType<TileLogisticalWire> tileEntityType, BlockPos pos, BlockState state) {
		super(tileEntityType, pos, state);
	}

	public SubtypeWire wire = null;

	@Override
	public SubtypeWire getWireType() {
		if (wire == null) {
			wire = ((BlockWire) getBlockState().getBlock()).wire;
		}
		return wire;
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		compound.putInt("ord", getWireType().ordinal());
		super.saveAdditional(compound);
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		wire = SubtypeWire.values()[compound.getInt("ord")];
	}
}
