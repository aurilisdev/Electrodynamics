package electrodynamics.prefab.utilities;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class ItemUtils {

	/**
	 * Item analouge to ItemStack.areItemsEqual(stackA,stackB)
	 * 
	 * @param itemA
	 * @param itemB
	 * @return boolean if the items are equal or not
	 */
	public static boolean testItems(Item comparator, Item... itemsToCompare) {
		ItemStack stack = new ItemStack(comparator);
		for (Item item : itemsToCompare) {
			if (stack.getItem() == item) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns an Ingredient from the input tag
	 * 
	 * @param location The mod location e.g "forge", "minecraft"
	 * @param tag      The tag in question e.g. "ingots/gold", "planks"
	 * @return An Ingredient based on the tag
	 */
	@Nullable
	public static Ingredient getIngredientFromTag(String location, String tag) {
		return Ingredient.of(ItemTags.createOptional(new ResourceLocation(location, tag)));
	}

	public static Item fromBlock(Block block) {
		return new ItemStack(block).getItem();
	}

	public static boolean isIngredientMember(Ingredient ing, Item item) {
		for (ItemStack stack : ing.getItems()) {
			if (testItems(item, stack.getItem())) {
				return true;
			}
		}
		return false;
	}

}
