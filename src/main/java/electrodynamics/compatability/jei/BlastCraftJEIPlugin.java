package electrodynamics.compatability.jei;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import electrodynamics.common.recipe.MachineRecipes;
import electrodynamics.compatability.jei.recipeCategories.psuedoRecipes.PsuedoRecipes;
import electrodynamics.compatability.jei.recipeCategories.specificMachines.blastcraft.BlastCompressorRecipeCategory;

import electrodynamics.prefab.tile.processing.O2OProcessingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;

@JeiPlugin
public class BlastCraftJEIPlugin implements IModPlugin{
	
	public static final boolean isBlastCraftLoaded = ModList.get().isLoaded("blastcraft");
	
	private static final Logger logger = LogManager.getLogger(electrodynamics.api.References.ID);
	
	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(electrodynamics.api.References.ID, "blastc_jei_plugin");
	}
	
	
	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
	{
		
		if(isBlastCraftLoaded) {
			
			//Blast Compressor
			registration.addRecipeCatalyst(new ItemStack(blastcraft.DeferredRegisters.blockBlastCompressor.getBlock()), 
					BlastCompressorRecipeCategory.UID);
		
		}
	}
	
	@Override
	public void registerRecipes(IRecipeRegistration registration) 
	{
		if(isBlastCraftLoaded) {
			PsuedoRecipes.addBlastCraftRecipes();
			
			//Blast Compressor
			Set<O2OProcessingRecipe> blastCompressorRecipes = 
					MachineRecipes.o2orecipemap.get(blastcraft.DeferredRegisters.TILE_BLASTCOMPRESSOR.get());
			
			registration.addRecipes(blastCompressorRecipes, BlastCompressorRecipeCategory.UID);
			
			blastcraftInfoTabs(registration);
			
		}
	}
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) 
	{
		if(isBlastCraftLoaded) {
			
			//Blast Compressor
			registration.addRecipeCategories(new BlastCompressorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
			
		}
	}
	
	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registry) {
		
		if(isBlastCraftLoaded) {
			
			
		}
	}

	
	
	
	private void blastcraftInfoTabs(IRecipeRegistration registration) {
		
	}
	

}
