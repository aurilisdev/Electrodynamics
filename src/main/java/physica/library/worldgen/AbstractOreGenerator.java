package physica.library.worldgen;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public abstract class AbstractOreGenerator implements IWorldGenerator {

	public Block oreBlock;

	public int oreMeta;
	public int harvestLevel;

	public String harvestTool;

	public AbstractOreGenerator(Block block, int meta, String harvestTool, int harvestLevel) {
		this.harvestTool = harvestTool;
		this.harvestLevel = harvestLevel;
		oreBlock = block;
		oreMeta = meta;
		block.setHarvestLevel(this.harvestTool, this.harvestLevel, meta);
	}

	public abstract void generate(World world, Random random, int varX, int varZ);

	public abstract boolean isOreGeneratedInWorld(World world, IChunkProvider chunkGenerator);

	@Override
	public final void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		chunkX = chunkX << 4;
		chunkZ = chunkZ << 4;

		if (isOreGeneratedInWorld(world, chunkGenerator))
		{
			generate(world, rand, chunkX, chunkZ);
		}
	}
}
