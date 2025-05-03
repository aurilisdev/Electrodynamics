package electrodynamics.datagen.server.recipe.types.vanilla;

import java.util.function.Consumer;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeConcrete;
import electrodynamics.registers.ElectrodynamicsBlocks;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;
import voltaic.datagen.utils.server.recipe.CustomSingleItemRecipeBuilder;

public class ElectrodynamicsStonecuttingRecipes extends AbstractRecipeGenerator {

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {
		
		CustomSingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.of(VoltaicTags.Items.CONCRETES), ElectrodynamicsItems.ITEMS_CONCRETE.getValue(SubtypeConcrete.regular), 1).complete(Electrodynamics.ID, "stonecutting_concrete_regular", consumer);
		CustomSingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.of(VoltaicTags.Items.CONCRETES), ElectrodynamicsItems.ITEMS_CONCRETE.getValue(SubtypeConcrete.bricks), 1).complete(Electrodynamics.ID, "stonecutting_concrete_bricks", consumer);
		CustomSingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.of(VoltaicTags.Items.CONCRETES), ElectrodynamicsItems.ITEMS_CONCRETE.getValue(SubtypeConcrete.tile), 1).complete(Electrodynamics.ID, "stonecutting_concrete_tile", consumer);

	}

}
