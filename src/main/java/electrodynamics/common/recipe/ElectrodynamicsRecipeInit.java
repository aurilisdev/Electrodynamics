package electrodynamics.common.recipe;

import electrodynamics.common.recipe.categories.fluid2fluid.Fluid2FluidRecipeTypes;
import electrodynamics.common.recipe.categories.fluid2fluid.specificmachines.ElectrolyticSeparatorRecipe;
import electrodynamics.common.recipe.categories.fluid2item.Fluid2ItemRecipeTypes;
import electrodynamics.common.recipe.categories.fluid2item.specificmachines.ChemicalCrystalizerRecipe;
import electrodynamics.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipeTypes;
import electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines.ChemicalMixerRecipe;
import electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines.FermentationPlantRecipe;
import electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines.MineralWasherRecipe;
import electrodynamics.common.recipe.categories.item2item.Item2ItemRecipeTypes;
import electrodynamics.common.recipe.categories.item2item.specificmachines.EnergizedAlloyerRecipe;
import electrodynamics.common.recipe.categories.item2item.specificmachines.LatheRecipe;
import electrodynamics.common.recipe.categories.item2item.specificmachines.MineralCrusherRecipe;
import electrodynamics.common.recipe.categories.item2item.specificmachines.MineralGrinderRecipe;
import electrodynamics.common.recipe.categories.item2item.specificmachines.OxidationFurnaceRecipe;
import electrodynamics.common.recipe.categories.item2item.specificmachines.ReinforcedAlloyerRecipe;
import electrodynamics.common.recipe.categories.item2item.specificmachines.WireMillRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ElectrodynamicsRecipeInit {

	// Deferred Register
	public static DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, electrodynamics.api.References.ID);
	public static DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registry.RECIPE_TYPE.key(), electrodynamics.api.References.ID);
	/* RECIPE TYPES */

	// Item2Item
	public static final RegistryObject<RecipeType<WireMillRecipe>> WIRE_MILL_TYPE = RECIPE_TYPES.register(WireMillRecipe.RECIPE_GROUP, () -> new CustomRecipeType<>());
	public static final RegistryObject<RecipeType<MineralGrinderRecipe>> MINERAL_GRINDER_TYPE = RECIPE_TYPES.register(MineralGrinderRecipe.RECIPE_GROUP, () -> new CustomRecipeType<>());
	public static final RegistryObject<RecipeType<MineralCrusherRecipe>> MINERAL_CRUSHER_TYPE = RECIPE_TYPES.register(MineralCrusherRecipe.RECIPE_GROUP, () -> new CustomRecipeType<>());
	public static final RegistryObject<RecipeType<LatheRecipe>> LATHE_TYPE = RECIPE_TYPES.register(LatheRecipe.RECIPE_GROUP, () -> new CustomRecipeType<>());
	public static final RegistryObject<RecipeType<OxidationFurnaceRecipe>> OXIDATION_FURNACE_TYPE = RECIPE_TYPES.register(OxidationFurnaceRecipe.RECIPE_GROUP, () -> new CustomRecipeType<>());
	public static final RegistryObject<RecipeType<EnergizedAlloyerRecipe>> ENERGIZED_ALLOYER_TYPE = RECIPE_TYPES.register(EnergizedAlloyerRecipe.RECIPE_GROUP, () -> new CustomRecipeType<>());
	public static final RegistryObject<RecipeType<ReinforcedAlloyerRecipe>> REINFORCED_ALLOYER_TYPE = RECIPE_TYPES.register(ReinforcedAlloyerRecipe.RECIPE_GROUP, () -> new CustomRecipeType<>());

	// FluidItem2Fluid
	public static final RegistryObject<RecipeType<ChemicalMixerRecipe>> CHEMICAL_MIXER_TYPE = RECIPE_TYPES.register(ChemicalMixerRecipe.RECIPE_GROUP, () -> new CustomRecipeType<>());
	public static final RegistryObject<RecipeType<FermentationPlantRecipe>> FERMENTATION_PLANT_TYPE = RECIPE_TYPES.register(FermentationPlantRecipe.RECIPE_GROUP, () -> new CustomRecipeType<>());
	public static final RegistryObject<RecipeType<MineralWasherRecipe>> MINERAL_WASHER_TYPE = RECIPE_TYPES.register(MineralWasherRecipe.RECIPE_GROUP, () -> new CustomRecipeType<>());

	// Fluid2Item
	public static final RegistryObject<RecipeType<ChemicalCrystalizerRecipe>> CHEMICAL_CRYSTALIZER_TYPE = RECIPE_TYPES.register(ChemicalCrystalizerRecipe.RECIPE_GROUP, () -> new CustomRecipeType<>());

	// Fluid2Fluid
	public static final RegistryObject<RecipeType<ElectrolyticSeparatorRecipe>> ELECTROLYTIC_SEPERATOR_TYPE = RECIPE_TYPES.register(ElectrolyticSeparatorRecipe.RECIPE_GROUP, () -> new CustomRecipeType<>());

	/* SERIALIZERS */

	// Item2Item
	public static final RegistryObject<RecipeSerializer<?>> WIRE_MILL_SERIALIZER = RECIPE_SERIALIZER.register(WireMillRecipe.RECIPE_GROUP, () -> Item2ItemRecipeTypes.WIRE_MILL_JSON_SERIALIZER);
	public static final RegistryObject<RecipeSerializer<?>> MINERAL_GRINDER_SERIALIZER = RECIPE_SERIALIZER.register(MineralGrinderRecipe.RECIPE_GROUP, () -> Item2ItemRecipeTypes.MINERAL_CRUSHER_JSON_SERIALIZER);
	public static final RegistryObject<RecipeSerializer<?>> MINERAL_CRUSHER_SERIALIZER = RECIPE_SERIALIZER.register(MineralCrusherRecipe.RECIPE_GROUP, () -> Item2ItemRecipeTypes.MINERAL_GRINDER_JSON_SERIALIZER);
	public static final RegistryObject<RecipeSerializer<?>> LATHE_SERIALIZER = RECIPE_SERIALIZER.register(LatheRecipe.RECIPE_GROUP, () -> Item2ItemRecipeTypes.LATHE_JSON_SERIALIZER);
	public static final RegistryObject<RecipeSerializer<?>> OXIDATION_FURNACE_SERIALIZER = RECIPE_SERIALIZER.register(OxidationFurnaceRecipe.RECIPE_GROUP, () -> Item2ItemRecipeTypes.OXIDATION_FURNACE_JSON_SERIALIZER);
	public static final RegistryObject<RecipeSerializer<?>> ENERGIZED_ALLOYER_SERIALIZER = RECIPE_SERIALIZER.register(EnergizedAlloyerRecipe.RECIPE_GROUP, () -> Item2ItemRecipeTypes.ENERGIZED_ALLOYER_JSON_SERIALIZER);
	public static final RegistryObject<RecipeSerializer<?>> REINFORCED_ALLOYER_SERIALIZER = RECIPE_SERIALIZER.register(ReinforcedAlloyerRecipe.RECIPE_GROUP, () -> Item2ItemRecipeTypes.REINFORCED_ALLOYER_JSON_SERIALIZER);

	// FluidItem2Fluid
	public static final RegistryObject<RecipeSerializer<?>> CHEMICAL_MIXER_SERIALIZER = RECIPE_SERIALIZER.register(ChemicalMixerRecipe.RECIPE_GROUP, () -> FluidItem2FluidRecipeTypes.CHEMICAL_MIXER_JSON_SERIALIZER);
	public static final RegistryObject<RecipeSerializer<?>> FERMENTATION_PLANT_SERIALIZER = RECIPE_SERIALIZER.register(FermentationPlantRecipe.RECIPE_GROUP, () -> FluidItem2FluidRecipeTypes.FERMENTATION_PLANT_JSON_SERIALIZER);
	public static final RegistryObject<RecipeSerializer<?>> MINERAL_WASHER_SERIALIZER = RECIPE_SERIALIZER.register(MineralWasherRecipe.RECIPE_GROUP, () -> FluidItem2FluidRecipeTypes.MINERAL_WASHER_JSON_SERIALIZER);

	// Fluid2Item
	public static final RegistryObject<RecipeSerializer<?>> CHEMICAL_CRYSTALIZER_SERIALIZER = RECIPE_SERIALIZER.register(ChemicalCrystalizerRecipe.RECIPE_GROUP, () -> Fluid2ItemRecipeTypes.CHEMICAL_CRYSTALIZER_JSON_SERIALIZER);

	// Fluid2Fluid
	public static final RegistryObject<RecipeSerializer<?>> ELECTROLYTIC_SEPARATOR_SERIALIZER = RECIPE_SERIALIZER.register(ElectrolyticSeparatorRecipe.RECIPE_GROUP, () -> Fluid2FluidRecipeTypes.ELECTROLYTIC_SEPARATOR_RECIPE_SERIALIZER);

	/* Functional Methods */

	private static class CustomRecipeType<T extends Recipe<?>> implements RecipeType<T> {
		@Override
		public String toString() {
			return Registry.RECIPE_TYPE.getKey(this).toString();
		}
	}
	
}
