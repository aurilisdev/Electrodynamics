package electrodynamics.common.blockitem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Supplier;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class BlockItemDescriptable extends BlockItem {

	private static HashMap<Supplier<Block>, HashSet<MutableComponent>> descriptionMappings = new HashMap<>();
	private static HashMap<Block, HashSet<MutableComponent>> processedDescriptionMappings = new HashMap<>();

	private static boolean initialized = false;

	public BlockItemDescriptable(Supplier<Block> block, Properties builder) {
		super(block.get(), builder);
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		if (!initialized) {
			BlockItemDescriptable.initialized = true;

			descriptionMappings.forEach((supplier, set) -> {

				processedDescriptionMappings.put(supplier.get(), set);

			});

		}
		HashSet<MutableComponent> gotten = processedDescriptionMappings.get(getBlock());
		if (gotten != null) {
			for (MutableComponent s : gotten) {
				tooltip.add(s.withStyle(ChatFormatting.GRAY));
			}
		}
		if (stack.hasTag()) {
			double joules = stack.getTag().getDouble("joules");
			if (joules > 0) {
				tooltip.add(Component.literal("Stored: " + ChatFormatter.getChatDisplay(joules, DisplayUnit.JOULES, 3, false)));
			}
		}
	}

	@Override
	public int getMaxStackSize(ItemStack stack) {
		return stack.hasTag() && stack.getTag().getDouble("joules") > 0 ? 1 : super.getMaxStackSize(stack);
	}

	public static void addDescription(Supplier<Block> block, MutableComponent description) {

		HashSet<MutableComponent> set = descriptionMappings.getOrDefault(block, new HashSet<>());

		set.add(description);
		
		descriptionMappings.put(block, set);

	}

}
