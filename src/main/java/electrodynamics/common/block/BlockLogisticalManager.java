package electrodynamics.common.block;

import electrodynamics.common.tile.TileLogisticalManager;
import electrodynamics.prefab.block.GenericMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockLogisticalManager extends GenericMachineBlock {

	public BlockLogisticalManager() {
		super(TileLogisticalManager::new);
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		return InteractionResult.FAIL;
	}

	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
		Direction facing = pState.getValue(GenericMachineBlock.FACING);
		switch (facing) {
		case WEST:
			return Block.box(4.0D, 4.0D, 0, 16.0D, 12.0D, 16.0D);
		case EAST:
			return Block.box(0, 4.0D, 0, 12.0D, 12.0D, 16.0D);
		case SOUTH:
			return Block.box(0, 4.0D, 0, 16.0D, 12.0D, 12.0D);
		case NORTH:
			return Block.box(0, 4.0D, 4.0D, 16.0D, 12.0D, 16.0D);
		default:
			return super.getShape(pState, pLevel, pPos, pContext);
		}
	}

}
