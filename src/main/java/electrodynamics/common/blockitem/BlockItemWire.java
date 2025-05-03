package electrodynamics.common.blockitem;

import java.util.HashSet;
import java.util.List;
import java.util.function.Supplier;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.common.blockitem.BlockItemDescriptable;
import voltaic.prefab.utilities.math.Color;

public class BlockItemWire extends BlockItemDescriptable {

	private static HashSet<BlockItemWire> WIRES = new HashSet<>();

	private final BlockWire wire;

	public BlockItemWire(BlockWire wire, Properties builder, Supplier<CreativeModeTab> creativeTab) {
		super(wire, builder, creativeTab);
		this.wire = wire;
		WIRES.add(this);
	}

	@Override
	public void appendHoverText(ItemStack stack, Level context, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, context, tooltip, flagIn);
		tooltip.add(ElectroTextUtils.tooltip("itemwire.resistance", ChatFormatter.getChatDisplayShort(wire.wire.getResistance(), DisplayUnits.RESISTANCE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		tooltip.add(ElectroTextUtils.tooltip("itemwire.maxamps", ChatFormatter.getChatDisplayShort(wire.wire.getAmpacity(), DisplayUnits.AMPERE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		if (wire.wire.getInsulation().shockVoltage() == 0) {
			tooltip.add(ElectroTextUtils.tooltip("itemwire.info.uninsulated").withStyle(ChatFormatting.GRAY));
		} else {
			tooltip.add(ElectroTextUtils.tooltip("itemwire.info.insulationrating", ChatFormatter.getChatDisplayShort(wire.wire.getInsulation().shockVoltage(), DisplayUnits.VOLTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		}
		if (wire.wire.getInsulation().fireproof()) {
			ElectroTextUtils.tooltip("itemwire.info.fireproof").withStyle(ChatFormatting.GRAY);
		}
		if (wire.wire.getWireClass().conductsRedstone()) {
			ElectroTextUtils.tooltip("itemwire.info.redstone").withStyle(ChatFormatting.GRAY);
		}
	}

	@EventBusSubscriber(value = Dist.CLIENT, modid = Electrodynamics.ID, bus = EventBusSubscriber.Bus.MOD)
	private static class ColorHandler {

		@SubscribeEvent
		public static void registerColoredBlocks(ColorHandlerEvent.Item event) {
			WIRES.forEach(item -> event.getItemColors().register((stack, index) -> {
				if (index == 1) {
					return item.wire.wire.getWireColor().getColor().color();
				}
				return Color.WHITE.color();
			}, item));
		}

	}

}
