package electrodynamics.api.item.nbtutils;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.Electrodynamics;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class DirectionStorage {

	public static final String KEY = "directionstorage";
	private static final int MAX_SIZE = 1000;
	
	public static void addDirection(int index, Direction value, ItemStack item) {
		if(index >= MAX_SIZE) {
			Electrodynamics.LOGGER.warn(index + " is to large! Not adding value.");
		} else {
			CompoundTag tag = item.getOrCreateTagElement(KEY);
			String key = KEY + index;
			tag.remove(key);
			tag.putString(key, value.getName());
		}
	}
	
	public static Direction getDirection(int index, ItemStack item) {
		if(index > MAX_SIZE) {
			throw new UnsupportedOperationException(index + " is out of bounds!");
		}
		CompoundTag tag = item.getOrCreateTagElement(KEY);
		return Direction.valueOf(tag.getString(KEY + index).toUpperCase());
	}
	
	public static int getSize(ItemStack item) {
		CompoundTag tag = item.getOrCreateTagElement(KEY);
		int size = 0;
		for(int i = 0; i < MAX_SIZE; i++) {
			if(tag.contains(KEY + i)) {
				size++;
			} else {
				break;
			}
		}
		return size;
	}
	
	public static List<Direction> getAllDirections(ItemStack item){
		List<Direction> dirs = new ArrayList<>();
		CompoundTag tag = item.getOrCreateTagElement(KEY);
		for(int i = 0; i < getSize(item); i++) {
			if(tag.contains(KEY + i)) {
				dirs.add(getDirection(i, item));
			} else {
				break;
			}
		}
		return dirs;
	}
	
	public static void clearData(ItemStack item) {
		item.removeTagKey(KEY);
	}
}
