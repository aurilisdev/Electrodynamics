package electrodynamics.common.recipe;

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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ElectrodynamicsRecipeInit {

    // Deferred Register
    public static DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS,
	    electrodynamics.api.References.ID);

    /* RECIPE TYPES */

    // Item2Item
    public static final RecipeType<WireMillRecipe> WIRE_MILL_TYPE = registerType(WireMillRecipe.RECIPE_ID);
    public static final RecipeType<MineralGrinderRecipe> MINERAL_GRINDER_TYPE = registerType(MineralGrinderRecipe.RECIPE_ID);
    public static final RecipeType<MineralCrusherRecipe> MINERAL_CRUSHER_TYPE = registerType(MineralCrusherRecipe.RECIPE_ID);
    public static final RecipeType<LatheRecipe> LATHE_TYPE = registerType(LatheRecipe.RECIPE_ID);
    public static final RecipeType<OxidationFurnaceRecipe> OXIDATION_FURNACE_TYPE = registerType(OxidationFurnaceRecipe.RECIPE_ID);
    public static final RecipeType<EnergizedAlloyerRecipe> ENERGIZED_ALLOYER_TYPE = registerType(EnergizedAlloyerRecipe.RECIPE_ID);
    public static final RecipeType<ReinforcedAlloyerRecipe> REINFORCED_ALLOYER_TYPE = registerType(ReinforcedAlloyerRecipe.RECIPE_ID);

    // FluidItem2Fluid
    public static final RecipeType<ChemicalMixerRecipe> CHEMICAL_MIXER_TYPE = registerType(ChemicalMixerRecipe.RECIPE_ID);
    public static final RecipeType<FermentationPlantRecipe> FERMENTATION_PLANT_TYPE = registerType(FermentationPlantRecipe.RECIPE_ID);
    public static final RecipeType<MineralWasherRecipe> MINERAL_WASHER_TYPE = registerType(MineralWasherRecipe.RECIPE_ID);

    // Fluid2Item
    public static final RecipeType<ChemicalCrystalizerRecipe> CHEMICAL_CRYSTALIZER_TYPE = registerType(ChemicalCrystalizerRecipe.RECIPE_ID);

    /* SERIALIZERS */

    // Item2Item
    public static final RegistryObject<RecipeSerializer<?>> WIRE_MILL_SERIALIZER = RECIPE_SERIALIZER.register(WireMillRecipe.RECIPE_GROUP,
	    () -> Item2ItemRecipeTypes.WIRE_MILL_JSON_SERIALIZER);
    public static final RegistryObject<RecipeSerializer<?>> MINERAL_GRINDER_SERIALIZER = RECIPE_SERIALIZER.register(MineralGrinderRecipe.RECIPE_GROUP,
	    () -> Item2ItemRecipeTypes.MINERAL_CRUSHER_JSON_SERIALIZER);
    public static final RegistryObject<RecipeSerializer<?>> MINERAL_CRUSHER_SERIALIZER = RECIPE_SERIALIZER.register(MineralCrusherRecipe.RECIPE_GROUP,
	    () -> Item2ItemRecipeTypes.MINERAL_GRINDER_JSON_SERIALIZER);
    public static final RegistryObject<RecipeSerializer<?>> LATHE_SERIALIZER = RECIPE_SERIALIZER.register(LatheRecipe.RECIPE_GROUP,
	    () -> Item2ItemRecipeTypes.LATHE_JSON_SERIALIZER);
    public static final RegistryObject<RecipeSerializer<?>> OXIDATION_FURNACE_SERIALIZER = RECIPE_SERIALIZER
	    .register(OxidationFurnaceRecipe.RECIPE_GROUP, () -> Item2ItemRecipeTypes.OXIDATION_FURNACE_JSON_SERIALIZER);
    public static final RegistryObject<RecipeSerializer<?>> ENERGIZED_ALLOYER_SERIALIZER = RECIPE_SERIALIZER
	    .register(EnergizedAlloyerRecipe.RECIPE_GROUP, () -> Item2ItemRecipeTypes.ENERGIZED_ALLOYER_JSON_SERIALIZER);
    public static final RegistryObject<RecipeSerializer<?>> REINFORCED_ALLOYER_SERIALIZER = RECIPE_SERIALIZER
	    .register(ReinforcedAlloyerRecipe.RECIPE_GROUP, () -> Item2ItemRecipeTypes.REINFORCED_ALLOYER_JSON_SERIALIZER);

    // FluidItem2Fluid
    public static final RegistryObject<RecipeSerializer<?>> CHEMICAL_MIXER_SERIALIZER = RECIPE_SERIALIZER.register(ChemicalMixerRecipe.RECIPE_GROUP,
	    () -> FluidItem2FluidRecipeTypes.CHEMICAL_MIXER_JSON_SERIALIZER);
    public static final RegistryObject<RecipeSerializer<?>> FERMENTATION_PLANT_SERIALIZER = RECIPE_SERIALIZER
	    .register(FermentationPlantRecipe.RECIPE_GROUP, () -> FluidItem2FluidRecipeTypes.FERMENTATION_PLANT_JSON_SERIALIZER);
    public static final RegistryObject<RecipeSerializer<?>> MINERAL_WASHER_SERIALIZER = RECIPE_SERIALIZER.register(MineralWasherRecipe.RECIPE_GROUP,
	    () -> FluidItem2FluidRecipeTypes.MINERAL_WASHER_JSON_SERIALIZER);

    // Fluid2Item
    public static final RegistryObject<RecipeSerializer<?>> CHEMICAL_CRYSTALIZER_SERIALIZER = RECIPE_SERIALIZER
	    .register(ChemicalCrystalizerRecipe.RECIPE_GROUP, () -> Fluid2ItemRecipeTypes.CHEMICAL_CRYSTALIZER_JSON_SERIALIZER);

    /* Functional Methods */

    public static <T extends RecipeType<?>> T registerType(ResourceLocation recipeTypeId) {
	return (T) Registry.register(Registry.RECIPE_TYPE, recipeTypeId, new CustomRecipeType<>());
    }

    private static class CustomRecipeType<T extends Recipe<?>> implements RecipeType<T> {
	@Override
	public String toString() {
	    return Registry.RECIPE_TYPE.getKey(this).toString();
	}
    }

}
