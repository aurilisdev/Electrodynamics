package electrodynamics.common.item.gear.tools;

import electrodynamics.api.IWrenchItem;
import electrodynamics.prefab.tile.IWrenchable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemWrench extends Item implements IWrenchItem {

	public ItemWrench(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResultType useOn(ItemUseContext context) {

		PlayerEntity player = context.getPlayer();

		if (player == null) {
			return ActionResultType.FAIL;
		}

		BlockPos pos = context.getClickedPos();
		BlockState state = context.getLevel().getBlockState(pos);
		Block block = state.getBlock();

		ItemStack stack = player.getItemInHand(context.getHand());

		if (block instanceof IWrenchable) {
			IWrenchable wrenchable = (IWrenchable) block;

			if (player.isShiftKeyDown()) {

				if (onPickup(stack, pos, player)) {

					wrenchable.onPickup(stack, pos, player);

					return ActionResultType.CONSUME;

				}

			} else if (onRotate(stack, pos, player)) {

				wrenchable.onRotate(stack, pos, player);

				return ActionResultType.CONSUME;

			}

		}

		return ActionResultType.PASS;
	}

	@Override
	public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pUsedHand) {
		return ActionResult.fail(pPlayer.getItemInHand(pUsedHand));
	}

	@Override
	public boolean onRotate(ItemStack stack, BlockPos pos, PlayerEntity player) {
		return true;
	}

	@Override
	public boolean onPickup(ItemStack stack, BlockPos pos, PlayerEntity player) {
		return true;
	}
}
