package electrodynamics.common.inventory.container.item;

import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;

public class ContainerGuidebook extends GenericContainer {

	public ContainerGuidebook(int id, PlayerInventory playerinv) {
		super(ElectrodynamicsMenuTypes.CONTAINER_GUIDEBOOK.get(), id, playerinv);
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		// DO NOT USE
	}

}