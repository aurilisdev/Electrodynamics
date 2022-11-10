package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.TileElectricFurnaceTriple;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotUpgrade;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerElectricFurnaceTriple extends GenericContainerBlockEntity<TileElectricFurnaceTriple> {

	public ContainerElectricFurnaceTriple(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(9));
	}

	public ContainerElectricFurnaceTriple(int id, Inventory playerinv, Container inventory) {
		this(id, playerinv, inventory, new SimpleContainerData(3));
	}

	public ContainerElectricFurnaceTriple(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICFURNACETRIPLE.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		playerInvOffset = 20;
		addSlot(new SlotGeneric(inv, nextIndex(), 56, 24));
		addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 116, 24));
		addSlot(new SlotGeneric(inv, nextIndex(), 56, 44));
		addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 116, 44));
		addSlot(new SlotGeneric(inv, nextIndex(), 56, 64));
		addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 116, 64));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 14, ContainerElectricFurnace.VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 34, ContainerElectricFurnace.VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 54, ContainerElectricFurnace.VALID_UPGRADES));
	}
}
