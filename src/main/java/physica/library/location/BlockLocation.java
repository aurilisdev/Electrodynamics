package physica.library.location;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLocation {

	public int x;
	public int y;
	public int z;

	public BlockLocation() {
		this(0, 0, 0);
	}

	public BlockLocation(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public BlockLocation(TileEntity tile) {
		this(tile.xCoord, tile.yCoord, tile.zCoord);
	}

	public void set(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public int getZ()
	{
		return z;
	}

	public float norm()
	{
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	public Location normalize()
	{
		float n = norm();
		return new Location(x / n, y / n, z / n);
	}

	public float getDistance(float x2, float y2, float z2)
	{
		double d3 = x - x2;
		double d4 = y - y2;
		double d5 = z - z2;
		return MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
	}

	public float getDistance(double x2, double y2, double z2)
	{
		double d3 = x - x2;
		double d4 = y - y2;
		double d5 = z - z2;
		return MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
	}

	public float getDistance(Location vector)
	{
		double d3 = x - vector.x;
		double d4 = y - vector.y;
		double d5 = z - vector.z;
		return MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
	}

	public float getDistance(BlockLocation vector)
	{
		double d3 = x - vector.x;
		double d4 = y - vector.y;
		double d5 = z - vector.z;
		return MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
	}

	public double getAverage()
	{
		return (x + y + z) / 3f;
	}

	public TileEntity getTile(IBlockAccess world)
	{
		return world.getTileEntity(x, y, z);
	}

	public int getMetadata(IBlockAccess world)
	{
		return world.getBlockMetadata(x, y, z);
	}

	public void setMetadata(World world, int meta)
	{
		world.setBlockMetadataWithNotify(x, y, z, meta, 3);
	}

	public float getHardness(World world)
	{
		return getBlock(world).getBlockHardness(world, x, y, z);
	}

	public Block getBlock(IBlockAccess world)
	{
		return world.getBlock(x, y, z);
	}

	public void setBlock(World world, Block block)
	{
		world.setBlock(x, y, z, block);
	}

	public void setBlockNonUpdate(World world, Block block)
	{
		world.setBlock(x, y, z, block, 0, 2);
	}

	public void setBlockAir(World world)
	{
		world.setBlockToAir(x, y, z);
	}

	public void setBlockAirNonUpdate(World world)
	{
		world.setBlock(x, y, z, Blocks.air, 0, 2);
	}

	public void setTile(World world, TileEntity tile)
	{
		world.setTileEntity(x, y, z, tile);
	}

	public boolean isAirBlock(IBlockAccess world)
	{
		return world.isAirBlock(x, y, z);
	}

	public BlockLocation sub(Location b)
	{
		x -= b.x;
		y -= b.y;
		z -= b.z;
		return this;
	}

	public BlockLocation sub(int sub)
	{
		x -= sub;
		y -= sub;
		z -= sub;
		return this;
	}

	public BlockLocation add(Location b)
	{
		x += b.x;
		y += b.y;
		z += b.z;
		return this;
	}

	public BlockLocation add(int add)
	{
		x += add;
		y += add;
		z += add;
		return this;
	}

	public BlockLocation mul(Location b)
	{
		x *= b.x;
		y *= b.y;
		z *= b.z;
		return this;
	}

	public BlockLocation mul(float mul)
	{
		x *= mul;
		y *= mul;
		z *= mul;
		return this;
	}

	public BlockLocation abs()
	{
		x = Math.abs(x);
		y = Math.abs(y);
		z = Math.abs(z);
		return this;
	}

	public BlockLocation Copy()
	{
		return new BlockLocation(x, y, z);
	}

	public Location Location()
	{
		return new Location(x, y, z);
	}

	public static BlockLocation Sub(BlockLocation a, BlockLocation b)
	{
		return new BlockLocation(a.x - b.x, a.y - b.y, a.z - b.z);
	}

	public static BlockLocation Add(BlockLocation a, BlockLocation b)
	{
		return new BlockLocation(a.x + b.x, a.y + b.y, a.z + b.z);
	}

	public static BlockLocation Mul(BlockLocation a, int f)
	{
		return new BlockLocation(a.x * f, a.y * f, a.z * f);
	}

	public static BlockLocation Abs(BlockLocation a)
	{
		return new BlockLocation(Math.abs(a.x), Math.abs(a.y), Math.abs(a.z));
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		} else if (obj == null || getClass() != obj.getClass())
		{
			return false;
		}
		BlockLocation other = (BlockLocation) obj;
		return x == other.x && y == other.y && z == other.z;
	}

	@Override
	public int hashCode()
	{
		return 255 - y;
	}

	public void writeToNBT(NBTTagCompound tag, String vectorName)
	{
		tag.setInteger(vectorName + "-X", x);
		tag.setInteger(vectorName + "-Y", y);
		tag.setInteger(vectorName + "-Z", z);
	}

	public void readFromNBT(NBTTagCompound tag, String vectorName)
	{
		x = tag.getInteger(vectorName + "-X");
		y = tag.getInteger(vectorName + "-Y");
		z = tag.getInteger(vectorName + "-Z");
	}

	@Override
	public String toString()
	{
		return "BlockLocation [" + x + ", " + y + ", " + z + "]";
	}
}
