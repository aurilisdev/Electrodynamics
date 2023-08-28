package electrodynamics.common.item.gear.tools;

import java.util.function.Supplier;

import electrodynamics.api.IWrenchItem;
import electrodynamics.common.item.ItemElectrodynamics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemWrench extends ItemElectrodynamics implements IWrenchItem {

	public ItemWrench(Properties properties, Supplier<CreativeModeTab> creativeTab) {
		super(properties, creativeTab);
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
