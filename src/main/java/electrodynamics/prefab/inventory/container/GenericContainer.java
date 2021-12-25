package electrodynamics.prefab.inventory.container;

import electrodynamics.prefab.inventory.container.slot.GenericSlot;
import electrodynamics.prefab.utilities.UtilitiesContainers;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class GenericContainer extends AbstractContainerMenu {

	protected final Container inventory;
	protected final Level world;
	protected final int slotCount;
	protected int playerInvOffset = 0;
	private int nextIndex = 0;

	public int nextIndex() {
		return nextIndex++;
	}

	protected GenericContainer(MenuType<?> type, int id, Inventory playerinv, Container inventory) {
		super(type, id);
		checkContainerSize(inventory, inventory.getContainerSize());
		this.inventory = inventory;
		world = playerinv.player.level;
		addInventorySlots(inventory, playerinv);
		slotCount = slots.size();
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlot(new GenericSlot(playerinv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + playerInvOffset));
			}
		}

		for (int k = 0; k < 9; ++k) {
			addSlot(new GenericSlot(playerinv, k, 8 + k * 18, 142 + playerInvOffset));
		}
	}

	public abstract void addInventorySlots(Container inv, Inventory playerinv);

	public void clear() {
		inventory.clearContent();
	}

	@OnlyIn(Dist.CLIENT)
	public int getSize() {
		return inventory.getContainerSize();
	}

	public Container getIInventory() {
		return inventory;
	}

	@Override
	public boolean stillValid(Player player) {
		return inventory.stillValid(player);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		return UtilitiesContainers.handleShiftClick(slots, player, index);
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		inventory.stopOpen(player);
	}

}
