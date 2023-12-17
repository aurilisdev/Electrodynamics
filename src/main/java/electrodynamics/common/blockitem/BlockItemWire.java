package electrodynamics.common.blockitem;

import java.util.List;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class BlockItemWire extends BlockItem {

	private final BlockWire wire;

	public BlockItemWire(BlockWire wire, Properties builder) {
		super(wire, builder);
		this.wire = wire;
	}

	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(ElectroTextUtils.tooltip("itemwire.resistance", ChatFormatter.getChatDisplayShort(wire.wire.resistance, DisplayUnit.RESISTANCE)).withStyle(TextFormatting.GRAY));
		tooltip.add(ElectroTextUtils.tooltip("itemwire.maxamps", ChatFormatter.getChatDisplayShort(wire.wire.conductor.ampacity, DisplayUnit.AMPERE)).withStyle(TextFormatting.GRAY));
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
}