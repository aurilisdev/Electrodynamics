package physica.library.location;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import physica.api.core.abstraction.Face;

public class Location {

	public int	xCoord;
	public int	yCoord;
	public int	zCoord;

	public Location() {
		this(0, 0, 0);
	}

	public Location(int x, int y, int z) {
		xCoord = x;
		yCoord = y;
		zCoord = z;
	}

	public Location(TileEntity tile) {
		this(tile.xCoord, tile.yCoord, tile.zCoord);
	}

	public Location set(int x, int y, int z)
	{
		xCoord = x;
		yCoord = y;
		zCoord = z;
		return this;
	}

	public Location set(Location location)
	{
		return set(location.xCoord, location.yCoord, location.zCoord);
	}

	public int getX()
	{
		return xCoord;
	}

	public int getY()
	{
		return yCoord;
	}

	public int getZ()
	{
		return zCoord;
	}

	public float norm()
	{
		return (float) Math.sqrt(xCoord * xCoord + yCoord * yCoord + zCoord * zCoord);
	}

	public VectorLocation normalize()
	{
		float n = norm();
		return new VectorLocation(xCoord / n, yCoord / n, zCoord / n);
	}

	public Location OffsetFace(Face direction)
	{
		return new Location(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
	}

	public float getDistance(float x2, float y2, float z2)
	{
		double d3 = xCoord - x2;
		double d4 = yCoord - y2;
		double d5 = zCoord - z2;
		return MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
	}

	public float getDistance(double x2, double y2, double z2)
	{
		double d3 = xCoord - x2;
		double d4 = yCoord - y2;
		double d5 = zCoord - z2;
		return MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
	}

	public float getDistance(VectorLocation vector)
	{
		double d3 = xCoord - vector.x;
		double d4 = yCoord - vector.y;
		double d5 = zCoord - vector.z;
		return MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
	}

	public float getDistance(Location vector)
	{
		double d3 = xCoord - vector.xCoord;
		double d4 = yCoord - vector.yCoord;
		double d5 = zCoord - vector.zCoord;
		return MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
	}

	public double getAverage()
	{
		return (xCoord + yCoord + zCoord) / 3f;
	}

	public TileEntity getTile(IBlockAccess world)
	{
		return world.getTileEntity(xCoord, yCoord, zCoord);
	}

	public int getMetadata(IBlockAccess world)
	{
		return world.getBlockMetadata(xCoord, yCoord, zCoord);
	}

	public void setMetadata(World world, int meta)
	{
		world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta, 3);
	}

	public float getHardness(World world)
	{
		return getBlock(world).getBlockHardness(world, xCoord, yCoord, zCoord);
	}

	public Block getBlock(IBlockAccess world)
	{
		return world.getBlock(xCoord, yCoord, zCoord);
	}

	public void setBlock(World world, Block block)
	{
		world.setBlock(xCoord, yCoord, zCoord, block);
	}

	public void setBlockNonUpdate(World world, Block block)
	{
		world.setBlock(xCoord, yCoord, zCoord, block, 0, 2);
	}

	public void setBlockAir(World world)
	{
		world.setBlockToAir(xCoord, yCoord, zCoord);
	}

	public void setBlockAirNonUpdate(World world)
	{
		world.setBlock(xCoord, yCoord, zCoord, Blocks.air, 0, 2);
	}

	public void setTile(World world, TileEntity tile)
	{
		world.setTileEntity(xCoord, yCoord, zCoord, tile);
	}

	public boolean isAirBlock(IBlockAccess world)
	{
		return world.isAirBlock(xCoord, yCoord, zCoord);
	}

	public Location sub(Location b)
	{
		xCoord -= b.xCoord;
		yCoord -= b.yCoord;
		zCoord -= b.zCoord;
		return this;
	}

	public Location sub(int sub)
	{
		xCoord -= sub;
		yCoord -= sub;
		zCoord -= sub;
		return this;
	}

	public Location add(Location b)
	{
		xCoord += b.xCoord;
		yCoord += b.yCoord;
		zCoord += b.zCoord;
		return this;
	}

	public Location add(int add)
	{
		xCoord += add;
		yCoord += add;
		zCoord += add;
		return this;
	}

	public Location mul(Location b)
	{
		xCoord *= b.xCoord;
		yCoord *= b.yCoord;
		zCoord *= b.zCoord;
		return this;
	}

	public Location mul(float mul)
	{
		xCoord *= mul;
		yCoord *= mul;
		zCoord *= mul;
		return this;
	}

	public Location abs()
	{
		xCoord = Math.abs(xCoord);
		yCoord = Math.abs(yCoord);
		zCoord = Math.abs(zCoord);
		return this;
	}

	public Location Copy()
	{
		return new Location(xCoord, yCoord, zCoord);
	}

	public VectorLocation Vector()
	{
		return new VectorLocation(xCoord, yCoord, zCoord);
	}

	public static Location Sub(Location a, Location b)
	{
		return new Location(a.xCoord - b.xCoord, a.yCoord - b.yCoord, a.zCoord - b.zCoord);
	}

	public static Location Add(Location a, Location b)
	{
		return new Location(a.xCoord + b.xCoord, a.yCoord + b.yCoord, a.zCoord + b.zCoord);
	}

	public static Location Mul(Location a, int f)
	{
		return new Location(a.xCoord * f, a.yCoord * f, a.zCoord * f);
	}

	public static Location Abs(Location a)
	{
		return new Location(Math.abs(a.xCoord), Math.abs(a.yCoord), Math.abs(a.zCoord));
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
		Location other = (Location) obj;
		return xCoord == other.xCoord && yCoord == other.yCoord && zCoord == other.zCoord;
	}

	@Override
	public int hashCode()
	{
		return 255 - yCoord;
	}

	public void writeToNBT(NBTTagCompound tag, String vectorName)
	{
		tag.setInteger(vectorName + "-X", xCoord);
		tag.setInteger(vectorName + "-Y", yCoord);
		tag.setInteger(vectorName + "-Z", zCoord);
	}

	public void readFromNBT(NBTTagCompound tag, String vectorName)
	{
		xCoord = tag.getInteger(vectorName + "-X");
		yCoord = tag.getInteger(vectorName + "-Y");
		zCoord = tag.getInteger(vectorName + "-Z");
	}

	@Override
	public String toString()
	{
		return "BlockLocation [" + xCoord + ", " + yCoord + ", " + zCoord + "]";
	}

}
