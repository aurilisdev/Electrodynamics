package electrodynamics.prefab.item;

import java.util.Set;

import javax.annotation.Nonnull;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ItemMultiDigger extends DiggerItem {
    protected final Set<Tag<Block>> set;

    public ItemMultiDigger(float damage, float speed, Tier tier, Set<Tag<Block>> set, Properties prop) {
	super(damage, speed, tier, (Tag<Block>) set.toArray()[0], prop);
	this.set = set;
    }

    protected boolean checkState(BlockState state) {
	for (Tag<Block> tag : set) {
	    if (state.is(tag)) {
		return true;
	    }
	}
	return false;
    }

    @Deprecated(forRemoval = true)
    // FORGE: Use stack sensitive variant below
    // ONLY ADDED CAUSE IT EXISTS IN THE DiggerItem.class!!!
    @Override
    public boolean isCorrectToolForDrops(BlockState state) {
	if (net.minecraftforge.common.TierSortingRegistry.isTierSorted(getTier())) {
	    return net.minecraftforge.common.TierSortingRegistry.isCorrectTierForDrops(getTier(), state) && checkState(state);
	}
	int i = getTier().getLevel();
	if (i < 3 && state.is(BlockTags.NEEDS_DIAMOND_TOOL) || i < 2 && state.is(BlockTags.NEEDS_IRON_TOOL)) {
	    return false;
	}
	return i >= 1 && state.is(BlockTags.NEEDS_STONE_TOOL) && checkState(state);
    }

    // FORGE START
    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
	return checkState(state) && net.minecraftforge.common.TierSortingRegistry.isCorrectTierForDrops(getTier(), state);
    }

    @Override
    public float getDestroySpeed(@Nonnull ItemStack stack, BlockState state) {
	if (isCorrectToolForDrops(stack, state)) {
	    return getTier().getSpeed();
	}
	return 1;
    }
}
