package electrodynamics.common.recipe;

import electrodynamics.api.References;
import electrodynamics.common.recipe.categories.fluid2gas.Fluid2GasRecipeTypes;
import electrodynamics.common.recipe.categories.fluid2gas.specificmachines.ElectrolyticSeparatorRecipe;
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
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.GasIngredient;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.common.crafting.IngredientType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ElectrodynamicsRecipeInit {

    // Deferred Register
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(Registries.RECIPE_SERIALIZER, References.ID);
    
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, References.ID);
    
    public static final DeferredRegister<IngredientType<?>> INGREDIENT_TYPES = DeferredRegister.create(NeoForgeRegistries.INGREDIENT_TYPES, References.ID);
    
    /* INGREDIENT TYPES */
    
    public static final DeferredHolder<IngredientType<?>, IngredientType<CountableIngredient>> COUNTABLE_INGREDIENT_TYPE = INGREDIENT_TYPES.register("countableingredient", () -> new IngredientType<>(CountableIngredient.CODEC));
    public static final DeferredHolder<IngredientType<?>, IngredientType<FluidIngredient>> FLUID_INGREDIENT_TYPE = INGREDIENT_TYPES.register("fluidingredient", () -> new IngredientType<>(FluidIngredient.CODEC));
    public static final DeferredHolder<IngredientType<?>, IngredientType<GasIngredient>> GAS_INGREDIENT_TYPE = INGREDIENT_TYPES.register("gasingredient", () -> new IngredientType<>(GasIngredient.CODEC));
    
    /* RECIPE TYPES */

    // Item2Item
    public static final DeferredHolder<RecipeType<?>, RecipeType<WireMillRecipe>> WIRE_MILL_TYPE = RECIPE_TYPES.register(WireMillRecipe.RECIPE_GROUP, CustomRecipeType::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<MineralGrinderRecipe>> MINERAL_GRINDER_TYPE = RECIPE_TYPES.register(MineralGrinderRecipe.RECIPE_GROUP, CustomRecipeType::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<MineralCrusherRecipe>> MINERAL_CRUSHER_TYPE = RECIPE_TYPES.register(MineralCrusherRecipe.RECIPE_GROUP, CustomRecipeType::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<LatheRecipe>> LATHE_TYPE = RECIPE_TYPES.register(LatheRecipe.RECIPE_GROUP, CustomRecipeType::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<OxidationFurnaceRecipe>> OXIDATION_FURNACE_TYPE = RECIPE_TYPES.register(OxidationFurnaceRecipe.RECIPE_GROUP, CustomRecipeType::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<EnergizedAlloyerRecipe>> ENERGIZED_ALLOYER_TYPE = RECIPE_TYPES.register(EnergizedAlloyerRecipe.RECIPE_GROUP, CustomRecipeType::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<ReinforcedAlloyerRecipe>> REINFORCED_ALLOYER_TYPE = RECIPE_TYPES.register(ReinforcedAlloyerRecipe.RECIPE_GROUP, CustomRecipeType::new);

    // FluidItem2Fluid
    public static final DeferredHolder<RecipeType<?>, RecipeType<ChemicalMixerRecipe>> CHEMICAL_MIXER_TYPE = RECIPE_TYPES.register(ChemicalMixerRecipe.RECIPE_GROUP, CustomRecipeType::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<FermentationPlantRecipe>> FERMENTATION_PLANT_TYPE = RECIPE_TYPES.register(FermentationPlantRecipe.RECIPE_GROUP, CustomRecipeType::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<MineralWasherRecipe>> MINERAL_WASHER_TYPE = RECIPE_TYPES.register(MineralWasherRecipe.RECIPE_GROUP, CustomRecipeType::new);

    // Fluid2Item
    public static final DeferredHolder<RecipeType<?>, RecipeType<ChemicalCrystalizerRecipe>> CHEMICAL_CRYSTALIZER_TYPE = RECIPE_TYPES.register(ChemicalCrystalizerRecipe.RECIPE_GROUP, CustomRecipeType::new);

    // Fluid2Gas
    public static final DeferredHolder<RecipeType<?>, RecipeType<ElectrolyticSeparatorRecipe>> ELECTROLYTIC_SEPERATOR_TYPE = RECIPE_TYPES.register(ElectrolyticSeparatorRecipe.RECIPE_GROUP, CustomRecipeType::new);

    /* SERIALIZERS */

    // Item2Item
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> WIRE_MILL_SERIALIZER = RECIPE_SERIALIZER.register(WireMillRecipe.RECIPE_GROUP, () -> Item2ItemRecipeTypes.WIRE_MILL_JSON_SERIALIZER);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> MINERAL_GRINDER_SERIALIZER = RECIPE_SERIALIZER.register(MineralGrinderRecipe.RECIPE_GROUP, () -> Item2ItemRecipeTypes.MINERAL_CRUSHER_JSON_SERIALIZER);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> MINERAL_CRUSHER_SERIALIZER = RECIPE_SERIALIZER.register(MineralCrusherRecipe.RECIPE_GROUP, () -> Item2ItemRecipeTypes.MINERAL_GRINDER_JSON_SERIALIZER);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> LATHE_SERIALIZER = RECIPE_SERIALIZER.register(LatheRecipe.RECIPE_GROUP, () -> Item2ItemRecipeTypes.LATHE_JSON_SERIALIZER);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> OXIDATION_FURNACE_SERIALIZER = RECIPE_SERIALIZER.register(OxidationFurnaceRecipe.RECIPE_GROUP, () -> Item2ItemRecipeTypes.OXIDATION_FURNACE_JSON_SERIALIZER);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> ENERGIZED_ALLOYER_SERIALIZER = RECIPE_SERIALIZER.register(EnergizedAlloyerRecipe.RECIPE_GROUP, () -> Item2ItemRecipeTypes.ENERGIZED_ALLOYER_JSON_SERIALIZER);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> REINFORCED_ALLOYER_SERIALIZER = RECIPE_SERIALIZER.register(ReinforcedAlloyerRecipe.RECIPE_GROUP, () -> Item2ItemRecipeTypes.REINFORCED_ALLOYER_JSON_SERIALIZER);

    // FluidItem2Fluid
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> CHEMICAL_MIXER_SERIALIZER = RECIPE_SERIALIZER.register(ChemicalMixerRecipe.RECIPE_GROUP, () -> FluidItem2FluidRecipeTypes.CHEMICAL_MIXER_JSON_SERIALIZER);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> FERMENTATION_PLANT_SERIALIZER = RECIPE_SERIALIZER.register(FermentationPlantRecipe.RECIPE_GROUP, () -> FluidItem2FluidRecipeTypes.FERMENTATION_PLANT_JSON_SERIALIZER);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> MINERAL_WASHER_SERIALIZER = RECIPE_SERIALIZER.register(MineralWasherRecipe.RECIPE_GROUP, () -> FluidItem2FluidRecipeTypes.MINERAL_WASHER_JSON_SERIALIZER);

    // Fluid2Item
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> CHEMICAL_CRYSTALIZER_SERIALIZER = RECIPE_SERIALIZER.register(ChemicalCrystalizerRecipe.RECIPE_GROUP, () -> Fluid2ItemRecipeTypes.CHEMICAL_CRYSTALIZER_JSON_SERIALIZER);

    // Fluid2Gas
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> ELECTROLYTIC_SEPARATOR_SERIALIZER = RECIPE_SERIALIZER.register(ElectrolyticSeparatorRecipe.RECIPE_GROUP, () -> Fluid2GasRecipeTypes.ELECTROLYTIC_SEPARATOR_RECIPE_SERIALIZER);

    /* Functional Methods */

    public static class CustomRecipeType<T extends Recipe<?>> implements RecipeType<T> {
        @Override
        public String toString() {
            return BuiltInRegistries.RECIPE_TYPE.getKey(this).toString();
        }
    }

}
