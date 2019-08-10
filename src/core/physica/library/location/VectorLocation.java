package physica.library.location;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class VectorLocation {

	public double	x;
	public double	y;
	public double	z;

	public VectorLocation() {
		this(0, 0, 0);
	}

	public VectorLocation(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public VectorLocation(Entity entity) {
		x = entity.posX;
		y = entity.posY;
		z = entity.posZ;
	}

	public VectorLocation(TileEntity tile) {
		x = tile.xCoord;
		y = tile.yCoord;
		z = tile.zCoord;
	}

	public void set(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int floorX()
	{
		return (int) Math.floor(x);
	}

	public int floorY()
	{
		return (int) Math.floor(y);
	}

	public int floorZ()
	{
		return (int) Math.floor(z);
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public double getZ()
	{
		return z;
	}

	public float norm()
	{
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	public void translate(float x, float y, float z)
	{
		this.x += x;
		this.y += y;
		this.z += z;
	}

	public VectorLocation normalize()
	{
		float n = norm();
		return div(n);
	}

	public VectorLocation createNormalized()
	{
		float n = norm();
		return new VectorLocation(x / n, y / n, z / n);
	}

	public float getDistance(float x2, float y2, float z2)
	{
		double d3 = x - x2;
		double d4 = y - y2;
		double d5 = z - z2;
		return MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
	}

	public float getDistance(VectorLocation vector)
	{
		double d3 = x - vector.x;
		double d4 = y - vector.y;
		double d5 = z - vector.z;
		return MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
	}

	public float getDistance(Location blockLocation)
	{
		double d3 = x - blockLocation.xCoord;
		double d4 = y - blockLocation.yCoord;
		double d5 = z - blockLocation.zCoord;
		return MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
	}

	public double getAverage()
	{
		return (x + y + z) / 3f;
	}

	public TileEntity getTile(IBlockAccess world)
	{
		return world.getTileEntity(floorX(), floorY(), floorZ());
	}

	public int getMetadata(IBlockAccess world)
	{
		return world.getBlockMetadata(floorX(), floorY(), floorZ());
	}

	public void setMetadata(World world, int meta)
	{
		world.setBlockMetadataWithNotify(floorX(), floorY(), floorZ(), meta, 3);
	}

	public float getHardness(World world)
	{
		return getBlock(world).getBlockHardness(world, floorX(), floorY(), floorZ());
	}

	public Block getBlock(IBlockAccess world)
	{
		return world.getBlock(floorX(), floorY(), floorZ());
	}

	public void setBlock(World world, Block block)
	{
		world.setBlock(floorX(), floorY(), floorZ(), block);
	}

	public void setBlockAir(World world)
	{
		world.setBlockToAir(floorX(), floorY(), floorZ());
	}

	public void setTile(World world, TileEntity tile)
	{
		world.setTileEntity(floorX(), floorY(), floorZ(), tile);
	}

	public boolean isAirBlock(IBlockAccess world)
	{
		return world.isAirBlock(floorX(), floorY(), floorZ());
	}

	public VectorLocation cross(VectorLocation b)
	{
		x = y * b.z - z * b.y;
		y = z * b.x - x * b.z;
		z = x * b.y - y * b.x;
		return this;
	}

	public VectorLocation sub(VectorLocation b)
	{
		x -= b.x;
		y -= b.y;
		z -= b.z;
		return this;
	}

	public VectorLocation sub(int sub)
	{
		x -= sub;
		y -= sub;
		z -= sub;
		return this;
	}

	public VectorLocation add(VectorLocation b)
	{
		x += b.x;
		y += b.y;
		z += b.z;
		return this;
	}

	public VectorLocation add(int add)
	{
		x += add;
		y += add;
		z += add;
		return this;
	}

	public VectorLocation add(double add)
	{
		x += add;
		y += add;
		z += add;
		return this;
	}

	public VectorLocation mul(VectorLocation b)
	{
		x *= b.x;
		y *= b.y;
		z *= b.z;
		return this;
	}

	public VectorLocation mul(float mul)
	{
		x *= mul;
		y *= mul;
		z *= mul;
		return this;
	}

	public VectorLocation div(VectorLocation b)
	{
		x /= b.x;
		y /= b.y;
		z /= b.z;
		return this;
	}

	public VectorLocation div(float div)
	{
		x /= div;
		y /= div;
		z /= div;
		return this;
	}

	public VectorLocation abs()
	{
		x = Math.abs(x);
		y = Math.abs(y);
		z = Math.abs(z);
		return this;
	}

	public VectorLocation floor()
	{
		x = Math.floor(x);
		y = Math.floor(y);
		z = Math.floor(z);
		return this;
	}

	public VectorLocation Copy()
	{
		return new VectorLocation(x, y, z);
	}

	public Location BlockLocation()
	{
		return new Location(floorX(), floorY(), floorZ());
	}

	public static VectorLocation Cross(VectorLocation a, VectorLocation b)
	{
		double x = a.y * b.z - a.z * b.y;
		double y = a.z * b.x - a.x * b.z;
		double z = a.x * b.y - a.y * b.x;
		return new VectorLocation(x, y, z);
	}

	public static VectorLocation Sub(VectorLocation a, VectorLocation b)
	{
		return new VectorLocation(a.x - b.x, a.y - b.y, a.z - b.z);
	}

	public static VectorLocation Add(VectorLocation a, VectorLocation b)
	{
		return new VectorLocation(a.x + b.x, a.y + b.y, a.z + b.z);
	}

	public static VectorLocation Mul(VectorLocation a, float f)
	{
		return new VectorLocation(a.x * f, a.y * f, a.z * f);
	}

	public static VectorLocation Abs(VectorLocation a)
	{
		return new VectorLocation(Math.abs(a.x), Math.abs(a.y), Math.abs(a.z));
	}

	public static VectorLocation Floor(VectorLocation a)
	{
		return new VectorLocation(Math.floor(a.x), Math.floor(a.y), Math.floor(a.z));
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		VectorLocation other = (VectorLocation) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
		{
			return false;
		}
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
		{
			return false;
		}
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
		{
			return false;
		}
		return true;
	}

	public void writeToNBT(NBTTagCompound tag, String vectorName)
	{
		tag.setDouble(vectorName + "-X", x);
		tag.setDouble(vectorName + "-Y", y);
		tag.setDouble(vectorName + "-Z", z);
	}

	public void readFromNBT(NBTTagCompound tag, String vectorName)
	{
		x = tag.getDouble(vectorName + "-X");
		y = tag.getDouble(vectorName + "-Y");
		z = tag.getDouble(vectorName + "-Z");
	}

}
