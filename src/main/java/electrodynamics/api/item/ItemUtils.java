package electrodynamics.api.item;

import javax.annotation.Nullable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public class ItemUtils {

	/**
	 * Item analouge to ItemStack.areItemsEqual(stackA,stackB)
	 * 
	 * @param itemA
	 * @param itemB
	 * @return boolean if the items are equal or not
	 */
	public static boolean testItems(Item itemA, Item itemB) {
		return ItemStack.isSame(new ItemStack(itemA), new ItemStack(itemB));
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
		return Ingredient.of(ItemTags.getAllTags().getTag(new ResourceLocation(location, tag)));
	}
	
	public static Item fromBlock(Block block) {
		return new ItemStack(block).getItem();
	}

}
