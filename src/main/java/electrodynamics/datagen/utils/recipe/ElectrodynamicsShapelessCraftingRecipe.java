package electrodynamics.datagen.utils.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.google.gson.JsonObject;

import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class ElectrodynamicsShapelessCraftingRecipe extends ShapelessRecipeBuilder.Result {

	private ElectrodynamicsShapelessCraftingRecipe(ResourceLocation recipeId, Item result, int count, List<Ingredient> ingredients) {
		super(recipeId, result, count, "", ingredients, null, null);
	}
	
	public static Builder start(Item item, int count) {
		return new Builder(item, count);
	}
	
	@Override
	public JsonObject serializeAdvancement() {
		return null;
	}
	
	public static class Builder {
		
		private Item item;
		private int count;
		private List<Ingredient> ingredients = new ArrayList<>();
		
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
		
		
		
		public void complete(String parent, String name, Consumer<FinishedRecipe> consumer){
			consumer.accept(new ElectrodynamicsShapelessCraftingRecipe(new ResourceLocation(parent, name), item, count, ingredients));
		}
		
		private TagKey<Item> itemTag(ResourceLocation tag) {
			return TagKey.create(Registry.ITEM_REGISTRY, tag);
		}
		
	}

}
