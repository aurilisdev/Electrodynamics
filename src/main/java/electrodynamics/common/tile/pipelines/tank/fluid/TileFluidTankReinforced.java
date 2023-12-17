package electrodynamics.common.tile.pipelines.tank.fluid;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsBlockTypes;

public class TileFluidTankReinforced extends GenericTileFluidTank {

	public static final int CAPACITY = 32000;

	public TileFluidTankReinforced() {
		super(ElectrodynamicsBlockTypes.TILE_TANKREINFORCED.get(), CAPACITY, SubtypeMachine.tankreinforced);
	}
}