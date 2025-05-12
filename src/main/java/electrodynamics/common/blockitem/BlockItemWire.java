package electrodynamics.common.blockitem;

import java.util.HashSet;
import java.util.List;
import java.util.function.Supplier;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
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

	public BlockItemWire(BlockWire wire, Properties builder, Supplier<ItemGroup> creativeTab) {
		super(wire, builder, creativeTab);
		this.wire = wire;
		WIRES.add(this);
	}

	@Override
	public void appendHoverText(ItemStack stack, World context, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, context, tooltip, flagIn);
		tooltip.add(ElectroTextUtils.tooltip("itemwire.resistance", ChatFormatter.getChatDisplayShort(wire.wire.getResistance(), DisplayUnits.RESISTANCE).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY));
		tooltip.add(ElectroTextUtils.tooltip("itemwire.maxamps", ChatFormatter.getChatDisplayShort(wire.wire.getAmpacity(), DisplayUnits.AMPERE).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY));
		if (wire.wire.getInsulation().shockVoltage() == 0) {
			tooltip.add(ElectroTextUtils.tooltip("itemwire.info.uninsulated").withStyle(TextFormatting.GRAY));
		} else {
			tooltip.add(ElectroTextUtils.tooltip("itemwire.info.insulationrating", ChatFormatter.getChatDisplayShort(wire.wire.getInsulation().shockVoltage(), DisplayUnits.VOLTAGE).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY));
		}
		if (wire.wire.getInsulation().fireproof()) {
			ElectroTextUtils.tooltip("itemwire.info.fireproof").withStyle(TextFormatting.GRAY);
		}
		if (wire.wire.getWireClass().conductsRedstone()) {
			ElectroTextUtils.tooltip("itemwire.info.redstone").withStyle(TextFormatting.GRAY);
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
