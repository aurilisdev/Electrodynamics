package electrodynamics.common.tile.tanks.fluid;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileFluidTankHSLA extends GenericTileFluidTank {

	public static final int CAPACITY = 128000;

	public TileFluidTankHSLA(BlockPos pos, BlockState state) {
		super(ElectrodynamicsBlockTypes.TILE_TANKHSLA.get(), CAPACITY, SubtypeMachine.tankhsla, pos, state);
	}

}
