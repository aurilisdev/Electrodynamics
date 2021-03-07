package electrodynamics.common.block;

import electrodynamics.common.multiblock.IMultiblockSubnode;
import electrodynamics.common.tile.TileMultiSubnode;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockMultiSubnode extends Block implements IMultiblockSubnode {

    public BlockMultiSubnode() {
	super(AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(3.5F).sound(SoundType.METAL)
		.setOpaque(BlockMultiSubnode::isntSolid).notSolid());
    }

    private static boolean isntSolid(BlockState state, IBlockReader reader, BlockPos pos) {
	return false;
    }

    @Override
    @Deprecated
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
	TileEntity tile = worldIn.getTileEntity(pos);
	if (tile instanceof TileMultiSubnode) {
	    TileMultiSubnode subnode = (TileMultiSubnode) tile;
	    return subnode.getShape();
	}
	return VoxelShapes.fullCube();
    }

    @Override
    @Deprecated
    public BlockRenderType getRenderType(BlockState state) {
	return BlockRenderType.INVISIBLE;
    }

    @Override
    @Deprecated
    public VoxelShape getRayTraceShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
	return VoxelShapes.empty();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    @Deprecated
    public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
	return true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    @Deprecated
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
	return 1.0f;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
	return true;
    }

    @Override
    @Deprecated
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
	    Hand handIn, BlockRayTraceResult hit) {
	TileEntity tile = worldIn.getTileEntity(pos);
	if (tile instanceof TileMultiSubnode) {
	    TileMultiSubnode subnode = (TileMultiSubnode) tile;
	    if (subnode.nodePos != null) {
		worldIn.getBlockState(subnode.nodePos).getBlock().onBlockActivated(
			worldIn.getBlockState(subnode.nodePos), worldIn, subnode.nodePos, player, handIn, hit);
	    }
	}
	return ActionResultType.SUCCESS;
    }

    @Override
    @Deprecated
    public boolean canProvidePower(BlockState state) {
	return true;
    }

    @Override
    @Deprecated
    public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
	TileEntity tile = blockAccess.getTileEntity(pos);
	if (tile instanceof TileMultiSubnode) {
	    TileMultiSubnode subnode = (TileMultiSubnode) tile;
	    if (subnode.nodePos != null) {
		return blockAccess.getBlockState(subnode.nodePos).getBlock()
			.getStrongPower(blockAccess.getBlockState(subnode.nodePos), blockAccess, subnode.nodePos, side);
	    }
	}
	return super.getStrongPower(blockState, blockAccess, pos, side);
    }

    @Override
    @Deprecated
    public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
	TileEntity tile = blockAccess.getTileEntity(pos);
	if (tile instanceof TileMultiSubnode) {
	    TileMultiSubnode subnode = (TileMultiSubnode) tile;
	    if (subnode.nodePos != null) {
		return blockAccess.getBlockState(subnode.nodePos).getBlock()
			.getWeakPower(blockAccess.getBlockState(subnode.nodePos), blockAccess, subnode.nodePos, side);
	    }
	}
	return super.getWeakPower(blockState, blockAccess, pos, side);
    }

    @Override
    @Deprecated
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
	TileEntity tile = worldIn.getTileEntity(pos);
	if (tile instanceof TileMultiSubnode) {
	    TileMultiSubnode subnode = (TileMultiSubnode) tile;
	    if (subnode.nodePos != null) {
		worldIn.destroyBlock(subnode.nodePos, true);
	    }
	}
	super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    @Deprecated
    public boolean hasTileEntity(BlockState state) {
	return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
	return new TileMultiSubnode();
    }
}
