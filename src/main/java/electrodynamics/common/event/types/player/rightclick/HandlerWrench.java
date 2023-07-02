package electrodynamics.common.event.types.player.rightclick;

import electrodynamics.api.IWrenchItem;
import electrodynamics.prefab.tile.IWrenchable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;

public class HandlerWrench extends AbstractRightClickBlockHandler {

	@Override
	public void handle(RightClickBlock event) {

		Player player = event.getEntity();

		if (player.level.isClientSide) {
			return;
		}

		BlockState state = event.getLevel().getBlockState(event.getPos());
		Block block = state.getBlock();
		ItemStack stack = event.getItemStack();
		Item item = stack.getItem();

		if (block instanceof IWrenchable wrenchable && item instanceof IWrenchItem wrench) {

			if (player.isShiftKeyDown()) {

				if (wrench.onPickup(stack, event.getPos(), player)) {

					wrenchable.onPickup(stack, event.getPos(), player);

				}

			} else if (wrench.onRotate(stack, event.getPos(), player)) {

				wrenchable.onRotate(stack, event.getPos(), player);

			}

		} 

	}

}
