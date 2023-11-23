package electrodynamics.common.tile.machines.mineralcrusher;

import electrodynamics.common.block.subtype.SubtypeMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileMineralCrusherTriple extends TileMineralCrusher {
	public TileMineralCrusherTriple(BlockPos pos, BlockState state) {
		super(SubtypeMachine.mineralcrushertriple, 2, pos, state);
	}
}
