package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.tile.electricitygrid.generators.TileHydroelectricGenerator;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotUpgrade;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class ContainerHydroelectricGenerator extends GenericContainerBlockEntity<TileHydroelectricGenerator> {

	public static final SubtypeItemUpgrade[] VALID_UPGRADES = new SubtypeItemUpgrade[] { SubtypeItemUpgrade.stator };

	public ContainerHydroelectricGenerator(int id, PlayerInventory playerinv) {
		this(id, playerinv, new Inventory(1), new IntArray(3));
	}

	public ContainerHydroelectricGenerator(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_HYDROELECTRICGENERATOR.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		addSlot(new SlotUpgrade(inv, nextIndex(), 25, 42, VALID_UPGRADES));
	}
}