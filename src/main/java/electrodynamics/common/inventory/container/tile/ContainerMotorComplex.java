package electrodynamics.common.inventory.container.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.tile.TileMotorComplex;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotUpgrade;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerMotorComplex extends GenericContainerBlockEntity<TileMotorComplex> {

	public ContainerMotorComplex(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(DeferredRegisters.CONTAINER_MOTORCOMPLEX.get(), id, playerinv, inventory, inventorydata);
	}

	public ContainerMotorComplex(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(3), new SimpleContainerData(3));
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 14, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 34, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 54, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed));
	}

}
