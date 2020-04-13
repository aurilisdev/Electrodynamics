package physica.core.common.component;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import physica.core.common.block.state.EnumOreState;
import physica.core.common.item.subtypes.EnumBlend;
import physica.core.common.item.subtypes.EnumImpureBlend;
import physica.core.common.item.subtypes.EnumIngot;
import physica.core.common.item.subtypes.EnumPlate;

public class ComponentDictionary {
	public static void initOreDictionary() {
		for (EnumIngot ingot : EnumIngot.values()) {
			OreDictionary.registerOre(ingot.getOre(), ComponentItems.ingotBase.createStackFromSubtype(ingot.ordinal()));
		}
		for (EnumBlend blend : EnumBlend.values()) {
			OreDictionary.registerOre(blend.getOre(), ComponentItems.blendBase.createStackFromSubtype(blend.ordinal()));
		}
		for (EnumImpureBlend blend : EnumImpureBlend.values()) {
			OreDictionary.registerOre(blend.getOre(), ComponentItems.blendBase.createStackFromSubtype(blend.ordinal()));
		}
		for (EnumPlate plate : EnumPlate.values()) {
			OreDictionary.registerOre(plate.getOre(), ComponentItems.plateBase.createStackFromSubtype(plate.ordinal()));
		}
		for (EnumOreState ore : EnumOreState.values()) {
			OreDictionary.registerOre(ore.getName(), new ItemStack(ComponentBlocks.blockOre, 1, ore.ordinal()));
		}
	}
}
