package electrodynamics.api.item.nbtutils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class BooleanStorage {

	public static final String KEY = "booleanstorage";
	
	public static void addBoolean(int index, boolean value, ItemStack item) {
		CompoundTag tag = item.getOrCreateTagElement(KEY);
		String key = KEY + index;
		tag.remove(key);
		tag.putBoolean(key, value);
	}
	
	public static boolean getBoolean(int index, ItemStack item) {
		CompoundTag tag = item.getOrCreateTagElement(KEY);
		return tag.getBoolean(KEY + index);
	}
	
	public static void clearData(ItemStack item) {
		item.removeTagKey(KEY);
	}
	
}
