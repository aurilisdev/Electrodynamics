package electrodynamics.common.block;

import electrodynamics.common.block.subtype.SubtypeOre;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;

public class BlockOre extends Block {

	public BlockOre(SubtypeOre subtype) {
		super(Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(subtype.hardness, subtype.resistance));
	}

}
