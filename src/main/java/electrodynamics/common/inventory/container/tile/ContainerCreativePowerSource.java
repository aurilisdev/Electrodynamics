package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.electricitygrid.generators.TileCreativePowerSource;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;

public class ContainerCreativePowerSource extends GenericContainerBlockEntity<TileCreativePowerSource> {

	public ContainerCreativePowerSource(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainerData(5));
	}

	public ContainerCreativePowerSource(int id, Inventory playerinv, ContainerData inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_CREATIVEPOWERSOURCE.get(), id, playerinv, EMPTY, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {

	}
}
