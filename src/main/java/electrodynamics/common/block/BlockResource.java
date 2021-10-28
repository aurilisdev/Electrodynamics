package electrodynamics.common.block;

import electrodynamics.common.block.subtype.SubtypeResourceBlock;
import net.minecraft.world.level.block.Block;

public class BlockResource extends Block {

    public BlockResource(SubtypeResourceBlock subtype) {
	super(Block.Properties.of(subtype.getMaterial()).strength(subtype.getHardness(), subtype.getResistance()).sound(subtype.getSoundType()));
    }

}
