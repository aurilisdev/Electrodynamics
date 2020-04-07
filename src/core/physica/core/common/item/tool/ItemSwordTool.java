package physica.core.common.item.tool;

import net.minecraft.item.ItemSword;

public class ItemSwordTool extends ItemSword {

	public ItemSwordTool(String name, ToolMaterial material) {
		super(material);
		setTranslationKey(name);
		setRegistryName(name);
	}
}
