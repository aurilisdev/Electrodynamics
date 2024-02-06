package electrodynamics.common.block;

import com.mojang.serialization.MapCodec;

import electrodynamics.common.block.subtype.SubtypeGlass;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class BlockCustomGlass extends Block {

    public BlockCustomGlass(float hardness, float resistance) {
        super(Blocks.GLASS.properties().requiresCorrectToolForDrops().strength(hardness, resistance).isRedstoneConductor((x, y, z) -> false).noOcclusion());
    }

    public BlockCustomGlass(SubtypeGlass glass) {
        super(Blocks.GLASS.properties().requiresCorrectToolForDrops().strength(glass.hardness, glass.resistance).isRedstoneConductor((x, y, z) -> false).noOcclusion());
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

    @Override
    protected MapCodec<? extends Block> codec() {
        throw new UnsupportedOperationException("Need to implement CODEC");
    }

}
