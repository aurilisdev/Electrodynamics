package electrodynamics.prefab.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import electrodynamics.prefab.utilities.math.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.network.play.server.SUpdateLightPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.lighting.WorldLightManager;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fluids.IFluidBlock;

public class WorldUtils {

	public static final double CHUNK_WIDTH = 16;

	public static List<Chunk> getChunksForRadius(World world, BlockPos pos, int xRadius, int zRadius) {

		// sanity check
		xRadius = Math.abs(xRadius);
		zRadius = Math.abs(zRadius);

		List<Chunk> chunks = new ArrayList<>(getChunksForQuadrant(world, pos, xRadius, zRadius, 1, 1));
		for (Chunk chunk : getChunksForQuadrant(world, pos, xRadius, zRadius, -1, 1)) {
			if (!chunks.contains(chunk)) {
				chunks.add(chunk);
			}
		}
		for (Chunk chunk : getChunksForQuadrant(world, pos, xRadius, zRadius, -1, -1)) {
			if (!chunks.contains(chunk)) {
				chunks.add(chunk);
			}
		}
		for (Chunk chunk : getChunksForQuadrant(world, pos, xRadius, zRadius, 1, -1)) {
			if (!chunks.contains(chunk)) {
				chunks.add(chunk);
			}
		}
		return chunks;
	}

	private static List<Chunk> getChunksForQuadrant(World world, BlockPos pos, int xRadius, int zRadius, int xSign, int zSign) {

		List<Chunk> quadrant = new ArrayList<>();

		int posX = pos.getX();
		int posZ = pos.getZ();
		int posY = pos.getY();

		int chunkRadiusX = (int) Math.ceil(xRadius / CHUNK_WIDTH);
		int chunkRadiusZ = (int) Math.ceil(zRadius / CHUNK_WIDTH);

		for (int i = 0; i <= chunkRadiusX; i++) {
			for (int j = 0; j <= chunkRadiusZ; j++) {
				quadrant.add((Chunk) world.getChunk(new BlockPos(posX, posY, posZ)));
				posZ += zSign * CHUNK_WIDTH;
			}
			posZ = pos.getZ();
			// increment after getting chunk!
			posX += xSign * CHUNK_WIDTH;
		}
		return quadrant;
	}

	public static BlockPos getClosestBlockToCenter(World world, BlockPos startPos, int maxRadius, Block... caseBlocks) {
		for (int radius = 1; radius <= maxRadius; radius++) {
			int iMin = -radius;
			int iMax = radius;
			int jMax = radius;
			int jMin = -radius;
			for (Direction dir : Direction.values()) {
				Vector3i orientation = dir.getNormal();
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

	public static ArrayList<TileEntity> getNearbyTiles(World level, BlockPos pos, int radius) {
		ArrayList<TileEntity> list = new ArrayList<>();
		for (int i = -radius; i <= radius; i++) {
			for (int j = -radius; j <= radius; j++) {
				for (int k = -radius; k <= radius; k++) {
					BlockPos offset = pos.offset(i, j, k);
					TileEntity entity = level.getBlockEntity(offset);
					if (entity != null) {
						list.add(entity);
					}
				}
			}
		}
		return list;
	}

	private static HashMap<ChunkPos, Chunk> chunkCache = new HashMap<>();

	public static void clearChunkCache() {
		for (Chunk chunk : chunkCache.values()) {
			ServerWorld level = (ServerWorld) chunk.getLevel();
			chunk.setUnsaved(true);
			WorldLightManager lightManager = level.getLightEngine();
			lightManager.enableLightSources(chunk.getPos(), false);

			SUpdateLightPacket packet = new SUpdateLightPacket(chunk.getPos(), lightManager, false);
			level.getChunkSource().chunkMap.getPlayers(chunk.getPos(), false).forEach(e -> e.connection.send(packet));
			level.getChunkSource().updateChunkForced(chunk.getPos(), true);
		}
		chunkCache.clear();
	}

	public static void fastRemoveBlockExplosion(ServerWorld level, BlockPos pos) {
		if (!level.isOutsideBuildHeight(pos)) {
			Chunk chunk = getChunk(level, pos);
			ChunkSection storage = getBlockStorage(pos);
			BlockState oldState = chunk.getBlockState(pos);
			Block block = oldState.getBlock();
			if (oldState != Blocks.AIR.defaultBlockState() && oldState != Blocks.VOID_AIR.defaultBlockState() && oldState.getDestroySpeed(level, pos) >= 0) {
				if (oldState.hasTileEntity() || block instanceof FallingBlock || block instanceof IFluidBlock) {
					level.removeBlock(pos, false);
					level.getLightEngine().checkBlock(pos);
					return;
				}
				if (storage != null) {
					storage.setBlockState(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15, Blocks.AIR.defaultBlockState());
					level.getLightEngine().checkBlock(pos);
				}
			}
		}
	}

	private static ChunkSection getBlockStorage(BlockPos pos) {
		Chunk chunk = getChunk(null, pos);
		for (ChunkSection section : chunk.getSections()) {
			if (section.bottomBlockY() > pos.getY()) {
				continue;
			}
			return section;
		}
		return chunk.getSections()[0]; // Should work TM ?
	}

	private static Chunk getChunk(ServerWorld level, BlockPos pos) {
		ChunkPos cp = new ChunkPos(pos);
		if (!chunkCache.containsKey(cp)) {
			chunkCache.put(cp, level.getChunk(pos.getX() >> 4, pos.getZ() >> 4));
		}
		return chunkCache.get(cp);
	}

	public static double distanceBetweenPositions(BlockPos a, BlockPos b) {
		return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2) + Math.pow(a.getZ() - b.getZ(), 2));
	}

	public static void award(ServerWorld world, Vector3d pos, int amt) {
		while (amt > 0) {
			int i = getExperienceValue(amt);
			amt -= i;
			if (!tryMergeToExisting(world, pos, i)) {
				world.addFreshEntity(new ExperienceOrbEntity(world, pos.x(), pos.y(), pos.z(), i));
			}
		}

	}

	private static boolean tryMergeToExisting(ServerWorld world, Vector3d pos, int amt) {
		AxisAlignedBB aabb = MathUtils.ofSize(pos, 1.0D, 1.0D, 1.0D);
		int i = world.getRandom().nextInt(40);

		List<ExperienceOrbEntity> list = world.getEntitiesOfClass(ExperienceOrbEntity.class, aabb, entity -> canMerge(entity, i, amt));
		if (!list.isEmpty()) {
			ExperienceOrbEntity experienceorb = list.get(0);
			++experienceorb.value;
			experienceorb.age = 0;
			return true;
		} else {
			return false;
		}
	}

	private static boolean canMerge(ExperienceOrbEntity orb, int random, int amt) {
		return !orb.removed && (orb.getId() - random) % 40 == 0 && orb.value == amt;
	}

	public static int getExperienceValue(int pExpValue) {
		if (pExpValue >= 2477) {
			return 2477;
		} else if (pExpValue >= 1237) {
			return 1237;
		} else if (pExpValue >= 617) {
			return 617;
		} else if (pExpValue >= 307) {
			return 307;
		} else if (pExpValue >= 149) {
			return 149;
		} else if (pExpValue >= 73) {
			return 73;
		} else if (pExpValue >= 37) {
			return 37;
		} else if (pExpValue >= 17) {
			return 17;
		} else if (pExpValue >= 7) {
			return 7;
		} else {
			return pExpValue >= 3 ? 3 : 1;
		}
	}
}
