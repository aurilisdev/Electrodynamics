package electrodynamics.prefab.inventory.container;

import electrodynamics.prefab.inventory.container.slot.GenericSlot;
import electrodynamics.prefab.inventory.container.slot.SlotNoModification;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public abstract class GenericContainerItem extends GenericContainer {

	private IItemHandler handler;
	private Player player;

	protected GenericContainerItem(MenuType<?> type, int id, Inventory playerinv, IItemHandler handler) {
		// the items have to be stored in the handler, so the container is just for indexing purposes
		super(type, id, playerinv, new SimpleContainer(handler.getSlots()));
		this.handler = handler;
		addSafePlayerInventory(playerinv);
		addItemInventorySlots(inventory, playerinv);
		this.player = playerinv.player;
	}

	@Override
	protected final void addPlayerInventory(Inventory playerinv) {
	}

	protected void addSafePlayerInventory(Inventory playerinv) {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlot(new GenericSlot(playerinv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + playerInvOffset));
			}
		}

		for (int k = 0; k < 9; ++k) {
			int index = k;
			ItemStack stack = playerinv.getItem(index);
			LazyOptional<IItemHandler> cap = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
			if (cap.isPresent()) {
				if (cap.resolve().get() == handler) {
					addSlot(new SlotNoModification(playerinv, k, 8 + k * 18, 142 + playerInvOffset));
					continue;
				}
			}
			addSlot(new GenericSlot(playerinv, k, 8 + k * 18, 142 + playerInvOffset));
		}
	}

	@Override
	public void clicked(int slot, int craft, ClickType type, Player pl) {
		if (type == ClickType.SWAP) {
			Inventory playerinv = pl.getInventory();
			ItemStack stack = playerinv.getItem(slot);
			LazyOptional<IItemHandler> cap = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
			if (cap.isPresent()) {
				if (cap.resolve().get() == handler) {
					return;
				}
			}
			stack = playerinv.getItem(craft);
			cap = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
			if (cap.isPresent()) {
				if (cap.resolve().get() == handler) {
					return;
				}
			}
		}
		super.clicked(slot, craft, type, pl);
	}

	@Override
	public boolean stillValid(Player player) {
		ItemStack stack = player.getUseItem();
		LazyOptional<IItemHandler> cap = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
		if (cap.isPresent()) {
			if (cap.resolve().get() != handler) {
				return false;
			}
		}
		return super.stillValid(player);
	}

	public abstract void addItemInventorySlots(Container inv, Inventory playerinv);

	@Override
	// at this point in time the handler isnt intialized so we need to force a non usage policy on this and rather use addItemInventorySlots
	public final void addInventorySlots(Container inv, Inventory playerinv) {
	}

	public IItemHandler getHandler() {
		return handler;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	//cheesing NBT one line of code at a time
	public ItemStack getOwnerItem() {
		ItemStack handItem = player.getItemInHand(InteractionHand.MAIN_HAND);
		if(!handItem.isEmpty()) {
			return handItem;
		}
		return player.getItemInHand(InteractionHand.OFF_HAND);
		
	}
}
