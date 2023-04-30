package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.tanks.gas.GenericTileGasTank;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotGas;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerGasTankGeneric extends GenericContainerBlockEntity<GenericTileGasTank> {

	public ContainerGasTankGeneric(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_GASTANK.get(), id, playerinv, inventory, inventorydata);
	}
	
	public ContainerGasTankGeneric(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(2), new SimpleContainerData(3));
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotGas(inv, nextIndex(), 27, 34));
		addSlot(new SlotGas(inv, nextIndex(), 133, 34));
	}

}
