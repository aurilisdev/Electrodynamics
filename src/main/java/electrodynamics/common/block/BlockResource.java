package electrodynamics.common.block;

import electrodynamics.common.block.subtype.SubtypeResourceBlock;
import net.minecraft.block.Block;

public class BlockResource extends Block {

    public BlockResource(SubtypeResourceBlock subtype) {
	super(Block.Properties.create(subtype.getMaterial()).hardnessAndResistance(subtype.getHardness(), subtype.getResistance())
		.sound(subtype.getSoundType()));
    }

}
