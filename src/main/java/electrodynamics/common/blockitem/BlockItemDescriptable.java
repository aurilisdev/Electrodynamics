package electrodynamics.common.blockitem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

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
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
	super.appendHoverText(stack, worldIn, tooltip, flagIn);
	HashSet<String> gotten = descriptionMappings.get(block);
	if (gotten != null) {
	    for (String s : gotten) {
		boolean translate = s.contains("|translate|");
		if (translate) {
		    tooltip.add(new TranslatableComponent(s.replace("|translate|", "")).withStyle(ChatFormatting.GRAY));
		} else {
		    tooltip.add(new TextComponent(s).withStyle(ChatFormatting.GRAY));
		}
	    }
	}
	double joules = stack.getOrCreateTag().getDouble("joules");
	if (joules > 0) {
	    tooltip.add(new TextComponent("Stored: " + ChatFormatter.getElectricDisplay(joules, ElectricUnit.JOULES, 2, false)));
	}
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
	return stack.hasTag() && stack.getOrCreateTag().getDouble("joules") > 0 ? 1 : super.getItemStackLimit(stack);
    }
}
