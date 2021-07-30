package electrodynamics.compatability.jei;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import electrodynamics.DeferredRegisters;
import electrodynamics.client.screen.ScreenChemicalCrystallizer;
import electrodynamics.client.screen.ScreenChemicalMixer;
import electrodynamics.client.screen.ScreenDO2OProcessor;
import electrodynamics.client.screen.ScreenElectricFurnace;
import electrodynamics.client.screen.ScreenElectricFurnaceDouble;
import electrodynamics.client.screen.ScreenElectricFurnaceTriple;
import electrodynamics.client.screen.ScreenFermentationPlant;
import electrodynamics.client.screen.ScreenMineralWasher;
import electrodynamics.client.screen.ScreenO2OProcessor;
import electrodynamics.client.screen.ScreenO2OProcessorDouble;
import electrodynamics.client.screen.ScreenO2OProcessorTriple;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.categories.do2o.DO2ORecipe;
import electrodynamics.common.recipe.categories.fluid2item.Fluid2ItemRecipe;
import electrodynamics.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipe;
import electrodynamics.common.recipe.categories.o2o.O2ORecipe;
import electrodynamics.compatability.jei.recipecategories.psuedorecipes.PsuedoRecipes;
import electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics.ChemicalCrystallizerRecipeCategory;
import electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics.ChemicalMixerRecipeCategory;
import electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics.ElectricFurnaceRecipeCategory;
import electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics.EnergizedAlloyerRecipeCategory;
import electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics.ExtruderRecipeCategory;
import electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics.FermentationPlantRecipeCategory;
import electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics.MineralCrusherRecipeCategory;
import electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics.MineralGrinderRecipeCategory;
import electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics.MineralWasherRecipeCategory;
import electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics.OxidationFurnaceRecipeCategory;
import electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics.ReinforcedAlloyerRecipeCategory;
import electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics.WireMillRecipeCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

@JeiPlugin
public class ElectrodynamicsJEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
	return new ResourceLocation(electrodynamics.api.References.ID, "elecdyn_jei_plugin");
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {

	/* Electric Furnace */
	// 1X
	registration.addRecipeCatalyst(ElectricFurnaceRecipeCategory.INPUT_MACHINE,ElectricFurnaceRecipeCategory.UID);
	// 2X
	registration.addRecipeCatalyst(
		new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacedouble)),
		ElectricFurnaceRecipeCategory.UID);
	// 3X
	registration.addRecipeCatalyst(
		new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacetriple)),
		ElectricFurnaceRecipeCategory.UID);

	/* Wire Mill */

	// 1X
	registration.addRecipeCatalyst(WireMillRecipeCategory.INPUT_MACHINE,WireMillRecipeCategory.UID);
	// 2X
	registration.addRecipeCatalyst(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.wiremilldouble)),
		WireMillRecipeCategory.UID);
	// 3X
	registration.addRecipeCatalyst(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.wiremilltriple)),
		WireMillRecipeCategory.UID);

	/* Mineral Crusher */

	// 1X
	registration.addRecipeCatalyst(MineralCrusherRecipeCategory.INPUT_MACHINE,MineralCrusherRecipeCategory.UID);
	// 2X
	registration.addRecipeCatalyst(
		new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralcrusherdouble)),
		MineralCrusherRecipeCategory.UID);
	// 3X
	registration.addRecipeCatalyst(
		new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralcrushertriple)),
		MineralCrusherRecipeCategory.UID);

	/* Mineral Grinder */

	// 1X
	registration.addRecipeCatalyst(MineralGrinderRecipeCategory.INPUT_MACHINE,MineralGrinderRecipeCategory.UID);
	// 2X
	registration.addRecipeCatalyst(
		new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralgrinderdouble)),
		MineralGrinderRecipeCategory.UID);
	// 3X
	registration.addRecipeCatalyst(
		new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralgrindertriple)),
		MineralGrinderRecipeCategory.UID);

	/* Oxidation Furnace */

	registration.addRecipeCatalyst(OxidationFurnaceRecipeCategory.INPUT_MACHINE,OxidationFurnaceRecipeCategory.UID);

	/* Energized Alloyer */

	registration.addRecipeCatalyst(EnergizedAlloyerRecipeCategory.INPUT_MACHINE,EnergizedAlloyerRecipeCategory.UID);
	
	/* Extruder */
	
	registration.addRecipeCatalyst(ExtruderRecipeCategory.INPUT_MACHINE,ExtruderRecipeCategory.UID);

	/* Mineral Washer */

	registration.addRecipeCatalyst(MineralWasherRecipeCategory.INPUT_MACHINE,MineralWasherRecipeCategory.UID);

	/* Chemical Crystallizer */

	registration.addRecipeCatalyst(ChemicalCrystallizerRecipeCategory.INPUT_MACHINE,ChemicalCrystallizerRecipeCategory.UID);

	/* Chemical Mixer */

	registration.addRecipeCatalyst(ChemicalMixerRecipeCategory.INPUT_MACHINE,ChemicalMixerRecipeCategory.UID);

	/* Fermentation Chamber */

	registration.addRecipeCatalyst(FermentationPlantRecipeCategory.INPUT_MACHINE,FermentationPlantRecipeCategory.UID);

	/* Reinforced Alloyer */
	
	registration.addRecipeCatalyst(ReinforcedAlloyerRecipeCategory.INPUT_MACHINE, ReinforcedAlloyerRecipeCategory.UID);
	
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
	PsuedoRecipes.addElectrodynamicsRecipes();
	Minecraft mc = Minecraft.getInstance();
	ClientWorld world = Objects.requireNonNull(mc.world);
	RecipeManager recipeManager = world.getRecipeManager();

	/* Electrodynamics */

	// Electric Furnace
	// still broken; just cleaning up
	@SuppressWarnings("unchecked")
	Set<FurnaceRecipe> electricFurnaceRecipes = ImmutableSet.copyOf(recipeManager.getRecipesForType((IRecipeType<FurnaceRecipe>) Registry.RECIPE_TYPE.getOrDefault(VanillaRecipeCategoryUid.FURNACE)));
	registration.addRecipes(electricFurnaceRecipes, ElectricFurnaceRecipeCategory.UID);

	// Wire Mill
	Set<O2ORecipe> wireMillRecipes = ImmutableSet.copyOf(recipeManager.getRecipesForType(ElectrodynamicsRecipeInit.WIRE_MILL_TYPE));
	registration.addRecipes(wireMillRecipes, WireMillRecipeCategory.UID);

	// Mineral Crusher
	Set<O2ORecipe> mineralCrusherRecipes = ImmutableSet.copyOf(recipeManager.getRecipesForType(ElectrodynamicsRecipeInit.MINERAL_CRUSHER_TYPE));
	registration.addRecipes(mineralCrusherRecipes, MineralCrusherRecipeCategory.UID);

	// Mineral Grinder
	Set<O2ORecipe> mineralGrinderRecipes = ImmutableSet.copyOf(recipeManager.getRecipesForType(ElectrodynamicsRecipeInit.MINERAL_GRINDER_TYPE));
	registration.addRecipes(mineralGrinderRecipes, MineralGrinderRecipeCategory.UID);

	// Oxidation Furnace
	Set<DO2ORecipe> oxidationFurnaceRecipes = ImmutableSet.copyOf(recipeManager.getRecipesForType(ElectrodynamicsRecipeInit.OXIDATION_FURNACE_TYPE));
	registration.addRecipes(oxidationFurnaceRecipes, OxidationFurnaceRecipeCategory.UID);

	// Energized Alloyer
	Set<DO2ORecipe> energizedAlloyerRecipes = ImmutableSet.copyOf(recipeManager.getRecipesForType(ElectrodynamicsRecipeInit.ENERGIZED_ALLOYER_TYPE));
	registration.addRecipes(energizedAlloyerRecipes, EnergizedAlloyerRecipeCategory.UID);

	// Extruder
	Set<O2ORecipe> extruderRecipes = ImmutableSet.copyOf(recipeManager.getRecipesForType(ElectrodynamicsRecipeInit.EXTRUDER_TYPE));
	registration.addRecipes(extruderRecipes, ExtruderRecipeCategory.UID);
	
	// Mineral Washer
	Set<FluidItem2FluidRecipe> mineralWasherRecipes = ImmutableSet.copyOf(recipeManager.getRecipesForType(ElectrodynamicsRecipeInit.MINERAL_WASHER_TYPE));
	registration.addRecipes(mineralWasherRecipes, MineralWasherRecipeCategory.UID);

	// Chemical Crystallizer
	Set<Fluid2ItemRecipe> chemicalCrystallizerRecipes = ImmutableSet.copyOf(recipeManager.getRecipesForType(ElectrodynamicsRecipeInit.CHEMICAL_CRYSTALIZER_TYPE));
	registration.addRecipes(chemicalCrystallizerRecipes, ChemicalCrystallizerRecipeCategory.UID);

	// Chemical Mixer
	Set<FluidItem2FluidRecipe> chemicalMixerRecipes = ImmutableSet.copyOf(recipeManager.getRecipesForType(ElectrodynamicsRecipeInit.CHEMICAL_MIXER_TYPE));
	registration.addRecipes(chemicalMixerRecipes, ChemicalMixerRecipeCategory.UID);

	// Fermentation Chamber
	Set<FluidItem2FluidRecipe> fermenterRecipes = ImmutableSet.copyOf(recipeManager.getRecipesForType(ElectrodynamicsRecipeInit.FERMENTATION_PLANT_TYPE));
	registration.addRecipes(fermenterRecipes, FermentationPlantRecipeCategory.UID);

	// Reinforced Alloyer
	Set<DO2ORecipe> reinforcedAlloyerRecipes = ImmutableSet.copyOf(recipeManager.getRecipesForType(ElectrodynamicsRecipeInit.REINFORCED_ALLOYER_TYPE));
	registration.addRecipes(reinforcedAlloyerRecipes, ReinforcedAlloyerRecipeCategory.UID);
	
	electrodynamicsInfoTabs(registration);

    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
    	
    	IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
    	
		// Electric Furnace
		registration.addRecipeCategories(new ElectricFurnaceRecipeCategory(guiHelper));
	
		// Wire Mill
		registration.addRecipeCategories(new WireMillRecipeCategory(guiHelper));
	
		// Mineral Grinder
		registration.addRecipeCategories(new MineralGrinderRecipeCategory(guiHelper));
	
		// Mineral Crusher
		registration.addRecipeCategories(new MineralCrusherRecipeCategory(guiHelper));
	
		// Oxidation Furnace
		registration.addRecipeCategories(new OxidationFurnaceRecipeCategory(guiHelper));
	
		// Energized Alloyer
		registration.addRecipeCategories(new EnergizedAlloyerRecipeCategory(guiHelper));
	
		// Extruder
		registration.addRecipeCategories(new ExtruderRecipeCategory(guiHelper));
		
		// Mineral Washer
		registration.addRecipeCategories(new MineralWasherRecipeCategory(guiHelper));
	
		// Chemical Crystallizer
		registration.addRecipeCategories(new ChemicalCrystallizerRecipeCategory(guiHelper));
	
		// Chemical Mixer
		registration.addRecipeCategories(new ChemicalMixerRecipeCategory(guiHelper));
	
		// Fermentation Chamber
		registration.addRecipeCategories(new FermentationPlantRecipeCategory(guiHelper));
	
		// Reinforced Alloyer
		registration.addRecipeCategories(new ReinforcedAlloyerRecipeCategory(guiHelper));
		
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registry) {
	int[] o2oarrowLoc = { 80 + 5, 35, 22, 15 };

	/* Wire Mill, Mineral Grinder, Mineral Crusher, Blast Compressor */

	// 1X
	registry.addRecipeClickArea(ScreenO2OProcessor.class, o2oarrowLoc[0], o2oarrowLoc[1], o2oarrowLoc[2], o2oarrowLoc[3], getO2OGuiScreens());
	// 2X
	registry.addRecipeClickArea(ScreenO2OProcessorDouble.class, o2oarrowLoc[0], o2oarrowLoc[1] - 10, o2oarrowLoc[2], o2oarrowLoc[3] * 2 + 5,
		getO2OGuiScreens());
	// 3X
	registry.addRecipeClickArea(ScreenO2OProcessorTriple.class, o2oarrowLoc[0], o2oarrowLoc[1] - 10, o2oarrowLoc[2], o2oarrowLoc[3] * 3 + 10,
		getO2OGuiScreens());

	/* Oxidation Furnace */
	registry.addRecipeClickArea(ScreenDO2OProcessor.class, o2oarrowLoc[0], o2oarrowLoc[1], o2oarrowLoc[2], o2oarrowLoc[3],
		OxidationFurnaceRecipeCategory.UID, EnergizedAlloyerRecipeCategory.UID, ReinforcedAlloyerRecipeCategory.UID);

	/* Electric Furnace Click Area */

	// 1X
	registry.addRecipeClickArea(ScreenElectricFurnace.class, o2oarrowLoc[0], o2oarrowLoc[1], o2oarrowLoc[2], o2oarrowLoc[3],
		ElectricFurnaceRecipeCategory.UID);
	// 2X
	registry.addRecipeClickArea(ScreenElectricFurnaceDouble.class, o2oarrowLoc[0], o2oarrowLoc[1] - 10, o2oarrowLoc[2], o2oarrowLoc[3] * 2 + 5,
		ElectricFurnaceRecipeCategory.UID);
	// 3X
	registry.addRecipeClickArea(ScreenElectricFurnaceTriple.class, o2oarrowLoc[0], o2oarrowLoc[1] - 10, o2oarrowLoc[2], o2oarrowLoc[3] * 2 + 10,
		ElectricFurnaceRecipeCategory.UID);

	/* Chemical Mixer */
	registry.addRecipeClickArea(ScreenChemicalMixer.class, 97, 31, o2oarrowLoc[2], o2oarrowLoc[3], ChemicalMixerRecipeCategory.UID);

	/* Fermentation Plant */
	registry.addRecipeClickArea(ScreenFermentationPlant.class, 97, 31, o2oarrowLoc[2], o2oarrowLoc[3], FermentationPlantRecipeCategory.UID);

	/* Mineral Washer */
	registry.addRecipeClickArea(ScreenMineralWasher.class, 45, 31, o2oarrowLoc[2], o2oarrowLoc[3], MineralWasherRecipeCategory.UID);

	/* Chemical Crystalizer */
	registry.addRecipeClickArea(ScreenChemicalCrystallizer.class, 45, 35, o2oarrowLoc[2], o2oarrowLoc[3], ChemicalCrystallizerRecipeCategory.UID);
    }

    public static ResourceLocation[] getO2OGuiScreens() {

	ArrayList<ResourceLocation> locations = new ArrayList<>();

	locations.add(WireMillRecipeCategory.UID);
	locations.add(MineralGrinderRecipeCategory.UID);
	locations.add(MineralCrusherRecipeCategory.UID);
	locations.add(ExtruderRecipeCategory.UID);
	
	ResourceLocation[] totalLocations = new ResourceLocation[locations.size()];

	for (int i = 0; i < locations.size(); i++) {
	    totalLocations[i] = locations.get(i);
	}

	return totalLocations;

    }

    @Deprecated
    private static void electrodynamicsInfoTabs(IRecipeRegistration registration) {
	
		/*
		 * Machines currently with tabs:
		 * 
		 * Coal Generator Upgrade Transformer Downgrade Transformer Solar Panel Advanced
		 * Solar Panel Thermoelectric Generator Combustion Chamber Hydroelectric
		 * Generator Wind Generator Mineral Washer Chemical Mixer Chemical Crystalizer
		 * 
		 */
		ArrayList<ItemStack> edMachines = PsuedoRecipes.ELECTRODYNAMICS_MACHINES;
		String temp;
	
		for (ItemStack itemStack : edMachines) {
		    temp = itemStack.getItem().toString();
		    registration.addIngredientInfo(itemStack, VanillaTypes.ITEM, "info.jei.block." + temp);
		}
		
		registration.addIngredientInfo(new ItemStack(DeferredRegisters.COMPOSITE_HELMET.get()), VanillaTypes.ITEM, "info.jei.item." + DeferredRegisters.COMPOSITE_HELMET.get());
		registration.addIngredientInfo(new ItemStack(DeferredRegisters.COMPOSITE_CHESTPLATE.get()), VanillaTypes.ITEM, "info.jei.item." + DeferredRegisters.COMPOSITE_HELMET.get());
		registration.addIngredientInfo(new ItemStack(DeferredRegisters.COMPOSITE_LEGGINGS.get()), VanillaTypes.ITEM, "info.jei.item." + DeferredRegisters.COMPOSITE_HELMET.get());
		registration.addIngredientInfo(new ItemStack(DeferredRegisters.COMPOSITE_BOOTS.get()), VanillaTypes.ITEM, "info.jei.item." + DeferredRegisters.COMPOSITE_HELMET.get());
		
		
		
		
    }

}
