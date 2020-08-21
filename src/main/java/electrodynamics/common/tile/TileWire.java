package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.network.conductor.IConductor;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.block.wire.BlockWire;
import electrodynamics.common.tile.generic.GenericTileWire;

public class TileWire extends GenericTileWire implements IConductor {

	public TileWire() {
		super(DeferredRegisters.TILE_WIRE.get());
	}

	@Override
	public SubtypeWire getWireType() {
		return ((BlockWire) getBlockState().getBlock()).wire;
	}
}
