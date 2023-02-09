package electrodynamics.datagen.utils.recipe;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

/**
 * Imagine mojank providing a working data generator that didn't have the recipe book hard-coded into it
 * 
 * @author skip999
 *
 */
public class ElectrodynamicsSingleItemRecipeBuilder extends SingleItemRecipeBuilder.Result {

	public ElectrodynamicsSingleItemRecipeBuilder(ResourceLocation id, RecipeSerializer<?> recipe, Ingredient input, Item result, int count) {
		super(id, recipe, "", input, result, count, null, null);
	}

	@Override
	@Nullable
	public JsonObject serializeAdvancement() {
		return null;
	}

	public static Builder stonecuttingRecipe(Ingredient input, Item output, int count) {
		return new Builder(input, output, count, RecipeSerializer.STONECUTTER);
	}

	public static class Builder {

		private final Item result;
		private final int count;
		private final RecipeSerializer<?> serializer;
		private final Ingredient input;

		public Builder(Ingredient input, Item result, int count, RecipeSerializer<?> serializer) {
			this.result = result;
			this.count = count;
			this.serializer = serializer;
			this.input = input;
		}

		public void complete(String parent, String name, Consumer<FinishedRecipe> consumer) {
			consumer.accept(new ElectrodynamicsSingleItemRecipeBuilder(new ResourceLocation(parent, name), serializer, input, result, count));
		}

	}

}
