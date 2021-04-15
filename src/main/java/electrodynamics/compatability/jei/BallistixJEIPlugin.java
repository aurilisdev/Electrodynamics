package electrodynamics.compatability.jei;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import electrodynamics.compatability.jei.recipeCategories.psuedoRecipes.PsuedoRecipes;
import electrodynamics.compatability.jei.recipeCategories.specificMachines.ballistix.WarheadRecipeCategory;

import electrodynamics.prefab.tile.processing.DO2OProcessingRecipe;
import electrodynamics.prefab.tile.processing.O2OProcessingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;

@JeiPlugin
public class BallistixJEIPlugin implements IModPlugin{
	
	public static final boolean isBallistixLoaded = ModList.get().isLoaded("ballistix");
	
	private static final Logger logger = LogManager.getLogger(electrodynamics.api.References.ID);
	
	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(electrodynamics.api.References.ID, "balx_jei_plugin");
	}
	
	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
	{
		if(isBallistixLoaded) {
			
			//Warhead Template
			registration.addRecipeCatalyst(new ItemStack(ballistix.DeferredRegisters.ITEM_MISSILELONGRANGE.get()), 
					WarheadRecipeCategory.UID);
		}
	}
	
	@Override
	public void registerRecipes(IRecipeRegistration registration) 
	{
		if(isBallistixLoaded) {
			PsuedoRecipes.addBallistixRecipes();
			
			//Warhead Template
			Set<DO2OProcessingRecipe> warheadTemplateRecipes = new HashSet<>(PsuedoRecipes.WARHEAD_RECIPES);
			registration.addRecipes(warheadTemplateRecipes, WarheadRecipeCategory.UID);
			
			ballistixInfoTabs(registration);
			
		}
	}
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) 
	{
		if(isBallistixLoaded) {
			
			//Warhead Template
			registration.addRecipeCategories(new WarheadRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
		}
	}
	
	private void ballistixInfoTabs(IRecipeRegistration registration) {
		/* Items currently with tabs:

		  Close Range Missile 
		  Medium Range Missile 
          Long Range Missile 
          Missile Silo
		
		*/
		ArrayList<ItemStack> ballistixInfoItems = PsuedoRecipes.BALLISTIX_ITEMS.get(1);
		String temp;
		
		for(ItemStack itemStack: ballistixInfoItems) {
			temp = itemStack.getItem().toString();
			registration.addIngredientInfo(itemStack, VanillaTypes.ITEM, "info.jei.item." + temp);
			//logger.info("Item name: " + temp);
		}
		
		ballistixInfoItems = PsuedoRecipes.BALLISTIX_ITEMS.get(2);
		
		for(ItemStack itemStack: ballistixInfoItems) {
			temp = itemStack.getItem().toString();
			registration.addIngredientInfo(itemStack, VanillaTypes.ITEM, "info.jei.item." + temp);
			//logger.info("Item name: " + temp);
		}
		
	}

}
