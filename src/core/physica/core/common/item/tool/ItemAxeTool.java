package physica.core.common.item.tool;

import net.minecraft.item.ItemAxe;
import physica.core.Physica;
import physica.core.common.component.ComponentsTab;

public class ItemAxeTool extends ItemAxe {
	public final String baseName;

	public void registerItemModel(String folder) {
		String prefix = folder.isEmpty() ? folder : folder + "/";
		Physica.proxy.registerItemRenderer(this, 0, prefix + baseName);
	}

	public ItemAxeTool(String name, ToolMaterial material) {
		super(material, 8.0f, -3.1f);
		baseName = name;
		setTranslationKey(name);
		setRegistryName(name);
		setCreativeTab(ComponentsTab.BASICCOMPONENTS);
	}
}
