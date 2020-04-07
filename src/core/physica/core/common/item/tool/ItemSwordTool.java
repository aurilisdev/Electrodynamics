package physica.core.common.item.tool;

import net.minecraft.item.ItemSword;
import physica.core.common.component.ComponentsTab;

public class ItemSwordTool extends ItemSword {

	public ItemSwordTool(String name, ToolMaterial material) {
		super(material);
		setTranslationKey(name);
		setRegistryName(name);
		setCreativeTab(ComponentsTab.BASICCOMPONENTS);
	}
}
