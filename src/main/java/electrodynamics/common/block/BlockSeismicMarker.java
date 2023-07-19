package electrodynamics.common.block;

import electrodynamics.common.tile.quarry.TileSeismicMarker;
import electrodynamics.prefab.block.GenericMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockSeismicMarker extends GenericMachineBlock {

	private static final VoxelShape AABB = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 12.0D, 12.0D);

	public BlockSeismicMarker() {
		super(TileSeismicMarker::new);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return AABB;
	}

	@Override
	public boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
		return true;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
		return canSupportCenter(reader, pos.below(), Direction.UP);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction dir, BlockState other, LevelAccessor world, BlockPos pos, BlockPos otherpos) {
		return dir == Direction.DOWN && !canSurvive(state, world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, dir, other, world, pos, otherpos);
	}

}
