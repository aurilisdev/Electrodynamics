package physica.library.tile;

import net.minecraft.item.ItemStack;
import physica.api.core.tile.ITileBaseContainer;

public abstract class TileBaseContainer extends TileBaseRotateable implements ITileBaseContainer {

	private ItemStack[] _inventoryArray;

	@Override
	public ItemStack[] getInventoryArray() {
		if (_inventoryArray == null) {
			_inventoryArray = new ItemStack[getSizeInventory()];
		}
		return _inventoryArray;
	}

	@Override
	public void nullifyInventoryArray() {
		_inventoryArray = null;
	}

	@Override
	public final boolean canExtractItem(int slot, ItemStack stack, int side) {
		return ITileBaseContainer.super.canExtractItem(slot, stack, side);
	}

	@Override
	public final boolean canInsertItem(int slot, ItemStack stack, int side) {
		return ITileBaseContainer.super.canInsertItem(slot, stack, side);
	}

	@Override
	public final int[] getAccessibleSlotsFromSide(int side) {
		return ITileBaseContainer.super.getAccessibleSlotsFromSide(side);
	}

}
