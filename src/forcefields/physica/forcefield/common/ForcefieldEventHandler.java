package physica.forcefield.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import defense.api.ExplosionEvent.PostExplosionEvent;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.ExplosionEvent;
import physica.forcefield.common.item.Permission;
import physica.forcefield.common.tile.TileFortronField;
import physica.forcefield.common.tile.TileFortronFieldConstructor;
import physica.forcefield.common.tile.TileInterdictionMatrix;
import physica.library.location.GridLocation;

public class ForcefieldEventHandler {

	public static final ForcefieldEventHandler				INSTANCE				= new ForcefieldEventHandler();
	private static final Set<TileInterdictionMatrix>		set						= new HashSet<>();
	private static final Set<TileFortronFieldConstructor>	forceFieldConstructors	= new HashSet<>();

	public void registerConstructor(TileFortronFieldConstructor tile)
	{
		forceFieldConstructors.add(tile);
	}

	public void unregisterConstructor(TileFortronFieldConstructor tile)
	{
		forceFieldConstructors.remove(tile);
	}

	public boolean isConstructorRegistered(TileFortronFieldConstructor tile)
	{
		return forceFieldConstructors.contains(tile);
	}

	public void registerMatrix(TileInterdictionMatrix tile)
	{
		if (!set.contains(tile))
		{
			set.add(tile);
		}
	}

	public boolean isMatrixRegistered(TileInterdictionMatrix tile)
	{
		return set.contains(tile);
	}

	public void unregisterMatrix(TileInterdictionMatrix tile)
	{
		set.remove(tile);
	}

	public ArrayList<TileFortronFieldConstructor> getRelevantConstructors(World world, double x, double y, double z)
	{
		forceFieldConstructors.removeIf(TileFortronFieldConstructor::isInvalid);

		ArrayList<TileFortronFieldConstructor> list = new ArrayList<>();
		for (TileFortronFieldConstructor constructor : forceFieldConstructors) {
			if (constructor.getWorldObj().equals(world)) {
				GridLocation loc = constructor.getLocation();
				double distSquared = Math.pow(loc.xCoord - x, 2) + Math.pow(loc.yCoord - y, 2) + Math.pow(loc.zCoord - z, 2);
				if (distSquared < 400 * 400) {
					list.add(constructor);
				}
			}
		}
		return list;
	}

	@SubscribeEvent
	public void interactEvent(PlayerInteractEvent evt)
	{
		if (evt.action == Action.RIGHT_CLICK_BLOCK || evt.action == Action.LEFT_CLICK_BLOCK)
		{
			TileEntity tile = evt.world.getTileEntity(evt.x, evt.y, evt.z);
			if (tile instanceof TileFortronFieldConstructor)
			{
				TileFortronFieldConstructor constructor = (TileFortronFieldConstructor) tile;
				if (constructor.isCalculating)
				{
					evt.setCanceled(true);
					evt.setResult(Result.DENY);
					return;
				}
			}
			if (evt.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK && evt.world.getBlock(evt.x, evt.y, evt.z) == ForcefieldBlockRegister.blockFortronField)
			{
				if (tile instanceof TileFortronField)
				{
					TileFortronField field = (TileFortronField) tile;
					if (field.isForcefieldActive())
					{
						evt.setCanceled(true);
						evt.setResult(Result.DENY);
						evt.world.markBlockForUpdate(evt.x, evt.y, evt.z);
						return;
					}
				}
			}
			if (evt.entityPlayer.capabilities.isCreativeMode)
			{
				return;
			}
			Iterator<TileInterdictionMatrix> iterator = set.iterator();
			while (iterator.hasNext())
			{
				TileInterdictionMatrix matrix = iterator.next();
				if (matrix.isInvalid())
				{
					iterator.remove();
				} else if (matrix.isActivated())
				{
					if (matrix.getActiveBB().isVecInside(Vec3.createVectorHelper(evt.x, evt.y, evt.z)))
					{
						if (evt.action == Action.RIGHT_CLICK_BLOCK && matrix.hasModule("moduleUpgradeBlockAccess"))
						{
							if (!matrix.isPlayerValidated(evt.entityPlayer, Permission.BLOCK_ACCESS))
							{
								evt.entityPlayer.addChatMessage(new ChatComponentText("You have no permission to do that!"));
								evt.setResult(Result.DENY);
								evt.setCanceled(true);
								return;
							}
						} else if (evt.action == Action.LEFT_CLICK_BLOCK && matrix.hasModule("moduleUpgradeBlockAlter"))
						{
							if (!matrix.isPlayerValidated(evt.entityPlayer, Permission.BLOCK_ALTER))
							{
								evt.entityPlayer.addChatMessage(new ChatComponentText("You have no permission to do that!"));
								evt.setCanceled(true);
								evt.setResult(Result.DENY);
								return;
							}
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void livingSpawnEvent(LivingSpawnEvent evt)
	{
		for (TileInterdictionMatrix matrix : set)
		{
			if (matrix.isActivated())
			{
				if (matrix.getActiveBB().isVecInside(Vec3.createVectorHelper(evt.x, evt.y, evt.z)))
				{
					if (matrix.hasModule("moduleUpgradeAntiSpawn"))
					{
						if (evt.isCancelable())
						{
							evt.setCanceled(true);
						}
						evt.setResult(Result.DENY);
						return;
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onDefenseExplosion(PostExplosionEvent event)
	{
		if (event.explosion != null)
		{
			onExplosionImpl(event.iExplosion.getEnergy(), event.explosion.explosionSize, event.world, event.x, event.y, event.z);
		}
	}

	@SubscribeEvent
	public void onResonantExplosion(resonant.api.explosion.ExplosionEvent.PostExplosionEvent event)
	{
		if (event.explosion != null)
		{
			onExplosionImpl(event.iExplosion.getEnergy(), event.explosion.explosionSize, event.world, event.x, event.y, event.z);
		}
	}

	@SubscribeEvent
	public void onExplosion(ExplosionEvent.Detonate event)
	{
		if (event.explosion != null)
		{
			onExplosionImpl((long) (event.explosion.explosionSize * 50), event.explosion.explosionSize, event.world, event.explosion.explosionX, event.explosion.explosionY, event.explosion.explosionZ);
		}
	}

	private static void onExplosionImpl(long energy, float size, World world, double x, double y, double z)
	{
		if (size > 0 && energy > 0)
		{
			for (TileFortronFieldConstructor tile : forceFieldConstructors)
			{
				if (tile != null && !tile.isInvalid() && world == tile.getWorldObj())
				{
					double distance = tile.getDistanceFrom(x, y, z);
					double electricity = Math.min(energy, energy / (distance / size));
					tile.receiveExplosionEnergy((int) electricity);
				}
			}
		}
	}

}
