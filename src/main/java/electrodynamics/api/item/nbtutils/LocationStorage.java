package electrodynamics.api.item.nbtutils;

import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class LocationStorage {
	
	public static final String KEY = "locationstorage";
	
	public static void addBlockPos(int index, BlockPos value, ItemStack item) {
		Location loc = new Location(value.getX(), value.getY(), value.getZ());
		addLocation(index, loc, item);
	}
	
	public static void addLocation(int index, Location value, ItemStack item) {
		CompoundTag tag = item.getOrCreateTagElement(KEY);
		String key = KEY + index;
		tag.remove(key);
		value.writeToNBT(tag, key);
	}
	
	public static Location getLocation(int index, ItemStack item) {
		CompoundTag tag = item.getOrCreateTagElement(KEY);
		return Location.readFromNBT(tag, KEY + index);
	}
	
	public static BlockPos getBlockPos(int index, ItemStack item) {
		Location loc = getLocation(index, item);
		return new BlockPos(loc.x(), loc.y(), loc.z());
	}
	
	public static void clearData(ItemStack item) {
		item.removeTagKey(KEY);
	}
}
