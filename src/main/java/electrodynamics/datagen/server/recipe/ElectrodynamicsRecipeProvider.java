package electrodynamics.datagen.server.recipe;

import electrodynamics.datagen.server.recipe.types.custom.fluid2fluid.ElectrodynamicsElectrolyticSeparatorRecipes;
import electrodynamics.datagen.server.recipe.types.custom.fluid2item.ElectrodynamicsChemicalCrystallizerRecipes;
import electrodynamics.datagen.server.recipe.types.custom.fluiditem2fluid.ElectrodynamicsChemicalMixerRecipes;
import electrodynamics.datagen.server.recipe.types.custom.fluiditem2fluid.ElectrodynamicsFermentationPlantRecipes;
import electrodynamics.datagen.server.recipe.types.custom.fluiditem2fluid.ElectrodynamicsMineralWasherRecipes;
import electrodynamics.datagen.server.recipe.types.custom.item2item.ElectrodynamicsEnergizedAlloyerRecipes;
import electrodynamics.datagen.server.recipe.types.custom.item2item.ElectrodynamicsLatheRecipes;
import electrodynamics.datagen.server.recipe.types.custom.item2item.ElectrodynamicsMineralCrusherRecipes;
import electrodynamics.datagen.server.recipe.types.custom.item2item.ElectrodynamicsMineralGrinderRecipes;
import electrodynamics.datagen.server.recipe.types.custom.item2item.ElectrodynamicsOxidationFurnaceRecipes;
import electrodynamics.datagen.server.recipe.types.custom.item2item.ElectrodynamicsReinforcedAlloyerRecipes;
import electrodynamics.datagen.server.recipe.types.custom.item2item.ElectrodynamicsWireMillRecipes;
import electrodynamics.datagen.server.recipe.types.vanilla.ElectrodynamicsCraftingTableRecipes;
import electrodynamics.datagen.server.recipe.types.vanilla.ElectrodynamicsSmeltingRecipes;
import electrodynamics.datagen.server.recipe.types.vanilla.ElectrodynamicsStonecuttingRecipes;
import net.minecraft.data.DataGenerator;
import voltaic.datagen.utils.server.recipe.BaseRecipeProvider;

public class ElectrodynamicsRecipeProvider extends BaseRecipeProvider {

	public ElectrodynamicsRecipeProvider(DataGenerator gen) {
		super(gen);
	}

	@Override
	public void addRecipes() {
		generators.add(new ElectrodynamicsCraftingTableRecipes());
		generators.add(new ElectrodynamicsSmeltingRecipes());
		generators.add(new ElectrodynamicsElectrolyticSeparatorRecipes());
		generators.add(new ElectrodynamicsChemicalCrystallizerRecipes());
		generators.add(new ElectrodynamicsMineralWasherRecipes());
		generators.add(new ElectrodynamicsFermentationPlantRecipes());
		generators.add(new ElectrodynamicsChemicalMixerRecipes());
		generators.add(new ElectrodynamicsEnergizedAlloyerRecipes());
		generators.add(new ElectrodynamicsLatheRecipes());
		generators.add(new ElectrodynamicsMineralCrusherRecipes());
		generators.add(new ElectrodynamicsMineralGrinderRecipes());
		generators.add(new ElectrodynamicsOxidationFurnaceRecipes());
		generators.add(new ElectrodynamicsReinforcedAlloyerRecipes());
		generators.add(new ElectrodynamicsWireMillRecipes());
		generators.add(new ElectrodynamicsStonecuttingRecipes());
	}

}
