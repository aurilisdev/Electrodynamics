package electrodynamics.registers;

import electrodynamics.Electrodynamics;
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
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import voltaic.common.recipe.VoltaicRecipeType;
import voltaic.common.recipe.categories.fluid2fluid.Fluid2FluidRecipeSerializer;
import voltaic.common.recipe.categories.fluid2item.Fluid2ItemRecipeSerializer;
import voltaic.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipeSerializer;
import voltaic.common.recipe.categories.item2item.Item2ItemRecipeSerializer;

public class ElectrodynamicsRecipies {

	/// Deferred Register
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Electrodynamics.ID);
    
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Electrodynamics.ID);

    
    /* RECIPE TYPES */

    // Item2Item
    public static final RegistryObject<RecipeType<WireMillRecipe>> WIRE_MILL_TYPE = RECIPE_TYPES.register(WireMillRecipe.RECIPE_GROUP, VoltaicRecipeType::new);
    public static final RegistryObject<RecipeType<MineralGrinderRecipe>> MINERAL_GRINDER_TYPE = RECIPE_TYPES.register(MineralGrinderRecipe.RECIPE_GROUP, VoltaicRecipeType::new);
    public static final RegistryObject<RecipeType<MineralCrusherRecipe>> MINERAL_CRUSHER_TYPE = RECIPE_TYPES.register(MineralCrusherRecipe.RECIPE_GROUP, VoltaicRecipeType::new);
    public static final RegistryObject<RecipeType<LatheRecipe>> LATHE_TYPE = RECIPE_TYPES.register(LatheRecipe.RECIPE_GROUP, VoltaicRecipeType::new);
    public static final RegistryObject<RecipeType<OxidationFurnaceRecipe>> OXIDATION_FURNACE_TYPE = RECIPE_TYPES.register(OxidationFurnaceRecipe.RECIPE_GROUP, VoltaicRecipeType::new);
    public static final RegistryObject<RecipeType<EnergizedAlloyerRecipe>> ENERGIZED_ALLOYER_TYPE = RECIPE_TYPES.register(EnergizedAlloyerRecipe.RECIPE_GROUP, VoltaicRecipeType::new);
    public static final RegistryObject<RecipeType<ReinforcedAlloyerRecipe>> REINFORCED_ALLOYER_TYPE = RECIPE_TYPES.register(ReinforcedAlloyerRecipe.RECIPE_GROUP, VoltaicRecipeType::new);

    // FluidItem2Fluid
    public static final RegistryObject<RecipeType<ChemicalMixerRecipe>> CHEMICAL_MIXER_TYPE = RECIPE_TYPES.register(ChemicalMixerRecipe.RECIPE_GROUP, VoltaicRecipeType::new);
    public static final RegistryObject<RecipeType<FermentationPlantRecipe>> FERMENTATION_PLANT_TYPE = RECIPE_TYPES.register(FermentationPlantRecipe.RECIPE_GROUP, VoltaicRecipeType::new);
    public static final RegistryObject<RecipeType<MineralWasherRecipe>> MINERAL_WASHER_TYPE = RECIPE_TYPES.register(MineralWasherRecipe.RECIPE_GROUP, VoltaicRecipeType::new);

    // Fluid2Item
    public static final RegistryObject<RecipeType<ChemicalCrystalizerRecipe>> CHEMICAL_CRYSTALIZER_TYPE = RECIPE_TYPES.register(ChemicalCrystalizerRecipe.RECIPE_GROUP, VoltaicRecipeType::new);

    // Fluid2Gas
    public static final RegistryObject<RecipeType<ElectrolyticSeparatorRecipe>> ELECTROLYTIC_SEPERATOR_TYPE = RECIPE_TYPES.register(ElectrolyticSeparatorRecipe.RECIPE_GROUP, VoltaicRecipeType::new);

    /* SERIALIZERS */

    // Item2Item
    public static final RegistryObject<RecipeSerializer<?>> WIRE_MILL_SERIALIZER = RECIPE_SERIALIZER.register(WireMillRecipe.RECIPE_GROUP, () -> new Item2ItemRecipeSerializer<>(WireMillRecipe::new));
    public static final RegistryObject<RecipeSerializer<?>> MINERAL_GRINDER_SERIALIZER = RECIPE_SERIALIZER.register(MineralGrinderRecipe.RECIPE_GROUP, () -> new Item2ItemRecipeSerializer<>(MineralGrinderRecipe::new));
    public static final RegistryObject<RecipeSerializer<?>> MINERAL_CRUSHER_SERIALIZER = RECIPE_SERIALIZER.register(MineralCrusherRecipe.RECIPE_GROUP, () -> new Item2ItemRecipeSerializer<>(MineralCrusherRecipe::new));
    public static final RegistryObject<RecipeSerializer<?>> LATHE_SERIALIZER = RECIPE_SERIALIZER.register(LatheRecipe.RECIPE_GROUP, () -> new Item2ItemRecipeSerializer<>(LatheRecipe::new));
    public static final RegistryObject<RecipeSerializer<?>> OXIDATION_FURNACE_SERIALIZER = RECIPE_SERIALIZER.register(OxidationFurnaceRecipe.RECIPE_GROUP, () -> new Item2ItemRecipeSerializer<>(OxidationFurnaceRecipe::new));
    public static final RegistryObject<RecipeSerializer<?>> ENERGIZED_ALLOYER_SERIALIZER = RECIPE_SERIALIZER.register(EnergizedAlloyerRecipe.RECIPE_GROUP, () -> new Item2ItemRecipeSerializer<>(EnergizedAlloyerRecipe::new));
    public static final RegistryObject<RecipeSerializer<?>> REINFORCED_ALLOYER_SERIALIZER = RECIPE_SERIALIZER.register(ReinforcedAlloyerRecipe.RECIPE_GROUP, () -> new Item2ItemRecipeSerializer<>(ReinforcedAlloyerRecipe::new));

    // FluidItem2Fluid
    public static final RegistryObject<RecipeSerializer<?>> CHEMICAL_MIXER_SERIALIZER = RECIPE_SERIALIZER.register(ChemicalMixerRecipe.RECIPE_GROUP, () -> new FluidItem2FluidRecipeSerializer<>(ChemicalMixerRecipe::new));
    public static final RegistryObject<RecipeSerializer<?>> FERMENTATION_PLANT_SERIALIZER = RECIPE_SERIALIZER.register(FermentationPlantRecipe.RECIPE_GROUP, () -> new FluidItem2FluidRecipeSerializer<>(FermentationPlantRecipe::new));
    public static final RegistryObject<RecipeSerializer<?>> MINERAL_WASHER_SERIALIZER = RECIPE_SERIALIZER.register(MineralWasherRecipe.RECIPE_GROUP, () -> new FluidItem2FluidRecipeSerializer<>(MineralWasherRecipe::new));

    // Fluid2Item
    public static final RegistryObject<RecipeSerializer<?>> CHEMICAL_CRYSTALIZER_SERIALIZER = RECIPE_SERIALIZER.register(ChemicalCrystalizerRecipe.RECIPE_GROUP, () -> new Fluid2ItemRecipeSerializer<>(ChemicalCrystalizerRecipe::new));

    // Fluid2Gas
    public static final RegistryObject<RecipeSerializer<?>> ELECTROLYTIC_SEPARATOR_SERIALIZER = RECIPE_SERIALIZER.register(ElectrolyticSeparatorRecipe.RECIPE_GROUP, () -> new Fluid2FluidRecipeSerializer<>(ElectrolyticSeparatorRecipe::new));

}
