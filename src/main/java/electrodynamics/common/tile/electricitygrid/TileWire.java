package electrodynamics.common.tile.electricitygrid;

import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.tileentity.TileEntityType;
import voltaic.api.network.cable.type.IWire;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;

public class TileWire extends GenericTileWire {

	public SingleProperty<Double> transmit = property(new SingleProperty<>(PropertyTypes.DOUBLE, "transmit", 0.0));

	public IWire wire = null;
	public IWire.IWireColor color = null;

	public TileWire() {
		super(ElectrodynamicsTiles.TILE_WIRE.get());
	}

	public TileWire(TileEntityType<?> tileEntityType) {
		super(tileEntityType);
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
