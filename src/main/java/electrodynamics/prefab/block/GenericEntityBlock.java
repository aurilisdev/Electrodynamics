package electrodynamics.prefab.block;

import java.util.Arrays;
import java.util.List;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.IWrenchable;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.state.DirectionProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public abstract class GenericEntityBlock extends Block implements IWrenchable {
	public static final DirectionProperty FACING = HorizontalBlock.FACING;

	protected GenericEntityBlock(Properties properties) {
		super(properties);
	}

	@Override
	public BlockRenderType getRenderShape(BlockState pState) {
		return BlockRenderType.MODEL;
	}

	@Override
	public void onRotate(ItemStack stack, BlockPos pos, PlayerEntity player) {
		if (player.level.getBlockState(pos).hasProperty(FACING)) {
			BlockState state = rotate(player.level.getBlockState(pos), Rotation.CLOCKWISE_90);
			player.level.setBlockAndUpdate(pos, state);
		}
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		if (state.hasProperty(FACING)) {
			return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
		}
		return super.rotate(state, rot);
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		if (state.hasProperty(FACING)) {
			return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
		}
		return super.mirror(state, mirrorIn);
	}

	@Override
	public void onPickup(ItemStack stack, BlockPos pos, PlayerEntity player) {
		World world = player.level;
		world.destroyBlock(pos, true, player);
	}

	// TODO get this to work
	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		TileEntity tile = builder.getOptionalParameter(LootParameters.BLOCK_ENTITY);
		if (tile instanceof GenericTile) {
			GenericTile machine = (GenericTile) tile;
			ItemStack stack = new ItemStack(this);
			ComponentInventory inv = machine.getComponent(IComponentType.Inventory);
			if (inv != null) {
				InventoryHelper.dropContents(machine.getLevel(), machine.getBlockPos(), inv.getItems());
				tile.getCapability(ElectrodynamicsCapabilities.ELECTRODYNAMIC).ifPresent(el -> {
					double joules = el.getJoulesStored();
					if (joules > 0) {
						stack.getOrCreateTag().putDouble("joules", joules);
					}
				});
			}
			return Arrays.asList(stack);

		}
		return super.getDrops(state, builder);
	}

	// TODO get this to work

	@Override
	public void onRemove(BlockState state, World level, BlockPos pos, BlockState newState, boolean isMoving) {
		TileEntity entity = level.getBlockEntity(pos);
		if (entity instanceof GenericTile) {
			GenericTile generic = (GenericTile) entity;
			if (newState.isAir(level, pos) || !newState.is(state.getBlock())) {
				generic.onBlockDestroyed();
			} else {
				generic.onBlockStateUpdate(state, newState);
			}
		}
		super.onRemove(state, level, pos, newState, isMoving);
	}

	/**
	 * Fired when a neighboring tile changes
	 */
	@Override
	public void onNeighborChange(BlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor) {
		super.onNeighborChange(state, world, pos, neighbor);
		TileEntity entity = world.getBlockEntity(pos);
		if (entity instanceof GenericTile) {
			GenericTile generic = (GenericTile) entity;
			generic.onNeightborChanged(neighbor, false);
		}
	}

	@Override
	public void onPlace(BlockState newState, World level, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onPlace(newState, level, pos, oldState, isMoving);
		TileEntity entity = level.getBlockEntity(pos);
		if (entity instanceof GenericTile) {
			GenericTile generic = (GenericTile) entity;
			generic.onPlace(oldState, isMoving);
		}
	}

	/**
	 * Fired when a neighboring blockstate changes
	 */
	@Override
	public void neighborChanged(BlockState state, World level, BlockPos pos, Block block, BlockPos neighbor, boolean isMoving) {
		super.neighborChanged(state, level, pos, block, neighbor, isMoving);
		TileEntity entity = level.getBlockEntity(pos);
		if (entity instanceof GenericTile) {
			GenericTile generic = (GenericTile) entity;
			generic.onNeightborChanged(neighbor, true);
		}

	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState pState) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState state, World level, BlockPos pos) {
		TileEntity entity = level.getBlockEntity(pos);
		if (entity instanceof GenericTile) {
			GenericTile generic = (GenericTile) entity;
			return generic.getComparatorSignal();
		}
		return super.getAnalogOutputSignal(state, level, pos);
	}

	@Override
	public ActionResultType use(BlockState state, World level, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		TileEntity entity = level.getBlockEntity(pos);
		if (entity instanceof GenericTile) {
			GenericTile generic = (GenericTile) entity;
			return generic.use(player, handIn, hit);
		}

		return super.use(state, level, pos, player, handIn, hit);
	}

	@Override
	public int getDirectSignal(BlockState state, IBlockReader level, BlockPos pos, Direction direction) {
		TileEntity entity = level.getBlockEntity(pos);
		if (entity instanceof GenericTile) {
			GenericTile generic = (GenericTile) entity;
			return generic.getDirectSignal(direction);
		}
		return super.getDirectSignal(state, level, pos, direction);
	}

	@Override
	public int getSignal(BlockState state, IBlockReader level, BlockPos pos, Direction direction) {
		TileEntity entity = level.getBlockEntity(pos);
		if (entity instanceof GenericTile) {
			GenericTile generic = (GenericTile) entity;
			return generic.getSignal(direction);
		}
		return super.getSignal(state, level, pos, direction);
	}

	@Override
	public void entityInside(BlockState state, World level, BlockPos pos, Entity entity) {
		super.entityInside(state, level, pos, entity);
		TileEntity blockentity = level.getBlockEntity(pos);
		if (blockentity instanceof GenericTile) {
			GenericTile generic = (GenericTile) blockentity;
			generic.onEntityInside(state, level, pos, entity);
		}
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public abstract TileEntity createTileEntity(BlockState state, IBlockReader world);

}
