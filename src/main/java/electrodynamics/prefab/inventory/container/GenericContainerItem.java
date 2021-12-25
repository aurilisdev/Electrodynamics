package electrodynamics.prefab.inventory.container;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public abstract class GenericContainerItem extends GenericContainer {

	private ItemStack stack;
	
	protected GenericContainerItem(MenuType<?> type, int id, Inventory playerinv, Container inventory, ItemStack stack) {
		super(type, id, playerinv, inventory);
		this.stack = stack;
	}
	
	@Override
	public void removed(Player player) {
		super.removed(player);
		List<ItemStack> contained = new ArrayList<>();
		contained.addAll(getItems());
		LazyOptional<IItemHandler> capability = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
		if(capability.isPresent()) {
			ItemStackHandler handler = (ItemStackHandler) capability.resolve().get();
			for(int i = 0; i < contained.size(); i++) {
				handler.setStackInSlot(i, contained.get(i));
			}
		}
	}
	
	

}
