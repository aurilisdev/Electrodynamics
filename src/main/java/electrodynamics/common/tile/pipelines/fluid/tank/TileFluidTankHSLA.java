package electrodynamics.common.tile.pipelines.fluid.tank;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsTiles;

public class TileFluidTankHSLA extends GenericTileFluidTank {

	public static final int CAPACITY = 128000;

	public TileFluidTankHSLA() {
		super(ElectrodynamicsTiles.TILE_TANKHSLA.get(), CAPACITY, SubtypeMachine.tankhsla);
	}

}
