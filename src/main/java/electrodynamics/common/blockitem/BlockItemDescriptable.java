package electrodynamics.common.blockitem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Supplier;

import com.mojang.datafixers.util.Pair;

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
	private static HashMap<Block, HashSet<MutableComponent>> descriptionMappings = new HashMap<>();
	private static List<Pair<Supplier<Block>, MutableComponent>> cachedDescriptions = new ArrayList<>();

	private final Supplier<Block> block;

	private static boolean initialized;

	private static void createDescription(Block block, MutableComponent description) {
		HashSet<MutableComponent> gotten = descriptionMappings.containsKey(block) ? descriptionMappings.get(block) : new HashSet<>();
		if (!descriptionMappings.containsKey(block)) {
			descriptionMappings.put(block, gotten);
		}
		gotten.add(description);
	}

	public BlockItemDescriptable(Supplier<Block> block, Properties builder) {
		super(block.get(), builder);
		this.block = block;
	}

	@Override
	public Block getBlock() {
		return block.get();
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		if (!initialized) {
			BlockItemDescriptable.initialized = true;
			for (Pair<Supplier<Block>, MutableComponent> pair : cachedDescriptions) {
				createDescription(pair.getFirst().get(), pair.getSecond());
			}
		}
		HashSet<MutableComponent> gotten = descriptionMappings.get(block.get());
		if (gotten != null) {
			for (MutableComponent s : gotten) {
				tooltip.add(s.withStyle(ChatFormatting.GRAY));
			}
		}
		if (stack.hasTag()) {
			double joules = stack.getTag().getDouble("joules");
			if (joules > 0) {
				tooltip.add(Component.literal("Stored: " + ChatFormatter.getChatDisplay(joules, DisplayUnit.JOULES, 2, false)));
			}
		}
	}

	@Override
	public int getMaxStackSize(ItemStack stack) {
		return stack.hasTag() && stack.getTag().getDouble("joules") > 0 ? 1 : super.getMaxStackSize(stack);
	}

	public static void addDescription(Supplier<Block> block, MutableComponent description) {
		createDescription(block.get(), description);
	}
}
