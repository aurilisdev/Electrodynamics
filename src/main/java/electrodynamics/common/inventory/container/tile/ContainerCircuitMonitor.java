package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.electricitygrid.TileCircuitMonitor;
import electrodynamics.prefab.inventory.container.ContainerBlockEntityEmpty;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerCircuitMonitor extends ContainerBlockEntityEmpty<TileCircuitMonitor> {

	public ContainerCircuitMonitor(int id, Inventory playerinv, ContainerData inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_CIRCUITMONITOR.get(), id, playerinv, inventorydata);
	}
	
	public ContainerCircuitMonitor(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainerData(3));
	}
	
	@Override
	public int getPlayerInvOffset() {
		return 40;
	}
	
	@Override
	protected void addPlayerInventory(Inventory playerinv) {
		
	}

}
