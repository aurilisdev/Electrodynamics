package electrodynamics.prefab.utilities.math;

import net.minecraft.world.phys.AABB;

/**
 * Useful wrapper class for automatically splitting doubles into whole and remainder components
 * 
 * @author skip999
 *
 */
public class PrecisionVector {

	public final int x;
	public final int y;
	public final int z;

	public final double remX;
	public final double remY;
	public final double remZ;

	public PrecisionVector(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		remX = 0;
		remY = 0;
		remZ = 0;
	}

	public PrecisionVector(double x, double y, double z) {
		this.x = (int) x;
		this.y = (int) y;
		this.z = (int) z;
		remX = x - this.x;
		remY = y - this.y;
		remZ = z - this.z;
	}

	public AABB shiftWhole(AABB source) {
		return source.move(x, y, z);
	}

	public AABB shiftRemainder(AABB source) {
		return source.move(remX, remY, remZ);
	}

	public double totX() {
		return x + remX;
	}

	public double totY() {
		return y + remY;
	}

	public double totZ() {
		return z + remZ;
	}

}
