package electrodynamics.compatability.jei.recipeCategories.specificMachines.nuclearScience;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.matrix.MatrixStack;
import electrodynamics.api.References;

import electrodynamics.prefab.tile.processing.O2OProcessingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
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

public class ParticleAcceleratorDarkMatterRecipeCategory implements IRecipeCategory<O2OProcessingRecipe>{
	
	public static final int OUTPUT_SLOT = 0;
	
	private static String MOD_ID =  References.ID;
	private static String RECIPE_GROUP = "partical_accelerator_darkmatter";
	
	private static int[] GUIBackgroundCoords = {0,0,132,132};
	private static int[] OutputOffset = {57,57};
	
	public static int ARROW_SMELT_TIME = 50;
	
	private IDrawable background;
	private IDrawable icon;
	
	private LoadingCache<Integer, ArrayList<IDrawableAnimated>> cachedArrows;
	
	private static ItemStack INPUT_MACHINE = new ItemStack(nuclearscience.DeferredRegisters.blockParticleInjector);
	
	private static String GUI_TEXTURE_STRING = "textures/gui/jei/particle_accelerator_dark_matter_gui.png";
	
	private static ResourceLocation GUI_TEXTURE = new ResourceLocation(MOD_ID,GUI_TEXTURE_STRING);
	public static ResourceLocation UID = new ResourceLocation(MOD_ID,RECIPE_GROUP);
	
	private int Y_HEIGHT = 122;

	
	//private static final Logger logger = LogManager.getLogger(ElectrodynamicsPatches.MOD_ID);
	
	public ParticleAcceleratorDarkMatterRecipeCategory(IGuiHelper guiHelper) {
		
		this.icon = guiHelper.createDrawableIngredient(INPUT_MACHINE);
		this.background = guiHelper.createDrawable(GUI_TEXTURE,GUIBackgroundCoords[0],GUIBackgroundCoords[1],
				GUIBackgroundCoords[2],GUIBackgroundCoords[3]);
		
		this.cachedArrows = CacheBuilder.newBuilder()
				.maximumSize(25)
				.build(new CacheLoader<Integer, ArrayList<IDrawableAnimated>>() 
				{
					@Override
					public ArrayList<IDrawableAnimated> load(Integer cookTime) {
						
					
						IDrawableAnimated majorArrowBottom = guiHelper.drawableBuilder(
								GUI_TEXTURE, 179,17, 37, 75
								).buildAnimated(cookTime, IDrawableAnimated.StartDirection.BOTTOM, false);
						
						IDrawableAnimated majorArrowTop = guiHelper.drawableBuilder(
								GUI_TEXTURE, 132, 0, 37, 75
								).buildAnimated(cookTime, IDrawableAnimated.StartDirection.TOP, false);
						
						IDrawableAnimated[] arrows = {majorArrowBottom,majorArrowTop};
						return new ArrayList<IDrawableAnimated>(Arrays.asList(arrows));
					}
				});
	
	}
	
	
	@Override
	public ResourceLocation getUid() {
		return UID;
	}
	

	@Override
	public Class<? extends O2OProcessingRecipe> getRecipeClass() {
		return O2OProcessingRecipe.class;
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
	public void setIngredients(O2OProcessingRecipe recipe, IIngredients ingredients) {
		NonNullList<Ingredient> inputs = NonNullList.create();
		inputs.addAll(getIngredients(recipe));
		
		ingredients.setInputIngredients(inputs);
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
	}

	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, O2OProcessingRecipe recipe, IIngredients ingredients) {
		
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		
		guiItemStacks.init(OUTPUT_SLOT, false, OutputOffset[0], OutputOffset[1]);
		
		guiItemStacks.set(ingredients);
		
	}
	
	
	@Override
	public void draw(O2OProcessingRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) 
	{
		ArrayList<IDrawableAnimated> arrow = getArrow(recipe);
		arrow.get(0).draw(matrixStack, 70, 38);
		arrow.get(1).draw(matrixStack,23,21);

		drawSmeltTime(recipe, matrixStack, Y_HEIGHT);
	}
	
	
	protected ArrayList<IDrawableAnimated> getArrow(O2OProcessingRecipe recipe) 
	{
		return this.cachedArrows.getUnchecked(ARROW_SMELT_TIME);
	}
	
	
	protected void drawSmeltTime(O2OProcessingRecipe recipe, MatrixStack matrixStack, int y) 
	{
		int smeltTimeSeconds = ARROW_SMELT_TIME / 20;
		TranslationTextComponent timeString = new TranslationTextComponent("gui.jei.category."+RECIPE_GROUP+".info.power", smeltTimeSeconds);
		Minecraft minecraft = Minecraft.getInstance();
		FontRenderer fontRenderer = minecraft.fontRenderer;
		int stringWidth = fontRenderer.getStringPropertyWidth(timeString);
		fontRenderer.func_243248_b(matrixStack,timeString,background.getWidth() - stringWidth,y,0xFF808080);
	}
	
	
	public NonNullList<Ingredient> getIngredients(O2OProcessingRecipe recipe){
		Ingredient ingredient1 = Ingredient.fromStacks(recipe.getInput());
		NonNullList<Ingredient> ingredients = NonNullList.create();
		ingredients.add(ingredient1);
		return ingredients;
	}
}
