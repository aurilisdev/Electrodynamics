package electrodynamics.api.inventory;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@FunctionalInterface
public interface InventoryTickConsumer {

	void apply(ItemStack stack, World world, Entity entity, Integer slot, Boolean isSelected);
	
}
