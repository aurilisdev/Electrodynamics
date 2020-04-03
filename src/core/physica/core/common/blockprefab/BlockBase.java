package physica.core.common.blockprefab;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import physica.core.Physica;

public class BlockBase extends Block {

	protected String name;

	public BlockBase(Material material, String name) {
		super(material);
		this.name = name;
		setTranslationKey(name);
		setRegistryName(name);
	}

	public void registerItemModel(Item itemBlock) {
		Physica.proxy.registerItemRenderer(itemBlock, 0, name);
	}

	public Item createItemBlock() {
		return new ItemBlock(this).setRegistryName(getRegistryName());
	}

	@Override
	public BlockBase setCreativeTab(CreativeTabs tab) {
		super.setCreativeTab(tab);
		return this;
	}

}