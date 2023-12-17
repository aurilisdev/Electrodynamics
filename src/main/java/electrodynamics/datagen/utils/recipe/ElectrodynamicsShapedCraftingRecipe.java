package electrodynamics.datagen.utils.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;

public class ElectrodynamicsShapedCraftingRecipe implements IFinishedRecipe {

	private final ResourceLocation id;
	private final Item result;
	private final int count;
	private final String group;
	private final List<String> pattern;
	private final Map<Character, Ingredient> key;

	@Nullable
	private ICondition[] recipeConditions;

	private ElectrodynamicsShapedCraftingRecipe(ResourceLocation recipeId, Item result, int count, List<String> pattern, Map<Character, Ingredient> keys, ICondition[] recipeConditions) {

		id = recipeId;
		this.result = result;
		this.count = count;
		group = "";
		this.pattern = pattern;
		key = keys;
		
		this.recipeConditions = recipeConditions;
	}

	public static Builder start(Item item, int count) {
		return new Builder(item, count);
	}

	@Override
	public void serializeRecipeData(JsonObject json) {
		if (!this.group.isEmpty()) {
			json.addProperty("group", this.group);
		}

		JsonArray jsonarray = new JsonArray();

		for (String s : this.pattern) {
			jsonarray.add(s);
		}

		json.add("pattern", jsonarray);
		JsonObject jsonobject = new JsonObject();

		for (Entry<Character, Ingredient> entry : this.key.entrySet()) {
			jsonobject.add(String.valueOf(entry.getKey()), entry.getValue().toJson());
		}

		json.add("key", jsonobject);
		JsonObject jsonobject1 = new JsonObject();
		jsonobject1.addProperty("item", Registry.ITEM.getKey(this.result).toString());
		if (this.count > 1) {
			jsonobject1.addProperty("count", this.count);
		}

		json.add("result", jsonobject1);

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
		private List<String> patterns = new ArrayList<>();
		private Map<Character, Ingredient> keys = new HashMap<>();
		@Nullable
		private ICondition[] recipeConditions;

		private Builder(Item item, int count) {
			this.item = item;
			this.count = count;
		}

		public Builder addPattern(String pattern) {
			if (patterns.size() > 3) {
				throw new UnsupportedOperationException("Already 3 patterns present");
			}
			patterns.add(pattern);
			return this;
		}

		public Builder addKey(Character key, Ingredient ing) {
			keys.put(key, ing);
			return this;
		}

		public Builder addKey(Character key, INamedTag<Item> ing) {
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

		public Builder addConditions(ICondition... conditions) {
			recipeConditions = conditions;
			return this;
		}

		public void complete(String parent, String name, Consumer<IFinishedRecipe> consumer) {
			consumer.accept(new ElectrodynamicsShapedCraftingRecipe(new ResourceLocation(parent, name), item, count, patterns, keys, recipeConditions));
		}

		private INamedTag<Item> itemTag(ResourceLocation tag) {
			return ItemTags.createOptional(tag);
		}

	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	@Override
	public IRecipeSerializer<?> getType() {
		return IRecipeSerializer.SHAPED_RECIPE;
	}

	@Override
	public ResourceLocation getAdvancementId() {
		return null;
	}

}
