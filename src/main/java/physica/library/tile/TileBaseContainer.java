package physica.library.tile;

import net.minecraft.item.ItemStack;
import physica.api.core.ITileBaseContainer;

public abstract class TileBaseContainer extends TileBaseRotateable implements ITileBaseContainer {

	private ItemStack[] _inventoryArray;

	@Override
	public ItemStack[] getInventoryArray()
	{
		if (_inventoryArray == null) {
			_inventoryArray = new ItemStack[getSizeInventory()];
		}
		return _inventoryArray;
	}

	@Override
	public void nullifyInventoryArray()
	{
		_inventoryArray = null;
	}

}
