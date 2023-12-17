package electrodynamics.common.blockitem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Supplier;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class BlockItemDescriptable extends BlockItem {

	private static HashMap<Supplier<Block>, HashSet<IFormattableTextComponent>> descriptionMappings = new HashMap<>();
	private static HashMap<Block, HashSet<IFormattableTextComponent>> processedDescriptionMappings = new HashMap<>();

	private static boolean initialized = false;

	public BlockItemDescriptable(Supplier<Block> block, Properties builder) {
		super(block.get(), builder);
	}

	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		if (!initialized) {
			BlockItemDescriptable.initialized = true;

			descriptionMappings.forEach((supplier, set) -> {

				processedDescriptionMappings.put(supplier.get(), set);

			});

		}
		HashSet<IFormattableTextComponent> gotten = processedDescriptionMappings.get(getBlock());
		if (gotten != null) {
			for (IFormattableTextComponent s : gotten) {
				tooltip.add(s.withStyle(TextFormatting.GRAY));
			}
		}
		if (stack.hasTag()) {
			double joules = stack.getTag().getDouble("joules");
			if (joules > 0) {
				tooltip.add(new StringTextComponent("Stored: " + ChatFormatter.getChatDisplay(joules, DisplayUnit.JOULES, 3, false)));
			}
		}
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		return stack.hasTag() && stack.getTag().getDouble("joules") > 0 ? 1 : super.getItemStackLimit(stack);
	}

	public static void addDescription(Supplier<Block> block, IFormattableTextComponent description) {

		HashSet<IFormattableTextComponent> set = descriptionMappings.getOrDefault(block, new HashSet<>());

		set.add(description);
		
		descriptionMappings.put(block, set);

	}

}
