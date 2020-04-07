package physica.core.common.item.tool;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class ToolMaterialPhysica {
	public static ToolMaterial COPPER;
	public static ToolMaterial TIN;
	public static ToolMaterial SILVER;
	public static ToolMaterial LEAD;

	public static void init() {
		COPPER = EnumHelper.addToolMaterial("COPPER", 2, 224, 5.5f, 2f, 12);
		TIN = EnumHelper.addToolMaterial("TIN", 2, 205, 5.5f, 2f, 12);
		SILVER = EnumHelper.addToolMaterial("SILVER", 2, 1022, 8f, 3f, 20);
		LEAD = EnumHelper.addToolMaterial("LEAD", 2, 871, 5f, 2f, 14);
	}
}