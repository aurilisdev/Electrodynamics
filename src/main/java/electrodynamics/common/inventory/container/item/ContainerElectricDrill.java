package electrodynamics.common.inventory.container.item;

import electrodynamics.common.item.gear.tools.electric.ItemElectricDrill;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IIntArray;
import voltaic.api.item.CapabilityItemStackHandler;
import voltaic.common.item.subtype.SubtypeItemUpgrade;
import voltaic.prefab.inventory.container.slot.itemhandler.type.SlotItemHandlerUpgrade;
import voltaic.prefab.inventory.container.types.GenericContainerItem;

public class ContainerElectricDrill extends GenericContainerItem {

	public static final SubtypeItemUpgrade[] VALID_UPGRADES = { SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed, SubtypeItemUpgrade.fortune, SubtypeItemUpgrade.silktouch };

	public ContainerElectricDrill(int id, PlayerInventory playerinv, CapabilityItemStackHandler handler, IIntArray data) {
		super(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICDRILL.get(), id, playerinv, handler, data);
	}

	public ContainerElectricDrill(int id, PlayerInventory playerInv) {
		this(id, playerInv, new CapabilityItemStackHandler(ItemElectricDrill.SLOT_COUNT, new ItemStack(Items.COBBLESTONE)), makeDefaultData(1));
	}

	@Override
	public void addInventorySlots(CapabilityItemStackHandler inv, PlayerInventory playerinv) {
		addSlot(new SlotItemHandlerUpgrade(inv, nextIndex(), 30, 35, VALID_UPGRADES));
		addSlot(new SlotItemHandlerUpgrade(inv, nextIndex(), 80, 35, VALID_UPGRADES));
		addSlot(new SlotItemHandlerUpgrade(inv, nextIndex(), 130, 35, VALID_UPGRADES));
	}

}
