package electrodynamics.common.event;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.IWrenchItem;
import electrodynamics.api.References;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.prefab.tile.IWrenchable;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = References.ID, bus = Bus.FORGE)
public class InteractionHandler {

    @SubscribeEvent
    public static void onPlayerInteract(RightClickBlock event) {
	Player player = event.getPlayer();
	if (!player.level.isClientSide) {
	    BlockState state = event.getWorld().getBlockState(event.getPos());
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
	    } else if (block instanceof BlockWire wireBlock) {
		SubtypeWire wire = wireBlock.wire;
		if (item == Items.SHEARS) {
		    if (wire.ceramic) {
			player.level.setBlockAndUpdate(event.getPos(),
				Block.updateFromNeighbourShapes(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS
					.get(SubtypeWire.valueOf(wire.name().replace("ceramic", ""))).defaultBlockState(), player.level,
					event.getPos()));
			ItemStack insu = new ItemStack(DeferredRegisters.ITEM_CERAMICINSULATION.get());
			if (!player.addItem(insu)) {
			    player.level.addFreshEntity(
				    new ItemEntity(player.level, (int) player.getX(), (int) player.getY(), (int) player.getZ(), insu));
			}
		    } else if (wire.insulated && !wire.highlyinsulated) {
			player.level.setBlockAndUpdate(event.getPos(),
				Block.updateFromNeighbourShapes(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS
					.get(SubtypeWire.valueOf(wire.name().replace(wire.logistical ? "logistics" : "insulated", "")))
					.defaultBlockState(), player.level, event.getPos()));
			ItemStack insu = new ItemStack(DeferredRegisters.ITEM_INSULATION.get());
			if (!player.addItem(insu)) {
			    player.level.addFreshEntity(
				    new ItemEntity(player.level, (int) player.getX(), (int) player.getY(), (int) player.getZ(), insu));
			}
		    }
		} else if (item == DeferredRegisters.ITEM_INSULATION.get()) {
		    if (!wire.insulated && !wire.logistical) {
			player.level.setBlockAndUpdate(event.getPos(), Blocks.AIR.defaultBlockState());
			player.level
				.setBlockAndUpdate(event.getPos(),
					Block.updateFromNeighbourShapes(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS
						.get(SubtypeWire.valueOf("insulated" + wire.name())).defaultBlockState(), player.level,
						event.getPos()));
			stack.shrink(1);
		    }
		} else if (item == DeferredRegisters.ITEM_CERAMICINSULATION.get() && wire.insulated && !wire.ceramic && !wire.logistical
			&& !wire.highlyinsulated) {
		    player.level.setBlockAndUpdate(event.getPos(), Blocks.AIR.defaultBlockState());
		    player.level.setBlockAndUpdate(event.getPos(),
			    Block.updateFromNeighbourShapes(
				    DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeWire.valueOf("ceramic" + wire.name())).defaultBlockState(),
				    player.level, event.getPos()));
		    stack.shrink(1);
		}
	    }
	}
    }
}
