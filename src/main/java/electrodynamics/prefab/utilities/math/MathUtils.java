package electrodynamics.prefab.utilities.math;

import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class MathUtils {
	public static Location getRaytracedBlock(Entity entity) {
		return getRaytracedBlock(entity, 100);
	}

	public static Location getRaytracedBlock(Entity entity, double rayLength) {
		return getRaytracedBlock(entity.level, entity.getLookAngle(), entity.getEyePosition(0), rayLength);
	}

	public static Location getRaytracedBlock(World world, Vector3d direction, Vector3d from, double rayLength) {
		// Just normalize for safety. Allows the direction
		// vector to be from some block to another with no
		// consideration for more math.
		Vector3d rayPath = direction.normalize().scale(rayLength);
		Vector3d to = from.add(rayPath);
		RayTraceContext rayContext = new RayTraceContext(from, to, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, null);
		BlockRayTraceResult rayHit = world.clip(rayContext);

		return rayHit.getType() != Type.BLOCK ? null : new Location(rayHit.getBlockPos());
	}

	public static AxisAlignedBB ofSize(Vector3d pCenter, double pXSize, double pYSize, double pZSize) {
		return new AxisAlignedBB(pCenter.x - pXSize / 2.0D, pCenter.y - pYSize / 2.0D, pCenter.z - pZSize / 2.0D, pCenter.x + pXSize / 2.0D, pCenter.y + pYSize / 2.0D, pCenter.z + pZSize / 2.0D);
	}

}
