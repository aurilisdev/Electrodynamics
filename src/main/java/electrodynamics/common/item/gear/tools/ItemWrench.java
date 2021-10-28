package electrodynamics.common.item.gear.tools;

import electrodynamics.api.IWrenchItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemWrench extends Item implements IWrenchItem {

    public ItemWrench(Properties properties) {
	super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
	return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
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
