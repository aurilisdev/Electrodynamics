package physica.core.common.item.tool;

import net.minecraft.item.ItemAxe;
import physica.core.common.component.ComponentsTab;

public class ItemAxeTool extends ItemAxe {

	public ItemAxeTool(String name, ToolMaterial material) {
		super(material, 8.0f, -3.1f);
		setTranslationKey(name);
		setRegistryName(name);
		setCreativeTab(ComponentsTab.BASICCOMPONENTS);
	}
}
