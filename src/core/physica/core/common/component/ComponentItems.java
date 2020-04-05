package physica.core.common.component;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import physica.core.common.item.ItemBase;
import physica.core.common.item.subtypes.EnumBlend;
import physica.core.common.item.subtypes.EnumGear;
import physica.core.common.item.subtypes.EnumImpureBlend;
import physica.core.common.item.subtypes.EnumIngot;
import physica.core.common.item.subtypes.EnumPlate;

public class ComponentItems {
	public static ItemBase ingotBase = new ItemBase("ingotBase", EnumIngot.values().length)
			.setCreativeTab(ComponentsTab.BASICCOMPONENTS);

	public static ItemBase plateBase = new ItemBase("plateBase", EnumPlate.values().length)
			.setCreativeTab(ComponentsTab.BASICCOMPONENTS);
	public static ItemBase blendBase = new ItemBase("blendBase", EnumBlend.values().length)
			.setCreativeTab(ComponentsTab.BASICCOMPONENTS);
	public static ItemBase impureBlendBase = new ItemBase("impureBlendBase", EnumImpureBlend.values().length)
			.setCreativeTab(ComponentsTab.BASICCOMPONENTS);
	public static ItemBase gearBase = new ItemBase("gearBase", EnumGear.values().length)
			.setCreativeTab(ComponentsTab.BASICCOMPONENTS);

	public static void register(IForgeRegistry<Item> registry) {
		registry.registerAll(ingotBase, plateBase, blendBase, impureBlendBase, gearBase);
	}

	public static void registerModels() {
		ingotBase.registerItemModel("ingot");
		plateBase.registerItemModel("plate");
		blendBase.registerItemModel("blend");
		impureBlendBase.registerItemModel("impureblend");
		gearBase.registerItemModel("gear");
	}

}