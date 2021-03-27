package electrodynamics.common.fluid;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.fluids.FluidAttributes;

public class FluidSulfuricAcid extends Fluid {

    @Override
    public Item getFilledBucket() {
	return Items.AIR;
    }

    @Override
    protected boolean canDisplace(FluidState fluidState, IBlockReader blockReader, BlockPos pos, Fluid fluid, Direction direction) {
	return false;
    }

    @Override
    protected Vector3d getFlow(IBlockReader blockReader, BlockPos pos, FluidState fluidState) {
	return Vector3d.ZERO;
    }

    @Override
    public int getTickRate(IWorldReader p_205569_1_) {
	return 0;
    }

    @Override
    protected float getExplosionResistance() {
	return 0;
    }

    @Override
    public float getActualHeight(FluidState p_215662_1_, IBlockReader p_215662_2_, BlockPos p_215662_3_) {
	return 0;
    }

    @Override
    public float getHeight(FluidState p_223407_1_) {
	return 0;
    }

    @Override
    protected BlockState getBlockState(FluidState state) {
	return Blocks.AIR.getDefaultState();
    }

    @Override
    public boolean isSource(FluidState state) {
	return false;
    }

    @Override
    public int getLevel(FluidState state) {
	return 0;
    }

    @Override
    protected FluidAttributes createAttributes() {

	return FluidAttributes.builder(null, null).translationKey("block.minecraft.air").color(0).density(0).temperature(0).luminosity(0).viscosity(0)
		.build(this);
    }

    @Override
    public VoxelShape func_215664_b(FluidState p_215664_1_, IBlockReader p_215664_2_, BlockPos p_215664_3_) {
	return VoxelShapes.fullCube();
    }

}
