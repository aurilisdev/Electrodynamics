package electrodynamics.common.block;

import javax.annotation.Nullable;

import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;

public class BlockOre extends DropExperienceBlock {

	@Nullable
	public final SubtypeOre ore;
	@Nullable
	public final SubtypeOreDeepslate deepOre;
	
	public BlockOre(SubtypeOre ore) {
		super(Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(ore.hardness, ore.resistance), UniformInt.of(ore.minXP, ore.maxXP));
		this.ore = ore;
		deepOre = null;
	}
	
	public BlockOre(SubtypeOreDeepslate ore) {
		super(Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops().strength(ore.hardness + 1.5f, ore.resistance + 1.5f), UniformInt.of(ore.minXP, ore.maxXP));
		deepOre = ore;
		this.ore = null;
	}

}
