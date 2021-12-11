package electrodynamics.common.block;

import java.util.Arrays;
import java.util.List;

import electrodynamics.common.block.subtype.SubtypeResourceBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext.Builder;

public class BlockResource extends Block {

    public BlockResource(SubtypeResourceBlock subtype) {
	super(Block.Properties.of(subtype.getMaterial()).strength(subtype.getHardness(), subtype.getResistance()).sound(subtype.getSoundType()));
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, Builder builder) {
	return Arrays.asList(new ItemStack(this));
    }

}
