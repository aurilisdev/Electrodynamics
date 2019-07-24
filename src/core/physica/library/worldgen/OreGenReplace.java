package physica.library.worldgen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import org.lwjgl.util.vector.Vector3f;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderEnd;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.ChunkProviderHell;
import physica.api.core.abstraction.Face;

public class OreGenReplace extends AbstractOreGenerator {

	public final OreGeneratorSettings	settings;

	public boolean						ignoreSurface	= false;
	public boolean						ignoreNether	= true;
	public boolean						ignoreEnd		= true;

	public OreGenReplace(Block block, int meta, OreGeneratorSettings settings, String harvestTool, int harvestLevel) {
		super(block, meta, harvestTool, harvestLevel);
		this.settings = settings;
	}

	@Override
	public void generate(World world, Random random, int varX, int varZ)
	{
		int blocksPlaced = 0;
		if (settings.amountPerChunk > 0)
		{
			while (blocksPlaced < settings.amountPerChunk)
			{
				int x = varX + random.nextInt(16);
				int z = varZ + random.nextInt(16);
				int y = random.nextInt(Math.max(settings.maxGenerateHeight - settings.minGenerateHeight, 0)) + settings.minGenerateHeight;
				int placed = generateBranch(world, random, varX, varZ, x, y, z);
				if (placed <= 0)
				{
					placed = settings.amountPerBranch;
				}
				blocksPlaced += placed;
			}
		}
	}

	public int generateBranch(World world, Random rand, int chunkCornerX, int chunkCornerZ, int varX, int varY, int varZ)
	{
		int blocksPlaced = 0;
		Set<Vector3f> pathed = new HashSet<>();
		Queue<Vector3f> toPath = new LinkedList<>();

		toPath.add(new Vector3f(varX, varY, varZ));

		List<Face> directions = new ArrayList<>();
		for (Face dir : Face.VALID)
		{
			directions.add(dir);
		}
		while (!toPath.isEmpty() && blocksPlaced < settings.amountPerBranch)
		{
			Vector3f next = toPath.poll();
			pathed.add(next);

			Block block = world.getBlock((int) next.x, (int) next.y, (int) next.z);
			if (settings.replaceBlock == null || block == settings.replaceBlock)
			{
				if (world.setBlock((int) next.x, (int) next.y, (int) next.z, oreBlock, oreMeta, 2))
				{
					blocksPlaced += 1;
				}
			}

			Collections.shuffle(directions);
			for (Face direction : directions)
			{
				Vector3f pos = new Vector3f(next.x + direction.offsetX, next.y + direction.offsetY, next.z + direction.offsetZ);
				if (!pathed.contains(pos) && world.rand.nextBoolean())
				{
					if (pos.y > 0 && pos.y < world.getHeight() - 1 && world.blockExists((int) pos.x, (int) pos.y, (int) pos.z))
					{
						boolean insideX = (int) pos.x >= chunkCornerX && (int) pos.x < chunkCornerX + 16;
						boolean insideZ = (int) pos.y >= chunkCornerZ && (int) pos.z < chunkCornerZ + 16;
						boolean insideY = (int) pos.z >= settings.minGenerateHeight && (int) pos.y <= settings.maxGenerateHeight;
						if (insideX && insideZ && insideY)
						{
							block = world.getBlock((int) pos.x, (int) pos.y, (int) pos.z);
							if (settings.replaceBlock == null || block == settings.replaceBlock)
							{
								toPath.add(pos);
							}
						}
					}

					if (!toPath.contains(pos))
					{
						pathed.add(pos);
					}
				}
			}
		}
		return blocksPlaced;
	}

	@Override
	public boolean isOreGeneratedInWorld(World world, IChunkProvider chunkGenerator)
	{
		return chunkGenerator instanceof ChunkProviderGenerate ? !ignoreSurface : chunkGenerator instanceof ChunkProviderHell ? !ignoreNether : chunkGenerator instanceof ChunkProviderEnd ? !ignoreEnd : false;
	}
}
