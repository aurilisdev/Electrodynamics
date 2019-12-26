package physica.missiles.common.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import physica.library.item.ItemBlockMetadata;
import physica.missiles.common.explosive.Explosive;

public class ItemBlockPrimed extends ItemBlockMetadata {

	public ItemBlockPrimed(Block block) {
		super(block);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		Explosive handler = Explosive.get(itemStack.getItemDamage());
		return "physicaMissiles.explosive." + (handler == null ? "unknown" : handler.localeName);
	}

}
