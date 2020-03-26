package physica.component;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ComponentsTab extends CreativeTabs {
	public static final ComponentsTab BASICCOMPONENTS = new ComponentsTab(getNextID(), "components.creativeTab");

	public ComponentsTab(int index, String label) {
		super(index, label);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack createIcon()
	{
		return new ItemStack(ComponentItems.ingotBase);
	}

}
