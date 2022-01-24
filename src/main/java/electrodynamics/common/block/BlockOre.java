package electrodynamics.common.block;

import electrodynamics.common.block.subtype.SubtypeOre;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.material.Material;

public class BlockOre extends OreBlock {

	public BlockOre(SubtypeOre subtype) {
		super(Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(subtype.hardness, subtype.resistance), UniformInt.of(subtype.minXP, subtype.maxXP));
	}

}
