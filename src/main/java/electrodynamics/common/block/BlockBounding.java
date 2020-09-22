package electrodynamics.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class BlockBounding extends Block {

	public BlockBounding() {
		super(Block.Properties.create(Material.IRON).hardnessAndResistance(3.5F, 8F).setRequiresTool().variableOpacity());
	}

	@Override
	public PushReaction getPushReaction(BlockState state) {
		return PushReaction.BLOCK;
	}

	@Deprecated
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		BlockPos mainPos = getMainBlockPos(worldIn, pos);
		if (mainPos == null) {
			return ActionResultType.FAIL;
		}
		BlockState mainState = worldIn.getBlockState(mainPos);
		return mainState.getBlock().onBlockActivated(mainState, worldIn, mainPos, player, handIn, hit);
	}

	public BlockPos getMainBlockPos(World world, BlockPos pos) {
		return pos;
	}

}
