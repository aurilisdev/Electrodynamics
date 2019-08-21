package physica.core.common.event;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import defense.api.ExplosionEvent.PostExplosionEvent;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.event.world.ExplosionEvent;
import physica.CoreReferences;
import physica.core.common.CoreBlockRegister;
import physica.core.common.tile.TileFulmination;
import physica.library.location.Location;
import physica.nuclear.common.NuclearItemRegister;
import physica.nuclear.common.items.update.ItemUpdateAntimatter;

public class FulminationEventHandler {

	public static final FulminationEventHandler	INSTANCE	= new FulminationEventHandler();
	private static final Set<TileFulmination>	set			= new HashSet<>();

	public void register(TileFulmination tile)
	{
		if (!set.contains(tile))
		{
			set.add(tile);
		}
	}

	public boolean isRegistered(TileFulmination tile)
	{
		return set.contains(tile);
	}

	public void unregister(TileFulmination tile)
	{
		set.remove(tile);
	}

	@SubscribeEvent
	public void onPreExplosion(PostExplosionEvent event)
	{
		if (event.explosion != null)
		{
			float size = event.explosion.explosionSize;
			onExplosionImpl(event.iExplosion.getEnergy(), size, event.world, event.x, event.y, event.z);
		}
	}

	@SubscribeEvent
	public void onExplosionEvent(resonant.api.explosion.ExplosionEvent event)
	{
		if (event.explosion != null)
		{
			float size = event.explosion.explosionSize;
			onExplosionImpl(event.iExplosion.getEnergy(), size, event.world, event.x, event.y, event.z);
		}
	}

	private static void onExplosionImpl(long energy, float size, World world, double x, double y, double z)
	{
		energy *= 5;
		if (size > 0 && energy > 0)
		{
			Iterator<TileFulmination> iterator = set.iterator();
			while (iterator.hasNext())
			{
				TileFulmination tile = iterator.next();
				if (tile.isInvalid())
				{
					iterator.remove();
				} else if (!tile.isInvalid() && world == tile.getWorldObj())
				{
					double distance = tile.getDistanceFrom(x, y, z);
					if (distance <= size && distance > 0)
					{
						double electricity = Math.min(energy, energy / (distance / size));
						Location loc = tile.getLocation();
						electricity = Math
								.max(electricity - world.getBlockDensity(Vec3.createVectorHelper(x, y, z), CoreBlockRegister.blockFulmination.getCollisionBoundingBoxFromPool(world, loc.xCoord, loc.yCoord, loc.zCoord)) * electricity, 0.0D);
						tile.setElectricityStored((int) (tile.getElectricityStored() + electricity));
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onExplosion(ExplosionEvent.Detonate event)
	{
		double energy = event.explosion.explosionSize * 50;
		if (Loader.isModLoaded(CoreReferences.DOMAIN + "nuclearphysics"))
		{
			if (event.explosion.exploder instanceof EntityItem && ((EntityItem) event.explosion.exploder).getEntityItem().getItem() == NuclearItemRegister.itemAntimatterCell1Gram)
			{
				energy *= ItemUpdateAntimatter.FULMINATION_ANTIMATTER_ENERGY_SCALE;
			}
		}
		if (event.explosion != null)
		{
			onExplosionImpl((long) energy, event.explosion.explosionSize, event.world, event.explosion.explosionX, event.explosion.explosionY, event.explosion.explosionZ);
		}
	}

}
