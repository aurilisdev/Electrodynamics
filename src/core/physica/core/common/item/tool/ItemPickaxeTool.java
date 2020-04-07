package physica.core.common.item.tool;

import net.minecraft.item.ItemPickaxe;
import physica.core.common.component.ComponentsTab;

public class ItemPickaxeTool extends ItemPickaxe {

	public ItemPickaxeTool(String name, ToolMaterial material) {
		super(material);
		setTranslationKey(name);
		setRegistryName(name);
		setCreativeTab(ComponentsTab.BASICCOMPONENTS);
	}
}
