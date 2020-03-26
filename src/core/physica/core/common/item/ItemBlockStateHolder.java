package physica.core.common.item;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import physica.core.common.block.BlockStateHolder;

public class ItemBlockStateHolder extends ItemBlock {
	public ItemBlockStateHolder(BlockStateHolder<?> block) {
		super(block);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		return super.getTranslationKey() + "." + stack.getMetadata();
	}
}