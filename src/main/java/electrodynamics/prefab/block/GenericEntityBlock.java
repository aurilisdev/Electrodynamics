package electrodynamics.prefab.block;

import java.util.Arrays;
import java.util.List;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.IWrenchable;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootContext;

public abstract class GenericEntityBlock extends BaseEntityBlock implements IWrenchable {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	protected GenericEntityBlock(Properties properties) {
		super(properties);
	}

	@Override
	public abstract BlockEntity newBlockEntity(BlockPos pos, BlockState state);

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level lvl, BlockState state, BlockEntityType<T> type) {
		return (l, pos, s, tile) -> {
			if (tile instanceof GenericTile generic && generic.getComponent(ComponentType.Tickable)instanceof ComponentTickable tickable) {
				tickable.performTick(l);
			}
		};
	}

	@Override
	public void onRotate(ItemStack stack, BlockPos pos, Player player) {
		if (player.level.getBlockState(pos).hasProperty(FACING)) {
			player.level.setBlockAndUpdate(pos, rotate(player.level.getBlockState(pos), Rotation.CLOCKWISE_90));
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
	public void onPickup(ItemStack stack, BlockPos pos, Player player) {
		Level world = player.level;
		BlockEntity tile = world.getBlockEntity(pos);
		if (tile instanceof GenericTile generic && generic.getComponent(ComponentType.Inventory)instanceof ComponentInventory inv) {
			Containers.dropContents(world, pos, inv);
		}
		world.destroyBlock(pos, true, player);
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		return Arrays.asList(new ItemStack(this));
	}
}
