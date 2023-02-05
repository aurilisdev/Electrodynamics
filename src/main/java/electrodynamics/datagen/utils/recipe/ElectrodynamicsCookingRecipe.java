package electrodynamics.datagen.utils.recipe;

import java.util.function.Consumer;

import com.google.gson.JsonObject;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ElectrodynamicsCookingRecipe extends SimpleCookingRecipeBuilder.Result {

	public static float expereince;

	private ElectrodynamicsCookingRecipe(ResourceLocation id, Ingredient input, Item result, float experience, int cookingTime, RecipeSerializer<? extends AbstractCookingRecipe> serializer) {
		super(id, "", input, result, experience, cookingTime, null, null, serializer);
	}

	@Override
	public JsonObject serializeAdvancement() {
		return null;
	}

	public static SmeltingBuilder smeltingRecipe(Item result, float experience, int smeltTime) {
		return new SmeltingBuilder(result, experience, smeltTime);
	}

	public static SmokingBuilder smokingRecipe(Item result, float experience, int smeltTime) {
		return new SmokingBuilder(result, experience, smeltTime);
	}

	public static BlastingBuilder blastingRecipe(Item result, float experience, int smeltTime) {
		return new BlastingBuilder(result, experience, smeltTime);
	}

	public static class Builder {

		private final Item result;
		private final float experience;
		private final int smeltTime;
		private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;
		private Ingredient input;

		private Builder(Item result, float experience, int smeltTime, RecipeSerializer<? extends AbstractCookingRecipe> serializer) {
			this.result = result;
			this.experience = experience;
			this.smeltTime = smeltTime;
			this.serializer = serializer;
		}

		public Builder input(Item item) {
			return input(new ItemStack(item));
		}

		public Builder input(ItemStack item) {
			return input(Ingredient.of(item));
		}

		public Builder input(TagKey<Item> tag) {
			return input(Ingredient.of(tag));
		}

		public Builder input(Ingredient item) {
			input = item;
			return this;
		}

		public void complete(String parent, String name, Consumer<FinishedRecipe> consumer) {
			consumer.accept(new ElectrodynamicsCookingRecipe(new ResourceLocation(parent, name), input, result, experience, smeltTime, serializer));
		}

	}

	public static class SmeltingBuilder extends Builder {

		private SmeltingBuilder(Item result, float experience, int smeltTime) {
			super(result, expereince, smeltTime, RecipeSerializer.SMELTING_RECIPE);
		}

	}

	public static class SmokingBuilder extends Builder {

		private SmokingBuilder(Item result, float experience, int smeltTime) {
			super(result, expereince, smeltTime, RecipeSerializer.SMOKING_RECIPE);
		}

	}

	public static class BlastingBuilder extends Builder {

		private BlastingBuilder(Item result, float experience, int smeltTime) {
			super(result, expereince, smeltTime, RecipeSerializer.BLASTING_RECIPE);
		}

	}

}
