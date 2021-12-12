package electrodynamics.common.item.subtype;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.util.TriConsumer;

import electrodynamics.api.ISubtype;
import electrodynamics.api.capability.dirstorage.CapabilityDirectionalStorage;
import electrodynamics.api.capability.dirstorage.ICapabilityDirectionalStorage;
import electrodynamics.api.item.ItemUtils;
import electrodynamics.common.tile.TileBatteryBox;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public enum SubtypeItemUpgrade implements ISubtype {
    basiccapacity((holder, processor, upgrade) -> {
	if (holder instanceof TileBatteryBox box) {
	    box.currentCapacityMultiplier = Math.min(box.currentCapacityMultiplier * 1.5, Math.pow(2.25, 3));
	    box.currentVoltageMultiplier = Math.max(box.currentVoltageMultiplier, 2);
	}
    }, 2),
    basicspeed((holder, processor, upgrade) -> {
	if (processor != null) {
	    processor.operatingSpeed = Math.min(processor.operatingSpeed * 1.5, Math.pow(2.25, 3));
	}
    }, 3),
    advancedcapacity((holder, processor, upgrade) -> {
	if (holder instanceof TileBatteryBox box) {
	    box.currentCapacityMultiplier = Math.min(box.currentCapacityMultiplier * 2.25, Math.pow(2.25, 3));
	    box.currentVoltageMultiplier = Math.max(box.currentVoltageMultiplier, 4);
	}
    }, 4),
    advancedspeed((holder, processor, upgrade) -> {
	if (processor != null) {
	    processor.operatingSpeed = Math.min(processor.operatingSpeed * 2.25, Math.pow(2.25, 3));
	}
    }, 3),
    
    iteminput((holder, processor, upgrade) -> {
    	ComponentInventory inv = holder.getComponent(ComponentType.Inventory);
    	if(CapabilityDirStorage.DIR_STORAGE_CAPABILITY != null) {
    		List<Direction> dirs = upgrade.getCapability(CapabilityDirStorage.DIR_STORAGE_CAPABILITY).map(m -> m.getDirections()).orElse(new ArrayList<>());
    		boolean isSmart = upgrade.getCapability(CapabilityDirStorage.DIR_STORAGE_CAPABILITY).map(m -> m.getBoolean()).orElse(false);
    		if(isSmart) {
    			int slot;
    			Direction dir = Direction.DOWN;
    			for(int i = 0; i < inv.getInputSlots().size(); i++) {
    				slot = inv.getInputSlots().get(i);
    				if(i < dirs.size()) {
    					dir = dirs.get(i);
    				}
    				inputSmartMode(getBlockEntity(holder, dir), slot, dir);
    			}
    		} else {
    			for(Direction dir : dirs) {
        			inputDefaultMode(getBlockEntity(holder, dir), inv, dir);
    	    	}
    		}
    	}
    }, 1),
    
    itemoutput((holder, processor, upgrade) -> {
    	ComponentInventory inv = holder.getComponent(ComponentType.Inventory);
    	if(CapabilityDirStorage.DIR_STORAGE_CAPABILITY != null) {
    		List<Direction> dirs = upgrade.getCapability(CapabilityDirStorage.DIR_STORAGE_CAPABILITY).map(m -> m.getDirections()).orElse(new ArrayList<>());
    		boolean isSmart = upgrade.getCapability(CapabilityDirStorage.DIR_STORAGE_CAPABILITY).map(m -> m.getBoolean()).orElse(false);
    		if(isSmart) {
    			List<ItemStack> combinedItems = new ArrayList<>();
    			combinedItems.addAll(inv.getOutputContents());
    			combinedItems.addAll(inv.getItemBiContents());
    			ItemStack stack;
    			Direction dir = Direction.DOWN;
    			for(int i = 0; i < combinedItems.size(); i++) {
    				stack = combinedItems.get(i);
    				if(i < dirs.size()) {
    					dir = dirs.get(i);
    				}
    				outputSmartMode(getBlockEntity(holder, dir), stack, dir);
    			}
    		} else {
    			for(Direction dir : dirs) {
        			outputDefaultMode(getBlockEntity(holder, dir), inv, dir);
    	    	}
    		}
    	}
    }, 1);

    public final TriConsumer<GenericTile, ComponentProcessor, ItemStack> applyUpgrade;
    public final int maxSize;

    SubtypeItemUpgrade(TriConsumer<GenericTile, ComponentProcessor, ItemStack> applyUpgrade, int maxSize) {
	this.applyUpgrade = applyUpgrade;
	this.maxSize = maxSize;
    }

    @Override
    public String tag() {
	return "upgrade" + name();
    }

    @Override
    public String forgeTag() {
	return "upgrade/" + name();
    }

    @Override
    public boolean isItem() {
	return true;
    }
    
    private static void inputSmartMode(BlockEntity entity, int slot, Direction dir) {
    	if(entity instanceof Container container) {
			attemptContainerExtract(slot, container, dir);
		} else if(entity != null && entity instanceof GenericTile tile) {
			ComponentInventory otherInv = tile.getComponent(ComponentType.Inventory);
			if(otherInv != null) {
				takeItemCompInv(s, otherInv, dir);
			}
		}
    }
    
    private static void inputDefaultMode(BlockEntity entity, ComponentInventory inv, Direction dir) {
    	if(entity instanceof Container container) {
    		attemptContainerExtract(inv, container, dir);
    	} else if(entity != null && entity instanceof GenericTile tile) {
			ComponentInventory otherInv = tile.getComponent(ComponentType.Inventory);
			takeItemCompInv(inv, otherInv, dir);
		
    	}
    }
    
    private static void outputSmartMode(BlockEntity entity, ItemStack stack, Direction dir) {
    	if(entity instanceof Container container) {
			attemptContainerInsert(stack, container, dir);
		} else if(entity != null && entity instanceof GenericTile tile) {
			ComponentInventory otherInv = tile.getComponent(ComponentType.Inventory);
			if(otherInv != null) {
				addItemCompInv(stack, otherInv, dir);
			}
			
		}
    }
    
    private static void outputDefaultMode(BlockEntity entity, ComponentInventory inv, Direction dir) {
		if(entity instanceof Container container) {
			for(ItemStack stack : inv.getOutputContents()) {
				attemptContainerInsert(stack, container, dir);
			}
		} else if(entity != null && entity instanceof GenericTile tile) {
			ComponentInventory otherInv = tile.getComponent(ComponentType.Inventory);
			if(otherInv != null) {
				for(ItemStack stack : inv.getOutputContents()) {
					addItemCompInv(stack, otherInv, dir);
				}
			}
		}
	    }

	}
    
    private static void attemptContainerInsert(ItemStack stack, Container container, Direction dir) {
    	if(container instanceof WorldlyContainer worldly) {
    		for(int slot :  worldly.getSlotsForFace(dir)) {
    			addItemToContainer(stack, container, slot);
    		}
    	} else {
    		for(int i = 0; i < container.getContainerSize(); i++) {
    			addItemToContainer(stack, container, i);
    		}
    	}
    }
    
    private static void attemptContainerExtract(ComponentInventory inv, Container container, Direction dir) {
    	if(container instanceof WorldlyContainer worldly) {
    		for(int slot : worldly.getSlotsForFace(dir)) {
        		takeItemFromContainer(inv, container, slot);
        	}
    	} else {
    		for(int i = 0; i < container.getContainerSize(); i++) {
        		takeItemFromContainer(inv, container, i);
        	}
    	}
    }
    
    private static void takeItemFromContainer(ComponentInventory inv, Container container, int slot) {
    	ItemStack containerItem = container.getItem(slot);
    	ItemStack invItem;
		for(int invSlot : inv.getInputSlots()) {
			if(inv.canPlaceItem(invSlot, containerItem)) {
				invItem = inv.getItem(invSlot);
				if(invItem.isEmpty() && !containerItem.isEmpty()) {
					int room = inv.getMaxStackSize();
					int amtAccepted = room >= containerItem.getCount() ? containerItem.getCount() : room;
    				inv.setItem(invSlot, new ItemStack(containerItem.getItem(), amtAccepted).copy());
    				containerItem.shrink(amtAccepted);
				} else if(!containerItem.isEmpty() && ItemUtils.testItems(invItem.getItem(), containerItem.getItem())) {
					int room = inv.getMaxStackSize() - invItem.getMaxStackSize();
					int amtAccepted = room >= containerItem.getCount() ? containerItem.getCount() : room;
					invItem.grow(amtAccepted);
					containerItem.shrink(amtAccepted);
					container.setChanged();
				}
				container.setChanged();
			}
		}
    }
    
    public static void addItemToContainer(ItemStack stack, Container container, int slot) {
    	if(!stack.isEmpty()) {
			if(container.canPlaceItem(slot, stack)) {
				ItemStack contained = container.getItem(slot);
				int room = container.getMaxStackSize() - contained.getCount();
				int amtAccepted = room >= stack.getCount() ? stack.getCount() : room;
				if(contained.isEmpty()) {
					container.setItem(slot, new ItemStack(stack.getItem(), amtAccepted).copy());
					stack.shrink(amtAccepted);
				} else if(ItemUtils.testItems(stack.getItem(), contained.getItem())) {
					contained.grow(amtAccepted);
					stack.shrink(amtAccepted);
				}
				container.setChanged();
			}
		}
	    }
	}
    }
    
    private static void addItemCompInv(ItemStack stack, ComponentInventory otherInv, Direction dir) {
    	for(int i : otherInv.getSlotsForFace(dir.getOpposite())) {
			if(otherInv.canPlaceItem(i, stack)) {
				ItemStack contained = otherInv.getItem(i);
				int room = otherInv.getMaxStackSize() - contained.getCount();
				int amtAccepted = room >= stack.getCount() ? stack.getCount() : room;
				if(contained.isEmpty()) {
					otherInv.setItem(i, new ItemStack(stack.getItem(), amtAccepted).copy());
					stack.shrink(amtAccepted);
				} else if(ItemUtils.testItems(stack.getItem(), contained.getItem())) {
					contained.grow(amtAccepted);
					stack.shrink(amtAccepted);
				}
				otherInv.setChanged();
			}
		}
		otherInv.setChanged();
	    }
	}
    }
    
    private static void takeItemCompInv(ComponentInventory inv, ComponentInventory otherInv, Direction dir) {
    	List<ItemStack> combinedOutputs = new ArrayList<>();
    	combinedOutputs.addAll(otherInv.getOutputContents());
    	combinedOutputs.addAll(otherInv.getItemBiContents());
    	ItemStack invItem;
    	for(int invSlot : inv.getInputSlots()) {
    		invItem = inv.getItem(invSlot);
    		for(ItemStack stack : combinedOutputs) {
    			if(inv.canPlaceItem(invSlot, stack)) {
    				invItem = inv.getItem(invSlot);
    				int room = inv.getMaxStackSize() - invItem.getCount();
    				int amtAccepted = room >= stack.getCount() ? stack.getCount() : room;
    				if(invItem.isEmpty()) {
    					inv.setItem(invSlot, new ItemStack(stack.getItem(), amtAccepted).copy());
    					stack.shrink(amtAccepted);
    				} else if (ItemUtils.testItems(stack.getItem(), invItem.getItem())) {
    					invItem.grow(amtAccepted);
    					stack.shrink(amtAccepted);
    				}
    			}
    		}
    	}
    }
    
    private static BlockEntity getBlockEntity(GenericTile holder, Direction dir) {
	BlockPos pos = holder.getBlockPos().relative(dir);
	BlockState state = holder.getLevel().getBlockState(pos);
	if (state.hasBlockEntity()) {
	    return holder.getLevel().getBlockEntity(holder.getBlockPos().relative(dir));
	}
	return null;
    }
}
