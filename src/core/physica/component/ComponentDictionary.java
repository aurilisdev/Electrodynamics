package physica.component;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import physica.block.state.EnumOreState;
import physica.item.subtypes.EnumBlend;
import physica.item.subtypes.EnumIngot;
import physica.item.subtypes.EnumPlate;

public class ComponentDictionary {
	public static void initOreDictionary()
	{
		for (EnumIngot ingot : EnumIngot.values())
		{
			OreDictionary.registerOre(ingot.getOre(), ComponentItems.ingotBase.createStackFromSubtype(ingot.ordinal()));
		}
		for (EnumBlend blend : EnumBlend.values())
		{
			OreDictionary.registerOre(blend.getOre(), ComponentItems.blendBase.createStackFromSubtype(blend.ordinal()));
		}
		for (EnumPlate plate : EnumPlate.values())
		{
			OreDictionary.registerOre(plate.getOre(), ComponentItems.plateBase.createStackFromSubtype(plate.ordinal()));
		}
		for (EnumOreState ore : EnumOreState.values())
		{
			OreDictionary.registerOre(ore.getName(), new ItemStack(ComponentBlocks.blockOre, 1, ore.ordinal()));
		}
	}
}
