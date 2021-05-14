package electrodynamics.common.blockitem;

import java.util.List;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.common.block.connect.BlockWire;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class BlockItemWire extends BlockItem {

    private final BlockWire wire;

    public BlockItemWire(BlockWire wire, Properties builder) {
	super(wire, builder);
	this.wire = wire;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
	super.addInformation(stack, worldIn, tooltip, flagIn);
	tooltip.add(new TranslationTextComponent("tooltip.itemwire.resistance",
		ChatFormatter.getElectricDisplay(wire.wire.resistance, ElectricUnit.RESISTANCE)).mergeStyle(TextFormatting.GRAY));
	tooltip.add(
		new TranslationTextComponent("tooltip.itemwire.maxamps", ChatFormatter.getElectricDisplay(wire.wire.capacity, ElectricUnit.AMPERE))
			.mergeStyle(TextFormatting.GRAY));
	if (wire.wire.logistical) {
	    tooltip.add(new TranslationTextComponent("tooltip.itemwire.info.logistical"));
	} else if (wire.wire.ceramic) {
	    tooltip.add(new TranslationTextComponent("tooltip.itemwire.info.ceramic"));
	} else if (wire.wire.insulated) {
	    tooltip.add(new TranslationTextComponent("tooltip.itemwire.info.insulated"));
	} else {
	    tooltip.add(new TranslationTextComponent("tooltip.itemwire.info.uninsulated"));
	}
    }
}
