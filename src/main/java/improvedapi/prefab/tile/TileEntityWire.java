package improvedapi.prefab.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.block.connect.BlockWire;
import net.minecraft.tileentity.TileEntityType;

public class TileEntityWire extends TileEntityConductor {
    public TileEntityWire(TileEntityType<?> tileEntityTypeIn) {
	super(tileEntityTypeIn);
    }

    public TileEntityWire() {
	this(DeferredRegisters.TILE_WIRE.get());
    }

    @Override
    public double getResistance() {
	return ((BlockWire) getBlockState().getBlock()).wire.resistance;
    }

    @Override
    public double getCurrentCapacity() {
	return ((BlockWire) getBlockState().getBlock()).wire.capacity;
    }

}
