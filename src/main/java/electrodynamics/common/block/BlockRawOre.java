package electrodynamics.common.block;

import java.util.List;

import com.google.common.collect.Lists;

import electrodynamics.common.block.subtype.SubtypeRawOreBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext.Builder;

public class BlockRawOre extends Block {

	public BlockRawOre(SubtypeRawOreBlock subtype) {
		super(Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F));
	}

	@Override
	public List<ItemStack> getDrops(BlockState pState, Builder pBuilder) {
		return Lists.newArrayList(new ItemStack(this));
	}
}
