package electrodynamics.common.world;

import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import electrodynamics.common.settings.OreConfig;
import electrodynamics.registers.DeferredRegisters;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;

public class OreGeneration {

	public static void generateSulfurAround(RandomSource random, BlockPos pos, WorldGenLevel level) {
		if (OreConfig.oresToSpawn.contains(SubtypeOre.sulfur.name())) {
			for (Direction direction : Direction.values()) {
				BlockPos offset = pos.offset(direction.getNormal());
				if (random.nextFloat() < 0.3) {
					if (level.getBlockState(offset).getBlock() == Blocks.STONE) {
						level.setBlock(offset, DeferredRegisters.getSafeBlock(SubtypeOre.sulfur).defaultBlockState(), 3);
					}
					if (level.getBlockState(offset).getBlock() == Blocks.DEEPSLATE) {
						level.setBlock(offset, DeferredRegisters.getSafeBlock(SubtypeOreDeepslate.sulfur).defaultBlockState(), 3);
					}
				}
			}
		}
	}
}
