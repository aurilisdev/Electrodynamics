package physica.core.common.component;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import physica.core.common.item.ItemBase;
import physica.core.common.item.subtypes.EnumBlend;
import physica.core.common.item.subtypes.EnumIngot;
import physica.core.common.item.subtypes.EnumPlate;

public class ComponentItems {
	public static ItemBase ingotBase = new ItemBase("ingotBase", EnumIngot.values().length)
			.setCreativeTab(ComponentsTab.BASICCOMPONENTS);

	// TODO: Shaped Recipe for superconductive ingot using dust. SiGoSi, GoEyeGo,
	// SiGoSi
	// TODO: Shapeless Recipe for bronze blend. 2 Copper Dust + 1 Tin Dust = 2
	// bronze dust
	public static ItemBase plateBase = new ItemBase("plateBase", EnumPlate.values().length)
			.setCreativeTab(ComponentsTab.BASICCOMPONENTS);
	public static ItemBase blendBase = new ItemBase("blendBase", EnumBlend.values().length)
			.setCreativeTab(ComponentsTab.BASICCOMPONENTS);

	public static void register(IForgeRegistry<Item> registry) {
		registry.registerAll(ingotBase);
		registry.registerAll(plateBase);
		registry.registerAll(blendBase);
	}

	public static void registerModels() {
		ingotBase.registerItemModel("ingot");
		plateBase.registerItemModel("plate");
		blendBase.registerItemModel("blend");
	}

}