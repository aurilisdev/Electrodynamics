package electrodynamics.prefab.inventory.container;

import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import electrodynamics.prefab.utilities.ContainerUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class GenericContainer extends Container {

	protected final IInventory inventory;
	protected final World world;
	public final int slotCount;
	protected int playerInvOffset = 0;
	private int nextIndex = 0;

	public int nextIndex() {
		return nextIndex++;
	}

	// Specialized constructor for screen with no player inv
	protected GenericContainer(ContainerType<?> type, int id, PlayerInventory playerInv) {
		super(type, id);
		inventory = new Inventory(0);
		world = playerInv.player.level;
		slotCount = 0;
	}

	protected GenericContainer(ContainerType<?> type, int id, PlayerInventory playerinv, IInventory inventory) {
		super(type, id);
		checkContainerSize(inventory, inventory.getContainerSize());
		this.inventory = inventory;
		world = playerinv.player.level;
		addInventorySlots(inventory, playerinv);
		slotCount = slots.size();
		addPlayerInventory(playerinv);
		
	}

	protected void addPlayerInventory(PlayerInventory playerinv) {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlot(new SlotGeneric(playerinv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + playerInvOffset));
			}
		}

		for (int k = 0; k < 9; ++k) {
			addSlot(new SlotGeneric(playerinv, k, 8 + k * 18, 142 + playerInvOffset));
		}
	}

	public abstract void addInventorySlots(IInventory inv, PlayerInventory playerinv);

	public void clear() {
		inventory.clearContent();
	}

	@OnlyIn(Dist.CLIENT)
	public int getSize() {
		return inventory.getContainerSize();
	}

	public IInventory getIInventory() {
		return inventory;
	}

	@Override
	public boolean stillValid(PlayerEntity player) {
		return inventory.stillValid(player);
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity player, int index) {
		return ContainerUtils.handleShiftClick(slots, player, index);
	}

	@Override
	public void removed(PlayerEntity player) {
		super.removed(player);
		inventory.stopOpen(player);
	}

}