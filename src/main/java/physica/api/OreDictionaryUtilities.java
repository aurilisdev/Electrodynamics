package physica.api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryUtilities {
	public static Object getOreItem(String ore_name, ItemStack alt) {
		if (OreDictionary.doesOreNameExist(ore_name))
		{
			for (ItemStack itemStack : OreDictionary.getOres(ore_name))
			{
				if (itemStack != null) return ore_name;
			}
		}
		return alt != null ? alt : ore_name;
	}

	public static Object getOreItem(String ore_name, Item alt) {
		if (OreDictionary.doesOreNameExist(ore_name))
		{
			for (ItemStack itemStack : OreDictionary.getOres(ore_name))
			{
				if (itemStack != null) return ore_name;
			}
		}
		return alt != null ? alt : ore_name;
	}

	public static Object getOreItem(String ore_name, Block alt) {
		if (OreDictionary.doesOreNameExist(ore_name))
		{
			for (ItemStack itemStack : OreDictionary.getOres(ore_name))
			{
				if (itemStack != null) return ore_name;
			}
		}
		return alt != null ? alt : ore_name;
	}
}