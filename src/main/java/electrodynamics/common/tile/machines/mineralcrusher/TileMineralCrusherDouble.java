package electrodynamics.common.tile.machines.mineralcrusher;

import electrodynamics.common.block.subtype.SubtypeMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileMineralCrusherDouble extends TileMineralCrusher {
	public TileMineralCrusherDouble(BlockPos pos, BlockState state) {
		super(SubtypeMachine.mineralcrusherdouble, 1, pos, state);
	}
}
