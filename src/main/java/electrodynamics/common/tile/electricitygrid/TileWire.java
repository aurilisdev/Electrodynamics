package electrodynamics.common.tile.electricitygrid;

import org.jetbrains.annotations.NotNull;

import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.block.subtype.SubtypeWire.WireColor;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileWire extends GenericTileWire {

	public Property<Double> transmit = property(new Property<>(PropertyType.Double, "transmit", 0.0));

	public SubtypeWire wire = null;
	public WireColor color = null;

	public TileWire(BlockPos pos, BlockState state) {
		super(ElectrodynamicsBlockTypes.TILE_WIRE.get(), pos, state);
		addComponent(new ComponentTickable(this));
	}

	public TileWire(BlockEntityType<TileLogisticalWire> tileEntityType, BlockPos pos, BlockState state) {
		super(tileEntityType, pos, state);
	}

	@Override
	public SubtypeWire getWireType() {
		if (wire == null) {
			wire = ((BlockWire) getBlockState().getBlock()).wire;
		}
		return wire;
	}

	@Override
	public WireColor getWireColor() {
		if (color == null) {
			color = ((BlockWire) getBlockState().getBlock()).wire.color;
		}
		return color;
	}

	@Override
	public void saveAdditional(@NotNull CompoundTag compound) {
		compound.putInt("ord", getWireType().ordinal());
		compound.putInt("color", getWireColor().ordinal());
		super.saveAdditional(compound);
	}

	@Override
	public void load(@NotNull CompoundTag compound) {
		super.load(compound);
		wire = SubtypeWire.values()[compound.getInt("ord")];
		color = WireColor.values()[compound.getInt("color")];
	}

}
