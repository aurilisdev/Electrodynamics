package electrodynamics.common.inventory.container.item;

import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;

public class ContainerGuidebook extends GenericContainer {

	public ContainerGuidebook(int id, Inventory playerinv) {
		super(ElectrodynamicsMenuTypes.CONTAINER_GUIDEBOOK.get(), id, playerinv);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		// DO NOT USE
	}

}