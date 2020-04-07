package physica.core.common.item.tool;

import net.minecraft.item.ItemSpade;

public class ItemSpadeTool extends ItemSpade {

	protected ItemSpadeTool(String name, ToolMaterial material) {
		super(material);
		setTranslationKey(name);
		setRegistryName(name);
	}
}
