package electrodynamics.datagen.server.recipe.types.vanilla;

import java.util.function.Consumer;

import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeConcrete;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.ElectrodynamicsSingleItemRecipeBuilder;
import electrodynamics.registers.ElectrodynamicsBlocks;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.Ingredient;

public class ElectrodynamicsStonecuttingRecipes extends AbstractRecipeGenerator {

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {
		
		ElectrodynamicsSingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.of(ElectrodynamicsTags.Items.CONCRETES), ElectrodynamicsBlocks.getBlock(SubtypeConcrete.regular).asItem(), 1).complete(References.ID, "stonecutting_concrete_regular", consumer);
		ElectrodynamicsSingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.of(ElectrodynamicsTags.Items.CONCRETES), ElectrodynamicsBlocks.getBlock(SubtypeConcrete.bricks).asItem(), 1).complete(References.ID, "stonecutting_concrete_bricks", consumer);
		ElectrodynamicsSingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.of(ElectrodynamicsTags.Items.CONCRETES), ElectrodynamicsBlocks.getBlock(SubtypeConcrete.tile).asItem(), 1).complete(References.ID, "stonecutting_concrete_tile", consumer);

	}

}
