package physica.core.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class Range {
	private World world;
	private int minX;
	private int minY;
	private int minZ;
	private int maxX;
	private int maxY;
	private int maxZ;

	public Range(World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		this.world = world;
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}

	public Range(World world, ChunkPos pos) {
		this.world = world;
		minX = pos.x * 16;
		minY = 0;
		minZ = pos.z * 16;
		maxX = minX + 16;
		maxY = 255;
		maxZ = minZ + 16;
	}

	public Range(World world, BlockPos pos) {
		this.world = world;
		minX = pos.getX();
		minY = pos.getY();
		minZ = pos.getZ();
		maxX = pos.getX() + 1;
		maxY = pos.getY() + 1;
		maxZ = pos.getZ() + 1;
	}

	public Range(Entity entity) {
		this(entity.getEntityWorld(), entity.getPosition());
	}

	public Range(TileEntity tile) {
		this(tile.getWorld(), tile.getPos());
	}

	public static Range getChunkRange(EntityPlayer player) {
		int radius = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getViewDistance();
		ChunkPos chunkPos = new ChunkPos(player.getPosition());

		return new Range(player.getEntityWorld(), chunkPos).expandChunks(radius);
	}

	public Range expandChunks(int chunks) {
		minX -= chunks * 16;
		maxX += chunks * 16;
		minZ -= chunks * 16;
		maxZ += chunks * 16;

		return this;
	}

	public boolean intersects(Range range) {
		return maxX + 1 - 1.E-05D > range.minX && range.maxX + 1 - 1.E-05D > minX && maxY + 1 - 1.E-05D > range.minY
				&& range.maxY + 1 - 1.E-05D > minY && maxZ + 1 - 1.E-05D > range.minZ
				&& range.maxZ + 1 - 1.E-05D > minZ;
	}

	public World getWorld() {
		return world;
	}

	public int getMinX() {
		return minX;
	}

	public int getMinY() {
		return minY;
	}

	public int getMinZ() {
		return minZ;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getMaxZ() {
		return maxZ;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Range) {
			Range range = (Range) object;

			return range.minX == minX && range.minY == minY && range.minZ == minZ && range.maxX == maxX
					&& range.maxY == maxY && range.maxZ == maxZ && range.world.equals(world);
		}

		return false;
	}
}