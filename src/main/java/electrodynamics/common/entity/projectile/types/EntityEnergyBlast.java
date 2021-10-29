package electrodynamics.common.entity.projectile.types;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.damage.DamageSources;
import electrodynamics.common.entity.projectile.EntityCustomProjectile;
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
	super(DeferredRegisters.ENTITY_ENERGYBLAST.get(), entity, world);
    }

    public EntityEnergyBlast(double x, double y, double z, Level worldIn) {
	super(DeferredRegisters.ENTITY_ENERGYBLAST.get(), x, y, z, worldIn);
    }

    @Override
    protected void onHitBlock(BlockHitResult p_230299_1_) {
	BlockState state = level.getBlockState(p_230299_1_.getBlockPos());
	if (!ItemStack.isSame(new ItemStack(state.getBlock().asItem()), new ItemStack(Items.AIR))) {
	    if (!level.isClientSide) {
		level.explode(null, p_230299_1_.getBlockPos().getX(), p_230299_1_.getBlockPos().getY(), p_230299_1_.getBlockPos().getZ(),
			4f / (tickCount / 40.0f + 1), true, BlockInteraction.DESTROY);
	    }
	    this.remove(Entity.RemovalReason.DISCARDED);
	}
	if (tickCount > 100) {
		this.remove(Entity.RemovalReason.DISCARDED);
	}
    }

    @Override
    public void tick() {
	super.tick();
	if (isInWater() || isInLava()) {
		this.remove(Entity.RemovalReason.DISCARDED);
	}
    }

    @Override
    public void onHitEntity(EntityHitResult p_213868_1_) {
	p_213868_1_.getEntity().hurt(DamageSources.PLASMA_BOLT, 40F / (tickCount / 40.0f + 1));
	super.onHitEntity(p_213868_1_);
    }

}
