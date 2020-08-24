package electrodynamics.api.tile.processing;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.subtype.Subtype;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DO2OProcessingRecipe {
	private ItemStack input1;
	private ItemStack input2;
	private ItemStack output;

	public DO2OProcessingRecipe(Object... objects) {
		int nextStack = 0;
		if (objects[nextStack] instanceof Subtype) {
			Subtype type = (Subtype) objects[nextStack];
			if (objects[nextStack + 1] instanceof Integer) {
				input1 = new ItemStack(type.isItem() ? DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(type) : DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(type), (int) objects[nextStack + 1]);
				nextStack = 2;
			} else {
				input1 = new ItemStack(type.isItem() ? DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(type) : DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(type));
				nextStack = 1;
			}
		} else if (objects[nextStack] instanceof ItemStack) {
			input1 = (ItemStack) objects[nextStack];
			nextStack = 1;
		} else if (objects[nextStack] instanceof Item) {
			input1 = new ItemStack((Item) objects[nextStack]);
			nextStack = 1;
		} else if (objects[nextStack] instanceof Block) {
			input1 = new ItemStack((Block) objects[nextStack]);
			nextStack = 1;
		}
		if (objects[nextStack] instanceof Subtype) {
			Subtype type = (Subtype) objects[nextStack];
			if (objects.length > nextStack + 1 && objects[nextStack + 1] instanceof Integer) {
				nextStack += 2;
				input2 = new ItemStack(type.isItem() ? DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(type) : DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(type), (int) objects[nextStack + 1]);
			} else {
				nextStack += 1;
				input2 = new ItemStack(type.isItem() ? DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(type) : DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(type));
			}
		} else if (objects[nextStack] instanceof ItemStack) {
			input2 = (ItemStack) objects[nextStack];
			nextStack += 1;
		} else if (objects[nextStack] instanceof Item) {
			input2 = new ItemStack((Item) objects[nextStack]);
			nextStack += 1;
		} else if (objects[nextStack] instanceof Block) {
			input2 = new ItemStack((Block) objects[nextStack]);
			nextStack += 1;
		}
		if (objects[nextStack] instanceof Subtype) {
			Subtype type = (Subtype) objects[nextStack];
			if (objects.length > nextStack + 1 && objects[nextStack + 1] instanceof Integer) {
				output = new ItemStack(type.isItem() ? DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(type) : DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(type), (int) objects[nextStack + 1]);
			} else {
				output = new ItemStack(type.isItem() ? DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(type) : DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(type));
			}
		} else if (objects[nextStack] instanceof ItemStack) {
			output = (ItemStack) objects[nextStack];
		} else if (objects[nextStack] instanceof Item) {
			output = new ItemStack((Item) objects[nextStack]);
		} else if (objects[nextStack] instanceof Block) {
			output = new ItemStack((Block) objects[nextStack]);
		}
	}

	public ItemStack getInput1() {
		return input1;
	}

	public ItemStack getInput2() {
		return input2;
	}

	public ItemStack getOutput() {
		return output;
	}
}
