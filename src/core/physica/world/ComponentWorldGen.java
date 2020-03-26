package physica.world;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkGeneratorEnd;
import net.minecraft.world.gen.ChunkGeneratorHell;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import physica.block.state.EnumOreState;
import physica.component.ComponentBlocks;

public class ComponentWorldGen implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		if (!(chunkGenerator instanceof ChunkGeneratorHell) && !(chunkGenerator instanceof ChunkGeneratorEnd)) {
			generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		}
	}

	private void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		for (EnumOreState ore : EnumOreState.values()) {
			generateOre(ComponentBlocks.blockOre.getStateFromMeta(ore.ordinal()), world, random, chunkX * 16,
					chunkZ * 16, ore.getMinY(), ore.getMaxY(),
					(int) (ore.getVeinSize() / 2.0f + random.nextFloat() * ore.getVeinSize() / 2.0f),
					ore.getVeinsPerChunk());
		}
	}

	private void generateOre(IBlockState ore, World world, Random random, int x, int z, int minY, int maxY,
			int veinSize, int veinsPerChunk) {
		int deltaY = maxY - minY;

		for (int i = 0; i < veinsPerChunk; i++) {
			BlockPos pos = new BlockPos(x + random.nextInt(16), minY + random.nextInt(deltaY), z + random.nextInt(16));

			WorldGenMinable generator = new WorldGenMinable(ore, veinSize);
			generator.generate(world, random, pos);
		}
	}
}
