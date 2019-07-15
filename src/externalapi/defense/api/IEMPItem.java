package defense.api;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

/**
 * Applied to all items that can be protected from EMP somehow.
 *
 * @author Calclavia
 */
public interface IEMPItem {

	/**
	 * Called when this item is being EMPed
	 *
	 * @param itemStack
	 *            - The itemstack attacked by EMP
	 * @param entity
	 *            - The entity holding the item
	 * @param empExplosives
	 *            - The IExplosive object
	 */
	public void onEMP(ItemStack itemStack, Entity entity, IExplosion empExplosive);
}
