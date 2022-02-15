package electrodynamics.compatibility.jei;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import electrodynamics.DeferredRegisters;
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
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.categories.fluid2fluid.Fluid2FluidRecipe;
import electrodynamics.common.recipe.categories.fluid2item.Fluid2ItemRecipe;
import electrodynamics.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipe;
import electrodynamics.common.recipe.categories.item2item.Item2ItemRecipe;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.TileCoalGenerator;
import electrodynamics.common.tile.TileTankHSLA;
import electrodynamics.common.tile.TileTankReinforced;
import electrodynamics.common.tile.TileTankSteel;
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
import electrodynamics.compatibility.jei.utils.InfoItems;
import electrodynamics.prefab.utilities.tile.CombustionFuelSource;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.Tags.IOptionalNamedTag;
import net.minecraftforge.fluids.FluidStack;

@JeiPlugin
public class ElectrodynamicsJEIPlugin implements IModPlugin {

	public static List<ResourceLocation> O2O_CLICK_AREAS = new ArrayList<>();
	public static List<ResourceLocation> DO2O_CLICK_AREAS = new ArrayList<>();

	private static final int FULL_FLUID_SQUARE = 1600;

	private static final String INFO_ITEM = "jei.info.item.";
	private static final String INFO_BLOCK = "jei.info.block.";
	private static final String INFO_FLUID = "jei.info.fluid.";

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(electrodynamics.api.References.ID, "jei");
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {

		registration.addRecipeCatalyst(ElectricFurnaceRecipeCategory.INPUT_MACHINE, ElectricFurnaceRecipeCategory.UID);
		registration.addRecipeCatalyst(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacedouble)), ElectricFurnaceRecipeCategory.UID);
		registration.addRecipeCatalyst(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacetriple)), ElectricFurnaceRecipeCategory.UID);
		registration.addRecipeCatalyst(WireMillRecipeCategory.INPUT_MACHINE, WireMillRecipeCategory.UID);
		registration.addRecipeCatalyst(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.wiremilldouble)), WireMillRecipeCategory.UID);
		registration.addRecipeCatalyst(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.wiremilltriple)), WireMillRecipeCategory.UID);
		registration.addRecipeCatalyst(MineralCrusherRecipeCategory.INPUT_MACHINE, MineralCrusherRecipeCategory.UID);
		registration.addRecipeCatalyst(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralcrusherdouble)), MineralCrusherRecipeCategory.UID);
		registration.addRecipeCatalyst(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralcrushertriple)), MineralCrusherRecipeCategory.UID);
		registration.addRecipeCatalyst(MineralGrinderRecipeCategory.INPUT_MACHINE, MineralGrinderRecipeCategory.UID);
		registration.addRecipeCatalyst(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralgrinderdouble)), MineralGrinderRecipeCategory.UID);
		registration.addRecipeCatalyst(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralgrindertriple)), MineralGrinderRecipeCategory.UID);
		registration.addRecipeCatalyst(ElectricArcFurnaceRecipeCategory.INPUT_MACHINE, ElectricArcFurnaceRecipeCategory.UID);
		registration.addRecipeCatalyst(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricarcfurnacedouble)), ElectricArcFurnaceRecipeCategory.UID);
		registration.addRecipeCatalyst(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricarcfurnacetriple)), ElectricArcFurnaceRecipeCategory.UID);
		registration.addRecipeCatalyst(OxidationFurnaceRecipeCategory.INPUT_MACHINE, OxidationFurnaceRecipeCategory.UID);
		registration.addRecipeCatalyst(EnergizedAlloyerRecipeCategory.INPUT_MACHINE, EnergizedAlloyerRecipeCategory.UID);
		registration.addRecipeCatalyst(LatheRecipeCategory.INPUT_MACHINE, LatheRecipeCategory.UID);
		registration.addRecipeCatalyst(MineralWasherRecipeCategory.INPUT_MACHINE, MineralWasherRecipeCategory.UID);
		registration.addRecipeCatalyst(ChemicalCrystallizerRecipeCategory.INPUT_MACHINE, ChemicalCrystallizerRecipeCategory.UID);
		registration.addRecipeCatalyst(ChemicalMixerRecipeCategory.INPUT_MACHINE, ChemicalMixerRecipeCategory.UID);
		registration.addRecipeCatalyst(FermentationPlantRecipeCategory.INPUT_MACHINE, FermentationPlantRecipeCategory.UID);
		registration.addRecipeCatalyst(ReinforcedAlloyerRecipeCategory.INPUT_MACHINE, ReinforcedAlloyerRecipeCategory.UID);
		registration.addRecipeCatalyst(ElectrolyticSeparatorRecipeCategory.INPUT_MACHINE, ElectrolyticSeparatorRecipeCategory.UID);

	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		InfoItems.addInfoItems();
		Minecraft mc = Minecraft.getInstance();
		ClientLevel world = Objects.requireNonNull(mc.level);
		RecipeManager recipeManager = world.getRecipeManager();

		// Electric Furnace
		Set<AbstractCookingRecipe> electricFurnaceRecipes = ImmutableSet.copyOf(recipeManager.getAllRecipesFor(RecipeType.SMELTING));
		registration.addRecipes(electricFurnaceRecipes, ElectricFurnaceRecipeCategory.UID);

		Set<AbstractCookingRecipe> electricArcFurnaceRecipes = ImmutableSet.copyOf(recipeManager.getAllRecipesFor(RecipeType.BLASTING));
		registration.addRecipes(electricArcFurnaceRecipes, ElectricArcFurnaceRecipeCategory.UID);

		// Wire Mill
		Set<Item2ItemRecipe> wireMillRecipes = ImmutableSet.copyOf(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.WIRE_MILL_TYPE));
		registration.addRecipes(wireMillRecipes, WireMillRecipeCategory.UID);

		// Mineral Crusher
		Set<Item2ItemRecipe> mineralCrusherRecipes = ImmutableSet.copyOf(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.MINERAL_CRUSHER_TYPE));
		registration.addRecipes(mineralCrusherRecipes, MineralCrusherRecipeCategory.UID);

		// Mineral Grinder
		Set<Item2ItemRecipe> mineralGrinderRecipes = ImmutableSet.copyOf(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.MINERAL_GRINDER_TYPE));
		registration.addRecipes(mineralGrinderRecipes, MineralGrinderRecipeCategory.UID);

		// Oxidation Furnace
		Set<Item2ItemRecipe> oxidationFurnaceRecipes = ImmutableSet.copyOf(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.OXIDATION_FURNACE_TYPE));
		registration.addRecipes(oxidationFurnaceRecipes, OxidationFurnaceRecipeCategory.UID);

		// Energized Alloyer
		Set<Item2ItemRecipe> energizedAlloyerRecipes = ImmutableSet.copyOf(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.ENERGIZED_ALLOYER_TYPE));
		registration.addRecipes(energizedAlloyerRecipes, EnergizedAlloyerRecipeCategory.UID);

		// Lathe
		Set<Item2ItemRecipe> latheRecipes = ImmutableSet.copyOf(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.LATHE_TYPE));
		registration.addRecipes(latheRecipes, LatheRecipeCategory.UID);

		// Mineral Washer
		Set<FluidItem2FluidRecipe> mineralWasherRecipes = ImmutableSet.copyOf(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.MINERAL_WASHER_TYPE));
		registration.addRecipes(mineralWasherRecipes, MineralWasherRecipeCategory.UID);

		// Chemical Crystallizer
		Set<Fluid2ItemRecipe> chemicalCrystallizerRecipes = ImmutableSet.copyOf(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.CHEMICAL_CRYSTALIZER_TYPE));
		registration.addRecipes(chemicalCrystallizerRecipes, ChemicalCrystallizerRecipeCategory.UID);

		// Chemical Mixer
		Set<FluidItem2FluidRecipe> chemicalMixerRecipes = ImmutableSet.copyOf(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.CHEMICAL_MIXER_TYPE));
		registration.addRecipes(chemicalMixerRecipes, ChemicalMixerRecipeCategory.UID);

		// Fermentation Chamber
		Set<FluidItem2FluidRecipe> fermenterRecipes = ImmutableSet.copyOf(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.FERMENTATION_PLANT_TYPE));
		registration.addRecipes(fermenterRecipes, FermentationPlantRecipeCategory.UID);

		// Reinforced Alloyer
		Set<Item2ItemRecipe> reinforcedAlloyerRecipes = ImmutableSet.copyOf(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.REINFORCED_ALLOYER_TYPE));
		registration.addRecipes(reinforcedAlloyerRecipes, ReinforcedAlloyerRecipeCategory.UID);

		// Electrolytic Separator
		Set<Fluid2FluidRecipe> electrolyticSeparatorRecipes = ImmutableSet.copyOf(recipeManager.getAllRecipesFor(ElectrodynamicsRecipeInit.ELECTROLYTIC_SEPERATOR_TYPE));
		registration.addRecipes(electrolyticSeparatorRecipes, ElectrolyticSeparatorRecipeCategory.UID);

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

		registry.addRecipeClickArea(ScreenO2OProcessor.class, 48, 35, 22, 15, O2O_CLICK_AREAS.toArray(new ResourceLocation[O2O_CLICK_AREAS.size()]));
		registry.addRecipeClickArea(ScreenO2OProcessorDouble.class, 48, 25, 22, 35, O2O_CLICK_AREAS.toArray(new ResourceLocation[O2O_CLICK_AREAS.size()]));
		registry.addRecipeClickArea(ScreenO2OProcessorTriple.class, 48, 25, 22, 55, O2O_CLICK_AREAS.toArray(new ResourceLocation[O2O_CLICK_AREAS.size()]));
		registry.addRecipeClickArea(ScreenDO2OProcessor.class, 48, 35, 22, 15, DO2O_CLICK_AREAS.toArray(new ResourceLocation[DO2O_CLICK_AREAS.size()]));
		registry.addRecipeClickArea(ScreenElectricFurnace.class, 85, 35, 22, 15, ElectricFurnaceRecipeCategory.UID);
		registry.addRecipeClickArea(ScreenElectricFurnaceDouble.class, 85, 25, 22, 35, ElectricFurnaceRecipeCategory.UID);
		registry.addRecipeClickArea(ScreenElectricFurnaceTriple.class, 85, 25, 22, 55, ElectricFurnaceRecipeCategory.UID);
		registry.addRecipeClickArea(ScreenChemicalMixer.class, 97, 31, 22, 15, ChemicalMixerRecipeCategory.UID);
		registry.addRecipeClickArea(ScreenFermentationPlant.class, 97, 31, 22, 15, FermentationPlantRecipeCategory.UID);
		registry.addRecipeClickArea(ScreenMineralWasher.class, 97, 31, 22, 15, MineralWasherRecipeCategory.UID);
		registry.addRecipeClickArea(ScreenChemicalCrystallizer.class, 41, 35, 22, 15, ChemicalCrystallizerRecipeCategory.UID);
		registry.addRecipeClickArea(ScreenElectrolyticSeparator.class, 38, 30, 22, 15, ElectrolyticSeparatorRecipeCategory.UID);
	}

	private static void electrodynamicsInfoTabs(IRecipeRegistration registration) {
		for (ItemStack item : InfoItems.ITEMS) {
			registration.addIngredientInfo(item, VanillaTypes.ITEM, new TranslatableComponent(INFO_BLOCK + item.getItem().toString()));
		}

		// Blocks
		registration.addIngredientInfo(new ItemStack(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.tanksteel)), VanillaTypes.ITEM, new TranslatableComponent(INFO_BLOCK + "tank", TileTankSteel.CAPACITY));
		registration.addIngredientInfo(new ItemStack(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.tankreinforced)), VanillaTypes.ITEM, new TranslatableComponent(INFO_BLOCK + "tank", TileTankReinforced.CAPACITY));
		registration.addIngredientInfo(new ItemStack(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.tanksteel)), VanillaTypes.ITEM, new TranslatableComponent(INFO_BLOCK + "tank", TileTankHSLA.CAPACITY));

		// Items
		registration.addIngredientInfo(new ItemStack(DeferredRegisters.ITEM_COMPOSITEHELMET.get()), VanillaTypes.ITEM, new TranslatableComponent(INFO_ITEM + "compositearmor"));
		registration.addIngredientInfo(new ItemStack(DeferredRegisters.ITEM_COMPOSITECHESTPLATE.get()), VanillaTypes.ITEM, new TranslatableComponent(INFO_ITEM + "compositearmor"));
		registration.addIngredientInfo(new ItemStack(DeferredRegisters.ITEM_COMPOSITELEGGINGS.get()), VanillaTypes.ITEM, new TranslatableComponent(INFO_ITEM + "compositearmor"));
		registration.addIngredientInfo(new ItemStack(DeferredRegisters.ITEM_COMPOSITEBOOTS.get()), VanillaTypes.ITEM, new TranslatableComponent(INFO_ITEM + "compositearmor"));
		registration.addIngredientInfo(new ItemStack(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeItemUpgrade.iteminput)), VanillaTypes.ITEM, new TranslatableComponent(INFO_ITEM + "upgradeiteminput"));
		registration.addIngredientInfo(new ItemStack(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeItemUpgrade.itemoutput)), VanillaTypes.ITEM, new TranslatableComponent(INFO_ITEM + "upgradeitemoutput"));
		registration.addIngredientInfo(new ItemStack(DeferredRegisters.ITEM_PLASMARAILGUN.get()), VanillaTypes.ITEM, new TranslatableComponent(INFO_ITEM + "railgunplasma"));
		registration.addIngredientInfo(new ItemStack(DeferredRegisters.ITEM_KINETICRAILGUN.get()), VanillaTypes.ITEM, new TranslatableComponent(INFO_ITEM + "railgunkinetic"));

		for (Item item : TileCoalGenerator.getValidItems()) {
			ItemStack fuelStack = new ItemStack(item);
			registration.addIngredientInfo(fuelStack, VanillaTypes.ITEM, new TranslatableComponent(INFO_ITEM + "coalgeneratorfuelsource", ForgeHooks.getBurnTime(fuelStack, null) / 20));
		}

		// Fluids
		for (IOptionalNamedTag<Fluid> tag : CombustionFuelSource.FUELS.keySet()) {
			for (Fluid fluid : tag.getValues()) {
				CombustionFuelSource source = CombustionFuelSource.getSourceFromFluid(fluid);
				registration.addIngredientInfo(new FluidStack(fluid, FULL_FLUID_SQUARE), VanillaTypes.FLUID, new TranslatableComponent(INFO_FLUID + "combustionchamberfuel", source.getFluidUsage(), source.getPowerMultiplier() * Constants.COMBUSTIONCHAMBER_JOULES_PER_TICK * 20 / 1000.0));
			}
		}

	}

	public static void addO2OClickArea(ResourceLocation loc) {
		O2O_CLICK_AREAS.add(loc);
	}

	public static void addDO2OClickArea(ResourceLocation loc) {
		DO2O_CLICK_AREAS.add(loc);
	}

}
