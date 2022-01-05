package electrodynamics.common.inventory.container.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.tile.TileCarbyneBatteryBox;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotCharging;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotUpgrade;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerCarbyneBatteryBox extends GenericContainerBlockEntity<TileCarbyneBatteryBox> {

	public ContainerCarbyneBatteryBox(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(5), new SimpleContainerData(3));
	}

	public ContainerCarbyneBatteryBox(int id, Inventory pinv, Container inv, ContainerData data) {
		super(DeferredRegisters.CONTAINER_CARBYNEBATTERYBOX.get(), id, pinv, inv, data);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 14, SubtypeItemUpgrade.advancedcapacity, SubtypeItemUpgrade.basiccapacity));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 34, SubtypeItemUpgrade.advancedcapacity, SubtypeItemUpgrade.basiccapacity));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 54, SubtypeItemUpgrade.advancedcapacity, SubtypeItemUpgrade.basiccapacity));
		addSlot(new SlotCharging(inv, nextIndex(), 133, 14));
	}

}