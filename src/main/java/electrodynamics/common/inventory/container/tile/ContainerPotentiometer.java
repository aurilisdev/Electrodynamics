package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.network.electric.TilePotentiometer;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerPotentiometer extends GenericContainerBlockEntity<TilePotentiometer> {

	public ContainerPotentiometer(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_POTENTIOMETER.get(), id, playerinv, inventory, inventorydata);
	}

	public ContainerPotentiometer(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(0), new SimpleContainerData(3));
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {

	}

}
