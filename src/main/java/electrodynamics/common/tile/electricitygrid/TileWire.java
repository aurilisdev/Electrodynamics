package electrodynamics.common.tile.electricitygrid;

import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.api.network.cable.type.IWire;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;

public class TileWire extends GenericTileWire {

	public SingleProperty<Double> transmit = property(new SingleProperty<>(PropertyTypes.DOUBLE, "transmit", 0.0));

	public IWire wire = null;
	public IWire.IWireColor color = null;

	public TileWire(BlockPos pos, BlockState state) {
		super(ElectrodynamicsTiles.TILE_WIRE.get(), pos, state);
	}

	public TileWire(BlockEntityType<TileLogisticalWire> tileEntityType, BlockPos pos, BlockState state) {
		super(tileEntityType, pos, state);
	}

	@Override
	public IWire getCableType() {
		if (wire == null) {
			wire = ((BlockWire) getBlockState().getBlock()).wire;
		}
		return wire;
	}

	@Override
	public IWire.IWireColor getWireColor() {
		if (color == null) {
			color = ((BlockWire) getBlockState().getBlock()).wire.getWireColor();
		}
		return color;
	}

}
