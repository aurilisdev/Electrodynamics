package electrodynamics.common.blockitem;

import java.util.List;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.block.connect.BlockWire;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class BlockItemWire extends BlockItem {

	private final BlockWire wire;

	public BlockItemWire(BlockWire wire, Properties builder) {
		super(wire, builder);
		this.wire = wire;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(Component.translatable("tooltip.itemwire.resistance", ChatFormatter.getChatDisplay(wire.wire.resistance, DisplayUnit.RESISTANCE)).withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("tooltip.itemwire.maxamps", ChatFormatter.getChatDisplay(wire.wire.capacity, DisplayUnit.AMPERE)).withStyle(ChatFormatting.GRAY));
		if (wire.wire.logistical) {
			tooltip.add(Component.translatable("tooltip.itemwire.info.logistical"));
		} else if (wire.wire.ceramic) {
			tooltip.add(Component.translatable("tooltip.itemwire.info.ceramic"));
		} else if (wire.wire.insulated) {
			tooltip.add(Component.translatable("tooltip.itemwire.info." + (wire.wire.highlyinsulated ? "highlyinsulated" : "insulated")));
		} else {
			tooltip.add(Component.translatable("tooltip.itemwire.info.uninsulated"));
		}
	}
}
