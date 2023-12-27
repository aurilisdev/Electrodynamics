package electrodynamics.common.tile.pipelines.tank.fluid;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsBlockTypes;

public class TileFluidTankSteel extends GenericTileFluidTank {

	public static final int CAPACITY = 8000;

	public TileFluidTankSteel() {
		super(ElectrodynamicsBlockTypes.TILE_TANKSTEEL.get(), CAPACITY, SubtypeMachine.tanksteel);
	}

}