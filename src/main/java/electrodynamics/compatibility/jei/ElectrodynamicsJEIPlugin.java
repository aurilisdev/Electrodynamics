package electrodynamics.compatibility.jei;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;

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
import electrodynamics.client.screen.tile.ScreenThermoelectricManipulator;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.reloadlistener.CombustionFuelRegister;
import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.common.tile.electricitygrid.generators.TileCoalGenerator;
import electrodynamics.compatibility.jei.recipecategories.fluid2gas.specificmachines.ElectrolyticSeparatorRecipeCategory;
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
import electrodynamics.compatibility.jei.recipecategories.thermomanipulator.CondensingGasRecipeCategory;
import electrodynamics.compatibility.jei.recipecategories.thermomanipulator.EvaporatingFluidRecipeCategory;
import electrodynamics.compatibility.jei.recipecategories.utils.psuedorecipes.ElectrodynamicsPsuedoRecipes;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.object.CombustionFuelSource;
import electrodynamics.registers.ElectrodynamicsBlocks;
import electrodynamics.registers.ElectrodynamicsFluids;
import electrodynamics.registers.ElectrodynamicsGases;
import electrodynamics.registers.ElectrodynamicsRecipies;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IAdvancedRegistration;
import mezz.jei.api.registration.IExtraIngredientRegistration;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.RegistryObject;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.api.gas.Gas;
import voltaic.api.gas.GasStack;
import voltaic.compatibility.jei.utils.ingredients.VoltaicJeiTypes;
import voltaic.registers.VoltaicGases;

@JeiPlugin
public class ElectrodynamicsJEIPlugin implements IModPlugin {

    public static final ResourceLocation ID = Electrodynamics.rl("jei");

    private static final int FULL_FLUID_SQUARE = 1600;

    @Override
    public @NotNull ResourceLocation getPluginUid() {
	return ID;
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {

	registration.addRecipeCatalyst(ElectricFurnaceRecipeCategory.INPUT_MACHINE,
		ElectricFurnaceRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(
		new ItemStack(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricfurnacedouble)),
		ElectricFurnaceRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(
		new ItemStack(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricfurnacetriple)),
		ElectricFurnaceRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(WireMillRecipeCategory.INPUT_MACHINE, WireMillRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(
		new ItemStack(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.wiremilldouble)),
		WireMillRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(
		new ItemStack(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.wiremilltriple)),
		WireMillRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(MineralCrusherRecipeCategory.INPUT_MACHINE,
		MineralCrusherRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(
		new ItemStack(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralcrusherdouble)),
		MineralCrusherRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(
		new ItemStack(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralcrushertriple)),
		MineralCrusherRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(MineralGrinderRecipeCategory.INPUT_MACHINE,
		MineralGrinderRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(
		new ItemStack(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralgrinderdouble)),
		MineralGrinderRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(
		new ItemStack(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralgrindertriple)),
		MineralGrinderRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(ElectricArcFurnaceRecipeCategory.INPUT_MACHINE,
		ElectricArcFurnaceRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(
		new ItemStack(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricarcfurnacedouble)),
		ElectricArcFurnaceRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(
		new ItemStack(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricarcfurnacetriple)),
		ElectricArcFurnaceRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(OxidationFurnaceRecipeCategory.INPUT_MACHINE,
		OxidationFurnaceRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(EnergizedAlloyerRecipeCategory.INPUT_MACHINE,
		EnergizedAlloyerRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(LatheRecipeCategory.INPUT_MACHINE, LatheRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(MineralWasherRecipeCategory.INPUT_MACHINE,
		MineralWasherRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(ChemicalCrystallizerRecipeCategory.INPUT_MACHINE,
		ChemicalCrystallizerRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(ChemicalMixerRecipeCategory.INPUT_MACHINE,
		ChemicalMixerRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(FermentationPlantRecipeCategory.INPUT_MACHINE,
		FermentationPlantRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(ReinforcedAlloyerRecipeCategory.INPUT_MACHINE,
		ReinforcedAlloyerRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(ElectrolyticSeparatorRecipeCategory.INPUT_MACHINE,
		ElectrolyticSeparatorRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(CondensingGasRecipeCategory.INPUT_MACHINE,
		CondensingGasRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(
		new ItemStack(ElectrodynamicsBlocks.BLOCK_ADVANCED_THERMOELECTRICMANIPULATOR.get()),
		CondensingGasRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(EvaporatingFluidRecipeCategory.INPUT_MACHINE,
		EvaporatingFluidRecipeCategory.RECIPE_TYPE);
	registration.addRecipeCatalyst(
		new ItemStack(ElectrodynamicsBlocks.BLOCK_ADVANCED_THERMOELECTRICMANIPULATOR.get()),
		EvaporatingFluidRecipeCategory.RECIPE_TYPE);

    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
	Minecraft mc = Minecraft.getInstance();
	ClientLevel world = Objects.requireNonNull(mc.level);
	RecipeManager recipeManager = world.getRecipeManager();

	ElectrodynamicsPsuedoRecipes.initRecipes();

	// Electric Furnace
	registration.addRecipes(ElectricFurnaceRecipeCategory.RECIPE_TYPE,
		recipeManager.getAllRecipesFor(RecipeType.SMELTING));

	// Electric Arc Furnace
	registration.addRecipes(ElectricArcFurnaceRecipeCategory.RECIPE_TYPE,
		recipeManager.getAllRecipesFor(RecipeType.BLASTING));

	// Wire Mill
	registration.addRecipes(WireMillRecipeCategory.RECIPE_TYPE,
		recipeManager.getAllRecipesFor(ElectrodynamicsRecipies.WIRE_MILL_TYPE.get()));

	// Mineral Crusher
	registration.addRecipes(MineralCrusherRecipeCategory.RECIPE_TYPE,
		recipeManager.getAllRecipesFor(ElectrodynamicsRecipies.MINERAL_CRUSHER_TYPE.get()));

	// Mineral Grinder
	registration.addRecipes(MineralGrinderRecipeCategory.RECIPE_TYPE,
		recipeManager.getAllRecipesFor(ElectrodynamicsRecipies.MINERAL_GRINDER_TYPE.get()));

	// Oxidation Furnace
	registration.addRecipes(OxidationFurnaceRecipeCategory.RECIPE_TYPE,
		recipeManager.getAllRecipesFor(ElectrodynamicsRecipies.OXIDATION_FURNACE_TYPE.get()));

	// Energized Alloyer
	registration.addRecipes(EnergizedAlloyerRecipeCategory.RECIPE_TYPE,
		recipeManager.getAllRecipesFor(ElectrodynamicsRecipies.ENERGIZED_ALLOYER_TYPE.get()));

	// Lathe
	registration.addRecipes(LatheRecipeCategory.RECIPE_TYPE,
		recipeManager.getAllRecipesFor(ElectrodynamicsRecipies.LATHE_TYPE.get()));

	// Mineral Washer
	registration.addRecipes(MineralWasherRecipeCategory.RECIPE_TYPE,
		recipeManager.getAllRecipesFor(ElectrodynamicsRecipies.MINERAL_WASHER_TYPE.get()));

	// Chemical Crystallizer
	registration.addRecipes(ChemicalCrystallizerRecipeCategory.RECIPE_TYPE,
		recipeManager.getAllRecipesFor(ElectrodynamicsRecipies.CHEMICAL_CRYSTALIZER_TYPE.get()));

	// Chemical Mixer
	registration.addRecipes(ChemicalMixerRecipeCategory.RECIPE_TYPE,
		recipeManager.getAllRecipesFor(ElectrodynamicsRecipies.CHEMICAL_MIXER_TYPE.get()));

	// Fermentation Chamber
	registration.addRecipes(FermentationPlantRecipeCategory.RECIPE_TYPE,
		recipeManager.getAllRecipesFor(ElectrodynamicsRecipies.FERMENTATION_PLANT_TYPE.get()));

	// Reinforced Alloyer
	registration.addRecipes(ReinforcedAlloyerRecipeCategory.RECIPE_TYPE,
		recipeManager.getAllRecipesFor(ElectrodynamicsRecipies.REINFORCED_ALLOYER_TYPE.get()));

	// Electrolytic Separator
	registration.addRecipes(ElectrolyticSeparatorRecipeCategory.RECIPE_TYPE,
		recipeManager.getAllRecipesFor(ElectrodynamicsRecipies.ELECTROLYTIC_SEPERATOR_TYPE.get()));

	// Thermoelectric Manipulator Condensing
	registration.addRecipes(CondensingGasRecipeCategory.RECIPE_TYPE,
		new ArrayList<>(ElectrodynamicsPsuedoRecipes.CONDENSATION_RECIPES));

	// Thermoelectric Manipulator Evaporating
	registration.addRecipes(EvaporatingFluidRecipeCategory.RECIPE_TYPE,
		new ArrayList<>(ElectrodynamicsPsuedoRecipes.EVAPORATION_RECIPES));

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
	registration.addRecipeCategories(new CondensingGasRecipeCategory(guiHelper));
	registration.addRecipeCategories(new EvaporatingFluidRecipeCategory(guiHelper));

    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registry) {
	registry.addRecipeClickArea(ScreenElectricFurnace.class, 85, 35, 22, 15,
		ElectricFurnaceRecipeCategory.RECIPE_TYPE);
	registry.addRecipeClickArea(ScreenElectricFurnaceDouble.class, 85, 25, 22, 35,
		ElectricFurnaceRecipeCategory.RECIPE_TYPE);
	registry.addRecipeClickArea(ScreenElectricFurnaceTriple.class, 85, 25, 22, 55,
		ElectricFurnaceRecipeCategory.RECIPE_TYPE);
	registry.addRecipeClickArea(ScreenElectricArcFurnace.class, 85, 35, 22, 15,
		ElectricArcFurnaceRecipeCategory.RECIPE_TYPE);
	registry.addRecipeClickArea(ScreenElectricArcFurnaceDouble.class, 85, 25, 22, 35,
		ElectricArcFurnaceRecipeCategory.RECIPE_TYPE);
	registry.addRecipeClickArea(ScreenElectricArcFurnaceTriple.class, 85, 25, 22, 55,
		ElectricArcFurnaceRecipeCategory.RECIPE_TYPE);
	registry.addRecipeClickArea(ScreenChemicalMixer.class, 42, 31, 22, 15, ChemicalMixerRecipeCategory.RECIPE_TYPE);
	registry.addRecipeClickArea(ScreenChemicalMixer.class, 97, 31, 22, 15, ChemicalMixerRecipeCategory.RECIPE_TYPE);
	registry.addRecipeClickArea(ScreenFermentationPlant.class, 42, 31, 22, 15,
		FermentationPlantRecipeCategory.RECIPE_TYPE);
	registry.addRecipeClickArea(ScreenFermentationPlant.class, 97, 31, 22, 15,
		FermentationPlantRecipeCategory.RECIPE_TYPE);
	registry.addRecipeClickArea(ScreenMineralWasher.class, 42, 31, 22, 15, MineralWasherRecipeCategory.RECIPE_TYPE);
	registry.addRecipeClickArea(ScreenMineralWasher.class, 97, 31, 22, 15, MineralWasherRecipeCategory.RECIPE_TYPE);
	registry.addRecipeClickArea(ScreenChemicalCrystallizer.class, 42, 35, 64, 15,
		ChemicalCrystallizerRecipeCategory.RECIPE_TYPE);
	registry.addRecipeClickArea(ScreenElectrolyticSeparator.class, 38, 30, 22, 15,
		ElectrolyticSeparatorRecipeCategory.RECIPE_TYPE);
	registry.addRecipeClickArea(ScreenElectrolyticSeparator.class, 78, 30, 22, 15,
		ElectrolyticSeparatorRecipeCategory.RECIPE_TYPE);
	registry.addRecipeClickArea(ScreenThermoelectricManipulator.class, 62, 19, 32, 47,
		CondensingGasRecipeCategory.RECIPE_TYPE, EvaporatingFluidRecipeCategory.RECIPE_TYPE);
    }

    private static void electrodynamicsInfoTabs(IRecipeRegistration registration) {
	// Items
	for (Item item : TileCoalGenerator.getValidItems()) {
	    ItemStack fuelStack = new ItemStack(item);
	    registration.addIngredientInfo(fuelStack, VanillaTypes.ITEM_STACK, ElectroTextUtils.jeiItemTranslated(
		    "coalgeneratorfuelsource",
		    ChatFormatter.getChatDisplayShort(fuelStack.getBurnTime(null) / 20.0, DisplayUnits.TIME_SECONDS)));
	}

	// Fluids
	for (CombustionFuelSource fuel : CombustionFuelRegister.INSTANCE.getFuels()) {
	    for (FluidStack fluid : fuel.getFuels()) {
		registration.addIngredientInfo(new FluidStack(fluid, FULL_FLUID_SQUARE), ForgeTypes.FLUID_STACK,
			ElectroTextUtils.jeiFluidTranslated("combustionchamberfuel",
				ChatFormatter.getChatDisplayShort(fuel.getPowerMultiplier()
					* ElectroConstants.COMBUSTIONCHAMBER_JOULES_PER_TICK * 20 / 1000.0,
					DisplayUnits.WATT),
				ChatFormatter.formatFluidMilibuckets(fluid.getAmount())));
	    }
	}

    }

    @Override
    public void registerAdvanced(IAdvancedRegistration registration) {

	// registration.addRecipeManagerPlugin(new RecipeManagerPluginCanister());

    }

    @Override
    public void registerExtraIngredients(IExtraIngredientRegistration registration) {
	List<FluidStack> fluids = new ArrayList<>();
	for (RegistryObject<? extends Fluid> fluid : ElectrodynamicsFluids.FLUIDS.getEntries()) {
	    fluids.add(new FluidStack(fluid.get(), 1000));
	}
	registration.addExtraIngredients(ForgeTypes.FLUID_STACK, fluids);

	List<GasStack> gases = new ArrayList<>();
	for (RegistryObject<? extends Gas> gas : ElectrodynamicsGases.GASES.getEntries()) {
	    if (gas.get() == VoltaicGases.EMPTY.get()) {
		continue;
	    }

	    gases.add(new GasStack(gas.get(), 1000, Gas.ROOM_TEMPERATURE, Gas.PRESSURE_AT_SEA_LEVEL));
	}
	registration.addExtraIngredients(VoltaicJeiTypes.GAS_STACK, gases);
    }

}
