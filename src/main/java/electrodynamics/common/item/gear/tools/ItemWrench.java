package electrodynamics.common.item.gear.tools;

import electrodynamics.api.IWrenchItem;
import electrodynamics.prefab.tile.IWrenchable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ItemWrench extends Item implements IWrenchItem {

	public ItemWrench(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {

		Player player = context.getPlayer();

		if (player == null) {
			return InteractionResult.FAIL;
		}

		BlockPos pos = context.getClickedPos();
		BlockState state = context.getLevel().getBlockState(pos);
		Block block = state.getBlock();

		ItemStack stack = player.getItemInHand(context.getHand());

		if (block instanceof IWrenchable wrenchable) {

			if (player.isShiftKeyDown()) {

				if (onPickup(stack, pos, player)) {

					wrenchable.onPickup(stack, pos, player);

					return InteractionResult.CONSUME;

				}

			} else if (onRotate(stack, pos, player)) {

				wrenchable.onRotate(stack, pos, player);

				return InteractionResult.CONSUME;

			}

		}

		return InteractionResult.PASS;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
		return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
	}

	@Override
	public boolean onRotate(ItemStack stack, BlockPos pos, Player player) {
		return true;
	}

	@Override
	public boolean onPickup(ItemStack stack, BlockPos pos, Player player) {
		return true;
	}
}
