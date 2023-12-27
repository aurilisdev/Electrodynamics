package electrodynamics.prefab.tile;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public interface IWrenchable {
	void onRotate(ItemStack stack, BlockPos pos, PlayerEntity player);

	void onPickup(ItemStack stack, BlockPos pos, PlayerEntity player);
}
