package electrodynamics.datagen.utils.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;

public class ElectrodynamicsShapelessCraftingRecipe extends ShapelessRecipeBuilder.Result {

	@Nullable
	private ICondition[] recipeConditions;

	private ElectrodynamicsShapelessCraftingRecipe(ResourceLocation recipeId, Item result, int count, List<Ingredient> ingredients, ICondition[] recipeConditions) {
		super(recipeId, result, count, "", ingredients, null, null);
		this.recipeConditions = recipeConditions;
	}

	public static Builder start(Item item, int count) {
		return new Builder(item, count);
	}

	@Override
	public void serializeRecipeData(JsonObject json) {
		super.serializeRecipeData(json);

		if (recipeConditions == null || recipeConditions.length == 0) {
			return;
		}

		JsonArray conditions = new JsonArray();

		for (ICondition condition : recipeConditions) {
			conditions.add(CraftingHelper.serialize(condition));
		}

		json.add("conditions", conditions);

	}

	@Override
	public JsonObject serializeAdvancement() {
		return null;
	}

	public static class Builder {

		private Item item;
		private int count;
		private List<Ingredient> ingredients = new ArrayList<>();
		@Nullable
		private ICondition[] recipeConditions;


		private Builder(Item item, int count) {
			this.item = item;
			this.count = count;
		}

		public Builder addIngredient(Ingredient ing) {
			ingredients.add(ing);
			return this;
		}

		public Builder addIngredient(String parent, String tag) {
			ingredients.add(Ingredient.of(itemTag(new ResourceLocation(parent, tag))));
			return this;
		}

		public Builder addIngredient(TagKey<Item> tag) {
			ingredients.add(Ingredient.of(tag));
			return this;
		}

		public Builder addIngredient(Item item) {
			return addIngredient(new ItemStack(item));
		}

		public Builder addIngredient(ItemStack item) {
			ingredients.add(Ingredient.of(item));
			return this;
		}
		
		public Builder addConditions(ICondition... conditions) {
			recipeConditions = conditions;
			return this;
		}

		public void complete(String parent, String name, Consumer<FinishedRecipe> consumer) {
			consumer.accept(new ElectrodynamicsShapelessCraftingRecipe(new ResourceLocation(parent, name), item, count, ingredients, recipeConditions));
		}

		private TagKey<Item> itemTag(ResourceLocation tag) {
			return TagKey.create(Registry.ITEM_REGISTRY, tag);
		}

	}

}
