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

public class StainlessSteelRod extends ElectrodynamicsProjectile{

private static final float DAMAGE_DEALT = 10f;
	
	public StainlessSteelRod(EntityType<? extends ProjectileItemEntity> type, World world) {
		super(type,world);
	}
	
	public StainlessSteelRod(LivingEntity entity, World world) {
		super(EntityRegistry.PROJECTILE_STAINLESSSTEELROD.get(), entity, world);
	}
	
	public StainlessSteelRod(double x, double y, double z, World worldIn) {
		super(EntityRegistry.PROJECTILE_STAINLESSSTEELROD.get(),x,y,z,worldIn);
	}

	@Override
	protected Item getDefaultItem() {
		return DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeRod.stainlesssteel);
	}
	
	@Override
	public void onEntityHit(EntityRayTraceResult p_213868_1_) {
		p_213868_1_.getEntity().attackEntityFrom(DamageSources.ACCELERATED_BOLT, DAMAGE_DEALT);
	}
	
}
