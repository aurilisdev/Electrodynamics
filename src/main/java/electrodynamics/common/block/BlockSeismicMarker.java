package electrodynamics.common.block;

import java.util.stream.Stream;

import electrodynamics.common.tile.machines.quarry.TileSeismicMarker;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import voltaic.common.block.voxelshapes.VoxelShapeProvider;
import voltaic.prefab.block.GenericMachineBlock;

public class BlockSeismicMarker extends GenericMachineBlock {

	private static final VoxelShapeProvider AxisAlignedBB = VoxelShapeProvider.createOmni(
            //
            Stream.of(
                    //
                    Block.box(4, 0, 4, 12, 1, 12),
                    //
                    Block.box(6, 1, 6, 10, 8, 10),
                    //
                    Block.box(6, 9, 6, 7, 11, 7),
                    //
                    Block.box(9, 9, 6, 10, 11, 7),
                    //
                    Block.box(9, 9, 9, 10, 11, 10),
                    //
                    Block.box(6, 9, 9, 7, 11, 10),
                    //
                    Block.box(5, 11, 5, 11, 12, 11),
                    //
                    Block.box(5, 8, 5, 11, 9, 11),
                    //
                    Block.box(7, 9, 7, 9, 11, 9)
                    //
            ).reduce(VoxelShapes::or).get());

	public BlockSeismicMarker() {
		super(TileSeismicMarker::new, AxisAlignedBB);
	}

	@Override
	public boolean canConnectRedstone(BlockState state, IBlockReader world, BlockPos pos, Direction direction) {
		return true;
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader reader, BlockPos pos) {
		return canSupportCenter(reader, pos.below(), Direction.UP);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction dir, BlockState other, IWorld world, BlockPos pos, BlockPos otherpos) {
		return dir == Direction.DOWN && !canSurvive(state, world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, dir, other, world, pos, otherpos);
	}

}
