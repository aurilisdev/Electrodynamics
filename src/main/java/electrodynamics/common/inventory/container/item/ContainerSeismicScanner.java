package electrodynamics.common.inventory.container.item;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.item.ItemUtils;
import electrodynamics.prefab.inventory.container.GenericContainerItem;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ContainerSeismicScanner extends GenericContainerItem {
	
	private static Item[] ores = new Item[0];
	
	static {
		ItemStack[] stacks = ItemUtils.getIngredientFromTag("forge", "ores").getItems();
		ores = new Item[stacks.length];
		for(int i = 0; i < stacks.length; i++) {
			ores[i] = stacks[i].getItem();
		}
	}
	
	public static final int SLOT_COUNT = 1;
	
	public ContainerSeismicScanner(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(SLOT_COUNT), ItemStack.EMPTY);
	}
	
	public ContainerSeismicScanner(int id, Inventory playerinv, Container inventory, ItemStack stack) {
		super(DeferredRegisters.CONTAINER_SEISMICSCANNER.get(), id, playerinv, inventory, stack);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotRestricted(inv, nextIndex(), 25, 42, Items.IRON_ORE));
	}

}
