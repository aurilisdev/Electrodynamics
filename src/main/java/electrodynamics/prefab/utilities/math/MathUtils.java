package electrodynamics.prefab.utilities.math;

import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.Vec3;

public class MathUtils {
	public static Location getRaytracedBlock(Entity entity) {
		return getRaytracedBlock(entity, 100);
	}

	public static Location getRaytracedBlock(Entity entity, double rayLength) {
		return getRaytracedBlock(entity.level, entity.getLookAngle(), entity.getEyePosition(0), rayLength);
	}

	public static Location getRaytracedBlock(Level world, Vec3 direction, Vec3 from, double rayLength) {
		// Just normalize for safety. Allows the direction
		// vector to be from some block to another with no
		// consideration for more math.
		Vec3 rayPath = direction.normalize().scale(rayLength);
		Vec3 to = from.add(rayPath);
		ClipContext rayContext = new ClipContext(from, to, ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, null);
		BlockHitResult rayHit = world.clip(rayContext);

		return rayHit.getType() != Type.BLOCK ? null : new Location(rayHit.getBlockPos());
	}
}
