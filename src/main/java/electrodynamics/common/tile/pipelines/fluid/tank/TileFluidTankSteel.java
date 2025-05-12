package electrodynamics.common.tile.pipelines.fluid.tank;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsTiles;

public class TileFluidTankSteel extends GenericTileFluidTank {

	public static final int CAPACITY = 8000;

	public TileFluidTankSteel() {
		super(ElectrodynamicsTiles.TILE_TANKSTEEL.get(), CAPACITY, SubtypeMachine.tanksteel);
	}

}
