package electrodynamics.compatability.jei;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import electrodynamics.compatability.jei.recipeCategories.psuedoRecipes.PsuedoGasCentrifugeRecipe;
import electrodynamics.compatability.jei.recipeCategories.psuedoRecipes.PsuedoRecipes;
import electrodynamics.compatability.jei.recipeCategories.psuedoRecipes.PsuedoSolAndLiqToLiquidRecipe;
import electrodynamics.compatability.jei.recipeCategories.psuedoRecipes.PsuedoSolAndLiqToSolidRecipe;
import electrodynamics.compatability.jei.recipeCategories.specificMachines.nuclearScience.ChemicalExtractorRecipeCategory;
import electrodynamics.compatability.jei.recipeCategories.specificMachines.nuclearScience.FissionReactorRecipeCategory;
import electrodynamics.compatability.jei.recipeCategories.specificMachines.nuclearScience.GasCentrifugeRecipeCategory;
import electrodynamics.compatability.jei.recipeCategories.specificMachines.nuclearScience.NuclearBoilerRecipeCategory;
import electrodynamics.compatability.jei.recipeCategories.specificMachines.nuclearScience.ParticleAcceleratorAntiMatterRecipeCategory;
import electrodynamics.compatability.jei.recipeCategories.specificMachines.nuclearScience.ParticleAcceleratorDarkMatterRecipeCategory;

import electrodynamics.prefab.tile.processing.O2OProcessingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;
import nuclearscience.client.screen.ScreenNuclearBoiler;
import nuclearscience.client.screen.ScreenChemicalExtractor;
import nuclearscience.client.screen.ScreenGasCentrifuge;
import nuclearscience.client.screen.ScreenParticleInjector;
import nuclearscience.client.screen.ScreenReactorCore;

@JeiPlugin
public class NuclearSciencePlugin implements IModPlugin{
	
	public static final boolean isNuclearScienceLoaded = ModList.get().isLoaded("nuclearscience");
	
	private static final Logger logger = LogManager.getLogger(electrodynamics.api.References.ID);
	
	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(electrodynamics.api.References.ID, "nucsci_jei_plugin");
	}
	
	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
	{
		if(isNuclearScienceLoaded) {
			
			//Gas Centrifuge
			registration.addRecipeCatalyst(new ItemStack(nuclearscience.DeferredRegisters.blockGasCentrifuge), 
					GasCentrifugeRecipeCategory.UID);
			
			//Nuclear Boiler
			registration.addRecipeCatalyst(new ItemStack(nuclearscience.DeferredRegisters.blockChemicalBoiler), 
					NuclearBoilerRecipeCategory.UID);
			
			//Chemical Extractor
			registration.addRecipeCatalyst(new ItemStack(nuclearscience.DeferredRegisters.blockChemicalExtractor), 
					ChemicalExtractorRecipeCategory.UID);
			
			//Fisison Reactor
			
			registration.addRecipeCatalyst(new ItemStack(nuclearscience.DeferredRegisters.blockReactorCore), 
					FissionReactorRecipeCategory.UID);
			
			//Anti Matter
			
			registration.addRecipeCatalyst(new ItemStack(nuclearscience.DeferredRegisters.blockParticleInjector),
					ParticleAcceleratorAntiMatterRecipeCategory.UID);
			
			//Dark Matter
			
			registration.addRecipeCatalyst(new ItemStack(nuclearscience.DeferredRegisters.blockParticleInjector),
					ParticleAcceleratorDarkMatterRecipeCategory.UID);
			
		}
	}
	
	@Override
	public void registerRecipes(IRecipeRegistration registration) 
	{
		if(isNuclearScienceLoaded) {
			PsuedoRecipes.addNuclearScienceRecipes();
			
			//Gas Centrifuge
			Set<PsuedoGasCentrifugeRecipe> gasCentrifugeRecipes = new HashSet<>(PsuedoRecipes.GAS_CENTRIFUGE_RECIPES);
					
			registration.addRecipes(gasCentrifugeRecipes, GasCentrifugeRecipeCategory.UID);
			
			//Nuclear Boiler
			Set<PsuedoSolAndLiqToLiquidRecipe> nuclearBoilerRecipes = new HashSet<>(PsuedoRecipes.NUCLEAR_BOILER_RECIPES);
			
			registration.addRecipes(nuclearBoilerRecipes, NuclearBoilerRecipeCategory.UID);
			
			//Chemical Extractor
			Set<PsuedoSolAndLiqToSolidRecipe> chemicalExtractorRecipes = new HashSet<>(PsuedoRecipes.CHEMICAL_EXTRACTOR_RECIPES);
			
			registration.addRecipes(chemicalExtractorRecipes, ChemicalExtractorRecipeCategory.UID);
			
			Set<O2OProcessingRecipe> fissionReactorRecipes = new HashSet<>(PsuedoRecipes.FISSION_REACTOR_RECIPES);
			registration.addRecipes(fissionReactorRecipes, FissionReactorRecipeCategory.UID);
			
			Set<O2OProcessingRecipe> antiMatterRecipes = new HashSet<>(PsuedoRecipes.ANTI_MATTER_RECIPES);
			registration.addRecipes(antiMatterRecipes, ParticleAcceleratorAntiMatterRecipeCategory.UID);
			
			Set<O2OProcessingRecipe> darkMatterRecipes = new HashSet<>(PsuedoRecipes.DARK_MATTER_RECIPES);
			registration.addRecipes(darkMatterRecipes, ParticleAcceleratorDarkMatterRecipeCategory.UID);
			
			nuclearScienceInfoTabs(registration);
			
		}
	}
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) 
	{
		if(isNuclearScienceLoaded) {
			
			//Gas Centrifuge
			registration.addRecipeCategories(new GasCentrifugeRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
			
			//Nuclear Boiler
			registration.addRecipeCategories(new NuclearBoilerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
			
			//Chemical Extractor
			registration.addRecipeCategories(new ChemicalExtractorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
			
			//Fision Reactor
			registration.addRecipeCategories(new FissionReactorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
			
			//Anti Matter
			registration.addRecipeCategories(new ParticleAcceleratorAntiMatterRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
			
			//Dark Matter
			registration.addRecipeCategories(new ParticleAcceleratorDarkMatterRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
			
		}
	}
	
	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registry) {
		if(isNuclearScienceLoaded) {
			int[] arrowLocation = {97,31,22,15};
			
			//Nuclear Boiler
			
			registry.addRecipeClickArea(ScreenNuclearBoiler.class, arrowLocation[0], arrowLocation[1],  arrowLocation[2], arrowLocation[3],
					NuclearBoilerRecipeCategory.UID);
			
			//Chemical Extractor
			registry.addRecipeClickArea(ScreenChemicalExtractor.class, arrowLocation[0], arrowLocation[1],  arrowLocation[2], arrowLocation[3],
					ChemicalExtractorRecipeCategory.UID);
			
			//Gas Centrifuge
			registry.addRecipeClickArea(ScreenGasCentrifuge.class,91, 22,  32, 41, GasCentrifugeRecipeCategory.UID);
			
			//Fission Reactor
			registry.addRecipeClickArea(ScreenReactorCore.class,117, 43,  14, 13, FissionReactorRecipeCategory.UID);
			
			//Particle Accelerator
			registry.addRecipeClickArea(ScreenParticleInjector.class,102, 33,  28, 14,
					ParticleAcceleratorAntiMatterRecipeCategory.UID,ParticleAcceleratorDarkMatterRecipeCategory.UID);
			
		}
	}
	
	
	private void nuclearScienceInfoTabs(IRecipeRegistration registration) {
		
		/* Machines currently with tabs:

			Fission Reactor Core
			Fusion Reactor Core
			Chemical Boiler
			Chemical Extractor
			Electromagnet
			Electromagnetic Booster
			Electromagnetic Switch
			Gas Centrifuge
			Particle Injector
			Quantum Capacitor
			Radioisotope Generator
			Turbine
					
		*/
		ArrayList<ItemStack> nsMachines = PsuedoRecipes.NUCLEAR_SCIENCE_MACHINES;
		String temp;
		
		for(ItemStack itemStack: nsMachines) {
			temp = itemStack.getItem().toString();
			registration.addIngredientInfo(itemStack, VanillaTypes.ITEM, "info.jei.block." + temp);
		}
		
	}

}
