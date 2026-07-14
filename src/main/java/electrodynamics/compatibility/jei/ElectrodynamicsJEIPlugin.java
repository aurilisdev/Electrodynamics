package electrodynamics.compatibility.jei;

import java.util.Objects;

import javax.annotation.Nonnull;

import electrodynamics.Electrodynamics;
import electrodynamics.client.screen.tile.ScreenChemicalCrystallizer;
import electrodynamics.client.screen.tile.ScreenChemicalMixer;
import electrodynamics.client.screen.tile.ScreenElectricArcFurnace;
import electrodynamics.client.screen.tile.ScreenElectricArcFurnaceDouble;
import electrodynamics.client.screen.tile.ScreenElectricArcFurnaceTriple;
import electrodynamics.client.screen.tile.ScreenElectricFurnace;
import electrodynamics.client.screen.tile.ScreenElectricFurnaceDouble;
import electrodynamics.client.screen.tile.ScreenElectricFurnaceTriple;
import electrodynamics.client.screen.tile.ScreenElectrolyticSeparator;
import electrodynamics.client.screen.tile.ScreenFermentationPlant;
import electrodynamics.client.screen.tile.ScreenMineralWasher;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.reloadlistener.CombustionFuelRegister;
import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.common.tile.electricitygrid.generators.TileCoalGenerator;
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
import electrodynamics.compatibility.jei.recipecategories.modfurnace.ElectricArcFurnaceRecipeCategory;
import electrodynamics.compatibility.jei.recipecategories.modfurnace.ElectricFurnaceRecipeCategory;
import electrodynamics.compatibility.jei.recipecategories.utils.psuedorecipes.ElectrodynamicsPsuedoRecipes;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.object.CombustionFuelSource;
import electrodynamics.registers.ElectrodynamicsBlocks;
import electrodynamics.registers.ElectrodynamicsRecipies;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
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
import net.minecraftforge.fluids.FluidStack;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;

@JeiPlugin
public class ElectrodynamicsJEIPlugin implements IModPlugin {

	public static final ResourceLocation ID = Electrodynamics.rl("jei");

	private static final int FULL_FLUID_SQUARE = 1600;

	@Override
	public @Nonnull ResourceLocation getPluginUid() {
		return ID;
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {

		registration.addRecipeCatalyst(ElectricFurnaceRecipeCategory.INPUT_MACHINE, ElectricFurnaceRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(new ItemStack(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricfurnacedouble)), ElectricFurnaceRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(new ItemStack(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricfurnacetriple)), ElectricFurnaceRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(WireMillRecipeCategory.INPUT_MACHINE, WireMillRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(new ItemStack(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.wiremilldouble)), WireMillRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(new ItemStack(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.wiremilltriple)), WireMillRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(MineralCrusherRecipeCategory.INPUT_MACHINE, MineralCrusherRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(new ItemStack(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralcrusherdouble)), MineralCrusherRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(new ItemStack(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralcrushertriple)), MineralCrusherRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(MineralGrinderRecipeCategory.INPUT_MACHINE, MineralGrinderRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(new ItemStack(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralgrinderdouble)), MineralGrinderRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(new ItemStack(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralgrindertriple)), MineralGrinderRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(ElectricArcFurnaceRecipeCategory.INPUT_MACHINE, ElectricArcFurnaceRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(new ItemStack(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricarcfurnacedouble)), ElectricArcFurnaceRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(new ItemStack(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricarcfurnacetriple)), ElectricArcFurnaceRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(OxidationFurnaceRecipeCategory.INPUT_MACHINE, OxidationFurnaceRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(EnergizedAlloyerRecipeCategory.INPUT_MACHINE, EnergizedAlloyerRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(LatheRecipeCategory.INPUT_MACHINE, LatheRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(MineralWasherRecipeCategory.INPUT_MACHINE, MineralWasherRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(ChemicalCrystallizerRecipeCategory.INPUT_MACHINE, ChemicalCrystallizerRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(ChemicalMixerRecipeCategory.INPUT_MACHINE, ChemicalMixerRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(FermentationPlantRecipeCategory.INPUT_MACHINE, FermentationPlantRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(ReinforcedAlloyerRecipeCategory.INPUT_MACHINE, ReinforcedAlloyerRecipeCategory.RECIPE_TYPE.getUid());
		registration.addRecipeCatalyst(ElectrolyticSeparatorRecipeCategory.INPUT_MACHINE, ElectrolyticSeparatorRecipeCategory.RECIPE_TYPE.getUid());

	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		Minecraft mc = Minecraft.getInstance();
		ClientWorld world = Objects.requireNonNull(mc.level);
		RecipeManager recipeManager = world.getRecipeManager();

		ElectrodynamicsPsuedoRecipes.initRecipes();

		// Electric Furnace
		registration.addRecipes(recipeManager.getAllRecipesFor(IRecipeType.SMELTING), ElectricFurnaceRecipeCategory.RECIPE_TYPE.getUid());

		// Electric Arc Furnace
		registration.addRecipes(recipeManager.getAllRecipesFor(IRecipeType.BLASTING), ElectricArcFurnaceRecipeCategory.RECIPE_TYPE.getUid());

		// Wire Mill
		registration.addRecipes(recipeManager.getAllRecipesFor(ElectrodynamicsRecipies.WIRE_MILL_TYPE), WireMillRecipeCategory.RECIPE_TYPE.getUid());

		// Mineral Crusher
		registration.addRecipes(recipeManager.getAllRecipesFor(ElectrodynamicsRecipies.MINERAL_CRUSHER_TYPE), MineralCrusherRecipeCategory.RECIPE_TYPE.getUid());

		// Mineral Grinder
		registration.addRecipes(recipeManager.getAllRecipesFor(ElectrodynamicsRecipies.MINERAL_GRINDER_TYPE), MineralGrinderRecipeCategory.RECIPE_TYPE.getUid());

		// Oxidation Furnace
		registration.addRecipes(recipeManager.getAllRecipesFor(ElectrodynamicsRecipies.OXIDATION_FURNACE_TYPE), OxidationFurnaceRecipeCategory.RECIPE_TYPE.getUid());

		// Energized Alloyer
		registration.addRecipes(recipeManager.getAllRecipesFor(ElectrodynamicsRecipies.ENERGIZED_ALLOYER_TYPE), EnergizedAlloyerRecipeCategory.RECIPE_TYPE.getUid());

		// Lathe
		registration.addRecipes(recipeManager.getAllRecipesFor(ElectrodynamicsRecipies.LATHE_TYPE), LatheRecipeCategory.RECIPE_TYPE.getUid());

		// Mineral Washer
		registration.addRecipes(recipeManager.getAllRecipesFor(ElectrodynamicsRecipies.MINERAL_WASHER_TYPE), MineralWasherRecipeCategory.RECIPE_TYPE.getUid());

		// Chemical Crystallizer
		registration.addRecipes(recipeManager.getAllRecipesFor(ElectrodynamicsRecipies.CHEMICAL_CRYSTALIZER_TYPE), ChemicalCrystallizerRecipeCategory.RECIPE_TYPE.getUid());

		// Chemical Mixer
		registration.addRecipes(recipeManager.getAllRecipesFor(ElectrodynamicsRecipies.CHEMICAL_MIXER_TYPE), ChemicalMixerRecipeCategory.RECIPE_TYPE.getUid());

		// Fermentation Chamber
		registration.addRecipes(recipeManager.getAllRecipesFor(ElectrodynamicsRecipies.FERMENTATION_PLANT_TYPE), FermentationPlantRecipeCategory.RECIPE_TYPE.getUid());

		// Reinforced Alloyer
		registration.addRecipes(recipeManager.getAllRecipesFor(ElectrodynamicsRecipies.REINFORCED_ALLOYER_TYPE), ReinforcedAlloyerRecipeCategory.RECIPE_TYPE.getUid());

		// Electrolytic Separator
		registration.addRecipes(recipeManager.getAllRecipesFor(ElectrodynamicsRecipies.ELECTROLYTIC_SEPERATOR_TYPE), ElectrolyticSeparatorRecipeCategory.RECIPE_TYPE.getUid());

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
		registry.addRecipeClickArea(ScreenElectricFurnace.class, 85, 35, 22, 15, ElectricFurnaceRecipeCategory.RECIPE_TYPE.getUid());
		registry.addRecipeClickArea(ScreenElectricFurnaceDouble.class, 85, 25, 22, 35, ElectricFurnaceRecipeCategory.RECIPE_TYPE.getUid());
		registry.addRecipeClickArea(ScreenElectricFurnaceTriple.class, 85, 25, 22, 55, ElectricFurnaceRecipeCategory.RECIPE_TYPE.getUid());
		registry.addRecipeClickArea(ScreenElectricArcFurnace.class, 85, 35, 22, 15, ElectricArcFurnaceRecipeCategory.RECIPE_TYPE.getUid());
		registry.addRecipeClickArea(ScreenElectricArcFurnaceDouble.class, 85, 25, 22, 35, ElectricArcFurnaceRecipeCategory.RECIPE_TYPE.getUid());
		registry.addRecipeClickArea(ScreenElectricArcFurnaceTriple.class, 85, 25, 22, 55, ElectricArcFurnaceRecipeCategory.RECIPE_TYPE.getUid());
		registry.addRecipeClickArea(ScreenChemicalMixer.class, 42, 31, 22, 15, ChemicalMixerRecipeCategory.RECIPE_TYPE.getUid());
		registry.addRecipeClickArea(ScreenChemicalMixer.class, 97, 31, 22, 15, ChemicalMixerRecipeCategory.RECIPE_TYPE.getUid());
		registry.addRecipeClickArea(ScreenFermentationPlant.class, 42, 31, 22, 15, FermentationPlantRecipeCategory.RECIPE_TYPE.getUid());
		registry.addRecipeClickArea(ScreenFermentationPlant.class, 97, 31, 22, 15, FermentationPlantRecipeCategory.RECIPE_TYPE.getUid());
		registry.addRecipeClickArea(ScreenMineralWasher.class, 42, 31, 22, 15, MineralWasherRecipeCategory.RECIPE_TYPE.getUid());
		registry.addRecipeClickArea(ScreenMineralWasher.class, 97, 31, 22, 15, MineralWasherRecipeCategory.RECIPE_TYPE.getUid());
		registry.addRecipeClickArea(ScreenChemicalCrystallizer.class, 42, 35, 64, 15, ChemicalCrystallizerRecipeCategory.RECIPE_TYPE.getUid());
		registry.addRecipeClickArea(ScreenElectrolyticSeparator.class, 38, 30, 22, 15, ElectrolyticSeparatorRecipeCategory.RECIPE_TYPE.getUid());
		registry.addRecipeClickArea(ScreenElectrolyticSeparator.class, 78, 30, 22, 15, ElectrolyticSeparatorRecipeCategory.RECIPE_TYPE.getUid());
	}

	private static void electrodynamicsInfoTabs(IRecipeRegistration registration) {
		// Items
		for (Item item : TileCoalGenerator.getValidItems()) {
			ItemStack fuelStack = new ItemStack(item);
			registration.addIngredientInfo(fuelStack, VanillaTypes.ITEM, ElectroTextUtils.jeiItemTranslated("coalgeneratorfuelsource", ChatFormatter.getChatDisplayShort(fuelStack.getBurnTime(null) / 20.0, DisplayUnits.TIME_SECONDS)));
		}

		// Fluids
		for (CombustionFuelSource fuel : CombustionFuelRegister.INSTANCE.getFuels()) {
			for (FluidStack fluid : fuel.getFuels()) {
				registration.addIngredientInfo(new FluidStack(fluid, FULL_FLUID_SQUARE), VanillaTypes.FLUID, ElectroTextUtils.jeiFluidTranslated("combustionchamberfuel", ChatFormatter.getChatDisplayShort(fuel.getPowerMultiplier() * ElectroConstants.COMBUSTIONCHAMBER_JOULES_PER_TICK * 20 / 1000.0, DisplayUnits.WATT), ChatFormatter.formatFluidMilibuckets(fluid.getAmount())));
			}
		}

	}

}
