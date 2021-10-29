package electrodynamics.common.block;

import electrodynamics.common.multiblock.IMultiblockSubnode;
import electrodynamics.common.tile.TileMultiSubnode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockMultiSubnode extends Block implements IMultiblockSubnode {

    public BlockMultiSubnode() {
	super(BlockBehaviour.Properties.of(Material.GLASS).strength(3.5F).sound(SoundType.METAL).isRedstoneConductor(BlockMultiSubnode::isntSolid)
		.harvestTool(BlockTags.PICKAXE).noOcclusion());
    }

    private static boolean isntSolid(BlockState state, BlockGetter reader, BlockPos pos) {
	return false;
    }

    @Override
    @Deprecated
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
	BlockEntity tile = worldIn.getBlockEntity(pos);
	if (tile instanceof TileMultiSubnode subnode) {
	    return subnode.getShape();
	}
	return Shapes.block();
    }

    @Override
    @Deprecated
    public RenderShape getRenderShape(BlockState state) {
	return RenderShape.INVISIBLE;
    }

    @Override
    @Deprecated
    public VoxelShape getVisualShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
	return Shapes.empty();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    @Deprecated
    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
	return true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    @Deprecated
    public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
	return 1.0f;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
	return true;
    }

    @Override
    @Deprecated
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
	BlockEntity tile = worldIn.getBlockEntity(pos);
	if (tile instanceof TileMultiSubnode subnode) {
	    if (subnode.nodePos != null) {
		subnode.nodePos.getBlock(worldIn).use(subnode.nodePos.getBlockState(worldIn), worldIn, subnode.nodePos.toBlockPos(), player, handIn,
			hit);
	    }
	}
	return InteractionResult.SUCCESS;
    }

    @Override
    @Deprecated
    public boolean isSignalSource(BlockState state) {
	return true;
    }

    @Override
    @Deprecated
    public int getDirectSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
	BlockEntity tile = blockAccess.getBlockEntity(pos);
	if (tile instanceof TileMultiSubnode subnode) {
	    if (subnode.nodePos != null) {
		return subnode.nodePos.getBlock(blockAccess).getDirectSignal(subnode.nodePos.getBlockState(blockAccess), blockAccess,
			subnode.nodePos.toBlockPos(), side);
	    }
	}
	return super.getDirectSignal(blockState, blockAccess, pos, side);
    }

    @Override
    @Deprecated(since = "Default MC")
    public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
	BlockEntity tile = blockAccess.getBlockEntity(pos);
	if (tile instanceof TileMultiSubnode subnode && subnode.nodePos != null) {
	    return subnode.nodePos.getBlock(blockAccess).getSignal(subnode.nodePos.getBlockState(blockAccess), blockAccess,
		    subnode.nodePos.toBlockPos(), side);
	}
	return super.getSignal(blockState, blockAccess, pos, side);
    }

    @Override
    @Deprecated(since = "Default MC")
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
	BlockEntity tile = worldIn.getBlockEntity(pos);
	if (tile instanceof TileMultiSubnode subnode && subnode.nodePos != null) {
	    worldIn.destroyBlock(subnode.nodePos.toBlockPos(), true);
	}
	super.onRemove(state, worldIn, pos, newState, isMoving);
    }

    @Override
    @Deprecated
    public boolean hasTileEntity(BlockState state) {
	return true;
    }

    @Override
    public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
	return new TileMultiSubnode();
    }
}
