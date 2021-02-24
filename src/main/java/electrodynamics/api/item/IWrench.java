package electrodynamics.api.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public interface IWrench {
    boolean onRotate(ItemStack stack, BlockPos pos, PlayerEntity player);

    boolean onPickup(ItemStack stack, BlockPos pos, PlayerEntity player);
}
