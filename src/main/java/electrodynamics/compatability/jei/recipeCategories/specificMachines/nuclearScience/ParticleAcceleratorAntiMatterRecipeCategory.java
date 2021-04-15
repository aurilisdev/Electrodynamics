package electrodynamics.compatability.jei.recipeCategories.specificMachines.nuclearScience;

import java.util.ArrayList;
import java.util.Arrays;
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
import net.minecraft.client.Minecraft;
import net.minecraft.item.AirItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticleAcceleratorAntiMatterRecipeCategory extends O2OProcessingRecipeCategory{
	//JEI Window Parameters
	private static int ANY_ITEM_INPUT_SLOT = 2;
	
	private static int[] GUIBackground = {0,0,132,66};
	private static final int[] ProcessingArrowLocation = {0,67,82,47};
		
	private static int[] InputItemOffset = {12,39};
	private static int[] OutputItemOffset = {101,20};
	private static int[] ProcessingArrowOffset = {17,6};
		
		
	private static String modID =  References.ID;
	private static String recipeGroup = "partical_accelerator_antimatter";
	private static String guiTexture = "textures/gui/jei/particle_accelerator_antimatter_gui.png";
	private static ItemStack inputMachine = 
			new ItemStack(nuclearscience.DeferredRegisters.blockParticleInjector);
	private static ArrayList<int[]> inputCoordinates = 
			new ArrayList<>(Arrays.asList(
											  GUIBackground,
											  ProcessingArrowLocation,
											  InputItemOffset,
											  OutputItemOffset,
											  ProcessingArrowOffset
				));
	private static int smeltTime = 50;
	private static int textYHeight =58;
	private static StartDirection arrowStartDirection = IDrawableAnimated.StartDirection.LEFT;
		
	public static ResourceLocation UID = new ResourceLocation(modID,recipeGroup);
	
	
	
	public ParticleAcceleratorAntiMatterRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, modID, recipeGroup, guiTexture, inputMachine, inputCoordinates, smeltTime, textYHeight, arrowStartDirection);
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
		guiItemStacks.init(ANY_ITEM_INPUT_SLOT, true, 12, 2);
		guiItemStacks.init(OUTPUT_SLOT, false, OutputItemOffset[0], OutputItemOffset[1]);
		
		guiItemStacks.set(ingredients);
		
	}
	
	
	private List<List<ItemStack>> recipeInput(O2OProcessingRecipe recipe){
		
		List<ItemStack> emagCell = new ArrayList<>();
		emagCell.add(recipe.getInput());
		
		/*Gets a list of all Vanilla items*/
		int i = 0;
		List<Item> allItems = new ArrayList<Item>(ForgeRegistries.ITEMS.getValues());
		List<Item> vanillaItems = new ArrayList<Item>();
		ItemGroup[] vanillaItemGroups = {ItemGroup.BREWING,ItemGroup.BUILDING_BLOCKS,ItemGroup.COMBAT,ItemGroup.DECORATIONS, ItemGroup.FOOD,
				ItemGroup.MISC,ItemGroup.REDSTONE,ItemGroup.TOOLS,ItemGroup.TRANSPORTATION};
		for(Item item : allItems) {
			for(i = 0 ; i < vanillaItemGroups.length;i++) {
				if(item.getGroup() == vanillaItemGroups[i]) {
					vanillaItems.add(item);
					break;
				}
			}
		}
		//Filters out Air item; breaks JEI category if left!
		for(Item item: vanillaItems) {
			if(item instanceof AirItem) {
				vanillaItems.remove(item);
			}
		}
		
		List<ItemStack> vanillaItemStacks = new ArrayList<ItemStack>();
		for(Item item : vanillaItems) {
			vanillaItemStacks.add(new ItemStack(item));
		}
		
		List<List<ItemStack>> inputSlots = new ArrayList<>();
		inputSlots.add(emagCell);
		inputSlots.add(vanillaItemStacks);
		
		return inputSlots;
		
	}

}
