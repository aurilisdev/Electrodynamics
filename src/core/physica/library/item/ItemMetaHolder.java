package physica.library.item;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import physica.CoreReferences;
import physica.core.common.CoreTabRegister;

public class ItemMetaHolder extends ItemUpdateable {

	public ArrayList<String>	subItems	= new ArrayList<>();
	@SideOnly(Side.CLIENT)
	public IIcon[]				subIcons;
	public String				textureFolder;

	public ItemMetaHolder setTextureFolder(String textureFolder)
	{
		this.textureFolder = textureFolder;
		return this;
	}

	public ItemMetaHolder(String name) {
		setUnlocalizedName(name);
		subItems.add(name);
		setCreativeTab(CoreTabRegister.coreTab);
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register)
	{
		subIcons = new IIcon[subItems.size()];
		for (int i = 0; i < subItems.size(); i++)
		{
			subIcons[i] = register.registerIcon(CoreReferences.PREFIX + textureFolder + "/" + subItems.get(i));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta)
	{
		return subIcons[meta];
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return "item." + subItems.get(stack.getItemDamage());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getSubItems(Item item, CreativeTabs tab, @SuppressWarnings("rawtypes") List list)
	{
		for (int i = 0; i < subItems.size(); i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}

	public ItemMetaHolder addSubItem(String name)
	{
		subItems.add(name);
		return this;
	}
}
