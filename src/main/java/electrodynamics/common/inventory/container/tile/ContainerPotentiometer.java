package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.electricitygrid.TilePotentiometer;
import electrodynamics.prefab.inventory.container.ContainerBlockEntityEmpty;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerPotentiometer extends ContainerBlockEntityEmpty<TilePotentiometer> {

	public ContainerPotentiometer(int id, Inventory playerinv, ContainerData inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_POTENTIOMETER.get(), id, playerinv, inventorydata);
	}

	public ContainerPotentiometer(int id, Inventory playerinv) {
		this(id, playerinv,  new SimpleContainerData(3));
	}

}
