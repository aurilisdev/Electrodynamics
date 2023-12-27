package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.electricitygrid.generators.TileCreativePowerSource;
import electrodynamics.prefab.inventory.container.ContainerBlockEntityEmpty;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class ContainerCreativePowerSource extends ContainerBlockEntityEmpty<TileCreativePowerSource> {

	public ContainerCreativePowerSource(int id, PlayerInventory playerinv) {
		this(id, playerinv, new IntArray(3));
	}

	public ContainerCreativePowerSource(int id, PlayerInventory playerinv, IIntArray inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_CREATIVEPOWERSOURCE.get(), id, playerinv, inventorydata);
	}

}