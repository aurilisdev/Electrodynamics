package electrodynamics.compatability.jei.recipeCategories.specificMachines.nuclearScience;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import electrodynamics.api.References;
import electrodynamics.compatability.jei.recipeCategories.O2OProcessingRecipeCategory;

import electrodynamics.prefab.tile.processing.O2OProcessingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class FissionReactorRecipeCategory extends O2OProcessingRecipeCategory{
	
	//JEI Window Parameters
	private static int[] GUIBackground = {0,0,132,132};
	private static final int[] ProcessingArrowLocation = {132,0,48,42};
		
	private static int[] InputItemOffset = {57,25};
	private static int[] OutputItemOffset = {57,89};
	private static int[] ProcessingArrowOffset = {42,45};
		
		
	private static String modID = References.ID;
	private static String recipeGroup = "fission_reactor";
	private static String guiTexture = "textures/gui/jei/fission_reactor_gui.png";
	private static ItemStack inputMachine = 
			new ItemStack(nuclearscience.DeferredRegisters.blockReactorCore);
	private static ArrayList<int[]> inputCoordinates = 
			new ArrayList<>(Arrays.asList(
											  GUIBackground,
											  ProcessingArrowLocation,
											  InputItemOffset,
											  OutputItemOffset,
											  ProcessingArrowOffset
				));
	private static int smeltTime = 50;
	private static int textYHeight =122;
	private static StartDirection arrowStartDirection = IDrawableAnimated.StartDirection.TOP;
		
	
	
	private static int[] FUEL_ROD_SLOTS = {2,3,4,5};
	
	
	
	public static ResourceLocation UID = new ResourceLocation(modID,recipeGroup);
	
	
	public FissionReactorRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, modID, recipeGroup, guiTexture, inputMachine, inputCoordinates, smeltTime, textYHeight,arrowStartDirection);
	}
	
	
	@Override
	public ResourceLocation getUid() {
		return UID;
	}
	
	
	@Override
	public void setIngredients(O2OProcessingRecipe recipe, IIngredients ingredients) {
		
		ingredients.setInputLists(VanillaTypes.ITEM,recipeInput(recipe));
		
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
	}
	
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, O2OProcessingRecipe recipe, IIngredients ingredients) {
		
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		
		guiItemStacks.init(INPUT_SLOT, true, InputItemOffset[0], InputItemOffset[1]);
		guiItemStacks.init(FUEL_ROD_SLOTS[0], true, 15, 15);
		guiItemStacks.init(FUEL_ROD_SLOTS[1], true, 99, 15);
		guiItemStacks.init(FUEL_ROD_SLOTS[2], true, 15, 99);
		guiItemStacks.init(FUEL_ROD_SLOTS[3], true, 99, 99);
		guiItemStacks.init(OUTPUT_SLOT, false, OutputItemOffset[0], OutputItemOffset[1]);
		
		guiItemStacks.set(ingredients);
	}
	
	
	private List<List<ItemStack>> recipeInput(O2OProcessingRecipe recipe){
		
		List<ItemStack> deuterium = new ArrayList<>();
		deuterium.add(recipe.getInput());
		
		ItemStack u235Cell = new ItemStack(nuclearscience.DeferredRegisters.ITEM_FUELHEUO2.get(),1);
		ItemStack u238Cell = new ItemStack(nuclearscience.DeferredRegisters.ITEM_FUELLEUO2.get(),1);
		
		List<ItemStack> fuels = new ArrayList<ItemStack>();
		fuels.add(u238Cell);
		fuels.add(u235Cell);
		
		List<List<ItemStack>> inputSlots = new ArrayList<>();
		inputSlots.add(deuterium);
		
		inputSlots.add(fuels);
		
		Collections.reverse(fuels);
		inputSlots.add(fuels);
		
		Collections.reverse(fuels);
		inputSlots.add(fuels);
		
		Collections.reverse(fuels);
		inputSlots.add(fuels);
		
		return inputSlots;
	}
	
}
