package electrodynamics.compatibility.jei;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.client.screen.tile.ScreenChemicalCrystallizer;
import electrodynamics.client.screen.tile.ScreenChemicalMixer;
import electrodynamics.client.screen.tile.ScreenDO2OProcessor;
import electrodynamics.client.screen.tile.ScreenElectricFurnace;
import electrodynamics.client.screen.tile.ScreenElectricFurnaceDouble;
import electrodynamics.client.screen.tile.ScreenElectricFurnaceTriple;
import electrodynamics.client.screen.tile.ScreenElectrolyticSeparator;
import electrodynamics.client.screen.tile.ScreenFermentationPlant;
import electrodynamics.client.screen.tile.ScreenMineralWasher;
import electrodynamics.client.screen.tile.ScreenO2OProcessor;
import electrodynamics.client.screen.tile.ScreenO2OProcessorDouble;
import electrodynamics.client.screen.tile.ScreenO2OProcessorTriple;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.categories.fluid2fluid.specificmachines.ElectrolyticSeparatorRecipe;
import electrodynamics.common.recipe.categories.fluid2item.specificmachines.ChemicalCrystalizerRecipe;
import electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines.ChemicalMixerRecipe;
import electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines.FermentationPlantRecipe;
import electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines.MineralWasherRecipe;
import electrodynamics.common.recipe.categories.item2item.specificmachines.EnergizedAlloyerRecipe;
import electrodynamics.common.recipe.categories.item2item.specificmachines.LatheRecipe;
import electrodynamics.common.recipe.categories.item2item.specificmachines.MineralCrusherRecipe;
import electrodynamics.common.recipe.categories.item2item.specificmachines.MineralGrinderRecipe;
import electrodynamics.common.recipe.categories.item2item.specificmachines.OxidationFurnaceRecipe;
import electrodynamics.common.recipe.categories.item2item.specificmachines.ReinforcedAlloyerRecipe;
import electrodynamics.common.recipe.categories.item2item.specificmachines.WireMillRecipe;
import electrodynamics.common.reloadlistener.CombustionFuelRegister;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.generators.TileCoalGenerator;
import electrodynamics.compatibility.jei.recipecategories.fluid2fluid.specificmachines.ElectrolyticSeparatorRecipeCategory;
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
import electrodynamics.compatibility.jei.recipecategories.modfurnace.specificmachines.ElectricArcFurnaceRecipeCategory;
import electrodynamics.compatibility.jei.recipecategories.modfurnace.specificmachines.ElectricFurnaceRecipeCategory;
import electrodynamics.compatibility.jei.screenhandlers.ScreenHandlerGuidebook;
import electrodynamics.prefab.utilities.TextUtils;
import electrodynamics.prefab.utilities.object.CombustionFuelSource;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fluids.FluidStack;

@JeiPlugin
public class ElectrodynamicsJEIPlugin implements IModPlugin {

	public static List<mezz.jei.api.recipe.RecipeType<?>> O2O_CLICK_AREAS = new ArrayList<>();
	public static List<mezz.jei.api.recipe.RecipeType<?>> DO2O_CLICK_AREAS = new ArrayList<>();

	private static final int FULL_FLUID_SQUARE = 1600;

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(electrodynamics.api.References.ID, "jei");
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {

		registration.addRecipeCatalyst(ElectricFurnaceRecipeCategory.INPUT_MACHINE, ElectricFurnaceRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(new ItemStack(electrodynamics.registers.UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electricfurnacedouble)), ElectricFurnaceRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(new ItemStack(electrodynamics.registers.UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electricfurnacetriple)), ElectricFurnaceRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(WireMillRecipeCategory.INPUT_MACHINE, WireMillRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(new ItemStack(electrodynamics.registers.UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.wiremilldouble)), WireMillRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(new ItemStack(electrodynamics.registers.UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.wiremilltriple)), WireMillRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(MineralCrusherRecipeCategory.INPUT_MACHINE, MineralCrusherRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(new ItemStack(electrodynamics.registers.UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.mineralcrusherdouble)), MineralCrusherRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(new ItemStack(electrodynamics.registers.UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.mineralcrushertriple)), MineralCrusherRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(MineralGrinderRecipeCategory.INPUT_MACHINE, MineralGrinderRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(new ItemStack(electrodynamics.registers.UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.mineralgrinderdouble)), MineralGrinderRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(new ItemStack(electrodynamics.registers.UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.mineralgrindertriple)), MineralGrinderRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(ElectricArcFurnaceRecipeCategory.INPUT_MACHINE, ElectricArcFurnaceRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(new ItemStack(electrodynamics.registers.UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electricarcfurnacedouble)), ElectricArcFurnaceRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(new ItemStack(electrodynamics.registers.UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electricarcfurnacetriple)), ElectricArcFurnaceRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(OxidationFurnaceRecipeCategory.INPUT_MACHINE, OxidationFurnaceRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(EnergizedAlloyerRecipeCategory.INPUT_MACHINE, EnergizedAlloyerRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(LatheRecipeCategory.INPUT_MACHINE, LatheRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(MineralWasherRecipeCategory.INPUT_MACHINE, MineralWasherRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(ChemicalCrystallizerRecipeCategory.INPUT_MACHINE, ChemicalCrystallizerRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(ChemicalMixerRecipeCategory.INPUT_MACHINE, ChemicalMixerRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(FermentationPlantRecipeCategory.INPUT_MACHINE, FermentationPlantRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(ReinforcedAlloyerRecipeCategory.INPUT_MACHINE, ReinforcedAlloyerRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(ElectrolyticSeparatorRecipeCategory.INPUT_MACHINE, ElectrolyticSeparatorRecipeCategory.RECIPE_TYPE);

	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		Minecraft mc = Minecraft.getInstance();
		ClientLevel world = Objects.requireNonNull(mc.level);
		RecipeManager recipeManager = world.getRecipeManager();

		// Electric Furnace
		List<SmeltingRecipe> electricFurnaceRecipes = recipeManager.getAllRecipesFor(RecipeType.SMELTING);
		registration.addRecipes(ElectricFurnaceRecipeCategory.RECIPE_TYPE, electricFurnaceRecipes);

		List<BlastingRecipe> electricArcFurnaceRecipes = recipeManager.getAllRecipesFor(RecipeType.BLASTING);
		registration.addRecipes(ElectricArcFurnaceRecipeCategory.RECIPE_TYPE, electricArcFurnaceRecipes);

		// Wire Mill
		List<WireMillRecipe> wireMillRecipes = recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.WIRE_MILL_TYPE.get());
		registration.addRecipes(WireMillRecipeCategory.RECIPE_TYPE, wireMillRecipes);

		// Mineral Crusher
		List<MineralCrusherRecipe> mineralCrusherRecipes = recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.MINERAL_CRUSHER_TYPE.get());
		registration.addRecipes(MineralCrusherRecipeCategory.RECIPE_TYPE, mineralCrusherRecipes);

		// Mineral Grinder
		List<MineralGrinderRecipe> mineralGrinderRecipes = recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.MINERAL_GRINDER_TYPE.get());
		registration.addRecipes(MineralGrinderRecipeCategory.RECIPE_TYPE, mineralGrinderRecipes);

		// Oxidation Furnace
		List<OxidationFurnaceRecipe> oxidationFurnaceRecipes = recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.OXIDATION_FURNACE_TYPE.get());
		registration.addRecipes(OxidationFurnaceRecipeCategory.RECIPE_TYPE, oxidationFurnaceRecipes);

		// Energized Alloyer
		List<EnergizedAlloyerRecipe> energizedAlloyerRecipes = recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.ENERGIZED_ALLOYER_TYPE.get());
		registration.addRecipes(EnergizedAlloyerRecipeCategory.RECIPE_TYPE, energizedAlloyerRecipes);

		// Lathe
		List<LatheRecipe> latheRecipes = recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.LATHE_TYPE.get());
		registration.addRecipes(LatheRecipeCategory.RECIPE_TYPE, latheRecipes);

		// Mineral Washer
		List<MineralWasherRecipe> mineralWasherRecipes = recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.MINERAL_WASHER_TYPE.get());
		registration.addRecipes(MineralWasherRecipeCategory.RECIPE_TYPE, mineralWasherRecipes);

		// Chemical Crystallizer
		List<ChemicalCrystalizerRecipe> chemicalCrystallizerRecipes = recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.CHEMICAL_CRYSTALIZER_TYPE.get());
		registration.addRecipes(ChemicalCrystallizerRecipeCategory.RECIPE_TYPE, chemicalCrystallizerRecipes);

		// Chemical Mixer
		List<ChemicalMixerRecipe> chemicalMixerRecipes = recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.CHEMICAL_MIXER_TYPE.get());
		registration.addRecipes(ChemicalMixerRecipeCategory.RECIPE_TYPE, chemicalMixerRecipes);

		// Fermentation Chamber
		List<FermentationPlantRecipe> fermenterRecipes = recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.FERMENTATION_PLANT_TYPE.get());
		registration.addRecipes(FermentationPlantRecipeCategory.RECIPE_TYPE, fermenterRecipes);

		// Reinforced Alloyer
		List<ReinforcedAlloyerRecipe> reinforcedAlloyerRecipes = recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.REINFORCED_ALLOYER_TYPE.get());
		registration.addRecipes(ReinforcedAlloyerRecipeCategory.RECIPE_TYPE, reinforcedAlloyerRecipes);

		// Electrolytic Separator
		List<ElectrolyticSeparatorRecipe> electrolyticSeparatorRecipes = recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.ELECTROLYTIC_SEPERATOR_TYPE.get());
		registration.addRecipes(ElectrolyticSeparatorRecipeCategory.RECIPE_TYPE, electrolyticSeparatorRecipes);

		electrodynamicsInfoTabs(registration);

	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {

		IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
		registration.addRecipeCategories(new ElectricFurnaceRecipeCategory(guiHelper));
		registration.addRecipeCategories(new ElectricArcFurnaceRecipeCategory(guiHelper));
		registration.addRecipeCategories(new WireMillRecipeCategory(guiHelper));
		registration.addRecipeCategories(new MineralGrinderRecipeCategory(guiHelper));
		registration.addRecipeCategories(new MineralCrusherRecipeCategory(guiHelper));
		registration.addRecipeCategories(new OxidationFurnaceRecipeCategory(guiHelper));
		registration.addRecipeCategories(new EnergizedAlloyerRecipeCategory(guiHelper));
		registration.addRecipeCategories(new LatheRecipeCategory(guiHelper));
		registration.addRecipeCategories(new MineralWasherRecipeCategory(guiHelper));
		registration.addRecipeCategories(new ChemicalCrystallizerRecipeCategory(guiHelper));
		registration.addRecipeCategories(new ChemicalMixerRecipeCategory(guiHelper));
		registration.addRecipeCategories(new FermentationPlantRecipeCategory(guiHelper));
		registration.addRecipeCategories(new ReinforcedAlloyerRecipeCategory(guiHelper));
		registration.addRecipeCategories(new ElectrolyticSeparatorRecipeCategory(guiHelper));

	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registry) {
		registry.addRecipeClickArea(ScreenO2OProcessor.class, 48, 35, 22, 15, O2O_CLICK_AREAS.toArray(new mezz.jei.api.recipe.RecipeType[O2O_CLICK_AREAS.size()]));
		registry.addRecipeClickArea(ScreenO2OProcessorDouble.class, 48, 25, 22, 35, O2O_CLICK_AREAS.toArray(new mezz.jei.api.recipe.RecipeType[O2O_CLICK_AREAS.size()]));
		registry.addRecipeClickArea(ScreenO2OProcessorTriple.class, 48, 25, 22, 55, O2O_CLICK_AREAS.toArray(new mezz.jei.api.recipe.RecipeType[O2O_CLICK_AREAS.size()]));
		registry.addRecipeClickArea(ScreenDO2OProcessor.class, 48, 35, 22, 15, DO2O_CLICK_AREAS.toArray(new mezz.jei.api.recipe.RecipeType[DO2O_CLICK_AREAS.size()]));
		registry.addRecipeClickArea(ScreenElectricFurnace.class, 85, 35, 22, 15, ElectricFurnaceRecipeCategory.RECIPE_TYPE);
		registry.addRecipeClickArea(ScreenElectricFurnaceDouble.class, 85, 25, 22, 35, ElectricFurnaceRecipeCategory.RECIPE_TYPE);
		registry.addRecipeClickArea(ScreenElectricFurnaceTriple.class, 85, 25, 22, 55, ElectricFurnaceRecipeCategory.RECIPE_TYPE);
		registry.addRecipeClickArea(ScreenChemicalMixer.class, 97, 31, 22, 15, ChemicalMixerRecipeCategory.RECIPE_TYPE);
		registry.addRecipeClickArea(ScreenFermentationPlant.class, 97, 31, 22, 15, FermentationPlantRecipeCategory.RECIPE_TYPE);
		registry.addRecipeClickArea(ScreenMineralWasher.class, 97, 31, 22, 15, MineralWasherRecipeCategory.RECIPE_TYPE);
		registry.addRecipeClickArea(ScreenChemicalCrystallizer.class, 41, 35, 22, 15, ChemicalCrystallizerRecipeCategory.RECIPE_TYPE);
		registry.addRecipeClickArea(ScreenElectrolyticSeparator.class, 38, 30, 22, 15, ElectrolyticSeparatorRecipeCategory.RECIPE_TYPE);
		
		registry.addGenericGuiContainerHandler(ScreenGuidebook.class, new ScreenHandlerGuidebook());
	}

	private static void electrodynamicsInfoTabs(IRecipeRegistration registration) {
		// Items
		for (Item item : TileCoalGenerator.getValidItems()) {
			ItemStack fuelStack = new ItemStack(item);
			registration.addIngredientInfo(fuelStack, VanillaTypes.ITEM_STACK, TextUtils.jeiItemTranslated("coalgeneratorfuelsource", ForgeHooks.getBurnTime(fuelStack, null) / 20));
		}

		// Fluids
		for (CombustionFuelSource fuel : CombustionFuelRegister.INSTANCE.getFuels()) {
			for (FluidStack fluid : fuel.getFuels()) {
				registration.addIngredientInfo(new FluidStack(fluid, FULL_FLUID_SQUARE), ForgeTypes.FLUID_STACK, TextUtils.jeiFluidTranslated("combustionchamberfuel", fluid.getAmount(), fuel.getPowerMultiplier() * Constants.COMBUSTIONCHAMBER_JOULES_PER_TICK * 20 / 1000.0));
			}
		}

	}

	public static void addO2OClickArea(mezz.jei.api.recipe.RecipeType<?> type) {
		O2O_CLICK_AREAS.add(type);
	}

	public static void addDO2OClickArea(mezz.jei.api.recipe.RecipeType<?> type) {
		DO2O_CLICK_AREAS.add(type);
	}

}
