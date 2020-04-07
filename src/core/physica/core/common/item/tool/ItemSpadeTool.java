package physica.core.common.item.tool;

import net.minecraft.item.ItemSpade;
import physica.core.common.component.ComponentsTab;

public class ItemSpadeTool extends ItemSpade {

	public ItemSpadeTool(String name, ToolMaterial material) {
		super(material);
		setTranslationKey(name);
		setRegistryName(name);
		setCreativeTab(ComponentsTab.BASICCOMPONENTS);
	}
}
