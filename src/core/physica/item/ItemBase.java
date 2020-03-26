package physica.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import physica.Physica;

public class ItemBase extends Item {

	protected String	name;
	protected int		subtypes	= 0;

	public ItemBase(String name, Integer subtypes) {
		this.name = name;
		setTranslationKey(name);
		setRegistryName(name);
		if (subtypes > 0)
		{
			this.subtypes = subtypes;
			setHasSubtypes(true);
		}
	}

	public ItemStack createStackFromSubtype(int subtype)
	{
		return new ItemStack(this, 1, subtype);
	}

	@Override
	public String getTranslationKey(ItemStack stack)
	{
		return super.getTranslationKey() + "." + stack.getMetadata();
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if (getHasSubtypes())
		{

			if (isInCreativeTab(tab))
			{
				for (int index = 0; index < subtypes; ++index)
				{
					items.add(new ItemStack(this, 1, index));
				}
			}
		}
	}

	@Override
	public ItemBase setCreativeTab(CreativeTabs tab)
	{
		super.setCreativeTab(tab);
		return this;
	}

	public void registerItemModel()
	{
		registerItemModel("");
	}

	public void registerItemModel(String folder)
	{
		String prefix = folder.isEmpty() ? folder : folder + "/";
		for (int index = 0; index < subtypes; index++)
		{
			Physica.proxy.registerItemRenderer(this, index, prefix + name + index);
		}
	}

}
