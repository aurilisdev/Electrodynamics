package electrodynamics.common.inventory.container.item;

import electrodynamics.common.item.gear.tools.electric.ItemSeismicScanner;
import electrodynamics.prefab.inventory.container.GenericContainerItem;
import electrodynamics.prefab.inventory.container.slot.itemhandler.SlotItemHandlerRestricted;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ContainerSeismicScanner extends GenericContainerItem {

	public ContainerSeismicScanner(int id, Inventory playerinv) {
		this(id, playerinv, new ItemStackHandler(ItemSeismicScanner.SLOT_COUNT));
	}

	public ContainerSeismicScanner(int id, Inventory playerinv, IItemHandler handler) {
		super(ElectrodynamicsMenuTypes.CONTAINER_SEISMICSCANNER.get(), id, playerinv, handler);
	}

	@Override
	public void addItemInventorySlots(Container inv, Inventory playerinv) {
		if (getHandler() != null) {
			addSlot(new SlotItemHandlerRestricted(getHandler(), nextIndex(), 25, 42, true, BlockItem.class));
		}
	}

}
