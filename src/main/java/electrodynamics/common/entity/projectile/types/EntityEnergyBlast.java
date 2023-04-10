package electrodynamics.common.entity.projectile.types;

import electrodynamics.common.damage.DamageSources;
import electrodynamics.common.entity.projectile.EntityCustomProjectile;
import electrodynamics.registers.ElectrodynamicsEntities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.Level;
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
		BlockState state = level.getBlockState(hit.getBlockPos());
		if (!ItemStack.isSame(new ItemStack(state.getBlock().asItem()), new ItemStack(Items.AIR))) {
			if (!level.isClientSide) {
				level.explode(null, hit.getBlockPos().getX(), hit.getBlockPos().getY(), hit.getBlockPos().getZ(), 4f / (tickCount / 40.0f + 1), true, BlockInteraction.DESTROY);
			}
			remove(Entity.RemovalReason.DISCARDED);
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
		hit.getEntity().hurt(DamageSources.PLASMA_BOLT, 40F / (tickCount / 40.0f + 1));
		super.onHitEntity(hit);
	}

}
