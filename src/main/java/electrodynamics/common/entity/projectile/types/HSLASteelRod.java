package electrodynamics.common.entity.projectile.types;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.damage.DamageSources;
import electrodynamics.common.entity.EntityRegistry;
import electrodynamics.common.entity.projectile.ElectrodynamicsProjectile;
import electrodynamics.common.item.subtype.SubtypeRod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;

public class HSLASteelRod extends ElectrodynamicsProjectile{

private static final float DAMAGE_DEALT = 2f;
	
	public HSLASteelRod(EntityType<? extends ProjectileItemEntity> type, World world) {
		super(type,world);
	}
	
	public HSLASteelRod(LivingEntity entity, World world) {
		super(EntityRegistry.PROJECTILE_HSLASTEELROD.get(), entity, world);
	}
	
	public HSLASteelRod(double x, double y, double z, World worldIn) {
		super(EntityRegistry.PROJECTILE_HSLASTEELROD.get(),x,y,z,worldIn);
	}

	@Override
	protected Item getDefaultItem() {
		return DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeRod.hslasteel);
	}
	
	@Override
	public void onEntityHit(EntityRayTraceResult p_213868_1_) {
		p_213868_1_.getEntity().attackEntityFrom(DamageSources.ACCELERATED_BOLT_IGNOREARMOR, DAMAGE_DEALT);
	}
}
