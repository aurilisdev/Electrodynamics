package electrodynamics.prefab.utilities;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.item.ItemUtils;
import electrodynamics.common.item.ItemUpgrade;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class InventoryUtils {

	public static void addItemsToInventory(Container inv, List<ItemStack> items, int start, int count) {
		for (ItemStack item : items) {
			for (int index = start; index < start + count; index++) {
				ItemStack contained = inv.getItem(index);
				int room = inv.getMaxStackSize() - contained.getCount();
				int amtAccepted = room >= item.getCount() ? item.getCount() : room;
				if (contained.isEmpty()) {
					inv.setItem(index, new ItemStack(item.getItem(), amtAccepted).copy());
					item.shrink(amtAccepted);
					inv.setChanged();
				} else if (ItemUtils.testItems(item.getItem(), contained.getItem())) {
					contained.grow(amtAccepted);
					item.shrink(amtAccepted);
					inv.setChanged();
				}
			}
		}
	}

	public static List<ItemStack> addItemsToItemHandler(IItemHandler inv, List<ItemStack> items) {
		return addItemsToItemHandler(inv, items, 0, inv.getSlots());
	}

	public static List<ItemStack> addItemsToItemHandler(IItemHandler inv, List<ItemStack> items, int start, int count) {
		ArrayList<ItemStack> returner = new ArrayList<>();
		for (ItemStack stack : items) {
			returner.add(addItemToItemHandler(inv, stack, start, count));
		}
		return returner;
	}

	public static ItemStack addItemToItemHandler(IItemHandler inv, ItemStack items, int start, int count) {
		for (int i = start; i < count; i++) {
			if (inv.isItemValid(i, items)) {
				if (items.getCount() == 0) {
					break;
				}
				items = inv.insertItem(i, items, false);
			}
		}
		return items;
	}

	public static void handleExperienceUpgrade(GenericTile tile) {
		ComponentInventory inv = tile.getComponent(ComponentType.Inventory);
		ComponentProcessor proc = tile.getComponent(ComponentType.Processor);
		if (inv != null && proc != null) {
			drainProcXp(inv, proc);
		} else {
			for (ComponentProcessor aproc : tile.getProcessors()) {
				if (aproc != null) {
					drainProcXp(inv, aproc);
				}
			}
		}
	}

	private static void drainProcXp(ComponentInventory inv, ComponentProcessor proc) {
		for (ItemStack stack : inv.getUpgradeContents()) {
			if (!stack.isEmpty() && stack.getItem() instanceof ItemUpgrade upgrade && upgrade.subtype == SubtypeItemUpgrade.experience) {
				CompoundTag tag = stack.getOrCreateTag();
				tag.putDouble(NBTUtils.XP, tag.getDouble(NBTUtils.XP) + proc.getStoredXp());
				proc.setStoredXp(0);
				break;
			}
		}
	}
}
