package electrodynamics.common.item.subtype;

import java.util.function.BiConsumer;

import electrodynamics.api.ISubtype;
import electrodynamics.api.item.ItemUtils;
import electrodynamics.common.tile.TileBatteryBox;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public enum SubtypeItemUpgrade implements ISubtype {
    basiccapacity((holder, processor) -> {
	if (holder instanceof TileBatteryBox box) {
	    box.currentCapacityMultiplier *= 1.5;
	    box.currentVoltageMultiplier = Math.max(box.currentVoltageMultiplier, 2);
	}
    }),
    basicspeed((holder, processor) -> {
	if (processor != null) {
	    processor.operatingSpeed *= 1.5;
	}
    }),
    advancedcapacity((holder, processor) -> {
	if (holder instanceof TileBatteryBox box) {
	    box.currentCapacityMultiplier *= 2.25;
	    box.currentVoltageMultiplier = Math.max(box.currentVoltageMultiplier, 4);
	}
    }),
    advancedspeed((holder, processor) -> {
	if (processor != null) {
	    processor.operatingSpeed *= 2.25;
	}
    }),
    itemoutput((holder, processor) -> {
    	ComponentInventory inv = holder.getComponent(ComponentType.Inventory);
    	for(Direction dir : Direction.values()) {
    		BlockPos pos = holder.getBlockPos().relative(dir);
    		BlockState state = holder.getLevel().getBlockState(pos);
    		if(state.hasBlockEntity()) {
    			BlockEntity entity = holder.getLevel().getBlockEntity(holder.getBlockPos().relative(dir));
    			if(entity instanceof Container container) {
        			for(ItemStack stack : inv.getOutputContents()) {
    					for(int i = 0; i < container.getContainerSize(); i++) {
    						if(!stack.isEmpty()) {
    							if(container.canPlaceItem(i, stack)) {
    								ItemStack contained = container.getItem(i);
    								int room = container.getMaxStackSize() - contained.getCount();
    								int amtAccepted = room >= stack.getCount() ? stack.getCount() : room;
    								
    								if(contained.isEmpty()) {
    									container.setItem(i, new ItemStack(stack.getItem(), amtAccepted).copy());
    									stack.shrink(amtAccepted);
    								} else {
    									if(ItemUtils.testItems(stack.getItem(), contained.getItem())) {
    										contained.grow(amtAccepted);
    										stack.shrink(amtAccepted);
    									}
    								}
	    							container.setChanged();
	    						}
	    					}
    					}
        			}
        		} else {
	    			if(entity != null && entity instanceof GenericTile tile) {
	    				ComponentInventory otherInv = tile.getComponent(ComponentType.Inventory);
	    				if(otherInv != null) {
	    					for(ItemStack stack : inv.getOutputContents()) {
		    					for(int i : otherInv.getSlotsForFace(dir.getOpposite())) {
			    					if(otherInv.canPlaceItem(i, stack)) {
			    						ItemStack contained = otherInv.getItem(i);
			    						int room = otherInv.getMaxStackSize() - contained.getCount();
	    								int amtAccepted = room >= stack.getCount() ? stack.getCount() : room;
	    								
	    								if(contained.isEmpty()) {
	    									otherInv.setItem(i, new ItemStack(stack.getItem(), amtAccepted).copy());
	    									stack.shrink(amtAccepted);
	    								} else {
	    									if(ItemUtils.testItems(stack.getItem(), contained.getItem())) {
	    										contained.grow(amtAccepted);
	    										stack.shrink(amtAccepted);
	    									}
	    								}
	    								otherInv.setChanged();
			    					}
			    				}
		    				}
	    				}
	    				
	    			}
        			/*if(entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.getOpposite()).map(m -> {return true;}).orElse(false)) {
	    				Electrodynamics.LOGGER.info("has cap");
	    				IItemHandler hand = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.getOpposite()).resolve().get();
	    				ItemStack newStack;
	    				for(ItemStack stack : inv.getOutputContents()) {
	    					for(int i = 0; i < hand.getSlots(); i++) {
	    						newStack = hand.insertItem(i, stack, true);
	    						if(newStack.getCount() > 0 ) {
	    							hand.insertItem(i, newStack, false);
	    							stack.shrink(newStack.getCount());
	    						}
	    					}
	    				}
	    			}
	    			*/
        		}
    		}
    	}
    })
    
    
    ;
	

    public final BiConsumer<GenericTile, ComponentProcessor> applyUpgrade;

    SubtypeItemUpgrade(BiConsumer<GenericTile, ComponentProcessor> applyUpgrade) {
	this.applyUpgrade = applyUpgrade;
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
}
