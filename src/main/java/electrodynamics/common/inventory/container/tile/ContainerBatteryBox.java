package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.electricitygrid.batteries.TileBatteryBox;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import voltaic.common.item.subtype.SubtypeItemUpgrade;
import voltaic.prefab.inventory.container.slot.item.type.SlotCharging;
import voltaic.prefab.inventory.container.slot.item.type.SlotUpgrade;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;

public class ContainerBatteryBox extends GenericContainerBlockEntity<TileBatteryBox> {

	public static final SubtypeItemUpgrade[] VALID_UPGRADES = new SubtypeItemUpgrade[] { SubtypeItemUpgrade.advancedcapacity, SubtypeItemUpgrade.basiccapacity };

	public ContainerBatteryBox(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(6), new SimpleContainerData(5));
	}

	public ContainerBatteryBox(int id, Inventory pinv, Container inv, ContainerData data) {
		super(ElectrodynamicsMenuTypes.CONTAINER_BATTERYBOX.get(), id, pinv, inv, data);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotCharging(inv, nextIndex(), 133, 14));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 14, VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 34, VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 54, VALID_UPGRADES));
	}

}