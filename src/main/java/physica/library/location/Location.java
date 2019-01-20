package physica.library.location;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Location {

	public double x;
	public double y;
	public double z;

	public Location() {
		this(0, 0, 0);
	}

	public Location(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Location(Entity entity) {
		x = entity.posX;
		y = entity.posY;
		z = entity.posZ;
	}

	public Location(TileEntity tile) {
		x = tile.xCoord;
		y = tile.yCoord;
		z = tile.zCoord;
	}

	public void set(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int floorX() {
		return (int) Math.floor(x);
	}

	public int floorY() {
		return (int) Math.floor(y);
	}

	public int floorZ() {
		return (int) Math.floor(z);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public float norm() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	public Location normalize() {
		float n = norm();
		return new Location(x / n, y / n, z / n);
	}

	public float getDistance(float x2, float y2, float z2) {
		double d3 = x - x2;
		double d4 = y - y2;
		double d5 = z - z2;
		return MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
	}

	public float getDistance(Location vector) {
		double d3 = x - vector.x;
		double d4 = y - vector.y;
		double d5 = z - vector.z;
		return MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
	}

	public float getDistance(BlockLocation blockLocation) {
		double d3 = x - blockLocation.x;
		double d4 = y - blockLocation.y;
		double d5 = z - blockLocation.z;
		return MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
	}

	public double getAverage() {
		return (x + y + z) / 3f;
	}

	public TileEntity getTile(IBlockAccess world) {
		return world.getTileEntity(floorX(), floorY(), floorZ());
	}

	public int getMetadata(IBlockAccess world) {
		return world.getBlockMetadata(floorX(), floorY(), floorZ());
	}

	public void setMetadata(World world, int meta) {
		world.setBlockMetadataWithNotify(floorX(), floorY(), floorZ(), meta, 3);
	}

	public float getHardness(World world) {
		return getBlock(world).getBlockHardness(world, floorX(), floorY(), floorZ());
	}

	public Block getBlock(IBlockAccess world) {
		return world.getBlock(floorX(), floorY(), floorZ());
	}

	public void setBlock(World world, Block block) {
		world.setBlock(floorX(), floorY(), floorZ(), block);
	}

	public void setBlockAir(World world) {
		world.setBlockToAir(floorX(), floorY(), floorZ());
	}

	public void setTile(World world, TileEntity tile) {
		world.setTileEntity(floorX(), floorY(), floorZ(), tile);
	}

	public boolean isAirBlock(IBlockAccess world) {
		return world.isAirBlock(floorX(), floorY(), floorZ());
	}

	public Location cross(Location b) {
		x = y * b.z - z * b.y;
		y = z * b.x - x * b.z;
		z = x * b.y - y * b.x;
		return this;
	}

	public Location sub(Location b) {
		x -= b.x;
		y -= b.y;
		z -= b.z;
		return this;
	}

	public Location sub(int sub) {
		x -= sub;
		y -= sub;
		z -= sub;
		return this;
	}

	public Location add(Location b) {
		x += b.x;
		y += b.y;
		z += b.z;
		return this;
	}

	public Location add(int add) {
		x += add;
		y += add;
		z += add;
		return this;
	}

	public Location mul(Location b) {
		x *= b.x;
		y *= b.y;
		z *= b.z;
		return this;
	}

	public Location mul(float mul) {
		x *= mul;
		y *= mul;
		z *= mul;
		return this;
	}

	public Location abs() {
		x = Math.abs(x);
		y = Math.abs(y);
		z = Math.abs(z);
		return this;
	}

	public Location floor() {
		x = Math.floor(x);
		y = Math.floor(y);
		z = Math.floor(z);
		return this;
	}

	public Location Copy() {
		return new Location(x, y, z);
	}

	public BlockLocation BlockLocation() {
		return new BlockLocation(floorX(), floorY(), floorZ());
	}

	public static Location Cross(Location a, Location b) {
		double x = a.y * b.z - a.z * b.y;
		double y = a.z * b.x - a.x * b.z;
		double z = a.x * b.y - a.y * b.x;
		return new Location(x, y, z);
	}

	public static Location Sub(Location a, Location b) {
		return new Location(a.x - b.x, a.y - b.y, a.z - b.z);
	}

	public static Location Add(Location a, Location b) {
		return new Location(a.x + b.x, a.y + b.y, a.z + b.z);
	}

	public static Location Mul(Location a, float f) {
		return new Location(a.x * f, a.y * f, a.z * f);
	}

	public static Location Abs(Location a) {
		return new Location(Math.abs(a.x), Math.abs(a.y), Math.abs(a.z));
	}

	public static Location Floor(Location a) {
		return new Location(Math.floor(a.x), Math.floor(a.y), Math.floor(a.z));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Location other = (Location) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
			return false;
		}
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
			return false;
		}
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z)) {
			return false;
		}
		return true;
	}

	public void writeToNBT(NBTTagCompound tag, String vectorName) {
		tag.setDouble(vectorName + "-X", x);
		tag.setDouble(vectorName + "-Y", y);
		tag.setDouble(vectorName + "-Z", z);
	}

	public void readFromNBT(NBTTagCompound tag, String vectorName) {
		x = tag.getDouble(vectorName + "-X");
		y = tag.getDouble(vectorName + "-Y");
		z = tag.getDouble(vectorName + "-Z");
	}

}
