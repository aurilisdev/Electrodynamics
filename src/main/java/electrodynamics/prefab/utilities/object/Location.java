package electrodynamics.prefab.utilities.object;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

public final class Location {

	public static final Location ZERO = new Location(0, 0, 0);

	protected double x;
	protected double y;
	protected double z;

	public Location() {
	}

	public Location(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double x() {
		return x;
	}

	public double y() {
		return y;
	}

	public double z() {
		return z;
	}

	public int intX() {
		return (int) Math.floor(x);
	}

	public int intY() {
		return (int) Math.floor(y);
	}

	public int intZ() {
		return (int) Math.floor(z);
	}

	public Location(BlockPos pos) {
		x = pos.getX() + 0.5;
		y = pos.getY() + 0.5;
		z = pos.getZ() + 0.5;
	}

	public Location(Vector3f vec) {
		x = vec.x();
		y = vec.y();
		z = vec.z();
	}

	public Location(Vector3d vec) {
		x = vec.x;
		y = vec.y;
		z = vec.z;
	}

	public Location(Location loc) {
		x = loc.x;
		y = loc.y;
		z = loc.z;
	}

	public Location(Entity entity) {
		x = entity.getX();
		y = entity.getY();
		z = entity.getZ();
	}

	public Location mul(double val) {
		x *= val;
		y *= val;
		z *= val;
		return this;
	}

	public Location mul(double xval, double yval, double zval) {
		x *= xval;
		y *= yval;
		z *= zval;
		return this;
	}

	public Location mul(Location loc) {
		x *= loc.x;
		y *= loc.y;
		z *= loc.z;
		return this;
	}

	public Location set(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Location set(Location loc) {
		x = loc.x;
		y = loc.y;
		z = loc.z;
		return this;
	}

	public Location add(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	public Location add(Location loc) {
		x += loc.x;
		y += loc.y;
		z += loc.z;
		return this;
	}

	public Location normalize() {
		double dis = distance(new Location());
		x /= dis;
		y /= dis;
		z /= dis;
		return this;
	}

	public double distanceSq(Location loc) {
		return Math.pow(x - loc.x, 2) + Math.pow(y - loc.y, 2) + Math.pow(z - loc.z, 2);
	}

	public double distance(Location loc) {
		return Math.sqrt(distanceSq(loc));
	}

	public double distancelinear(Location loc) {
		return Math.abs(x - loc.x) + Math.abs(y - loc.y) + Math.abs(z - loc.z);
	}

	public BlockPos toBlockPos() {
		return new BlockPos(x, y, z);
	}

	public BlockState getBlockState(World reader) {
		return reader.getBlockState(toBlockPos());
	}

	public Block getBlock(World reader) {
		return getBlockState(reader).getBlock();
	}

	public TileEntity getTile(World reader) {
		return reader.getBlockEntity(toBlockPos());
	}

	public Location setBlockState(World world, BlockState state) {
		world.setBlockAndUpdate(toBlockPos(), state);
		return this;
	}

	public Location setBlock(World world, Block block) {
		return setBlockState(world, block.defaultBlockState());
	}

	public Location setAir(World world) {
		return setBlock(world, Blocks.AIR);
	}

	public Location setAirFast(World world) {
		world.setBlock(toBlockPos(), Blocks.AIR.defaultBlockState(), 2 | 16);
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ temp >>> 32);
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ temp >>> 32);
		temp = Double.doubleToLongBits(z);
		return prime * result + (int) (temp ^ temp >>> 32);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Location other = (Location) obj;
		return Double.doubleToLongBits(x) == Double.doubleToLongBits(other.x) && Double.doubleToLongBits(y) == Double.doubleToLongBits(other.y) && Double.doubleToLongBits(z) == Double.doubleToLongBits(other.z);
	}

	@Override
	public String toString() {
		return "[" + intX() + ", " + intY() + ", " + intZ() + "]";
	}

	public static Location readFromNBT(CompoundNBT nbt, String name) {
		return new Location(nbt.getDouble(name + "X"), nbt.getDouble(name + "Y"), nbt.getDouble(name + "Z"));
	}

	public void writeToNBT(CompoundNBT nbt, String name) {
		nbt.putDouble(name + "X", x);
		nbt.putDouble(name + "Y", y);
		nbt.putDouble(name + "Z", z);
	}

	public void toBuffer(PacketBuffer buffer) {
		buffer.writeDouble(x);
		buffer.writeDouble(y);
		buffer.writeDouble(z);
	}

	public static Location fromBuffer(PacketBuffer buffer) {
		return new Location(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
	}
}
