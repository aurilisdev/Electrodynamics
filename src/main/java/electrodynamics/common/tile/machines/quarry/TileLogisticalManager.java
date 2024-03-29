package electrodynamics.common.tile.machines.quarry;

import java.util.Optional;

import electrodynamics.common.item.ItemDrillHead;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.Scheduler;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

public class TileLogisticalManager extends GenericTile {

	private TileQuarry[] quarries = new TileQuarry[6];
	private BlockEntity[] inventories = new BlockEntity[6];

	public TileLogisticalManager(BlockPos pos, BlockState state) {
		super(ElectrodynamicsBlockTypes.TILE_LOGISTICALMANAGER.get(), pos, state);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
	}

	private void tickServer(ComponentTickable tick) {
		for (int i = 0; i < 6; i++) {
			BlockEntity inventory = inventories[i];
			if (inventory != null) {
				LazyOptional<IItemHandler> lazy = inventory.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.values()[i].getOpposite());
				if (lazy.isPresent()) {
					Optional<IItemHandler> nonlazy = lazy.resolve();
					if (nonlazy.isPresent()) {
						IItemHandler handler = nonlazy.get();
						for (TileQuarry quarry : quarries) {
							if (quarry != null) {
								manipulateItems(quarry.getComponent(IComponentType.Inventory), handler);
							}
						}
					}
				}
			}
		}

	}

	@Override
	public void onNeightborChanged(BlockPos neighbor, boolean blockStateTrigger) {
		if (level.isClientSide) {
			return;
		}
		refreshConnections();
	}

	@Override
	public void onPlace(BlockState oldState, boolean isMoving) {
		super.onPlace(oldState, isMoving);
		if (level.isClientSide) {
			return;
		}
		refreshConnections();
	}

	public void refreshConnections() {
		quarries = new TileQuarry[6];
		inventories = new BlockEntity[6];
		for (Direction dir : Direction.values()) {
			BlockEntity entity = level.getBlockEntity(getBlockPos().relative(dir));
			if (entity != null) {
				if (entity instanceof TileQuarry quarry) {
					quarries[dir.ordinal()] = quarry;
				} else if (entity.getCapability(ForgeCapabilities.ITEM_HANDLER, dir.getOpposite()).isPresent()) {
					inventories[dir.ordinal()] = entity;
				}
			}
		}
	}

	@Override
	public void onLoad() {
		super.onLoad();
		Scheduler.schedule(1, this::refreshConnections);
	}

	private void manipulateItems(ComponentInventory quarryInventory, IItemHandler handler) {

		if (quarryInventory.getItem(TileQuarry.DRILL_HEAD_INDEX).isEmpty()) {
			restockDrillHead(quarryInventory, handler);
		}

		addItemsToInventory(quarryInventory, handler);

	}

	private void restockDrillHead(ComponentInventory quarryInventory, IItemHandler handler) {

		ItemStack stack;

		for (int i = 0; i < handler.getSlots(); i++) {

			stack = handler.getStackInSlot(i);

			if (!stack.isEmpty() && stack.getItem() instanceof ItemDrillHead) {
				quarryInventory.setItem(TileQuarry.DRILL_HEAD_INDEX, stack.copy());
				handler.extractItem(i, stack.getMaxStackSize(), false);
				break;
			}

		}

	}

	private void addItemsToInventory(ComponentInventory quarryInventory, IItemHandler handler) {
		for (int i = 0; i < quarryInventory.outputs(); i++) {
			int index = i + quarryInventory.getOutputStartIndex();
			ItemStack mined = quarryInventory.getItem(index);
			if (!mined.isEmpty()) {
				for (int j = 0; j < handler.getSlots(); j++) {
					mined = handler.insertItem(j, mined, false);
					quarryInventory.setItem(index, mined);
					quarryInventory.setChanged(index);
					if (mined.isEmpty()) {
						break;
					}
				}
			}
		}

	}

	public static boolean isQuarry(BlockPos pos, LevelAccessor world) {
		BlockEntity entity = world.getBlockEntity(pos);
		return entity instanceof TileQuarry;
	}

	public static boolean isValidInventory(BlockPos pos, LevelAccessor world, Direction dir) {
		BlockEntity entity = world.getBlockEntity(pos);
		if (entity == null) {
			return false;
		}
		if (entity.getCapability(ForgeCapabilities.ITEM_HANDLER, dir).isPresent()) {
			return true;
		}
		return entity instanceof Container;
	}

}
