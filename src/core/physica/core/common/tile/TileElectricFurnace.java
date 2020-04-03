package physica.core.common.tile;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import universalelectricity.prefab.capabilities.ElectricityStorage;
import universalelectricity.prefab.tile.electric.TileElectricInventoryMachine;

public class TileElectricFurnace extends TileElectricInventoryMachine {
	public TileElectricFurnace() {
		energyStorage = new ElectricityStorage(10000 * 2); // TODO: skriv no ordentlig seb
		inventory = new ItemStackHandler(4) {
			@Override
			protected void onContentsChanged(final int slot) {
				super.onContentsChanged(slot);
				markDirty();
			}

			private boolean isItemValidForSlot(final int slot, final ItemStack itemStack) {
				return true;
			}

			@Override
			public ItemStack insertItem(final int slot, final ItemStack stack, final boolean simulate) {
				if (!isItemValidForSlot(slot, stack)) {
					return stack;
				}

				return super.insertItem(slot, stack, simulate);
			}
		};
	}

	@Override
	public void firstUpdate(boolean isServer) {
		if (isServer) {
			setFacing(facing);
		}
	}
}
