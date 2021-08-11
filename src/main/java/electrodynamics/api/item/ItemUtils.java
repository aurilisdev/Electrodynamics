package electrodynamics.api.item;

import javax.annotation.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class ItemUtils {
	
	/**
	 * Item analouge to ItemStack.areItemsEqual(stackA,stackB)
	 * @param itemA
	 * @param itemB
	 * @return boolean if the items are equal or not
	 */
	public static boolean testItems(Item itemA, Item itemB) {
		return ItemStack.areItemsEqual(new ItemStack(itemA), new ItemStack(itemB));
	}
	
	/**
	 * Returns an Ingredient from the input tag
	 * @param location The mod location e.g "forge", "minecraft"
	 * @param tag The tag in question e.g. "ingots/gold", "planks"
	 * @return An Ingredient based on the tag
	 */
	@Nullable
	public static Ingredient getIngredientFromTag(String location, String tag) {
		return Ingredient.fromTag(ItemTags.getCollection().get(new ResourceLocation(location,tag)));
	}

}
