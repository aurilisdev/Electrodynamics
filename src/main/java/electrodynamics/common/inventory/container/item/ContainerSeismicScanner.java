package electrodynamics.common.inventory.container.item;

import electrodynamics.common.item.gear.tools.electric.ItemSeismicScanner;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IIntArray;
import voltaic.api.item.CapabilityItemStackHandler;
import voltaic.common.item.subtype.SubtypeItemUpgrade;
import voltaic.prefab.inventory.container.slot.itemhandler.type.SlotItemHandlerRestricted;
import voltaic.prefab.inventory.container.types.GenericContainerItem;
import voltaic.prefab.screen.component.types.ScreenComponentSlot.IconType;
import voltaic.prefab.screen.component.types.ScreenComponentSlot.SlotType;

public class ContainerSeismicScanner extends GenericContainerItem {
	
	public static final SubtypeItemUpgrade[] VALID_UPGRADES = {SubtypeItemUpgrade.range};

	public ContainerSeismicScanner(int id, PlayerInventory playerinv) {
		this(id, playerinv, new CapabilityItemStackHandler(ItemSeismicScanner.SLOT_COUNT, new ItemStack(Items.COBBLESTONE)), makeDefaultData(1));
	}

	public ContainerSeismicScanner(int id, PlayerInventory playerinv, CapabilityItemStackHandler handler, IIntArray data) {
		super(ElectrodynamicsMenuTypes.CONTAINER_SEISMICSCANNER.get(), id, playerinv, handler, data);
	}

	@Override
	public void addInventorySlots(CapabilityItemStackHandler inv, PlayerInventory playerinv) {
		addSlot(new SlotItemHandlerRestricted(SlotType.NORMAL, IconType.NONE, inv, nextIndex(), 25, 42).setRestriction(stack -> (stack != null && stack.getItem() instanceof BlockItem)));
	}

}
