package electrodynamics.prefab.utilities;

import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class UtilitiesMath {
    public static Location getRaytracedBlock(Entity entity) {
	return getRaytracedBlock(entity, 100);
    }

    public static Location getRaytracedBlock(Entity entity, double rayLength) {
	return getRaytracedBlock(entity.world, entity.getLookVec(), entity.getEyePosition(0), rayLength);
    }

    public static Location getRaytracedBlock(World world, Vector3d direction, Vector3d from, double rayLength) {
	// Just normalize for safety. Allows the direction
	// vector to be from some block to another with no
	// consideration for more math.
	Vector3d rayPath = direction.normalize().scale(rayLength);
	Vector3d to = from.add(rayPath);
	RayTraceContext rayContext = new RayTraceContext(from, to, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, null);
	BlockRayTraceResult rayHit = world.rayTraceBlocks(rayContext);

	return rayHit.getType() != Type.BLOCK ? null : new Location(rayHit.getPos());
    }
}
