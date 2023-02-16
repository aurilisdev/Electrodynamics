package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.TileLithiumBatteryBox;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotCharging;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotUpgrade;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerLithiumBatteryBox extends GenericContainerBlockEntity<TileLithiumBatteryBox> {

	public ContainerLithiumBatteryBox(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(5), new SimpleContainerData(3));
	}

	public ContainerLithiumBatteryBox(int id, Inventory pinv, Container inv, ContainerData data) {
		super(ElectrodynamicsMenuTypes.CONTAINER_LITHIUMBATTERYBOX.get(), id, pinv, inv, data);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotCharging(inv, nextIndex(), 133, 14));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 14, ContainerBatteryBox.VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 34, ContainerBatteryBox.VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 54, ContainerBatteryBox.VALID_UPGRADES));
	}

}