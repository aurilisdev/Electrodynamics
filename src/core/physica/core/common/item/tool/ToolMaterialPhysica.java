package physica.core.common.item.tool;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class ToolMaterialPhysica {
	public static ToolMaterial COPPER;
	public static ToolMaterial TIN;
	public static ToolMaterial LEAD;
	public static ToolMaterial SILVER;
	public static ToolMaterial BRONZE;
	public static ToolMaterial STEEL;

	public static void init() {
		COPPER = EnumHelper.addToolMaterial("PHYS_COPPER", 2, 224, 5.5f, 2f, 12);
		TIN = EnumHelper.addToolMaterial("PHYS_TIN", 2, 205, 5.5f, 2f, 12);
		LEAD = EnumHelper.addToolMaterial("PHYS_LEAD", 2, 871, 5f, 2f, 14);
		SILVER = EnumHelper.addToolMaterial("PHYS_SILVER", 2, 1022, 8f, 3f, 20);
		BRONZE = EnumHelper.addToolMaterial("PHYS_BRONZE", 2, 1156, 8f, 3f, 16);
		STEEL = EnumHelper.addToolMaterial("PHYS_STEEL", 2, 1345, 8f, 3f, 18);

	}
}