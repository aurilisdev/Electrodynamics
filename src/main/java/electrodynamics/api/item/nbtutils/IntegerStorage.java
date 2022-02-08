package electrodynamics.api.item.nbtutils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class IntegerStorage {
	
	public static final String KEY = "intstorage";
	
	public static void addInteger(int index, int value, ItemStack item) {
		CompoundTag tag = item.getOrCreateTagElement(KEY);
		String key = KEY + index;
		tag.remove(key);
		tag.putInt(key, value);
	}
	
	public static int getInteger(int index, ItemStack item) {
		CompoundTag tag = item.getOrCreateTagElement(KEY);
		return tag.getInt(KEY + index);
	}
	
	public static void clearData(ItemStack item) {
		item.removeTagKey(KEY);
	}

}
