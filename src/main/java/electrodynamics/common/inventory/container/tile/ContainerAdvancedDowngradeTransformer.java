package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.electricitygrid.transformer.TileAdvancedTransformer.TileAdvancedDowngradeTransformer;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;

public class ContainerAdvancedDowngradeTransformer extends GenericContainerBlockEntity<TileAdvancedDowngradeTransformer> {

	public ContainerAdvancedDowngradeTransformer(int id, Inventory playerinv, ContainerData inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_ADVANCEDDOWNGRADETRANSFORMER.get(), id, playerinv, EMPTY, inventorydata);
	}

	public ContainerAdvancedDowngradeTransformer(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainerData(5));
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		setPlayerInvOffset(30);
	}



}
