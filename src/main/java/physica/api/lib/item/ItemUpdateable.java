package physica.api.lib.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemUpdateable extends Item {
	protected IItemUpdate update = new IItemUpdate() {
	};

	public ItemUpdateable setUpdate(IItemUpdate update) {
		this.update = update;
		return this;
	}

	@Override
	public void onUpdate(ItemStack container, World world, Entity entity, int slot, boolean p_77663_5_) {
		update.onUpdate(container, world, entity, slot, p_77663_5_);
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		update.onEntityItemUpdate(entityItem.getEntityItem(), entityItem);
		return false;
	}
}