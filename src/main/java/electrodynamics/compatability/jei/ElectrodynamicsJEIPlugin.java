package electrodynamics.compatability.jei;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

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
import electrodynamics.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipe;
import electrodynamics.common.recipe.categories.o2o.O2ORecipe;
import electrodynamics.compatability.jei.recipecategories.psuedorecipes.Psuedo5XRecipe;
import electrodynamics.compatability.jei.recipecategories.psuedorecipes.PsuedoRecipes;
import electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics.ChemicalMixerRecipeCategory;
import electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics.ElectricFurnaceRecipeCategory;
import electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics.EnergizedAlloyerRecipeCategory;
import electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics.FermentationPlantRecipeCategory;
import electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics.MineralCrusherRecipeCategory;
import electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics.MineralGrinderRecipeCategory;
import electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics.OxidationFurnaceRecipeCategory;
import electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics.WireMillRecipeCategory;
import electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics.X5OreProcessingRecipeCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
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
	registration.addRecipeCatalyst(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnace)),
		ElectricFurnaceRecipeCategory.UID);
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
	registration.addRecipeCatalyst(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.wiremill)),
		WireMillRecipeCategory.UID);
	// 2X
	registration.addRecipeCatalyst(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.wiremilldouble)),
		WireMillRecipeCategory.UID);
	// 3X
	registration.addRecipeCatalyst(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.wiremilltriple)),
		WireMillRecipeCategory.UID);

	/* Mineral Crusher */

	// 1X
	registration.addRecipeCatalyst(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralcrusher)),
		MineralCrusherRecipeCategory.UID);
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
	registration.addRecipeCatalyst(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralgrinder)),
		MineralGrinderRecipeCategory.UID);
	// 2X
	registration.addRecipeCatalyst(
		new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralgrinderdouble)),
		MineralGrinderRecipeCategory.UID);
	// 3X
	registration.addRecipeCatalyst(
		new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralgrindertriple)),
		MineralGrinderRecipeCategory.UID);

	/* Oxidation Furnace */

	registration.addRecipeCatalyst(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.oxidationfurnace)),
		OxidationFurnaceRecipeCategory.UID);

	/* Energized Alloyer */

	registration.addRecipeCatalyst(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.energizedalloyer)),
		EnergizedAlloyerRecipeCategory.UID);

	/* 5x Ore Processing */

	registration.addRecipeCatalyst(
		new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chemicalcrystallizer)),
		X5OreProcessingRecipeCategory.UID);

	registration.addRecipeCatalyst(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chemicalmixer)),
		X5OreProcessingRecipeCategory.UID);

	/* Chemical Mixer */

	registration.addRecipeCatalyst(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chemicalmixer)),
		ChemicalMixerRecipeCategory.UID);

	/* Fermentation Chamber */

	registration.addRecipeCatalyst(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.fermentationplant)),
		FermentationPlantRecipeCategory.UID);

    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
	PsuedoRecipes.addElectrodynamicsRecipes();
	Minecraft mc = Minecraft.getInstance();
	ClientWorld world = Objects.requireNonNull(mc.world);

	/* Electrodynamics */

	// Electric Furnace
	// This is broken for the 3.0 gradle mappings and there isn't really a fix for
	// it. It works on the 4.0
	// version, so if we ever update then it will work!
	@SuppressWarnings("unchecked")
	Set<FurnaceRecipe> electricFurnaceRecipes = ImmutableSet.copyOf(world.getRecipeManager()
		.getRecipesForType((IRecipeType<FurnaceRecipe>) Registry.RECIPE_TYPE.getOrDefault(VanillaRecipeCategoryUid.FURNACE)));

	registration.addRecipes(electricFurnaceRecipes, ElectricFurnaceRecipeCategory.UID);

	// Wire Mill
	Set<O2ORecipe> wireMillRecipes = ImmutableSet.copyOf(world.getRecipeManager().getRecipesForType(ElectrodynamicsRecipeInit.WIRE_MILL_TYPE));
	registration.addRecipes(wireMillRecipes, WireMillRecipeCategory.UID);

	// Mineral Crusher
	Set<O2ORecipe> mineralCrusherRecipes = ImmutableSet
		.copyOf(world.getRecipeManager().getRecipesForType(ElectrodynamicsRecipeInit.MINERAL_CRUSHER_TYPE));
	registration.addRecipes(mineralCrusherRecipes, MineralCrusherRecipeCategory.UID);

	// Mineral Grinder
	Set<O2ORecipe> mineralGrinderRecipes = ImmutableSet
		.copyOf(world.getRecipeManager().getRecipesForType(ElectrodynamicsRecipeInit.MINERAL_GRINDER_TYPE));
	registration.addRecipes(mineralGrinderRecipes, MineralGrinderRecipeCategory.UID);

	// Oxidation Furnace
	Set<DO2ORecipe> oxidationFurnaceRecipes = ImmutableSet
		.copyOf(world.getRecipeManager().getRecipesForType(ElectrodynamicsRecipeInit.OXIDATION_FURNACE_TYPE));
	registration.addRecipes(oxidationFurnaceRecipes, OxidationFurnaceRecipeCategory.UID);

	// Energized Alloyer
	Set<DO2ORecipe> energizedAlloyerRecipes = ImmutableSet
		.copyOf(world.getRecipeManager().getRecipesForType(ElectrodynamicsRecipeInit.ENERGIZED_ALLOYER_TYPE));
	registration.addRecipes(energizedAlloyerRecipes, EnergizedAlloyerRecipeCategory.UID);

	// 5x Ore Processing
	Set<Psuedo5XRecipe> x5Recipes = new HashSet<>(PsuedoRecipes.X5_ORE_RECIPES);

	registration.addRecipes(x5Recipes, X5OreProcessingRecipeCategory.UID);

	// Chemical Mixer
	Set<FluidItem2FluidRecipe> chemicalMixerRecipes = ImmutableSet
		.copyOf(world.getRecipeManager().getRecipesForType(ElectrodynamicsRecipeInit.CHEMICAL_MIXER_TYPE));

	registration.addRecipes(chemicalMixerRecipes, ChemicalMixerRecipeCategory.UID);

	// Fermentation Chamber
	Set<FluidItem2FluidRecipe> fermenterRecipes = ImmutableSet
		.copyOf(world.getRecipeManager().getRecipesForType(ElectrodynamicsRecipeInit.FERMENTATION_PLANT_TYPE));

	registration.addRecipes(fermenterRecipes, FermentationPlantRecipeCategory.UID);

	electrodynamicsInfoTabs(registration);

    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
	// Electric Furnace
	registration.addRecipeCategories(new ElectricFurnaceRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

	// Wire Mill
	registration.addRecipeCategories(new WireMillRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

	// Mineral Grinder
	registration.addRecipeCategories(new MineralGrinderRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

	// Mineral Crusher
	registration.addRecipeCategories(new MineralCrusherRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

	// Oxidation Furnace
	registration.addRecipeCategories(new OxidationFurnaceRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

	// Energized Alloyer
	registration.addRecipeCategories(new EnergizedAlloyerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

	// 5x Ore Processing
	registration.addRecipeCategories(new X5OreProcessingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

	// Chemical Mixer
	registration.addRecipeCategories(new ChemicalMixerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

	// Fermentation Chamber
	registration.addRecipeCategories(new FermentationPlantRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registry) {
	int[] o2oarrowLoc = { 80 + 5, 35, 22, 15 };

	// Each click area needs to be tied to a unique machine Screen class. Otherwise
	// you will get multiple machines
	// popping up as with the O2O recipes for example

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
		OxidationFurnaceRecipeCategory.UID, EnergizedAlloyerRecipeCategory.UID);

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
	registry.addRecipeClickArea(ScreenMineralWasher.class, 45, 35, o2oarrowLoc[2], o2oarrowLoc[3], X5OreProcessingRecipeCategory.UID);

	/* Chemical Crystalizer */
	registry.addRecipeClickArea(ScreenChemicalCrystallizer.class, 45, 35, o2oarrowLoc[2], o2oarrowLoc[3], X5OreProcessingRecipeCategory.UID);
    }

    public static ResourceLocation[] getO2OGuiScreens() {

	ArrayList<ResourceLocation> locations = new ArrayList<>();

	locations.add(WireMillRecipeCategory.UID);
	locations.add(MineralGrinderRecipeCategory.UID);
	locations.add(MineralCrusherRecipeCategory.UID);

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

    }

}
