package electrodynamics.common.entity.projectile.types;

import electrodynamics.common.entity.projectile.EntityCustomProjectile;
import electrodynamics.registers.ElectrodynamicsDamageTypes;
import electrodynamics.registers.ElectrodynamicsEntities;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class EntityEnergyBlast extends EntityCustomProjectile {

	public EntityEnergyBlast(EntityType<? extends ThrowableItemProjectile> type, Level world) {
		super(type, world);
	}

	public EntityEnergyBlast(LivingEntity entity, Level world) {
		super(ElectrodynamicsEntities.ENTITY_ENERGYBLAST.get(), entity, world);
	}

	public EntityEnergyBlast(double x, double y, double z, Level worldIn) {
		super(ElectrodynamicsEntities.ENTITY_ENERGYBLAST.get(), x, y, z, worldIn);
	}

	@Override
	protected void onHitBlock(BlockHitResult hit) {
		BlockState state = level().getBlockState(hit.getBlockPos());
		if (!state.isAir()) {
			if (!level().isClientSide) {
				level().explode(null, hit.getBlockPos().getX(), hit.getBlockPos().getY(), hit.getBlockPos().getZ(), 4f / (tickCount / 40.0f + 1), true, ExplosionInteraction.BLOCK);
			}
			remove(Entity.RemovalReason.DISCARDED);
			level().playLocalSound(hit.getBlockPos().getX(), hit.getBlockPos().getY(), hit.getBlockPos().getZ(), ElectrodynamicsSounds.SOUND_RAILGUNPLASMA_HIT.get(), SoundSource.AMBIENT, 1.0F, 1.0F, false);
		}
		if (tickCount > 100) {
			remove(Entity.RemovalReason.DISCARDED);

		}
	}

	@Override
	public void tick() {
		super.tick();
		if (isInWater() || isInLava()) {
			remove(Entity.RemovalReason.DISCARDED);
		}
	}

	@Override
	public void onHitEntity(EntityHitResult hit) {
		Entity entity = hit.getEntity();
		hit.getEntity().hurt(entity.damageSources().source(ElectrodynamicsDamageTypes.PLASMA_BOLT, this, entity), 40F / (tickCount / 40.0f + 1));
		level().playLocalSound(hit.getLocation().x(), hit.getLocation().y(), hit.getLocation().z(), ElectrodynamicsSounds.SOUND_RAILGUNPLASMA_HIT.get(), SoundSource.AMBIENT, 1.0F, 1.0F, false);
		super.onHitEntity(hit);
	}

}
