package electrodynamics.common.tile.electricitygrid;

import javax.annotation.Nullable;

import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.api.network.cable.type.IWire;
import voltaic.api.network.cable.type.IWire.IWireColor;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;

public class TileWire extends GenericTileWire {

    public SingleProperty<Double> transmit = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.DOUBLE, "transmit", 0.0));

    private @Nullable IWire wire = null;
    private @Nullable IWire.IWireColor color = null;

    public TileWire(BlockPos pos, BlockState state) {
	super(ElectrodynamicsTiles.TILE_WIRE.get(), pos, state);
    }

    public TileWire(BlockEntityType<?> tileEntityType, BlockPos pos, BlockState state) {
	super(tileEntityType, pos, state);
    }

    @Override
    public IWire getCableType() {
	IWire pWire = wire;
	if (pWire == null) {
	    pWire = wire = ((BlockWire) getBlockState().getBlock()).wire;
	}
	return pWire;
    }

    @Override
    public IWire.IWireColor getWireColor() {
	IWireColor pColor = color;
	if (pColor == null) {
	    pColor = color = ((BlockWire) getBlockState().getBlock()).wire.getWireColor();
	}
	return pColor;
    }

}
