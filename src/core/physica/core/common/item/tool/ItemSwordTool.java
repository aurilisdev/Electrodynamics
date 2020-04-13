package physica.core.common.item.tool;

import net.minecraft.item.ItemSword;
import physica.core.Physica;
import physica.core.common.component.ComponentsTab;

public class ItemSwordTool extends ItemSword {
	public final String baseName;

	public void registerItemModel(String folder) {
		String prefix = folder.isEmpty() ? folder : folder + "/";
		Physica.proxy.registerItemRenderer(this, 0, prefix + baseName);
	}

	public ItemSwordTool(String name, ToolMaterial material) {
		super(material);
		baseName = name;
		setTranslationKey(name);
		setRegistryName(name);
		setCreativeTab(ComponentsTab.BASICCOMPONENTS);
	}

}
