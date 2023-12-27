package electrodynamics.common.tile.pipelines.tank.fluid;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsBlockTypes;

public class TileFluidTankHSLA extends GenericTileFluidTank {

	public static final int CAPACITY = 128000;

	public TileFluidTankHSLA() {
		super(ElectrodynamicsBlockTypes.TILE_TANKHSLA.get(), CAPACITY, SubtypeMachine.tankhsla);
	}

}