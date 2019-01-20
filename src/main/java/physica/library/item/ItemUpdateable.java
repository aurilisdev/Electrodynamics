package physica.library.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import physica.api.core.IItemUpdate;

public class ItemUpdateable extends Item {

	protected IItemUpdate update = new IItemUpdate() {
	};

	public ItemUpdateable setUpdate(IItemUpdate update) {
		this.update = update;
		return this;
	}

	@Override
	public void onUpdate(ItemStack container, World world, Entity entity, int slot, boolean par5) {
		update.onUpdate(container, world, entity, slot, par5);
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		update.onEntityItemUpdate(entityItem.getEntityItem(), entityItem);
		return false;
	}
}
