package electrodynamics.common.tile.pipelines.fluid.tank;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsTiles;

public class TileFluidTankReinforced extends GenericTileFluidTank {

	public static final int CAPACITY = 32000;

	public TileFluidTankReinforced() {
		super(ElectrodynamicsTiles.TILE_TANKREINFORCED.get(), CAPACITY, SubtypeMachine.tankreinforced);
	}
}
