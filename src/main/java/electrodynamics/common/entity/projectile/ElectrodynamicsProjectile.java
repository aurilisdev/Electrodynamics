package electrodynamics.common.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class ElectrodynamicsProjectile extends ProjectileItemEntity {

    private Item ITEM;
    private DamageSource DAMAGE_SOURCE;
    private float DAMAGE_AMOUNT;

    public ElectrodynamicsProjectile(EntityType<? extends ProjectileItemEntity> type, World world, Item item, DamageSource damageSource,
	    float damageAmount) {

	super(type, world);

	ITEM = item;
	DAMAGE_SOURCE = damageSource;
	DAMAGE_AMOUNT = damageAmount;
    }

    public ElectrodynamicsProjectile(EntityType<? extends ProjectileItemEntity> type, LivingEntity entity, World world, Item item,
	    DamageSource damageSource, float damageAmount) {

	super(type, entity, world);

	ITEM = item;
	DAMAGE_SOURCE = damageSource;
	DAMAGE_AMOUNT = damageAmount;
    }

    public ElectrodynamicsProjectile(EntityType<? extends ProjectileItemEntity> type, double x, double y, double z, World worldIn, Item item,
	    DamageSource damageSource, float damageAmount) {

	super(type, x, y, z, worldIn);

	ITEM = item;
	DAMAGE_SOURCE = damageSource;
	DAMAGE_AMOUNT = damageAmount;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
	return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void func_230299_a_(BlockRayTraceResult p_230299_1_) {
	this.remove();
    }

    @Override
    public void onEntityHit(EntityRayTraceResult p_213868_1_) {
	p_213868_1_.getEntity().attackEntityFrom(DAMAGE_SOURCE, DAMAGE_AMOUNT);
	this.remove();
    }

    @Override
    protected Item getDefaultItem() {
	return ITEM;
    }

}
