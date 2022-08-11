package resonant.api.explosion;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

@Cancelable
public class ExplosionEvent extends Event {

	public World world;
	public double x;
	public double y;
	public double z;
	public IExplosion iExplosion;
	public Explosion explosion;

	public ExplosionEvent(World world, IExplosion iExplosion) {
		this.world = world;
		this.iExplosion = iExplosion;
		x = ((Explosion) iExplosion).explosionX;
		y = ((Explosion) iExplosion).explosionY;
		z = ((Explosion) iExplosion).explosionZ;
		if (this.iExplosion instanceof Explosion) {
			explosion = (Explosion) this.iExplosion;
		}
	}

	public static class ExplosionConstructionEvent extends ExplosionEvent {

		public ExplosionConstructionEvent(World world, IExplosion explosion) {
			super(world, explosion);
		}
	}

	public static class PreExplosionEvent extends ExplosionEvent {

		public PreExplosionEvent(World world, IExplosion explosion) {
			super(world, explosion);
		}
	}

	public static class DoExplosionEvent extends ExplosionEvent {

		public DoExplosionEvent(World world, IExplosion explosion) {
			super(world, explosion);
		}
	}

	public static class PostExplosionEvent extends ExplosionEvent {

		public PostExplosionEvent(World world, IExplosion explosion) {
			super(world, explosion);
		}
	}
}
