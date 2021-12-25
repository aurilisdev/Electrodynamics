package electrodynamics.common.inventory.container.item;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.item.ItemUtils;
import electrodynamics.common.item.gear.tools.electric.ItemSeismicScanner;
import electrodynamics.prefab.inventory.container.GenericContainerItem;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerSeismicScanner extends GenericContainerItem {
	
	private static Item[] ores = new Item[0];
	
	static {
		ItemStack[] stacks = ItemUtils.getIngredientFromTag("forge", "ores").getItems();
		ores = new Item[stacks.length];
		for(int i = 0; i < stacks.length; i++) {
			ores[i] = stacks[i].getItem();
		}
	}
	
	public ContainerSeismicScanner(int id, Inventory playerinv) {
		this(id, playerinv, new ItemStackHandler(ItemSeismicScanner.SLOT_COUNT));
	}
	
	public ContainerSeismicScanner(int id, Inventory playerinv, IItemHandler handler) {
		super(DeferredRegisters.CONTAINER_SEISMICSCANNER.get(), id, playerinv, handler);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotItemHandler(getHandler(), nextIndex(), 25, 42));
	}

}
