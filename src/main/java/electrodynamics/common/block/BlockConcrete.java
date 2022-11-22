package electrodynamics.common.block;

import electrodynamics.common.block.subtype.SubtypeConcrete;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;

public class BlockConcrete extends Block {

	public BlockConcrete(SubtypeConcrete concrete) {
		super(Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(50F, 1200F));
	}

}
