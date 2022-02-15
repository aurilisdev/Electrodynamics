package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.item.ItemUtils;
import electrodynamics.common.item.ItemDrillHead;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileLogisticalManager extends GenericTile {

	public TileLogisticalManager(BlockPos pos, BlockState state) {
		super(DeferredRegisters.TILE_LOGISTICALMANAGER.get(), pos, state);
		addComponent(new ComponentDirection());
		addComponent(new ComponentTickable().tickServer(this::tickServer));
	}

	private void tickServer(ComponentTickable tick) {
		Direction facing = ((ComponentDirection) getComponent(ComponentType.Direction)).getDirection().getOpposite();
		Direction left = facing.getCounterClockWise();
		Direction right = facing.getClockWise();
		BlockPos pos = getBlockPos();
		Level world = getLevel();
		// Quarry
		BlockEntity front = world.getBlockEntity(pos.relative(facing));
		if (front != null && front instanceof TileQuarry quarry) {
			ComponentInventory quarryInv = quarry.getComponent(ComponentType.Inventory);
			// left is for drill heads
			int drillSlot = 0;
			if (quarryInv.getItem(drillSlot).isEmpty()) {
				BlockEntity leftChest = world.getBlockEntity(pos.relative(left));
				if (leftChest != null && leftChest instanceof Container container) {
					if (container instanceof WorldlyContainer worldly) {
						for (int containerSlot : worldly.getSlotsForFace(left)) {
							if (takeItemFromContainer(quarryInv, drillSlot, container, container.getItem(containerSlot))) {
								break;
							}
						}
					} else {
						for (int i = 0; i < container.getContainerSize(); i++) {
							if (takeItemFromContainer(quarryInv, drillSlot, container, container.getItem(i))) {
								break;
							}
						}
					}
				}
			}
			BlockEntity rightChest = world.getBlockEntity(pos.relative(right));
			if (rightChest != null && rightChest instanceof Container container) {
				for (ItemStack stack : quarryInv.getOutputContents()) {
					if (container instanceof WorldlyContainer worldly) {
						for (int slot : worldly.getSlotsForFace(right)) {
							addItemToContainer(stack, container, slot);
						}
					} else {
						for (int i = 0; i < container.getContainerSize(); i++) {
							addItemToContainer(stack, container, i);
						}
					}
				}
			}
		}
	}

	private static void addItemToContainer(ItemStack stack, Container container, int slot) {
		if (!stack.isEmpty()) {
			if (container.canPlaceItem(slot, stack)) {
				ItemStack contained = container.getItem(slot);
				int room = container.getMaxStackSize() - contained.getCount();
				int amtAccepted = room >= stack.getCount() ? stack.getCount() : room;
				if (contained.isEmpty()) {
					container.setItem(slot, new ItemStack(stack.getItem(), amtAccepted).copy());
					stack.shrink(amtAccepted);
					container.setChanged();
				} else if (ItemUtils.testItems(stack.getItem(), contained.getItem())) {
					contained.grow(amtAccepted);
					stack.shrink(amtAccepted);
					container.setChanged();
				}
			}
		}
	}

	private static boolean takeItemFromContainer(ComponentInventory quarryInv, int drillSlot, Container container, ItemStack item) {
		if (!item.isEmpty() && item.getItem() instanceof ItemDrillHead) {
			quarryInv.setItem(drillSlot, item.copy());
			item.shrink(item.getCount());
			container.setChanged();
			return true;
		}
		return false;
	}

}
