package physica.missiles.common.explosive.blast;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import physica.library.location.GridLocation;
import physica.library.location.VectorLocation;

public class BlastTemplate {
	public final int	fuseTime;
	public final int	tier;
	public final int	callCount;

	public BlastTemplate(int fuseTime, int tier, int callCount) {
		this.fuseTime = fuseTime;
		this.tier = tier;
		this.callCount = callCount;
	}

	public void prepare(World world, GridLocation loc, IStateHolder holder)
	{
	}

	public void call(World world, GridLocation loc, int callCount, IStateHolder holder)
	{
	}

	public void end(World world, GridLocation loc, IStateHolder holder)
	{
	}

	public void pushEntities(World world, GridLocation loc, float radius, float force, boolean drawTowards)
	{
		VectorLocation vec = loc.Vector().add(0.5);
		VectorLocation minCoord = vec.Copy();
		minCoord.translate(-radius - 1.0F, -radius - 1.0F, -radius - 1.0F);
		VectorLocation maxCoord = vec.Copy();
		maxCoord.translate(radius + 1.0F, radius + 1.0F, radius + 1.0F);
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(minCoord.x, minCoord.y, minCoord.z, maxCoord.x, maxCoord.y, maxCoord.z);
		List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, box);
		for (Entity entity : entities)
		{
			double var13 = entity.getDistance(vec.x, vec.y, vec.z) / radius;

			if (var13 <= 1.0D)
			{
				double xDifference = entity.posX - vec.x;
				double yDifference = entity.posY - vec.y;
				double zDifference = entity.posZ - vec.z;
				double distance = MathHelper.sqrt_double(xDifference * xDifference + yDifference * yDifference + zDifference * zDifference);
				xDifference /= distance;
				yDifference /= distance;
				zDifference /= distance;

				if (drawTowards)
				{
					double modifier = var13 * force * (entity instanceof EntityPlayer ? 0.5 : 1);
					entity.addVelocity(-xDifference * modifier, -yDifference * modifier, -zDifference * modifier);
				} else
				{
					double modifier = (1.0D - var13) * force * (entity instanceof EntityPlayer ? 0.5 : 1);
					entity.addVelocity(xDifference * modifier, yDifference * modifier, zDifference * modifier);
				}
			}
		}
	}

	public int incrementEvery()
	{
		return 1;
	}
}
