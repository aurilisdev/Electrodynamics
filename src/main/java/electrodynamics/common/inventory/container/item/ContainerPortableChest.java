package electrodynamics.common.inventory.container.item;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.item.gear.tools.ItemPortableChest;
import electrodynamics.prefab.inventory.container.GenericContainerItem;
import electrodynamics.prefab.inventory.container.slot.itemhandler.SlotItemHandlerRestricted;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ContainerPortableChest extends GenericContainerItem {

	public ContainerPortableChest(int id, Inventory playerinv) {
		this(id, playerinv, new ItemStackHandler(ItemPortableChest.SLOT_COUNT));
	}

	public ContainerPortableChest(int id, Inventory playerinv, IItemHandler itemStackHandler) {
		super(DeferredRegisters.CONTAINER_PORTABLECHEST.get(), id, playerinv, itemStackHandler);
	}

	@Override
	public void addItemInventorySlots(Container inv, Inventory playerinv) {
		if(getHandler() != null) {
			IItemHandler handler = getHandler();
			int rowSize = handler.getSlots() / 9;
			for(int j = 0; j < rowSize; ++j) {
		         for(int k = 0; k < 9; ++k) {
		            this.addSlot(new SlotItemHandlerRestricted(handler, nextIndex(), 8 + k * 18, 16 + j * 18, false, ItemPortableChest.class));
		         }
		    }
		}
		
	}
	
	@Override
	public void removed(Player player) {
		player.getLevel().playSound(null, player.blockPosition(), SoundEvents.CHEST_CLOSE, SoundSource.PLAYERS, 1, 1);
		super.removed(player);
	}

}
