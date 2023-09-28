package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.electricitygrid.transformer.TileAdvancedTransformer.TileAdvancedUpgradeTransformer;
import electrodynamics.prefab.inventory.container.ContainerBlockEntityEmpty;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerAdvancedUpgradeTransformer extends ContainerBlockEntityEmpty<TileAdvancedUpgradeTransformer> {

	public ContainerAdvancedUpgradeTransformer(int id, Inventory playerinv, ContainerData inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_ADVANCEDUPGRADETRANSFORMER.get(), id, playerinv, inventorydata);
	}

	public ContainerAdvancedUpgradeTransformer(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainerData(3));
	}

	@Override
	public int getPlayerInvOffset() {
		return 30;
	}

}
