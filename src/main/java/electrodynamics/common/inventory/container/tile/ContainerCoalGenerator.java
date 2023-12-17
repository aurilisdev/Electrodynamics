package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.reloadlistener.CoalGeneratorFuelRegister;
import electrodynamics.common.tile.electricitygrid.generators.TileCoalGenerator;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotRestricted;
import electrodynamics.prefab.utilities.math.Color;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class ContainerCoalGenerator extends GenericContainerBlockEntity<TileCoalGenerator> {

	public ContainerCoalGenerator(int id, PlayerInventory playerinv) {
		this(id, playerinv, new Inventory(1), new IntArray(3));
	}

	public ContainerCoalGenerator(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_COALGENERATOR.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		addSlot(new SlotRestricted(inv, nextIndex(), 25, 42).setRestriction(stack -> CoalGeneratorFuelRegister.INSTANCE.isFuel(stack.getItem())).setIOColor(new Color(0, 240, 255, 255)));
	}

}