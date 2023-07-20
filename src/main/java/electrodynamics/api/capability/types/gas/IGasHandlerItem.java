package electrodynamics.api.capability.types.gas;

import javax.annotation.Nonnull;

import net.minecraft.world.item.ItemStack;

/**
 * An implementation of an Item-Bound Gas Handler capability modeled after IFluidHandlerItem
 * 
 * @author skip999
 *
 */
public interface IGasHandlerItem extends IGasHandler {

	/**
	 * Get the container currently acted on by this gas handler. The ItemStack may be different from its initial state, in the case of fluid containers that have different items for their filled and empty states. May be an empty item if the container was drained and is consumable.
	 */
	@Nonnull
	ItemStack getContainer();

}
