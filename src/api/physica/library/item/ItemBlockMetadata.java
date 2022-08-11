package physica.library.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

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

	@Override
	public IIcon getIconFromDamage(int damage)
	{
		return field_150939_a.getIcon(0, damage);
	}
}
