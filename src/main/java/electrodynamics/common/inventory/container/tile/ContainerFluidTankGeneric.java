package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.pipelines.tank.fluid.GenericTileFluidTank;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotFluid;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class ContainerFluidTankGeneric extends GenericContainerBlockEntity<GenericTileFluidTank> {

	public ContainerFluidTankGeneric(int id, PlayerInventory playerinv) {
		this(id, playerinv, new Inventory(2), new IntArray(3));
	}

	public ContainerFluidTankGeneric(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_TANK.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		addSlot(new SlotFluid(inv, nextIndex(), 27, 34));
		addSlot(new SlotFluid(inv, nextIndex(), 133, 34));

	}

}