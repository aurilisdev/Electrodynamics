package electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.compatability.jei.recipecategories.psuedorecipes.Psuedo5XRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;

public class X5OreProcessingRecipeCategory implements IRecipeCategory<Psuedo5XRecipe> {
    public static int INPUT_SLOT = 0;
    public static int MACHINE_1 = 1;
    public static int MACHINE_2 = 2;
    public static int INPUT_FLUID = 3;
    public static int ORE_FLUID_SUBPART = 4;
    public static int OUTPUT_SLOT = 5;

    private static String MOD_ID = References.ID;
    private static String RECIPE_GROUP = "x5_ore_processing";

    public static ResourceLocation UID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

    private static int ARROW_SMELT_TIME = 100;

    private static IDrawable background;
    private static IDrawable icon;

    private LoadingCache<Integer, IDrawableAnimated> cachedArrows;

    private static String GUI_TEXTURE_STRING = "textures/gui/jei/5x_ore_processing_gui.png";

    private static ResourceLocation GUI_TEXTURE = new ResourceLocation(MOD_ID, GUI_TEXTURE_STRING);

    // private static Logger logger =
    // LogManager.getLogger(ElectrodynamicsPatches.MOD_ID);

    public X5OreProcessingRecipeCategory(IGuiHelper guiHelper) {

	icon = guiHelper.createDrawableIngredient(new ItemStack(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chemicalcrystallizer)));
	background = guiHelper.createDrawable(GUI_TEXTURE, 2, 0, 132, 58);

	cachedArrows = CacheBuilder.newBuilder().maximumSize(25).build(new CacheLoader<Integer, IDrawableAnimated>() {
	    @Override
	    public IDrawableAnimated load(Integer cookTime) {
		return guiHelper.drawableBuilder(GUI_TEXTURE, 0, 58, 88, 16).buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
	    }
	});

    }

    @Override
    public ResourceLocation getUid() {
	return UID;
    }

    @Override
    public Class<? extends Psuedo5XRecipe> getRecipeClass() {
	return Psuedo5XRecipe.class;
    }

    @Override
    public String getTitle() {
	return new TranslationTextComponent("gui.jei.category." + RECIPE_GROUP).getString();
    }

    @Override
    public IDrawable getBackground() {
	return background;
    }

    @Override
    public IDrawable getIcon() {
	return icon;
    }

    @Override
    public void setIngredients(Psuedo5XRecipe recipe, IIngredients ingredients) {
	NonNullList<Ingredient> inputs = NonNullList.create();
	inputs.addAll(getIngredients(recipe));

	ingredients.setInputIngredients(inputs);
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

	drawSmeltTime(recipe, matrixStack, 48);
    }

    protected IDrawableAnimated getArrow(Psuedo5XRecipe recipe) {
	return cachedArrows.getUnchecked(ARROW_SMELT_TIME);
    }

    protected void drawSmeltTime(Psuedo5XRecipe recipe, MatrixStack matrixStack, int y) {

	int smeltTimeSeconds = ARROW_SMELT_TIME / 20;
	TranslationTextComponent mineralWasherString = new TranslationTextComponent("gui.jei.category." + RECIPE_GROUP + ".info.mineral_washer",
		smeltTimeSeconds);
	TranslationTextComponent chemicalCrystalizerString = new TranslationTextComponent(
		"gui.jei.category." + RECIPE_GROUP + ".info.chemical_crystalizer", smeltTimeSeconds);
	Minecraft minecraft = Minecraft.getInstance();
	FontRenderer fontRenderer = minecraft.fontRenderer;
	int chemCrystWidth = fontRenderer.getStringPropertyWidth(chemicalCrystalizerString);
	fontRenderer.func_243248_b(matrixStack, mineralWasherString, 0, y, 0xFF808080);
	fontRenderer.func_243248_b(matrixStack, chemicalCrystalizerString, background.getWidth() - chemCrystWidth, y, 0xFF808080);
    }

    public NonNullList<FluidStack> getFluids(Psuedo5XRecipe recipe) {
	NonNullList<FluidStack> fluids = NonNullList.create();
	fluids.add(recipe.INPUT_FLUID);
	fluids.add(recipe.ORE_FLUID_SUBPART);
	return fluids;
    }

    public NonNullList<Ingredient> getIngredients(Psuedo5XRecipe recipe) {
	NonNullList<Ingredient> ingredients = NonNullList.create();
	ingredients.add(recipe.INPUT_ORE);
	ingredients.add(recipe.MACHINE_1);
	ingredients.add(recipe.MACHINE_2);
	return ingredients;
    }

}
