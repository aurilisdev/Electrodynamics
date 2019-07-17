package physica.library.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockMetadata extends ItemBlockDescriptable {

	public ItemBlockMetadata(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		return super.getUnlocalizedName(itemStack) + "." + itemStack.getItemDamage();
	}

	@Override
	public int getMetadata(int metadata)
	{
		return metadata;
	}
}
