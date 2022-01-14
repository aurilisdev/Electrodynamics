package electrodynamics.prefab.utilities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;

public class WorldUtils {

	public static final double CHUNK_WIDTH = 16;

	public static List<LevelChunk> getChunksForRadius(LevelReader world, BlockPos pos, int xRadius, int zRadius) {

		// sanity check
		xRadius = Math.abs(xRadius);
		zRadius = Math.abs(zRadius);

		List<LevelChunk> chunks = new ArrayList<>(getChunksForQuadrant(world, pos, xRadius, zRadius, 1, 1));
		for (LevelChunk chunk : getChunksForQuadrant(world, pos, xRadius, zRadius, -1, 1)) {
			if (!chunks.contains(chunk)) {
				chunks.add(chunk);
			}
		}
		for (LevelChunk chunk : getChunksForQuadrant(world, pos, xRadius, zRadius, -1, -1)) {
			if (!chunks.contains(chunk)) {
				chunks.add(chunk);
			}
		}
		for (LevelChunk chunk : getChunksForQuadrant(world, pos, xRadius, zRadius, 1, -1)) {
			if (!chunks.contains(chunk)) {
				chunks.add(chunk);
			}
		}
		return chunks;
	}

	private static List<LevelChunk> getChunksForQuadrant(LevelReader world, BlockPos pos, int xRadius, int zRadius, int xSign, int zSign) {

		List<LevelChunk> quadrant = new ArrayList<>();

		int posX = pos.getX();
		int posZ = pos.getZ();
		int posY = pos.getY();

		int chunkRadiusX = (int) Math.ceil(xRadius / CHUNK_WIDTH);
		int chunkRadiusZ = (int) Math.ceil(zRadius / CHUNK_WIDTH);

		for (int i = 0; i <= chunkRadiusX; i++) {
			for (int j = 0; j <= chunkRadiusZ; j++) {
				quadrant.add((LevelChunk) world.getChunk(new BlockPos(posX, posY, posZ)));
				posZ += zSign * CHUNK_WIDTH;
			}
			posZ = pos.getZ();
			// increment after getting chunk!
			posX += xSign * CHUNK_WIDTH;
		}
		return quadrant;
	}

	public static BlockPos getClosestBlockToCenter(LevelReader world, BlockPos startPos, int maxRadius, Block... caseBlocks) {
		for (int radius = 1; radius <= maxRadius; radius++) {
			int iMin = -radius;
			int iMax = radius;
			int jMax = radius;
			int jMin = -radius;
			for (Direction dir : Direction.values()) {
				Vec3i orientation = dir.getNormal();
				for (int i = iMin; i < iMax; i++) {
					for (int j = jMin; j < jMax; j++) {
						int x = 0, y = 0, z = 0;
						if (orientation.getX() != 0) {
							x = orientation.getX() * radius;
							y += i;
							z += j;
						} else if (orientation.getY() != 0) {
							x += i;
							y = orientation.getY() * radius;
							z += j;
						} else if (orientation.getZ() != 0) {
							x += i;
							y += j;
							z = orientation.getZ() * radius;
						}
						BlockPos currentBlockPos = new BlockPos(x + startPos.getX(), y + startPos.getY(), z + startPos.getZ());
						if (compareStates(world.getBlockState(currentBlockPos), caseBlocks)) {
							return currentBlockPos;
						}
					}
				}
			}
		}
		return startPos;
	}

	private static boolean compareStates(BlockState state, Block... caseBlocks) {
		for (Block caseBlock : caseBlocks) {
			if (state.is(caseBlock)) {
				return true;
			}
		}
		return false;
	}

	public static ArrayList<BlockEntity> getNearbyTiles(LevelReader level, BlockPos pos, int radius) {
		ArrayList<BlockEntity> list = new ArrayList<>();
		for (int i = -radius; i <= radius; i++) {
			for (int j = -radius; j <= radius; j++) {
				for (int k = -radius; k <= radius; k++) {
					BlockPos offset = pos.offset(i, j, k);
					BlockEntity entity = level.getBlockEntity(offset);
					if (entity != null) {
						list.add(entity);
					}
				}
			}
		}
		return list;
	}

}
