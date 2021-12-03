package electrodynamics.common.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.registries.RegistryObject;

public abstract class FluidNonPlaceable extends Fluid {

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
    public Item getBucket() {
	return itemSupplier.get().get();
    }

    @Override
    protected boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockReader, BlockPos pos, Fluid fluid, Direction direction) {
	return false;
    }

    @Override
    protected Vec3 getFlow(BlockGetter blockReader, BlockPos pos, FluidState fluidState) {
	return Vec3.ZERO;
    }

    @Override
    public int getTickDelay(LevelReader levelReader) {
	return 0;
    }

    @Override
    protected float getExplosionResistance() {
	return 0;
    }

    @Override
    public float getHeight(FluidState fluidState, BlockGetter blockGetter, BlockPos pos) {
	return 0;
    }

    @Override
    public float getOwnHeight(FluidState state) {
	return 0;
    }

    @Override
    protected BlockState createLegacyBlock(FluidState state) {
	return Blocks.AIR.defaultBlockState();
    }

    @Override
    public boolean isSource(FluidState state) {
	return false;
    }

    @Override
    public int getAmount(FluidState state) {
	return 0;
    }

    @Override
    protected FluidAttributes createAttributes() {
	if (color == 0) {
	    return FluidAttributes.builder(new ResourceLocation(modID + ":fluid/" + fluidName), new ResourceLocation(modID + ":fluid/" + fluidName))
		    .translationKey("fluid." + modID + "." + fluidName).build(this);
	}
	return FluidAttributes.builder(new ResourceLocation(modID + ":fluid/" + fluidName), new ResourceLocation(modID + ":fluid/" + fluidName))
		.translationKey("fluid." + modID + "." + fluidName).color(color).build(this);
    }

    @Override
    public VoxelShape getShape(FluidState p_215664_1_, BlockGetter p_215664_2_, BlockPos p_215664_3_) {
	return Shapes.block();
    }
}
