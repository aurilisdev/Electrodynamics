package electrodynamics.common.tile.wire;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.network.conductor.IConductor;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.block.wire.BlockWire;
import electrodynamics.common.tile.generic.GenericTileWire;
import net.minecraft.tileentity.TileEntityType;

public class TileWire extends GenericTileWire implements IConductor {

	public TileWire() {
		super(DeferredRegisters.TILE_WIRE.get());
	}

	public TileWire(TileEntityType<?> tile) {
		super(tile);
	}

	@Override
	public SubtypeWire getWireType() {
		return ((BlockWire) getBlockState().getBlock()).wire;
	}
}
