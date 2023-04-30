package electrodynamics.common.blockitem;

import java.util.List;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.prefab.utilities.TextUtils;
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
		tooltip.add(TextUtils.tooltip("itemwire.resistance", ChatFormatter.getChatDisplay(wire.wire.resistance, DisplayUnit.RESISTANCE)).withStyle(ChatFormatting.GRAY));
		tooltip.add(TextUtils.tooltip("itemwire.maxamps", ChatFormatter.getChatDisplay(wire.wire.capacity, DisplayUnit.AMPERE)).withStyle(ChatFormatting.GRAY));
		if (wire.wire.wireClass.shockVoltage == 0) {
			tooltip.add(TextUtils.tooltip("itemwire.info.uninsulated"));
		} else {
			tooltip.add(TextUtils.tooltip("itemwire.info.insulationrating", ChatFormatter.getChatDisplayShort(wire.wire.wireClass.shockVoltage, DisplayUnit.VOLTAGE)));
		}
		if (wire.wire.wireClass.fireProof) {
			TextUtils.tooltip("itemwire.info.fireproof");
		}
		if (wire.wire.wireType.conductsRedstone) {
			TextUtils.tooltip("itemwire.info.redstone");
		}
	}
}
