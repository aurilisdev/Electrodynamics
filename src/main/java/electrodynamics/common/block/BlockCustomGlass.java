package electrodynamics.common.block;

import electrodynamics.common.block.subtype.SubtypeGlass;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

public class BlockCustomGlass extends Block {

	public BlockCustomGlass(float hardness, float resistance) {
		super(Properties.of(Material.GLASS).requiresCorrectToolForDrops().strength(hardness, resistance).isRedstoneConductor((x, y, z) -> false).noOcclusion().harvestTool(ToolType.PICKAXE));
	}

	public BlockCustomGlass(SubtypeGlass glass) {
		super(Properties.of(Material.GLASS).requiresCorrectToolForDrops().strength(glass.hardness, glass.resistance).isRedstoneConductor((x, y, z) -> false).noOcclusion().harvestTool(ToolType.PICKAXE));
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.empty();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
		return adjacentBlockState.is(this) || super.skipRendering(state, adjacentBlockState, side);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public float getShadeBrightness(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return 1.0F;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return true;
	}

}