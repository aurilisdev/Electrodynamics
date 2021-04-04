package electrodynamics.common.event;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.IWrenchItem;
import electrodynamics.api.References;
import electrodynamics.api.tile.IWrenchable;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.block.subtype.SubtypeWire;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
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
	    if (block instanceof IWrenchable && item instanceof IWrenchItem) {
		if (player.isSneaking()) {
		    if (((IWrenchItem) item).onPickup(stack, event.getPos(), player)) {
			((IWrenchable) block).onPickup(stack, event.getPos(), player);
		    }
		} else {
		    if (((IWrenchItem) item).onRotate(stack, event.getPos(), player)) {
			((IWrenchable) block).onRotate(stack, event.getPos(), player);
		    }
		}
	    } else if (block instanceof BlockWire && item == DeferredRegisters.ITEM_INSULATION.get()) {
		SubtypeWire wire = ((BlockWire) block).wire;
		if (!wire.insulated && !wire.logistical) {
		    player.world.setBlockState(event.getPos(), Blocks.AIR.getDefaultState());
		    player.world.setBlockState(event.getPos(),
			    Block.getValidBlockForPosition(
				    DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeWire.valueOf("insulated" + wire.name())).getDefaultState(),
				    player.world, event.getPos()));
		}
	    }
	}
    }
}
