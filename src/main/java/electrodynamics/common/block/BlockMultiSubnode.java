package electrodynamics.common.block;

import electrodynamics.api.multiblock.child.IMultiblockChildBlock;
import electrodynamics.common.tile.TileMultiSubnode;
import electrodynamics.prefab.block.GenericEntityBlock;
import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

public class BlockMultiSubnode extends GenericEntityBlock implements IMultiblockChildBlock {

	public BlockMultiSubnode() {
		super(Properties.copy(Blocks.GLASS).strength(3.5F).sound(SoundType.METAL).isRedstoneConductor((a, b, c) -> false).noOcclusion().harvestTool(ToolType.PICKAXE));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		TileEntity entity = worldIn.getBlockEntity(pos);
		if (entity instanceof TileMultiSubnode) {
			return ((TileMultiSubnode) entity).getShape();
		}

		return VoxelShapes.block();
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.empty();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
		return true;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public float getShadeBrightness(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return 1.0f;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return true;
	}

	@Override
	public ItemStack getCloneItemStack(IBlockReader level, BlockPos pos, BlockState state) {
		TileEntity entity = level.getBlockEntity(pos);
		if (entity instanceof TileMultiSubnode) {
			TileMultiSubnode subnode = (TileMultiSubnode) entity;
			new ItemStack(level.getBlockState(subnode.parentPos.get().toBlockPos()).getBlock());
		}
		return super.getCloneItemStack(level, pos, state);
	}

	@Override
	public boolean isSignalSource(BlockState state) {
		return true;
	}

	@Override
	public TileEntity newBlockEntity(IBlockReader reader) {
		return new TileMultiSubnode();
	}

	@Override
	public void onRemove(BlockState state, World level, BlockPos pos, BlockState newState, boolean isMoving) {
		TileEntity entity = level.getBlockEntity(pos);
		if (newState.isAir(level, pos) && entity instanceof GenericTile) {
			GenericTile generic = (GenericTile) entity;
			generic.onBlockDestroyed();
		}
		super.onRemove(state, level, pos, newState, isMoving);
	}

	@Override
	public void onPlace(BlockState newState, World level, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onPlace(newState, level, pos, oldState, isMoving);
		TileEntity entity = level.getBlockEntity(pos);
		if (entity instanceof GenericTile) {
			GenericTile generic = (GenericTile) entity;
			generic.onPlace(oldState, isMoving);
		}
	}
}