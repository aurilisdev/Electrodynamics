package electrodynamics.common.inventory.container.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.tile.TileElectricArcFurnace;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotUpgrade;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerElectricArcFurnace extends GenericContainerBlockEntity<TileElectricArcFurnace> {

	public static final SubtypeItemUpgrade[] VALID_UPGRADES = new SubtypeItemUpgrade[] {SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed, SubtypeItemUpgrade.itemoutput, SubtypeItemUpgrade.iteminput, SubtypeItemUpgrade.experience};
	
	public ContainerElectricArcFurnace(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(5));
	}

	public ContainerElectricArcFurnace(int id, Inventory playerinv, Container inventory) {
		this(id, playerinv, inventory, new SimpleContainerData(3));
	}

	public ContainerElectricArcFurnace(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(DeferredRegisters.CONTAINER_ELECTRICARCFURNACE.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotGeneric(inv, nextIndex(), 56, 34));
		addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 116, 35));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 14, VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 34, VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 54, VALID_UPGRADES));
	}
}
