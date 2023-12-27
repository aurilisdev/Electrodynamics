package electrodynamics.common.tile.electricitygrid;

import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

public class TileWire extends GenericTileWire {
	public Property<Double> transmit = property(new Property<>(PropertyType.Double, "transmit", 0.0));

	public TileWire() {
		super(ElectrodynamicsBlockTypes.TILE_WIRE.get());
	}

	public TileWire(TileEntityType<TileLogisticalWire> tileEntityType) {
		super(tileEntityType);
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
	public CompoundNBT save(CompoundNBT compound) {
		compound.putInt("ord", getWireType().ordinal());
		return super.save(compound);
	}

	@Override
	public void load(BlockState state, CompoundNBT compound) {
		super.load(state, compound);
		wire = SubtypeWire.values()[compound.getInt("ord")];
	}
}