package electrodynamics.common.block;

import java.util.Arrays;
import java.util.List;

import electrodynamics.common.block.subtype.SubtypeConcrete;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext.Builder;

public class BlockConcrete extends Block {

	public BlockConcrete(SubtypeConcrete concrete) {
		super(Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(50F, 1200F));
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder) {
		return Arrays.asList(new ItemStack(this));
	}

}
