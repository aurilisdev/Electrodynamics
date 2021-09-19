package electrodynamics.common.entity.projectile.types;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.damage.DamageSources;
import electrodynamics.common.entity.projectile.EntityCustomProjectile;
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
	super(DeferredRegisters.ENTITY_ENERGYBLAST.get(), entity, world);
    }

    public EntityEnergyBlast(double x, double y, double z, World worldIn) {
	super(DeferredRegisters.ENTITY_ENERGYBLAST.get(), x, y, z, worldIn);
    }

    @Override
    protected void func_230299_a_(BlockRayTraceResult p_230299_1_) {
	BlockState state = world.getBlockState(p_230299_1_.getPos());
	if (!ItemStack.areItemsEqual(new ItemStack(state.getBlock().asItem()), new ItemStack(Items.AIR))) {
	    if (!world.isRemote) {
		world.createExplosion(null, p_230299_1_.getPos().getX(), p_230299_1_.getPos().getY(), p_230299_1_.getPos().getZ(),
			4f / (ticksExisted / 40.0f + 1), true, Mode.DESTROY);
	    }
	    this.remove();
	}
	if (ticksExisted > 100) {
	    this.remove();
	}
    }

    @Override
    public void tick() {
	super.tick();
	if (isInWater() || isInLava()) {
	    this.remove();
	}
    }

    @Override
    public void onEntityHit(EntityRayTraceResult p_213868_1_) {
	p_213868_1_.getEntity().attackEntityFrom(DamageSources.PLASMA_BOLT, 40F / (ticksExisted / 40.0f + 1));
	super.onEntityHit(p_213868_1_);
    }

}
