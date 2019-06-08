package physica.library.worldgen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import javax.vecmath.Vector3d;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderEnd;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.ChunkProviderHell;
import net.minecraftforge.common.util.ForgeDirection;

public class OreGenReplace extends AbstractOreGenerator {

	public final OreGeneratorSettings settings;

	public boolean ignoreSurface = false;
	public boolean ignoreNether = true;
	public boolean ignoreEnd = true;

	public OreGenReplace(Block block, int meta, OreGeneratorSettings settings, String harvestTool, int harvestLevel) {
		super(block, meta, harvestTool, harvestLevel);
		this.settings = settings;
	}

	@Override
	public void generate(World world, Random random, int varX, int varZ) {
		int blocksPlaced = 0;
		while (blocksPlaced < settings.amountPerChunk) {
			int x = varX + random.nextInt(16);
			int z = varZ + random.nextInt(16);
			int y = random.nextInt(Math.max(1, settings.maxGenerateHeight - settings.minGenerateHeight)) + settings.minGenerateHeight - 1;
			int placed = generateBranch(world, random, varX, varZ, x, y, z);
			if (placed <= 0) {
				placed = settings.amountPerBranch;
			}
			blocksPlaced += placed;
		}
	}

	public int generateBranch(World world, Random rand, int chunkCornerX, int chunkCornerZ, int varX, int varY, int varZ) {
		int blocksPlaced = 0;
		Set<Vector3d> pathed = new HashSet<>();
		Queue<Vector3d> toPath = new LinkedList<>();

		toPath.add(new Vector3d(varX, varY, varZ));

		List<ForgeDirection> directions = new ArrayList<>();
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			directions.add(dir);
		}
		while (!toPath.isEmpty() && blocksPlaced < settings.amountPerBranch) {
			Vector3d next = toPath.poll();
			pathed.add(next);

			Block block = world.getBlock((int) next.x, (int) next.y, (int) next.z);
			if (settings.replaceBlock == null || block == settings.replaceBlock) {
				if (world.setBlock((int) next.x, (int) next.y, (int) next.z, oreBlock, oreMeta, 2)) {
					blocksPlaced += 1;
				}
			}

			Collections.shuffle(directions);
			for (ForgeDirection direction : directions) {
				Vector3d pos = new Vector3d(next.x + direction.offsetX, next.y + direction.offsetY, next.z + direction.offsetZ);
				if (!pathed.contains(pos) && world.rand.nextBoolean()) {
					if (pos.y > 0 && pos.y < world.getHeight() - 1 && world.blockExists((int) pos.x, (int) pos.y, (int) pos.z)) {
						boolean insideX = (int) pos.x >= chunkCornerX && (int) pos.x < chunkCornerX + 16;
						boolean insideZ = (int) pos.y >= chunkCornerZ && (int) pos.z < chunkCornerZ + 16;
						boolean insideY = (int) pos.z >= settings.minGenerateHeight && (int) pos.y <= settings.maxGenerateHeight;
						if (insideX && insideZ && insideY) {
							block = world.getBlock((int) pos.x, (int) pos.y, (int) pos.z);
							if (settings.replaceBlock == null || block == settings.replaceBlock) {
								toPath.add(pos);
							}
						}
					}

					if (!toPath.contains(pos)) {
						pathed.add(pos);
					}
				}
			}
		}
		return blocksPlaced;
	}

	@Override
	public boolean isOreGeneratedInWorld(World world, IChunkProvider chunkGenerator) {
		return chunkGenerator instanceof ChunkProviderGenerate ? !ignoreSurface
				: chunkGenerator instanceof ChunkProviderHell ? !ignoreNether : chunkGenerator instanceof ChunkProviderEnd ? !ignoreEnd : false;
	}
}
