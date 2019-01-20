package physica.library.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryUtilities {

	public static boolean isSameOre(ItemStack stack, String name) {
		int[] oreIds = OreDictionary.getOreIDs(stack);
		for (int ore : oreIds) {
			if (OreDictionary.getOreName(ore).equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	public static Object getAlternatingOreItem(String ore_name, Object alt) {
		if (OreDictionary.doesOreNameExist(ore_name)) {
			for (ItemStack itemStack : OreDictionary.getOres(ore_name)) {
				if (itemStack != null) {
					return ore_name;
				}
			}
		}
		return alt != null ? alt : ore_name;
	}

}
