package electrodynamics.prefab.tile.processing;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.ISubtype;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class O2OProcessingRecipe {
    private ItemStack input;
    private ItemStack output;

    public O2OProcessingRecipe(Object... objects) {
	int nextStack = 0;
	if (objects[nextStack] instanceof ISubtype) {
	    ISubtype type = (ISubtype) objects[nextStack];
	    if (objects[nextStack + 1] instanceof Integer) {
		input = new ItemStack(
			type.isItem() ? DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(type) : DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(type),
			(int) objects[nextStack + 1]);
		nextStack = 2;
	    } else {
		input = new ItemStack(
			type.isItem() ? DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(type) : DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(type));
		nextStack = 1;
	    }
	} else if (objects[nextStack] instanceof ItemStack) {
	    input = (ItemStack) objects[nextStack];
	    nextStack = 1;
	} else if (objects[nextStack] instanceof Item) {
	    input = new ItemStack((Item) objects[nextStack]);
	    nextStack = 1;
	} else if (objects[nextStack] instanceof Block) {
	    input = new ItemStack((Block) objects[nextStack]);
	    nextStack = 1;
	}
	if (objects[nextStack] instanceof ISubtype) {
	    ISubtype type = (ISubtype) objects[nextStack];
	    if (objects.length > nextStack + 1 && objects[nextStack + 1] instanceof Integer) {
		output = new ItemStack(
			type.isItem() ? DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(type) : DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(type),
			(int) objects[nextStack + 1]);
	    } else {
		output = new ItemStack(
			type.isItem() ? DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(type) : DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(type));
	    }
	} else if (objects[nextStack] instanceof ItemStack) {
	    output = (ItemStack) objects[nextStack];
	} else if (objects[nextStack] instanceof Item) {
	    output = new ItemStack((Item) objects[nextStack]);
	} else if (objects[nextStack] instanceof Block) {
	    output = new ItemStack((Block) objects[nextStack]);
	}
    }

    public ItemStack getInput() {
	return input;
    }

    public ItemStack getOutput() {
	return output;
    }
}
