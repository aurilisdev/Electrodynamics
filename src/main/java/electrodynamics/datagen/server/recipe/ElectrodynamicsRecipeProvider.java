package electrodynamics.datagen.server.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import electrodynamics.datagen.server.recipe.types.custom.fluid2fluid.ElectrolyticSeparator;
import electrodynamics.datagen.server.recipe.types.custom.fluid2item.ChemicalCrystallizer;
import electrodynamics.datagen.server.recipe.types.custom.fluiditem2fluid.ChemicalMixer;
import electrodynamics.datagen.server.recipe.types.custom.fluiditem2fluid.FermentationPlant;
import electrodynamics.datagen.server.recipe.types.custom.fluiditem2fluid.MineralWasher;
import electrodynamics.datagen.server.recipe.types.custom.item2item.EnergizedAlloyer;
import electrodynamics.datagen.server.recipe.types.custom.item2item.Lathe;
import electrodynamics.datagen.server.recipe.types.custom.item2item.MineralCrusher;
import electrodynamics.datagen.server.recipe.types.custom.item2item.MineralGrinder;
import electrodynamics.datagen.server.recipe.types.custom.item2item.OxidationFurnace;
import electrodynamics.datagen.server.recipe.types.custom.item2item.ReinforcedAlloyer;
import electrodynamics.datagen.server.recipe.types.custom.item2item.WireMill;
import electrodynamics.datagen.server.recipe.types.vanilla.CraftingTable;
import electrodynamics.datagen.server.recipe.types.vanilla.Smelting;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

public class ElectrodynamicsRecipeProvider extends RecipeProvider {

	private static final List<AbstractRecipeGenerator> GENERATORS = new ArrayList<>();
	
	
	public ElectrodynamicsRecipeProvider(DataGenerator gen) {
		super(gen);
		GENERATORS.add(new CraftingTable());
		GENERATORS.add(new Smelting());
		GENERATORS.add(new ElectrolyticSeparator());
		GENERATORS.add(new ChemicalCrystallizer());
		GENERATORS.add(new MineralWasher());
		GENERATORS.add(new FermentationPlant());
		GENERATORS.add(new ChemicalMixer());
		GENERATORS.add(new EnergizedAlloyer());
		GENERATORS.add(new Lathe());
		GENERATORS.add(new MineralCrusher());
		GENERATORS.add(new MineralGrinder());
		GENERATORS.add(new OxidationFurnace());
		GENERATORS.add(new ReinforcedAlloyer());
		GENERATORS.add(new WireMill());
	}

	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		for(AbstractRecipeGenerator generator : GENERATORS) {
			generator.addRecipes(consumer);
		}
	}



}
