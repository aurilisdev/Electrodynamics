package defense.api;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IItemTracker {

	void setTrackingEntity(ItemStack itemStack, Entity entity);

	Entity getTrackingEntity(World worldObj, ItemStack itemStack);
}
