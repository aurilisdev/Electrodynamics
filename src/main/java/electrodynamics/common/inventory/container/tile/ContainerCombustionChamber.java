package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.electricitygrid.generators.TileCombustionChamber;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import voltaic.prefab.inventory.container.slot.item.type.SlotFluid;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;

public class ContainerCombustionChamber extends GenericContainerBlockEntity<TileCombustionChamber> {

	public ContainerCombustionChamber(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(1), new SimpleContainerData(3));
	}

	public ContainerCombustionChamber(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_COMBUSTION_CHAMBER.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotFluid(inv, nextIndex(), 45, 34));
	}
}
