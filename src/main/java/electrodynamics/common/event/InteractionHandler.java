package electrodynamics.common.event;

import electrodynamics.api.IWrenchItem;
import electrodynamics.api.References;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.block.subtype.SubtypeWire.WireType;
import electrodynamics.prefab.tile.IWrenchable;
import electrodynamics.registers.ElectrodynamicsBlocks;
import electrodynamics.registers.ElectrodynamicsItems;
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
		Player player = event.getEntity();
		if (!player.level.isClientSide) {
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
			} else if (block instanceof BlockWire wireBlock) {
				SubtypeWire wire = wireBlock.wire;
				if (item == Items.SHEARS) {
					if (wire.wireType == WireType.CERAMIC) {
						player.level.setBlockAndUpdate(event.getPos(), Block.updateFromNeighbourShapes(ElectrodynamicsBlocks.getBlock(SubtypeWire.getWireForType(WireType.INSULATED, wire.material)).defaultBlockState(), player.level, event.getPos()));
						ItemStack insu = new ItemStack(ElectrodynamicsItems.ITEM_CERAMICINSULATION.get());
						if (!player.addItem(insu)) {
							player.level.addFreshEntity(new ItemEntity(player.level, (int) player.getX(), (int) player.getY(), (int) player.getZ(), insu));
						}
					} else if (wire.wireType == WireType.INSULATED || wire.wireType == WireType.LOGISTICAL) {
						player.level.setBlockAndUpdate(event.getPos(), Block.updateFromNeighbourShapes(ElectrodynamicsBlocks.getBlock(SubtypeWire.getWireForType(WireType.UNINSULATED, wire.material)).defaultBlockState(), player.level, event.getPos()));
						ItemStack insu = new ItemStack(ElectrodynamicsItems.ITEM_INSULATION.get());
						if (!player.addItem(insu)) {
							player.level.addFreshEntity(new ItemEntity(player.level, (int) player.getX(), (int) player.getY(), (int) player.getZ(), insu));
						}
					}
				} else if (item == ElectrodynamicsItems.ITEM_INSULATION.get()) {
					if (wire.wireType == WireType.UNINSULATED) {
						player.level.setBlockAndUpdate(event.getPos(), Blocks.AIR.defaultBlockState());
						player.level.setBlockAndUpdate(event.getPos(), Block.updateFromNeighbourShapes(ElectrodynamicsBlocks.getBlock(SubtypeWire.getWireForType(WireType.INSULATED, wire.material)).defaultBlockState(), player.level, event.getPos()));
						stack.shrink(1);
					}
				} else if (item == ElectrodynamicsItems.ITEM_CERAMICINSULATION.get() && wire.wireType == WireType.INSULATED) {
					player.level.setBlockAndUpdate(event.getPos(), Blocks.AIR.defaultBlockState());
					player.level.setBlockAndUpdate(event.getPos(), Block.updateFromNeighbourShapes(ElectrodynamicsBlocks.getBlock(SubtypeWire.getWireForType(WireType.CERAMIC, wire.material)).defaultBlockState(), player.level, event.getPos()));
					stack.shrink(1);
				}
			}
		}
	}
}
