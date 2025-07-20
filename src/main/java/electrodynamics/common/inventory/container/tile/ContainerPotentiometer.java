package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.electricitygrid.TilePotentiometer;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;

public class ContainerPotentiometer extends GenericContainerBlockEntity<TilePotentiometer> {

	public ContainerPotentiometer(int id, PlayerInventory playerinv, IIntArray inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_POTENTIOMETER.get(), id, playerinv, EMPTY, inventorydata);
	}

	public ContainerPotentiometer(int id, PlayerInventory playerinv) {
		this(id, playerinv, new IntArray(5));
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {

	}
}
