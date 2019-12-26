package physica.missiles.common.explosive.blast.types;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import physica.library.location.GridLocation;
import physica.missiles.common.explosive.blast.BlastTemplate;
import physica.missiles.common.explosive.blast.IStateHolder;

public class BlastTemplatePush extends BlastTemplate {
	public final float		size;
	public final boolean	drawTowards;

	public BlastTemplatePush(int fuseTime, int tier, int callCount, float size, boolean drawTowards) {
		super(fuseTime, tier, callCount);
		this.size = size;
		this.drawTowards = drawTowards;
	}

	@Override
	public void prepare(World world, GridLocation loc, IStateHolder holder)
	{
		world.spawnParticle("hugeexplosion", loc.xCoord + 0.5, loc.yCoord + 0.5, loc.zCoord + 0.5, 0.0D, 0.0D, 0.0D);
	}

	@Override
	public void call(World world, GridLocation loc, int callCount, IStateHolder holder)
	{
		if (!world.isRemote)
		{
			world.createExplosion((Entity) holder.getObject("cause"), loc.xCoord + 0.5, loc.yCoord + 0.5, loc.zCoord + 0.5, size, true);
		}
	}

	@Override
	public void end(World world, GridLocation loc, IStateHolder holder)
	{
		pushEntities(world, loc, 12.0f, 8.0f, drawTowards);
	}
}
