package electrodynamics.common.event;

import electrodynamics.api.References;
import electrodynamics.api.item.IWrench;
import electrodynamics.api.tile.IWrenchable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = References.ID, bus = Bus.FORGE)
public class WrenchHandler {

	@SubscribeEvent
	public static void onPlayerInteract(RightClickBlock event) {
		PlayerEntity player = event.getPlayer();
		if (!player.world.isRemote) {
			BlockState state = event.getWorld().getBlockState(event.getPos());
			Block block = state.getBlock();
			ItemStack stack = event.getItemStack();
			Item item = stack.getItem();
			if (block instanceof IWrenchable && item instanceof IWrench) {
				if (player.isSneaking()) {
					System.out.println("works sneak");
					if (((IWrench) item).onPickup(stack, event.getPos(), player)) {
						((IWrenchable) block).onPickup(stack, event.getPos(), player);
					}
				} else {
					System.out.println("works normal");
					if (((IWrench) item).onRotate(stack, event.getPos(), player)) {
						((IWrenchable) block).onRotate(stack, event.getPos(), player);
					}
				}
			}
		}
	}
}
