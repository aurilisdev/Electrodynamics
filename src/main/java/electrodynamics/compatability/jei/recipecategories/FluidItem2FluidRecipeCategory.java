package electrodynamics.compatability.jei.recipecategories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public abstract class FluidItem2FluidRecipeCategory extends ElectrodynamicsRecipeCategory<FluidItem2FluidRecipe> {

    private static final int ITEM_INPUT_SLOT = 0;
    private static final int INPUT_FLUID_SLOT = 1;
    private static final int INPUT_FLUID_STACK_SLOT = 2;
    private static final int OUTPUT_FLUID_SLOT = 3;
    private static final int OUTPUT_FLUID_STACK_SLOT = 4;

    private int[] MAJOR_PROCESSING_ARROW_COORDS;
    private int[] MINOR_PROCESSING_ARROW_COORDS;

    private int[] INPUT_ITEM_OFFSET;
    private int[] INPUT_FLUID_BUCKET_OFFSET;
    private int[] INPUT_FLUID_TANK;
    private int[] OUTPUT_FLUID_TANK;
    private int[] OUTPUT_FLUID_STACK_OFFSET;

    private int[] MAJOR_PROCESSING_ARROW_OFFSET;
    private int[] MINOR_PROCESSING_ARROW_OFFSET;

    private int[] OUTPUT_TANK_FLUID_BAR;

    private LoadingCache<Integer, ArrayList<IDrawableAnimated>> CACHED_ARROWS;
    private LoadingCache<Integer, IDrawableStatic> CACHED_FLUID_BARS;

    private StartDirection MAJOR_ARROW_START_DIRECTION;
    private StartDirection MINOR_ARROW_START_DIRECTION;

    private IDrawableStatic OUTPUT_FLUID_BAR;

    public FluidItem2FluidRecipeCategory(IGuiHelper guiHelper, String modID, String recipeGroup, String guiTexture, ItemStack inputMachine,
	    ArrayList<int[]> inputCoordinates, int smeltTime, StartDirection majorArrowDirection, StartDirection minorArrowDirection,
	    int textYHeight) {

	/*
	 * INPUT COORDIANTES Layout
	 * 
	 * first array is gui background
	 * 
	 * second array is first processing arrow
	 * 
	 * third array is second processing arrow
	 * 
	 * array has following structure [startX,startY,xOffsec,yOffset]
	 * 
	 * 
	 * 
	 * 
	 * fourth array is offset of input item
	 * 
	 * fifth array is offset of input fluid bucket
	 * 
	 * sixth array is offset of input fluid tank [startx,endy,width,height,
	 * maxcapacity]
	 * 
	 * seventh array is offset of output fluid tank [startx,endy,width,height,
	 * maxcapacity]
	 * 
	 * 
	 *
	 *
	 * eighth array is offset of Major Processing arrow ninth array is offset of
	 * Minor Processing arrow
	 * 
	 * 10th array is the output tank's fluid bar [startx,starty,width,height]
	 * 
	 * 
	 */

	super(guiHelper, modID, recipeGroup, guiTexture, inputMachine, inputCoordinates.get(0), FluidItem2FluidRecipe.class, textYHeight, smeltTime);

	MAJOR_PROCESSING_ARROW_COORDS = inputCoordinates.get(1);
	MINOR_PROCESSING_ARROW_COORDS = inputCoordinates.get(2);

	INPUT_ITEM_OFFSET = inputCoordinates.get(3);
	INPUT_FLUID_BUCKET_OFFSET = inputCoordinates.get(4);
	INPUT_FLUID_TANK = inputCoordinates.get(5);
	OUTPUT_FLUID_TANK = inputCoordinates.get(6);
	OUTPUT_FLUID_STACK_OFFSET = inputCoordinates.get(7);

	MAJOR_PROCESSING_ARROW_OFFSET = inputCoordinates.get(8);
	MINOR_PROCESSING_ARROW_OFFSET = inputCoordinates.get(9);

	OUTPUT_TANK_FLUID_BAR = inputCoordinates.get(10);

	MAJOR_ARROW_START_DIRECTION = majorArrowDirection;
	MINOR_ARROW_START_DIRECTION = minorArrowDirection;

	CACHED_ARROWS = CacheBuilder.newBuilder().maximumSize(25).build(new CacheLoader<Integer, ArrayList<IDrawableAnimated>>() {

	    @Override
	    public ArrayList<IDrawableAnimated> load(Integer cookTime) {

		IDrawableAnimated largeArrow = guiHelper
			.drawableBuilder(getGuiTexture(), MAJOR_PROCESSING_ARROW_COORDS[0], MAJOR_PROCESSING_ARROW_COORDS[1],
				MAJOR_PROCESSING_ARROW_COORDS[2], MAJOR_PROCESSING_ARROW_COORDS[3])
			.buildAnimated(cookTime, MAJOR_ARROW_START_DIRECTION, false);

		IDrawableAnimated smallArrow = guiHelper
			.drawableBuilder(getGuiTexture(), MINOR_PROCESSING_ARROW_COORDS[0], MINOR_PROCESSING_ARROW_COORDS[1],
				MINOR_PROCESSING_ARROW_COORDS[2], MINOR_PROCESSING_ARROW_COORDS[3])
			.buildAnimated(cookTime, MINOR_ARROW_START_DIRECTION, false);

		IDrawableAnimated[] arrows = { largeArrow, smallArrow };
		return new ArrayList<>(Arrays.asList(arrows));
	    }
	});

	// Support for fluids without textures
	CACHED_FLUID_BARS = CacheBuilder.newBuilder().maximumSize(25).build(new CacheLoader<Integer, IDrawableStatic>() {

	    @Override
	    public IDrawableStatic load(Integer cookTime) {

		OUTPUT_FLUID_BAR = guiHelper.drawableBuilder(getGuiTexture(), OUTPUT_TANK_FLUID_BAR[0], OUTPUT_TANK_FLUID_BAR[1],
			OUTPUT_TANK_FLUID_BAR[2], OUTPUT_TANK_FLUID_BAR[3]).build();

		return OUTPUT_FLUID_BAR;
	    }
	});

    }

    @Override
    public void setIngredients(FluidItem2FluidRecipe recipe, IIngredients ingredients) {
	ingredients.setInputLists(VanillaTypes.ITEM, getIngredients(recipe));
	ingredients.setInputs(VanillaTypes.FLUID, getFluids(recipe));
	ingredients.setOutput(VanillaTypes.FLUID, recipe.getFluidRecipeOutput());
	ingredients.setOutput(VanillaTypes.ITEM, getBucketOutput(recipe));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, FluidItem2FluidRecipe recipe, IIngredients ingredients) {

	IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
	IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

	guiItemStacks.init(ITEM_INPUT_SLOT, true, INPUT_ITEM_OFFSET[0], INPUT_ITEM_OFFSET[1]);
	guiItemStacks.init(INPUT_FLUID_SLOT, true, INPUT_FLUID_BUCKET_OFFSET[0], INPUT_FLUID_BUCKET_OFFSET[1]);

	int fluidInputAmount = ((FluidIngredient) recipe.getIngredients().get(1)).getFluidStack().getAmount();
	int fluidOutputAmount = recipe.getFluidRecipeOutput().getAmount();

	int leftHeightOffset = (int) Math.ceil(fluidInputAmount / (float) INPUT_FLUID_TANK[4] * INPUT_FLUID_TANK[3]);
	int leftStartY = INPUT_FLUID_TANK[1] - leftHeightOffset + 1;

	int rightHeightOffset = (int) Math.ceil(fluidOutputAmount / (float) OUTPUT_FLUID_TANK[4] * OUTPUT_FLUID_TANK[3]);
	int rightStartY = OUTPUT_FLUID_TANK[1] - rightHeightOffset + 1;

	guiFluidStacks.init(INPUT_FLUID_STACK_SLOT, true, INPUT_FLUID_TANK[0], leftStartY, INPUT_FLUID_TANK[2], leftHeightOffset, fluidInputAmount,
		true, null);
	guiFluidStacks.init(OUTPUT_FLUID_SLOT, false, OUTPUT_FLUID_TANK[0], rightStartY, OUTPUT_FLUID_TANK[2], rightHeightOffset, fluidOutputAmount,
		true, null);

	guiItemStacks.init(OUTPUT_FLUID_STACK_SLOT, false, OUTPUT_FLUID_STACK_OFFSET[0], OUTPUT_FLUID_STACK_OFFSET[1]);
	guiItemStacks.set(ingredients);
	guiFluidStacks.set(ingredients);

    }

    @Override
    public void draw(FluidItem2FluidRecipe recipe, PoseStack matrixStack, double mouseX, double mouseY) {

	int fluidOutputAmount = recipe.getFluidRecipeOutput().getAmount();

	int rightHeightOffset = (int) Math.ceil(fluidOutputAmount / (float) OUTPUT_FLUID_TANK[4] * OUTPUT_FLUID_TANK[3]);
	int rightBarStartHeight = OUTPUT_FLUID_TANK[1] - OUTPUT_FLUID_TANK[3] + 1;

	getFluidBar(recipe).draw(matrixStack, OUTPUT_FLUID_TANK[0], rightBarStartHeight, OUTPUT_FLUID_TANK[3] - rightHeightOffset, 0, 0, 0);

	ArrayList<IDrawableAnimated> arrows = getArrows(recipe);

	arrows.get(0).draw(matrixStack, MAJOR_PROCESSING_ARROW_OFFSET[0], MAJOR_PROCESSING_ARROW_OFFSET[1]);
	arrows.get(1).draw(matrixStack, MINOR_PROCESSING_ARROW_OFFSET[0], MINOR_PROCESSING_ARROW_OFFSET[1]);

	drawSmeltTime(recipe, matrixStack, getYHeight());
    }

    protected ArrayList<IDrawableAnimated> getArrows(FluidItem2FluidRecipe recipe) {
	return CACHED_ARROWS.getUnchecked(getArrowSmeltTime());
    }

    protected IDrawableStatic getFluidBar(FluidItem2FluidRecipe recipe) {
	return CACHED_FLUID_BARS.getUnchecked(getArrowSmeltTime());
    }

    protected void drawSmeltTime(FluidItem2FluidRecipe recipe, PoseStack matrixStack, int y) {
	int smeltTimeSeconds = getArrowSmeltTime() / 20;
	TranslatableComponent outputString = new TranslatableComponent("gui.jei.category." + getRecipeGroup() + ".info.power", smeltTimeSeconds);
	Minecraft minecraft = Minecraft.getInstance();
	Font fontRenderer = minecraft.font;
	int outputWidth = fontRenderer.width(outputString);
	fontRenderer.draw(matrixStack, outputString, getBackground().getWidth() - outputWidth, y + 8, 0xFF808080);

    }

    public NonNullList<FluidStack> getFluids(FluidItem2FluidRecipe recipe) {
	NonNullList<FluidStack> fluids = NonNullList.create();
	fluids.addAll(((FluidIngredient) recipe.getIngredients().get(1)).getMatchingFluids());
	return fluids;
    }

    public List<List<ItemStack>> getIngredients(FluidItem2FluidRecipe recipe) {
	List<List<ItemStack>> ingredients = new ArrayList<>();
	ingredients.add(((CountableIngredient) recipe.getIngredients().get(0)).fetchCountedStacks());
	FluidStack stack = ((FluidIngredient) recipe.getIngredients().get(1)).getFluidStack();
	ItemStack bucket = new ItemStack(stack.getFluid().getBucket());
	bucket.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(h -> {
	    h.fill(stack, FluidAction.EXECUTE);
	});
	List<ItemStack> buckets = new ArrayList<>();
	buckets.add(bucket);
	ingredients.add(buckets);
	return ingredients;
    }

    public ItemStack getBucketOutput(FluidItem2FluidRecipe recipe) {
	FluidStack stack = recipe.getFluidRecipeOutput();
	ItemStack bucket = new ItemStack(stack.getFluid().getBucket());
	bucket.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(h -> {
	    h.fill(stack, FluidAction.EXECUTE);
	});
	return bucket;
    }
}