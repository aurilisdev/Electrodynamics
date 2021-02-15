package electrodynamics.api.math;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;

public final class Location {
	public double x;
	public double y;
	public double z;

	public Location() {
	}

	public Location(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Location(BlockPos pos) {
		this.x = pos.getX() + 0.5;
		this.y = pos.getY() + 0.5;
		this.z = pos.getZ() + 0.5;
	}

	public Location(Vector3f vec) {
		this.x = vec.getX();
		this.y = vec.getY();
		this.z = vec.getZ();
	}

	public Location(Location loc) {
		this.x = loc.x;
		this.y = loc.y;
		this.z = loc.z;
	}

	public Location mul(double val) {
		this.x *= val;
		this.y *= val;
		this.z *= val;
		return this;
	}

	public Location mul(double xval, double yval, double zval) {
		this.x *= xval;
		this.y *= yval;
		this.z *= zval;
		return this;
	}

	public Location mul(Location loc) {
		this.x *= loc.x;
		this.y *= loc.y;
		this.z *= loc.z;
		return this;
	}

	public Location set(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Location set(Location loc) {
		this.x = loc.x;
		this.y = loc.y;
		this.z = loc.z;
		return this;
	}

	public Location add(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	public Location add(Location loc) {
		this.x += loc.x;
		this.y += loc.y;
		this.z += loc.z;
		return this;
	}

	public Location normalize(Location loc) {
		double dis = distance(new Location());
		this.x /= dis;
		this.y /= dis;
		this.z /= dis;
		return this;
	}

	public double distanceSq(Location loc) {
		return Math.pow(x - loc.x, 2) + Math.pow(y - loc.y, 2) + Math.pow(z - loc.z, 2);
	}

	public double distance(Location loc) {
		return Math.sqrt(distanceSq(loc));
	}

	public BlockPos toBlockPos() {
		return new BlockPos(x, y, z);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}
}
