package physica.missiles.common.explosive.blast.types;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import physica.library.location.GridLocation;
import physica.library.location.VectorLocation;
import physica.missiles.common.explosive.blast.BlastTemplate;
import physica.missiles.common.explosive.blast.IStateHolder;

public class BlastTemplateFire extends BlastTemplate {
	private float size;

	public BlastTemplateFire(int fuseTime, int tier, int callCount, float size) {
		super(fuseTime, tier, callCount);
		this.size = size;
	}

	@Override
	public void call(World world, GridLocation loc, int callCount, IStateHolder holder)
	{
		VectorLocation vec = loc.Vector().add(0.5);
		if (!world.isRemote)
		{
			int radius = (int) size;
			for (int i = -radius; i <= radius; i++)
			{
				for (int j = -radius; j <= radius; j++)
				{
					for (int k = -radius; k <= radius; k++)
					{
						if (world.rand.nextFloat() < 0.95f)
						{
							if (vec.getDistance(loc.xCoord + i + 0.5f, loc.yCoord + j + 0.5f, loc.zCoord + k + 0.5f) <= radius)
							{
								GridLocation target = new GridLocation(i + loc.xCoord, j + loc.yCoord, k + loc.zCoord);
								if (target.isAirBlock(world) || target.getBlock(world) == Blocks.snow || target.getBlock(world) == Blocks.snow_layer)
								{
									target.setBlock(world, Blocks.fire);
								} else if (target.getBlock(world) == Blocks.ice)
								{
									target.setBlock(world, Blocks.water);
								}
							}
						}
					}

				}
			}
		}

	}

}
