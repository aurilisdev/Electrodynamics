package electrodynamics.common.blockitem.types;

import java.util.HashSet;
import java.util.List;
import java.util.function.Supplier;

import electrodynamics.api.References;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.blockitem.BlockItemElectrodynamics;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class BlockItemWire extends BlockItemElectrodynamics {

	private static HashSet<BlockItemWire> WIRES = new HashSet<>();

	private final BlockWire wire;

	public BlockItemWire(BlockWire wire, Properties builder, Supplier<CreativeModeTab> creativeTab) {
		super(wire, builder, creativeTab);
		this.wire = wire;
		WIRES.add(this);
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(ElectroTextUtils.tooltip("itemwire.resistance", ChatFormatter.getChatDisplayShort(wire.wire.resistance, DisplayUnit.RESISTANCE)).withStyle(ChatFormatting.GRAY));
		tooltip.add(ElectroTextUtils.tooltip("itemwire.maxamps", ChatFormatter.getChatDisplayShort(wire.wire.conductor.ampacity, DisplayUnit.AMPERE)).withStyle(ChatFormatting.GRAY));
		if (wire.wire.insulation.shockVoltage == 0) {
			tooltip.add(ElectroTextUtils.tooltip("itemwire.info.uninsulated"));
		} else {
			tooltip.add(ElectroTextUtils.tooltip("itemwire.info.insulationrating", ChatFormatter.getChatDisplayShort(wire.wire.insulation.shockVoltage, DisplayUnit.VOLTAGE)));
		}
		if (wire.wire.insulation.fireProof) {
			ElectroTextUtils.tooltip("itemwire.info.fireproof");
		}
		if (wire.wire.wireClass.conductsRedstone) {
			ElectroTextUtils.tooltip("itemwire.info.redstone");
		}
	}

	@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = References.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
	private static class ColorHandler {

		@SubscribeEvent
		public static void registerColoredBlocks(RegisterColorHandlersEvent.Item event) {
			WIRES.forEach(item -> event.register((stack, index) -> {
				if (index == 1) {
					return item.wire.wire.color.color.color();
				}
				return Color.WHITE.color();
			}, item));
		}

	}

}
