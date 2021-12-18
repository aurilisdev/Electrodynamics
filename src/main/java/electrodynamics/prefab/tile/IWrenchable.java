package electrodynamics.prefab.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IWrenchable {
	void onRotate(ItemStack stack, BlockPos pos, Player player);

	void onPickup(ItemStack stack, BlockPos pos, Player player);
}
