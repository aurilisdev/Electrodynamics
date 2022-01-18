package electrodynamics.common.block;

import electrodynamics.common.block.subtype.SubtypeRawOreBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;

public class BlockRawOre extends Block {

	public BlockRawOre(SubtypeRawOreBlock subtype) {
		super(Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F));
	}
}
