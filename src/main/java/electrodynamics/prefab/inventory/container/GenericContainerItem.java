package electrodynamics.prefab.inventory.container;

import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotNoModification;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public abstract class GenericContainerItem extends GenericContainer {

	private IItemHandler handler;
	private PlayerEntity player;

	public GenericContainerItem(ContainerType<?> type, int id, PlayerInventory playerinv, IItemHandler handler) {
		// the items have to be stored in the handler, so the container is just for indexing purposes
		super(type, id, playerinv, new Inventory(handler.getSlots()));
		this.handler = handler;
		addSafePlayerInventory(playerinv);
		addItemInventorySlots(inventory, playerinv);
		player = playerinv.player;
	}

	@Override
	protected final void addPlayerInventory(PlayerInventory playerinv) {
	}

	protected void addSafePlayerInventory(PlayerInventory playerinv) {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlot(new SlotGeneric(playerinv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + playerInvOffset));
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
			addSlot(new SlotGeneric(playerinv, k, 8 + k * 18, 142 + playerInvOffset));
		}
	}

	@Override
	public ItemStack clicked(int slot, int craft, ClickType type, PlayerEntity pl) {
		if (type == ClickType.SWAP) {
			PlayerInventory playerinv = pl.inventory;
			ItemStack stack = playerinv.getItem(slot);
			LazyOptional<IItemHandler> cap = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
			if (cap.isPresent()) {
				if (cap.resolve().get() == handler) {
					return ItemStack.EMPTY;
				}
			}
			stack = playerinv.getItem(craft);
			cap = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
			if (cap.isPresent()) {
				if (cap.resolve().get() == handler) {
					return ItemStack.EMPTY;
				}
			}
		}
		return super.clicked(slot, craft, type, pl);
	}

	@Override
	public boolean stillValid(PlayerEntity player) {
		ItemStack stack = player.getUseItem();
		LazyOptional<IItemHandler> cap = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
		if (cap.isPresent()) {
			if (cap.resolve().get() != handler) {
				return false;
			}
		}
		return super.stillValid(player);
	}

	public abstract void addItemInventorySlots(IInventory inv, PlayerInventory playerinv);

	@Override
	// at this point in time the handler isnt intialized so we need to force a non usage policy on this and rather use addItemInventorySlots
	public final void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
	}

	public IItemHandler getHandler() {
		return handler;
	}

	public PlayerEntity getPlayer() {
		return player;
	}

	// cheesing NBT one line of code at a time
	public ItemStack getOwnerItem() {
		ItemStack handItem = player.getItemInHand(Hand.MAIN_HAND);
		if (!handItem.isEmpty()) {
			return handItem;
		}
		return player.getItemInHand(Hand.OFF_HAND);

	}
}