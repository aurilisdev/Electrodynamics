package electrodynamics.datagen.utils.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.google.gson.JsonObject;

import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class ElectrodynamicsShapedCraftingRecipe extends ShapedRecipeBuilder.Result {

	private ElectrodynamicsShapedCraftingRecipe(ResourceLocation recipeId, Item result, int count, List<String> pattern, 
			Map<Character, Ingredient> keys) {
		super(recipeId, result, count, "", pattern, keys, null, null);
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
		private List<String> patterns = new ArrayList<>();
		private Map<Character, Ingredient> keys = new HashMap<>();
		
		private Builder(Item item, int count) {
			this.item = item;
			this.count = count;
		}
		
		public Builder addPattern(String pattern) {
			if(patterns.size() > 3) {
				throw new UnsupportedOperationException("Already 3 patterns present");
			}
			this.patterns.add(pattern);
			return this;
		}
		
		public Builder addKey(Character key, Ingredient ing) {
			keys.put(key, ing);
			return this;
		}
		
		public Builder addKey(Character key, TagKey<Item> ing) {
			keys.put(key, Ingredient.of(ing));
			return this;
		}
		
		public Builder addKey(Character key, String parent, String tag) {
			keys.put(key, Ingredient.of(itemTag(new ResourceLocation(parent, tag))));
			return this;
		}
		
		public Builder addKey(Character key, Item item) {
			return addKey(key, new ItemStack(item));
		}
		
		public Builder addKey(Character key, ItemStack item) {
			keys.put(key, Ingredient.of(item));
			return this;
		}
		
		public void complete(String parent, String name, Consumer<FinishedRecipe> consumer){
			consumer.accept(new ElectrodynamicsShapedCraftingRecipe(new ResourceLocation(parent, name), item, count, patterns, keys));
		}
		
		private TagKey<Item> itemTag(ResourceLocation tag) {
			return TagKey.create(Registry.ITEM_REGISTRY, tag);
		}
		
	}

}
