package electrodynamics.api.inventory;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@FunctionalInterface
public interface InventoryTickConsumer {

	void apply(ItemStack stack, Level world, Entity entity, Integer slot, Boolean isSelected);

}
