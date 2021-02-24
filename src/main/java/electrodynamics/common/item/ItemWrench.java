package electrodynamics.common.item;

import electrodynamics.api.item.IWrench;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemWrench extends Item implements IWrench {

    public ItemWrench(Properties properties) {
	super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
	return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
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
