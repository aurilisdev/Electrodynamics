package physica.core.common.item.tool;

import net.minecraft.item.ItemPickaxe;

public class ItemPickaxeTool extends ItemPickaxe {

	protected ItemPickaxeTool(String name, ToolMaterial material) {
		super(material);
		setTranslationKey(name);
		setRegistryName(name);
	}
}
