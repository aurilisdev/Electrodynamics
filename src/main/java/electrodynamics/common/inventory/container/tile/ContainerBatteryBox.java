package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.tile.electricitygrid.batteries.TileBatteryBox;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotCharging;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotUpgrade;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class ContainerBatteryBox extends GenericContainerBlockEntity<TileBatteryBox> {

	public static final SubtypeItemUpgrade[] VALID_UPGRADES = new SubtypeItemUpgrade[] { SubtypeItemUpgrade.advancedcapacity, SubtypeItemUpgrade.basiccapacity };

	public ContainerBatteryBox(int id, PlayerInventory playerinv) {
		this(id, playerinv, new Inventory(6), new IntArray(3));
	}

	public ContainerBatteryBox(int id, PlayerInventory pinv, IInventory inv, IIntArray data) {
		super(ElectrodynamicsMenuTypes.CONTAINER_BATTERYBOX.get(), id, pinv, inv, data);
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		addSlot(new SlotCharging(inv, nextIndex(), 133, 14));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 14, VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 34, VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 54, VALID_UPGRADES));
	}

}