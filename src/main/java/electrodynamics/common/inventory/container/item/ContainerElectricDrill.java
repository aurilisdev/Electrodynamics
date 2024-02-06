package electrodynamics.common.inventory.container.item;

import electrodynamics.common.item.gear.tools.electric.ItemElectricDrill;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.prefab.inventory.container.GenericContainerItem;
import electrodynamics.prefab.inventory.container.slot.itemhandler.type.SlotItemHandlerUpgrade;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

public class ContainerElectricDrill extends GenericContainerItem {

	public static final SubtypeItemUpgrade[] VALID_UPGRADES = new SubtypeItemUpgrade[] { SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed, SubtypeItemUpgrade.fortune, SubtypeItemUpgrade.silktouch };

	public ContainerElectricDrill(int id, Inventory playerinv, IItemHandler handler) {
		super(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICDRILL.get(), id, playerinv, handler);
	}

	public ContainerElectricDrill(int id, Inventory playerInv) {
		this(id, playerInv, new ItemStackHandler(ItemElectricDrill.SLOT_COUNT));
	}

	@Override
	public void addItemInventorySlots(Container inv, Inventory playerinv) {
		if (getHandler() == null) {
			return;
		}

		addSlot(new SlotItemHandlerUpgrade(getHandler(), nextIndex(), 30, 35, VALID_UPGRADES));
		addSlot(new SlotItemHandlerUpgrade(getHandler(), nextIndex(), 80, 35, VALID_UPGRADES));
		addSlot(new SlotItemHandlerUpgrade(getHandler(), nextIndex(), 130, 35, VALID_UPGRADES));

	}

}
