package electrodynamics.common.block;

import java.util.Arrays;
import java.util.List;

import electrodynamics.common.block.subtype.SubtypeGlass;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockCustomGlass extends Block {

    public BlockCustomGlass(float hardness, float resistance) {
	super(Properties.of(Material.GLASS).requiresCorrectToolForDrops().strength(hardness, resistance).isRedstoneConductor((x, y, z) -> false)
		.noOcclusion());
    }

    public BlockCustomGlass(SubtypeGlass glass) {
	super(Properties
		.of(Material.GLASS).requiresCorrectToolForDrops().strength(glass.hardness, glass.resistance)
		.isRedstoneConductor((x, y, z) -> false).noOcclusion());
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, Builder builder) {
	return Arrays.asList(new ItemStack(this));
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
	return Shapes.empty();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
	return adjacentBlockState.is(this) || super.skipRendering(state, adjacentBlockState, side);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
	return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
	return true;
    }

}
