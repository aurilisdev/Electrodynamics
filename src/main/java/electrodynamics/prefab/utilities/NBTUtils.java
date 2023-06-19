package electrodynamics.prefab.utilities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class NBTUtils {

	public static final String TIMER = "timer";
	public static final String MODE = "mode";
	public static final String XP = "xp";
	public static final String USED = "used";
	public static final String ON = "on";
	public static final String SMART = "smart";
	public static final String SIZE = "size";
	public static final String DIRECTION = "dir";
	public static final String LOCATION = "loc";
	public static final String PLATES = "plates";
	public static final String SUCESS = "sucess";
	public static final String PLAYING_SOUND = "false";
	public static final String DIMENSION = "dimension";
	
	public static final String FORTUNE_ENCHANT = "fortuneenchant";
	public static final String SILK_TOUCH_ENCHANT = "silktouchenchant";
	public static final String SPEED_ENCHANT = "speedenchant";

	public static List<Direction> readDirectionList(ItemStack item) {
		List<Direction> dirs = new ArrayList<>();
		CompoundTag tag = item.getTag();
		int size = tag.getInt(SIZE + DIRECTION);
		for (int i = 0; i < size; i++) {
			dirs.add(Direction.valueOf(tag.getString(DIRECTION + i).toUpperCase()));
		}
		return dirs;
	}

	public static void writeDirectionList(List<Direction> dirs, ItemStack item) {
		int size = dirs.size();
		CompoundTag tag = item.getTag();
		tag.putInt(SIZE + DIRECTION, size);
		for (int i = 0; i < size; i++) {
			tag.putString(DIRECTION + i, dirs.get(i).getName());
		}
	}

	public static void clearDirectionList(ItemStack item) {
		CompoundTag tag = item.getTag();
		int size = tag.getInt(SIZE + DIRECTION);
		for (int i = 0; i < size; i++) {
			tag.remove(DIRECTION + i);
		}
		tag.remove(SIZE + DIRECTION);
	}

	public static CompoundTag writeDimensionToTag(ResourceKey<Level> level) {
		CompoundTag tag = new CompoundTag();
		tag.putString(DIMENSION, level.location().toString());
		return tag;
	}

	public static ResourceKey<Level> readDimensionFromTag(CompoundTag tag) {
		return ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(tag.getString(DIMENSION)));
	}

}
