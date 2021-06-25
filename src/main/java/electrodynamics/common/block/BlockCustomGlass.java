package electrodynamics.common.block;

import java.util.Arrays;
import java.util.List;

import electrodynamics.common.block.subtype.SubtypeGlass;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext.Builder;
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
	super(Properties.create(Material.GLASS).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(hardness, resistance)
		.setOpaque(BlockCustomGlass::isntSolid).notSolid());
    }

    public BlockCustomGlass(SubtypeGlass glass) {
	super(Properties.create(Material.GLASS).setRequiresTool().harvestTool(ToolType.PICKAXE)
		.hardnessAndResistance(glass.hardness, glass.resistance).setOpaque(BlockCustomGlass::isntSolid).notSolid());
    }

    private static boolean isntSolid(BlockState state, IBlockReader reader, BlockPos pos) {
	return false;
    }

    @Override
    @Deprecated
    public List<ItemStack> getDrops(BlockState state, Builder builder) {
	return Arrays.asList(new ItemStack(this));
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
	return adjacentBlockState.isIn(this) || super.isSideInvisible(state, adjacentBlockState, side);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    @Deprecated
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
	return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
	return true;
    }

}
