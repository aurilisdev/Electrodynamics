package electrodynamics.common.block;

import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class BlockDeepslateOre extends Block {

	public BlockDeepslateOre(SubtypeOreDeepslate subtype) {
		super(Properties.of(Material.STONE).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops().strength(subtype.hardness + 1.5f, subtype.resistance + 1.5f));
	}
}
