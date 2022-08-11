package defense.api;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IItemTracker {

	public void setTrackingEntity(ItemStack itemStack, Entity entity);

	public Entity getTrackingEntity(World worldObj, ItemStack itemStack);
}
