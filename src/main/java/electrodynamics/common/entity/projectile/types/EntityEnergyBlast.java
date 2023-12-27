package electrodynamics.common.entity.projectile.types;

import electrodynamics.common.damage.DamageSources;
import electrodynamics.common.entity.projectile.EntityCustomProjectile;
import electrodynamics.registers.ElectrodynamicsEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;

public class EntityEnergyBlast extends EntityCustomProjectile {

	public EntityEnergyBlast(EntityType<? extends ProjectileItemEntity> type, World world) {
		super(type, world);
	}

	public EntityEnergyBlast(LivingEntity entity, World world) {
		super(ElectrodynamicsEntities.ENTITY_ENERGYBLAST.get(), entity, world);
	}

	public EntityEnergyBlast(double x, double y, double z, World worldIn) {
		super(ElectrodynamicsEntities.ENTITY_ENERGYBLAST.get(), x, y, z, worldIn);
	}

	@Override
	protected void onHitBlock(BlockRayTraceResult hit) {
		BlockState state = level.getBlockState(hit.getBlockPos());
		if (!state.isAir(level, hit.getBlockPos())) {
			if (!level.isClientSide) {
				level.explode(null, hit.getBlockPos().getX(), hit.getBlockPos().getY(), hit.getBlockPos().getZ(), 4f / (tickCount / 40.0f + 1), true, Mode.DESTROY);
			}
			remove();
		}
		if (tickCount > 100) {
			remove();

		}
	}

	@Override
	public void tick() {
		super.tick();
		if (isInWater() || isInLava()) {
			remove();
		}
	}

	@Override
	public void onHitEntity(EntityRayTraceResult hit) {
		hit.getEntity().hurt(DamageSources.PLASMA_BOLT, 40F / (tickCount / 40.0f + 1));
		super.onHitEntity(hit);
	}

}