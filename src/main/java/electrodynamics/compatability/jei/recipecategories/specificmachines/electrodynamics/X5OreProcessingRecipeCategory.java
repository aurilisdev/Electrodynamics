package electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.compatability.jei.recipecategories.ElectrodynamicsRecipeCategory;
import electrodynamics.compatability.jei.recipecategories.psuedorecipes.Psuedo5XRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;

public class X5OreProcessingRecipeCategory extends ElectrodynamicsRecipeCategory<Psuedo5XRecipe> {
    
	private static final int INPUT_SLOT = 0;
	private static final int MACHINE_1 = 1;
	private static final int MACHINE_2 = 2;
	private static final int INPUT_FLUID = 3;
	private static final int ORE_FLUID_SUBPART = 4;
	private static final int OUTPUT_SLOT = 5;
	
	private static int[] GUI_BACKGROUND = {2, 0, 132, 58};
    
    private static int SMELT_TIME = 100;
    private static int TEXT_Y_HEIGHT = 48;

    private static String MOD_ID = References.ID;
    private static String RECIPE_GROUP = "x5_ore_processing";
    private static String GUI_TEXTURE = "textures/gui/jei/5x_ore_processing_gui.png";
    
    private static ItemStack INPUT_MACHINE = new ItemStack(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chemicalcrystallizer));
    
    private LoadingCache<Integer, IDrawableAnimated> CACHED_ARROWS;
    
    public static ResourceLocation UID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

    public X5OreProcessingRecipeCategory(IGuiHelper guiHelper) {
    	
    	super(guiHelper, MOD_ID, RECIPE_GROUP, GUI_TEXTURE, INPUT_MACHINE, GUI_BACKGROUND,
    			Psuedo5XRecipe.class, TEXT_Y_HEIGHT, SMELT_TIME);

			CACHED_ARROWS = CacheBuilder.newBuilder().maximumSize(25).build(new CacheLoader<Integer, IDrawableAnimated>() {
			    @Override
			    public IDrawableAnimated load(Integer cookTime) {
				return guiHelper.drawableBuilder(getGuiTexture(), 0, 58, 88, 16).buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
			    }
			});

    }
    
    @Override
    public ResourceLocation getUid() {
	return UID;
    }

    @Override
    public void setIngredients(Psuedo5XRecipe recipe, IIngredients ingredients) {

	ingredients.setInputLists(VanillaTypes.ITEM, getIngredients(recipe));
	ingredients.setInputs(VanillaTypes.FLUID, getFluids(recipe));
	ingredients.setOutput(VanillaTypes.ITEM, recipe.OUTPUT_CRYSTALS);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, Psuedo5XRecipe recipe, IIngredients ingredients) {

	IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
	IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

	guiItemStacks.init(INPUT_SLOT, true, 43, 26);
	guiItemStacks.init(MACHINE_1, true, 23, 3);
	guiItemStacks.init(MACHINE_2, true, 93, 3);
	guiFluidStacks.init(INPUT_FLUID, true, 5, 23, 12, 23, 1000, true, null);
	guiFluidStacks.init(ORE_FLUID_SUBPART, true, 75, 23, 12, 23, 1000, true, null);
	guiItemStacks.init(OUTPUT_SLOT, false, 113, 26);

	guiItemStacks.set(ingredients);
	guiFluidStacks.set(ingredients);

    }

    @Override
    public void draw(Psuedo5XRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
	IDrawableAnimated arrow = getArrow(recipe);
	arrow.draw(matrixStack, 22, 27);

	drawSmeltTime(recipe, matrixStack, getYHeight());
    }

    protected IDrawableAnimated getArrow(Psuedo5XRecipe recipe) {
	return CACHED_ARROWS.getUnchecked(getArrowSmeltTime());
    }

    protected void drawSmeltTime(Psuedo5XRecipe recipe, MatrixStack matrixStack, int y) {

	int smeltTimeSeconds = getArrowSmeltTime() / 20;
	TranslationTextComponent mineralWasherString = new TranslationTextComponent("gui.jei.category." + getRecipeGroup() + ".info.mineral_washer",
		smeltTimeSeconds);
	TranslationTextComponent chemicalCrystalizerString = new TranslationTextComponent(
		"gui.jei.category." + RECIPE_GROUP + ".info.chemical_crystalizer", smeltTimeSeconds);
	Minecraft minecraft = Minecraft.getInstance();
	FontRenderer fontRenderer = minecraft.fontRenderer;
	int chemCrystWidth = fontRenderer.getStringPropertyWidth(chemicalCrystalizerString);
	fontRenderer.func_243248_b(matrixStack, mineralWasherString, 0, y, 0xFF808080);
	fontRenderer.func_243248_b(matrixStack, chemicalCrystalizerString, getBackground().getWidth() - chemCrystWidth, y, 0xFF808080);
    }

    public List<FluidStack> getFluids(Psuedo5XRecipe recipe) {
	List<FluidStack> fluids = new ArrayList<>();
	fluids.add(recipe.INPUT_FLUID);
	fluids.add(recipe.ORE_FLUID_SUBPART);
	return fluids;
    }

    public List<List<ItemStack>> getIngredients(Psuedo5XRecipe recipe) {
	List<List<ItemStack>> ingredients = new ArrayList<>();
	ingredients.add(Arrays.asList(recipe.INPUT_ORE.getMatchingStacks()));
	ingredients.add(Arrays.asList(recipe.MACHINE_1.getMatchingStacks()));
	ingredients.add(Arrays.asList(recipe.MACHINE_2.getMatchingStacks()));
	return ingredients;
    }

}
