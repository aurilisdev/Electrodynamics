package physica.core.common.item.tool;

import net.minecraft.item.ItemAxe;

public class ItemAxeTool extends ItemAxe {

	protected ItemAxeTool(String name, ToolMaterial material) {
		super(material);
		setTranslationKey(name);
		setRegistryName(name);
	}
}
