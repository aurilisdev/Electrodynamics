package electrodynamics.compatibility.jei;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.client.screen.tile.ScreenChemicalCrystallizer;
import electrodynamics.client.screen.tile.ScreenChemicalMixer;
import electrodynamics.client.screen.tile.ScreenDO2OProcessor;
import electrodynamics.client.screen.tile.ScreenElectricFurnace;
import electrodynamics.client.screen.tile.ScreenElectricFurnaceDouble;
import electrodynamics.client.screen.tile.ScreenElectricFurnaceTriple;
import electrodynamics.client.screen.tile.ScreenFermentationPlant;
import electrodynamics.client.screen.tile.ScreenMineralWasher;
import electrodynamics.client.screen.tile.ScreenO2OProcessor;
import electrodynamics.client.screen.tile.ScreenO2OProcessorDouble;
import electrodynamics.client.screen.tile.ScreenO2OProcessorTriple;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.reloadlistener.CombustionFuelRegister;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.electricitygrid.generators.TileCoalGenerator;
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
import electrodynamics.compatibility.jei.recipecategories.modfurnace.ElectricFurnaceRecipeCategory;
import electrodynamics.compatibility.jei.recipecategories.utils.psuedorecipes.ElectrodynamicsPsuedoRecipes;
import electrodynamics.compatibility.jei.screenhandlers.ScreenHandlerGuidebook;
import electrodynamics.compatibility.jei.screenhandlers.ScreenHandlerMaterialScreen;
import electrodynamics.compatibility.jei.utils.RecipeType;
import electrodynamics.prefab.screen.types.GenericMaterialScreen;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.object.CombustionFuelSource;
import electrodynamics.registers.ElectrodynamicsBlocks;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IAdvancedRegistration;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fluids.FluidStack;

@JeiPlugin
public class ElectrodynamicsJEIPlugin implements IModPlugin {

	public static List<ResourceLocation> O2O_CLICK_AREAS = new ArrayList<>();
	public static List<ResourceLocation> DO2O_CLICK_AREAS = new ArrayList<>();

	private static final int FULL_FLUID_SQUARE = 1600;

	// private static IJeiRuntime RUNTIME = null;

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(electrodynamics.api.References.ID, "jei");
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {

		registration.addRecipeCatalyst(ElectricFurnaceRecipeCategory.INPUT_MACHINE, ElectricFurnaceRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(new ItemStack(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricfurnacedouble)), ElectricFurnaceRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(new ItemStack(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricfurnacetriple)), ElectricFurnaceRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(WireMillRecipeCategory.INPUT_MACHINE, WireMillRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(new ItemStack(ElectrodynamicsBlocks.getBlock(SubtypeMachine.wiremilldouble)), WireMillRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(new ItemStack(ElectrodynamicsBlocks.getBlock(SubtypeMachine.wiremilltriple)), WireMillRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(MineralCrusherRecipeCategory.INPUT_MACHINE, MineralCrusherRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(new ItemStack(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralcrusherdouble)), MineralCrusherRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(new ItemStack(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralcrushertriple)), MineralCrusherRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(MineralGrinderRecipeCategory.INPUT_MACHINE, MineralGrinderRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(new ItemStack(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralgrinderdouble)), MineralGrinderRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(new ItemStack(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralgrindertriple)), MineralGrinderRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(OxidationFurnaceRecipeCategory.INPUT_MACHINE, OxidationFurnaceRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(EnergizedAlloyerRecipeCategory.INPUT_MACHINE, EnergizedAlloyerRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(LatheRecipeCategory.INPUT_MACHINE, LatheRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(MineralWasherRecipeCategory.INPUT_MACHINE, MineralWasherRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(ChemicalCrystallizerRecipeCategory.INPUT_MACHINE, ChemicalCrystallizerRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(ChemicalMixerRecipeCategory.INPUT_MACHINE, ChemicalMixerRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(FermentationPlantRecipeCategory.INPUT_MACHINE, FermentationPlantRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(ReinforcedAlloyerRecipeCategory.INPUT_MACHINE, ReinforcedAlloyerRecipeCategory.RECIPE_TYPE.getUid());

	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		Minecraft mc = Minecraft.getInstance();
		ClientWorld world = Objects.requireNonNull(mc.level);
		RecipeManager recipeManager = world.getRecipeManager();

		ElectrodynamicsPsuedoRecipes.initRecipes();

		// Electric Furnace
		registration.addRecipes(recipeManager.getAllRecipesFor(IRecipeType.SMELTING), ElectricFurnaceRecipeCategory.RECIPE_TYPE.getUid());

		// Wire Mill
		registration.addRecipes(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.WIRE_MILL_TYPE), WireMillRecipeCategory.RECIPE_TYPE.getUid());

		// Mineral Crusher
		registration.addRecipes(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.MINERAL_CRUSHER_TYPE), MineralCrusherRecipeCategory.RECIPE_TYPE.getUid());

		// Mineral Grinder
		registration.addRecipes(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.MINERAL_GRINDER_TYPE), MineralGrinderRecipeCategory.RECIPE_TYPE.getUid());

		// Oxidation Furnace
		registration.addRecipes(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.OXIDATION_FURNACE_TYPE), OxidationFurnaceRecipeCategory.RECIPE_TYPE.getUid());

		// Energized Alloyer
		registration.addRecipes(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.ENERGIZED_ALLOYER_TYPE), EnergizedAlloyerRecipeCategory.RECIPE_TYPE.getUid());

		// Lathe
		registration.addRecipes(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.LATHE_TYPE), LatheRecipeCategory.RECIPE_TYPE.getUid());

		// Mineral Washer
		registration.addRecipes(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.MINERAL_WASHER_TYPE), MineralWasherRecipeCategory.RECIPE_TYPE.getUid());

		// Chemical Crystallizer
		registration.addRecipes(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.CHEMICAL_CRYSTALIZER_TYPE), ChemicalCrystallizerRecipeCategory.RECIPE_TYPE.getUid());

		// Chemical Mixer
		registration.addRecipes(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.CHEMICAL_MIXER_TYPE), ChemicalMixerRecipeCategory.RECIPE_TYPE.getUid());

		// Fermentation Chamber
		registration.addRecipes(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.FERMENTATION_PLANT_TYPE), FermentationPlantRecipeCategory.RECIPE_TYPE.getUid());

		// Reinforced Alloyer
		registration.addRecipes(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.REINFORCED_ALLOYER_TYPE), ReinforcedAlloyerRecipeCategory.RECIPE_TYPE.getUid());

		electrodynamicsInfoTabs(registration);

	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {

		IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
		registration.addRecipeCategories(new ElectricFurnaceRecipeCategory(guiHelper));
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

	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registry) {
		registry.addRecipeClickArea(ScreenO2OProcessor.class, 48, 35, 22, 15, O2O_CLICK_AREAS.toArray(new ResourceLocation[O2O_CLICK_AREAS.size()]));
		registry.addRecipeClickArea(ScreenO2OProcessorDouble.class, 48, 25, 22, 35, O2O_CLICK_AREAS.toArray(new ResourceLocation[O2O_CLICK_AREAS.size()]));
		registry.addRecipeClickArea(ScreenO2OProcessorTriple.class, 48, 25, 22, 55, O2O_CLICK_AREAS.toArray(new ResourceLocation[O2O_CLICK_AREAS.size()]));
		registry.addRecipeClickArea(ScreenDO2OProcessor.class, 48, 35, 22, 15, DO2O_CLICK_AREAS.toArray(new ResourceLocation[DO2O_CLICK_AREAS.size()]));
		registry.addRecipeClickArea(ScreenElectricFurnace.class, 85, 35, 22, 15, ElectricFurnaceRecipeCategory.RECIPE_TYPE.getUid());
		registry.addRecipeClickArea(ScreenElectricFurnaceDouble.class, 85, 25, 22, 35, ElectricFurnaceRecipeCategory.RECIPE_TYPE.getUid());
		registry.addRecipeClickArea(ScreenElectricFurnaceTriple.class, 85, 25, 22, 55, ElectricFurnaceRecipeCategory.RECIPE_TYPE.getUid());
		registry.addRecipeClickArea(ScreenChemicalMixer.class, 97, 31, 22, 15, ChemicalMixerRecipeCategory.RECIPE_TYPE.getUid());
		registry.addRecipeClickArea(ScreenFermentationPlant.class, 97, 31, 22, 15, FermentationPlantRecipeCategory.RECIPE_TYPE.getUid());
		registry.addRecipeClickArea(ScreenMineralWasher.class, 97, 31, 22, 15, MineralWasherRecipeCategory.RECIPE_TYPE.getUid());
		registry.addRecipeClickArea(ScreenChemicalCrystallizer.class, 41, 35, 22, 15, ChemicalCrystallizerRecipeCategory.RECIPE_TYPE.getUid());

		registry.addGenericGuiContainerHandler(ScreenGuidebook.class, new ScreenHandlerGuidebook());
		registry.addGenericGuiContainerHandler(GenericMaterialScreen.class, new ScreenHandlerMaterialScreen());
	}

	private static void electrodynamicsInfoTabs(IRecipeRegistration registration) {
		// Items
		for (Item item : TileCoalGenerator.getValidItems()) {
			ItemStack fuelStack = new ItemStack(item);
			registration.addIngredientInfo(fuelStack, VanillaTypes.ITEM, ElectroTextUtils.jeiItemTranslated("coalgeneratorfuelsource", ChatFormatter.getChatDisplayShort(ForgeHooks.getBurnTime(fuelStack, null) / 20.0, DisplayUnit.TIME_SECONDS)));
		}

		// Fluids
		for (CombustionFuelSource fuel : CombustionFuelRegister.INSTANCE.getFuels()) {
			for (FluidStack fluid : fuel.getFuels()) {
				registration.addIngredientInfo(new FluidStack(fluid, FULL_FLUID_SQUARE), VanillaTypes.FLUID, ElectroTextUtils.jeiFluidTranslated("combustionchamberfuel", ChatFormatter.getChatDisplayShort(fuel.getPowerMultiplier() * Constants.COMBUSTIONCHAMBER_JOULES_PER_TICK * 20 / 1000.0, DisplayUnit.WATT), ChatFormatter.formatFluidMilibuckets(fluid.getAmount())));
			}
		}

	}

	public static void addO2OClickArea(RecipeType<?> type) {
		O2O_CLICK_AREAS.add(type.getUid());
	}

	public static void addDO2OClickArea(RecipeType<?> type) {
		DO2O_CLICK_AREAS.add(type.getUid());
	}

	@Override
	public void registerAdvanced(IAdvancedRegistration registration) {

		// registration.addRecipeManagerPlugin(new RecipeManagerPluginCanister());

	}

}
