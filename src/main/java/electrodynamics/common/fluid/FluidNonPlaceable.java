package electrodynamics.common.fluid;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fml.RegistryObject;

public abstract class FluidNonPlaceable extends Fluid{

	private final java.util.function.Supplier<RegistryObject<Item>> itemSupplier;
	private String modID;
	private String fluidName;
	private int color = 0;
	
	public FluidNonPlaceable(java.util.function.Supplier<RegistryObject<Item>> itemSupplier) {
		this.itemSupplier = itemSupplier;
	}
	
	public FluidNonPlaceable(java.util.function.Supplier<RegistryObject<Item>> itemSupplier, String modID, String fluidName) {
		this.itemSupplier = itemSupplier;
		this.modID = modID;
		this.fluidName = fluidName;
	}
	
	public FluidNonPlaceable(java.util.function.Supplier<RegistryObject<Item>> itemSupplier, String modID, String fluidName, int color) {
		this.itemSupplier = itemSupplier;
		this.modID = modID;
		this.fluidName = fluidName;
		this.color = color;
	}
	
	@Override
    public Item getFilledBucket() {
		return itemSupplier.get().get();
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
    	if(color == 0) {
    		return FluidAttributes
    				.builder(new ResourceLocation(modID + ":fluid/" + fluidName), new ResourceLocation(modID + ":fluid/" + fluidName))
    				.translationKey("fluid." + modID + "." + fluidName).build(this);
    	}
		return FluidAttributes
			.builder(new ResourceLocation(modID + ":fluid/" + fluidName), new ResourceLocation(modID + ":fluid/" + fluidName))
			.translationKey("fluid." + modID + "." + fluidName).color(color).build(this);
    }
    
    @Override
    public VoxelShape func_215664_b(FluidState p_215664_1_, IBlockReader p_215664_2_, BlockPos p_215664_3_) {
    	return VoxelShapes.fullCube();
    }
	
}