package electrodynamics.common.blockitem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class BlockItemDescriptable extends BlockItem {
    private static HashMap<Block, HashSet<String>> descriptionMappings = new HashMap<>();

    private final Block block;

    public static void addDescription(Block block, String description) {
	HashSet<String> gotten = descriptionMappings.containsKey(block) ? descriptionMappings.get(block) : new HashSet<>();
	if (!descriptionMappings.containsKey(block)) {
	    descriptionMappings.put(block, gotten);
	}
	gotten.add(description);
    }

    public BlockItemDescriptable(Block block, Properties builder) {
	super(block, builder);
	this.block = block;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
	super.addInformation(stack, worldIn, tooltip, flagIn);
	HashSet<String> gotten = descriptionMappings.get(block);
	if (gotten != null) {
	    for (String s : gotten) {
		boolean translate = s.contains("|translate|");
		if (translate) {
		    tooltip.add(new TranslationTextComponent(s.replace("|translate|", "")).mergeStyle(TextFormatting.GRAY));
		} else {
		    tooltip.add(new StringTextComponent(s).mergeStyle(TextFormatting.GRAY));
		}
	    }
	}
	double joules = stack.getOrCreateTag().getDouble("joules");
	if (joules > 0) {
	    tooltip.add(new StringTextComponent("Stored: " + ChatFormatter.getElectricDisplay(joules, ElectricUnit.JOULES, 2, false)));
	}
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
	return stack.hasTag() && stack.getOrCreateTag().getDouble("joules") > 0 ? 1 : super.getItemStackLimit(stack);
    }
}
