package electrodynamics.common.inventory.container.item;

import electrodynamics.common.item.gear.tools.electric.ItemSeismicScanner;
import electrodynamics.prefab.inventory.container.GenericContainerItem;
import electrodynamics.prefab.inventory.container.slot.itemhandler.type.SlotItemHandlerRestricted;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.IconType;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.SlotType;
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
		if (getHandler() == null) {
			return;
		}
		addSlot(new SlotItemHandlerRestricted(SlotType.NORMAL, IconType.NONE, getHandler(), nextIndex(), 25, 42).setRestriction(stack -> (stack != null && stack.getItem().getClass().isInstance(BlockItem.class))));
	}

}
