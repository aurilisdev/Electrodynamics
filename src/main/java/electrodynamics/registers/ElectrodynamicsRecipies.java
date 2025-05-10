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
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import voltaic.common.recipe.VoltaicRecipeType;
import voltaic.common.recipe.categories.fluid2fluid.Fluid2FluidRecipeSerializer;
import voltaic.common.recipe.categories.fluid2item.Fluid2ItemRecipeSerializer;
import voltaic.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipeSerializer;
import voltaic.common.recipe.categories.item2item.Item2ItemRecipeSerializer;

public class ElectrodynamicsRecipies {

	/// Deferred Register
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Electrodynamics.ID);

    
    /* RECIPE TYPES */

    // Item2Item
    public static final IRecipeType<WireMillRecipe> WIRE_MILL_TYPE = registerType(WireMillRecipe.RECIPE_ID, new VoltaicRecipeType<>());
    public static final IRecipeType<MineralGrinderRecipe> MINERAL_GRINDER_TYPE = registerType(MineralGrinderRecipe.RECIPE_ID, new VoltaicRecipeType<>());
    public static final IRecipeType<MineralCrusherRecipe> MINERAL_CRUSHER_TYPE = registerType(MineralCrusherRecipe.RECIPE_ID, new VoltaicRecipeType<>());
    public static final IRecipeType<LatheRecipe> LATHE_TYPE = registerType(LatheRecipe.RECIPE_ID, new VoltaicRecipeType<>());
    public static final IRecipeType<OxidationFurnaceRecipe> OXIDATION_FURNACE_TYPE = registerType(OxidationFurnaceRecipe.RECIPE_ID, new VoltaicRecipeType<>());
    public static final IRecipeType<EnergizedAlloyerRecipe> ENERGIZED_ALLOYER_TYPE = registerType(EnergizedAlloyerRecipe.RECIPE_ID, new VoltaicRecipeType<>());
    public static final IRecipeType<ReinforcedAlloyerRecipe> REINFORCED_ALLOYER_TYPE = registerType(ReinforcedAlloyerRecipe.RECIPE_ID, new VoltaicRecipeType<>());

    // FluidItem2Fluid
    public static final IRecipeType<ChemicalMixerRecipe> CHEMICAL_MIXER_TYPE = registerType(ChemicalMixerRecipe.RECIPE_ID, new VoltaicRecipeType<>());
    public static final IRecipeType<FermentationPlantRecipe> FERMENTATION_PLANT_TYPE = registerType(FermentationPlantRecipe.RECIPE_ID, new VoltaicRecipeType<>());
    public static final IRecipeType<MineralWasherRecipe> MINERAL_WASHER_TYPE = registerType(MineralWasherRecipe.RECIPE_ID, new VoltaicRecipeType<>());

    // Fluid2Item
    public static final IRecipeType<ChemicalCrystalizerRecipe> CHEMICAL_CRYSTALIZER_TYPE = registerType(ChemicalCrystalizerRecipe.RECIPE_ID, new VoltaicRecipeType<>());

    // Fluid2Gas
    public static final IRecipeType<ElectrolyticSeparatorRecipe> ELECTROLYTIC_SEPERATOR_TYPE = registerType(ElectrolyticSeparatorRecipe.RECIPE_ID, new VoltaicRecipeType<>());

    /* SERIALIZERS */

    // Item2Item
    public static final RegistryObject<IRecipeSerializer<?>> WIRE_MILL_SERIALIZER = RECIPE_SERIALIZER.register(WireMillRecipe.RECIPE_GROUP, () -> new Item2ItemRecipeSerializer<>(WireMillRecipe::new));
    public static final RegistryObject<IRecipeSerializer<?>> MINERAL_GRINDER_SERIALIZER = RECIPE_SERIALIZER.register(MineralGrinderRecipe.RECIPE_GROUP, () -> new Item2ItemRecipeSerializer<>(MineralGrinderRecipe::new));
    public static final RegistryObject<IRecipeSerializer<?>> MINERAL_CRUSHER_SERIALIZER = RECIPE_SERIALIZER.register(MineralCrusherRecipe.RECIPE_GROUP, () -> new Item2ItemRecipeSerializer<>(MineralCrusherRecipe::new));
    public static final RegistryObject<IRecipeSerializer<?>> LATHE_SERIALIZER = RECIPE_SERIALIZER.register(LatheRecipe.RECIPE_GROUP, () -> new Item2ItemRecipeSerializer<>(LatheRecipe::new));
    public static final RegistryObject<IRecipeSerializer<?>> OXIDATION_FURNACE_SERIALIZER = RECIPE_SERIALIZER.register(OxidationFurnaceRecipe.RECIPE_GROUP, () -> new Item2ItemRecipeSerializer<>(OxidationFurnaceRecipe::new));
    public static final RegistryObject<IRecipeSerializer<?>> ENERGIZED_ALLOYER_SERIALIZER = RECIPE_SERIALIZER.register(EnergizedAlloyerRecipe.RECIPE_GROUP, () -> new Item2ItemRecipeSerializer<>(EnergizedAlloyerRecipe::new));
    public static final RegistryObject<IRecipeSerializer<?>> REINFORCED_ALLOYER_SERIALIZER = RECIPE_SERIALIZER.register(ReinforcedAlloyerRecipe.RECIPE_GROUP, () -> new Item2ItemRecipeSerializer<>(ReinforcedAlloyerRecipe::new));

    // FluidItem2Fluid
    public static final RegistryObject<IRecipeSerializer<?>> CHEMICAL_MIXER_SERIALIZER = RECIPE_SERIALIZER.register(ChemicalMixerRecipe.RECIPE_GROUP, () -> new FluidItem2FluidRecipeSerializer<>(ChemicalMixerRecipe::new));
    public static final RegistryObject<IRecipeSerializer<?>> FERMENTATION_PLANT_SERIALIZER = RECIPE_SERIALIZER.register(FermentationPlantRecipe.RECIPE_GROUP, () -> new FluidItem2FluidRecipeSerializer<>(FermentationPlantRecipe::new));
    public static final RegistryObject<IRecipeSerializer<?>> MINERAL_WASHER_SERIALIZER = RECIPE_SERIALIZER.register(MineralWasherRecipe.RECIPE_GROUP, () -> new FluidItem2FluidRecipeSerializer<>(MineralWasherRecipe::new));

    // Fluid2Item
    public static final RegistryObject<IRecipeSerializer<?>> CHEMICAL_CRYSTALIZER_SERIALIZER = RECIPE_SERIALIZER.register(ChemicalCrystalizerRecipe.RECIPE_GROUP, () -> new Fluid2ItemRecipeSerializer<>(ChemicalCrystalizerRecipe::new));

    // Fluid2Gas
    public static final RegistryObject<IRecipeSerializer<?>> ELECTROLYTIC_SEPARATOR_SERIALIZER = RECIPE_SERIALIZER.register(ElectrolyticSeparatorRecipe.RECIPE_GROUP, () -> new Fluid2FluidRecipeSerializer<>(ElectrolyticSeparatorRecipe::new));
    
    private static <T extends IRecipeType<?>> T registerType(ResourceLocation recipeTypeId, VoltaicRecipeType<?> type) {
		return (T) Registry.register(Registry.RECIPE_TYPE, recipeTypeId, type);
	}
    
    public static void init() {
    	
    }

}
