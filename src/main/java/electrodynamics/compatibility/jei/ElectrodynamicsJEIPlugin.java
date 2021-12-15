package electrodynamics.compatibility.jei;

import java.util.ArrayList;
import java.util.List;
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
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.categories.fluid2item.Fluid2ItemRecipe;
import electrodynamics.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipe;
import electrodynamics.common.recipe.categories.item2item.Item2ItemRecipe;
import electrodynamics.compatibility.jei.recipecategories.fluid2item.specificmachines.ChemicalCrystallizerRecipeCategory;
import electrodynamics.compatibility.jei.recipecategories.fluiditem2fluid.specificmachines.ChemicalMixerRecipeCategory;
import electrodynamics.compatibility.jei.recipecategories.fluiditem2fluid.specificmachines.FermentationPlantRecipeCategory;
import electrodynamics.compatibility.jei.recipecategories.fluiditem2fluid.specificmachines.MineralWasherRecipeCategory;
import electrodynamics.compatibility.jei.recipecategories.item2item.specificmachines.EnergizedAlloyerRecipeCategory;
import electrodynamics.compatibility.jei.recipecategories.item2item.specificmachines.LatheRecipeCategory;
import electrodynamics.compatibility.jei.recipecategories.item2item.specificmachines.MineralCrusherRecipeCategory;
import electrodynamics.compatibility.jei.recipecategories.item2item.specificmachines.MineralGrinderRecipeCategory;
import electrodynamics.compatibility.jei.recipecategories.item2item.specificmachines.OxidationFurnaceRecipeCategory;
import electrodynamics.compatibility.jei.recipecategories.item2item.specificmachines.ReinforcedAlloyerRecipeCategory;
import electrodynamics.compatibility.jei.recipecategories.item2item.specificmachines.WireMillRecipeCategory;
import electrodynamics.compatibility.jei.recipecategories.modfurnace.specificmachines.ElectricFurnaceRecipeCategory;
import electrodynamics.compatibility.jei.utils.InfoItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;

@JeiPlugin
public class ElectrodynamicsJEIPlugin implements IModPlugin {

    public static List<ResourceLocation> O2O_CLICK_AREAS = new ArrayList<>();
    public static List<ResourceLocation> DO2O_CLICK_AREAS = new ArrayList<>();

    @Override
    public ResourceLocation getPluginUid() {
	return new ResourceLocation(electrodynamics.api.References.ID, "elecdyn_jei_plugin");
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {

	/* Electric Furnace */
	// 1X
	registration.addRecipeCatalyst(ElectricFurnaceRecipeCategory.INPUT_MACHINE, ElectricFurnaceRecipeCategory.UID);
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
	registration.addRecipeCatalyst(WireMillRecipeCategory.INPUT_MACHINE, WireMillRecipeCategory.UID);
	// 2X
	registration.addRecipeCatalyst(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.wiremilldouble)),
		WireMillRecipeCategory.UID);
	// 3X
	registration.addRecipeCatalyst(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.wiremilltriple)),
		WireMillRecipeCategory.UID);

	/* Mineral Crusher */

	// 1X
	registration.addRecipeCatalyst(MineralCrusherRecipeCategory.INPUT_MACHINE, MineralCrusherRecipeCategory.UID);
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
	registration.addRecipeCatalyst(MineralGrinderRecipeCategory.INPUT_MACHINE, MineralGrinderRecipeCategory.UID);
	// 2X
	registration.addRecipeCatalyst(
		new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralgrinderdouble)),
		MineralGrinderRecipeCategory.UID);
	// 3X
	registration.addRecipeCatalyst(
		new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralgrindertriple)),
		MineralGrinderRecipeCategory.UID);

	/* Oxidation Furnace */

	registration.addRecipeCatalyst(OxidationFurnaceRecipeCategory.INPUT_MACHINE, OxidationFurnaceRecipeCategory.UID);

	/* Energized Alloyer */

	registration.addRecipeCatalyst(EnergizedAlloyerRecipeCategory.INPUT_MACHINE, EnergizedAlloyerRecipeCategory.UID);

	/* Lathe */

	registration.addRecipeCatalyst(LatheRecipeCategory.INPUT_MACHINE, LatheRecipeCategory.UID);

	/* Mineral Washer */

	registration.addRecipeCatalyst(MineralWasherRecipeCategory.INPUT_MACHINE, MineralWasherRecipeCategory.UID);

	/* Chemical Crystallizer */

	registration.addRecipeCatalyst(ChemicalCrystallizerRecipeCategory.INPUT_MACHINE, ChemicalCrystallizerRecipeCategory.UID);

	/* Chemical Mixer */

	registration.addRecipeCatalyst(ChemicalMixerRecipeCategory.INPUT_MACHINE, ChemicalMixerRecipeCategory.UID);

	/* Fermentation Chamber */

	registration.addRecipeCatalyst(FermentationPlantRecipeCategory.INPUT_MACHINE, FermentationPlantRecipeCategory.UID);

	/* Reinforced Alloyer */

	registration.addRecipeCatalyst(ReinforcedAlloyerRecipeCategory.INPUT_MACHINE, ReinforcedAlloyerRecipeCategory.UID);

    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
	InfoItems.addInfoItems();
	Minecraft mc = Minecraft.getInstance();
	ClientLevel world = Objects.requireNonNull(mc.level);
	RecipeManager recipeManager = world.getRecipeManager();

	// Electric Furnace
	Set<SmeltingRecipe> electricFurnaceRecipes = ImmutableSet.copyOf(recipeManager.getAllRecipesFor(RecipeType.SMELTING));
	registration.addRecipes(electricFurnaceRecipes, ElectricFurnaceRecipeCategory.UID);

	// Wire Mill
	Set<Item2ItemRecipe> wireMillRecipes = ImmutableSet.copyOf(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.WIRE_MILL_TYPE));
	registration.addRecipes(wireMillRecipes, WireMillRecipeCategory.UID);

	// Mineral Crusher
	Set<Item2ItemRecipe> mineralCrusherRecipes = ImmutableSet
		.copyOf(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.MINERAL_CRUSHER_TYPE));
	registration.addRecipes(mineralCrusherRecipes, MineralCrusherRecipeCategory.UID);

	// Mineral Grinder
	Set<Item2ItemRecipe> mineralGrinderRecipes = ImmutableSet
		.copyOf(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.MINERAL_GRINDER_TYPE));
	registration.addRecipes(mineralGrinderRecipes, MineralGrinderRecipeCategory.UID);

	// Oxidation Furnace
	Set<Item2ItemRecipe> oxidationFurnaceRecipes = ImmutableSet
		.copyOf(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.OXIDATION_FURNACE_TYPE));
	registration.addRecipes(oxidationFurnaceRecipes, OxidationFurnaceRecipeCategory.UID);

	// Energized Alloyer
	Set<Item2ItemRecipe> energizedAlloyerRecipes = ImmutableSet
		.copyOf(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.ENERGIZED_ALLOYER_TYPE));
	registration.addRecipes(energizedAlloyerRecipes, EnergizedAlloyerRecipeCategory.UID);

	// Lathe
	Set<Item2ItemRecipe> latheRecipes = ImmutableSet.copyOf(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.LATHE_TYPE));
	registration.addRecipes(latheRecipes, LatheRecipeCategory.UID);

	// Mineral Washer
	Set<FluidItem2FluidRecipe> mineralWasherRecipes = ImmutableSet
		.copyOf(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.MINERAL_WASHER_TYPE));
	registration.addRecipes(mineralWasherRecipes, MineralWasherRecipeCategory.UID);

	// Chemical Crystallizer
	Set<Fluid2ItemRecipe> chemicalCrystallizerRecipes = ImmutableSet
		.copyOf(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.CHEMICAL_CRYSTALIZER_TYPE));
	registration.addRecipes(chemicalCrystallizerRecipes, ChemicalCrystallizerRecipeCategory.UID);

	// Chemical Mixer
	Set<FluidItem2FluidRecipe> chemicalMixerRecipes = ImmutableSet
		.copyOf(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.CHEMICAL_MIXER_TYPE));
	registration.addRecipes(chemicalMixerRecipes, ChemicalMixerRecipeCategory.UID);

	// Fermentation Chamber
	Set<FluidItem2FluidRecipe> fermenterRecipes = ImmutableSet
		.copyOf(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.FERMENTATION_PLANT_TYPE));
	registration.addRecipes(fermenterRecipes, FermentationPlantRecipeCategory.UID);

	// Reinforced Alloyer
	Set<Item2ItemRecipe> reinforcedAlloyerRecipes = ImmutableSet
		.copyOf(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.REINFORCED_ALLOYER_TYPE));
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
	registration.addRecipeCategories(new LatheRecipeCategory(guiHelper));

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

	/* O2O Machines */

	registry.addRecipeClickArea(ScreenO2OProcessor.class, 48, 35, 22, 15, O2O_CLICK_AREAS.toArray(new ResourceLocation[O2O_CLICK_AREAS.size()]));
	registry.addRecipeClickArea(ScreenO2OProcessorDouble.class, 48, 25, 22, 35,
		O2O_CLICK_AREAS.toArray(new ResourceLocation[O2O_CLICK_AREAS.size()]));
	registry.addRecipeClickArea(ScreenO2OProcessorTriple.class, 48, 25, 22, 55,
		O2O_CLICK_AREAS.toArray(new ResourceLocation[O2O_CLICK_AREAS.size()]));

	/* DO2O Machines */
	registry.addRecipeClickArea(ScreenDO2OProcessor.class, 48, 35, 22, 15,
		DO2O_CLICK_AREAS.toArray(new ResourceLocation[DO2O_CLICK_AREAS.size()]));

	/* Electric Furnace Click Area */

	registry.addRecipeClickArea(ScreenElectricFurnace.class, 85, 35, 22, 15, ElectricFurnaceRecipeCategory.UID);
	registry.addRecipeClickArea(ScreenElectricFurnaceDouble.class, 85, 25, 22, 35, ElectricFurnaceRecipeCategory.UID);
	registry.addRecipeClickArea(ScreenElectricFurnaceTriple.class, 85, 25, 22, 55, ElectricFurnaceRecipeCategory.UID);

	/* Chemical Mixer */
	registry.addRecipeClickArea(ScreenChemicalMixer.class, 97, 31, 22, 15, ChemicalMixerRecipeCategory.UID);

	/* Fermentation Plant */
	registry.addRecipeClickArea(ScreenFermentationPlant.class, 97, 31, 22, 15, FermentationPlantRecipeCategory.UID);

	/* Mineral Washer */
	registry.addRecipeClickArea(ScreenMineralWasher.class, 97, 31, 22, 15, MineralWasherRecipeCategory.UID);

	/* Chemical Crystalizer */
	registry.addRecipeClickArea(ScreenChemicalCrystallizer.class, 41, 35, 22, 15, ChemicalCrystallizerRecipeCategory.UID);
    }

    private static void electrodynamicsInfoTabs(IRecipeRegistration registration) {
	String temp;
	for (ItemStack item : InfoItems.ITEMS) {
	    temp = item.getItem().toString();
	    registration.addIngredientInfo(item, VanillaTypes.ITEM, new TranslatableComponent("info.jei.block." + temp));
	}

	registration.addIngredientInfo(new ItemStack(DeferredRegisters.COMPOSITE_HELMET.get()), VanillaTypes.ITEM,
		new TranslatableComponent("info.jei.item.compositearmor"));
	registration.addIngredientInfo(new ItemStack(DeferredRegisters.COMPOSITE_CHESTPLATE.get()), VanillaTypes.ITEM,
		new TranslatableComponent("info.jei.item.compositearmor"));
	registration.addIngredientInfo(new ItemStack(DeferredRegisters.COMPOSITE_LEGGINGS.get()), VanillaTypes.ITEM,
		new TranslatableComponent("info.jei.item.compositearmor"));
	registration.addIngredientInfo(new ItemStack(DeferredRegisters.COMPOSITE_BOOTS.get()), VanillaTypes.ITEM,
		new TranslatableComponent("info.jei.item.compositearmor"));
	registration.addIngredientInfo(new ItemStack(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeItemUpgrade.iteminput)), VanillaTypes.ITEM,
		new TranslatableComponent("info.jei.item.upgradeiteminput"));
	registration.addIngredientInfo(new ItemStack(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeItemUpgrade.itemoutput)), VanillaTypes.ITEM,
		new TranslatableComponent("info.jei.item.upgradeitemoutput"));
    }

    public static void addO2OClickArea(ResourceLocation loc) {
	O2O_CLICK_AREAS.add(loc);
    }

    public static void addDO2OClickArea(ResourceLocation loc) {
	DO2O_CLICK_AREAS.add(loc);
    }

}
