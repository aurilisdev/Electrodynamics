package electrodynamics.common.block;

import java.util.Random;

import electrodynamics.common.block.subtype.SubtypeOre;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.ToolType;

public class BlockOre extends OreBlock {
	public SubtypeOre ore;

	public BlockOre(SubtypeOre ore) {
		super(Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(ore.hardness, ore.resistance).harvestTool(ToolType.PICKAXE).harvestLevel(ore.harvestLevel));
		this.ore = ore;
	}

	@Override
	protected int xpOnDrop(Random random) {
		return MathHelper.nextInt(random, ore.minXP, ore.maxDrop);
	}

}
