package electrodynamics.api.item.nbtutils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class DoubleStorage {

	public static final String KEY = "doublestorage";
	
	public static void addDouble(int index, double value, ItemStack item) {
		CompoundTag tag = item.getOrCreateTagElement(KEY);
		String key = KEY + index;
		tag.remove(key);
		tag.putDouble(key, value);
	}
	
	public static double getDouble(int index, ItemStack item) {
		CompoundTag tag = item.getOrCreateTagElement(KEY);
		return tag.getDouble(KEY + index);
	}
	
	public static void clearData(ItemStack item) {
		item.removeTagKey(KEY);
	}
}
