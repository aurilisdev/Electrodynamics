package electrodynamics.common.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class EntityCustomProjectile extends ProjectileItemEntity {

    protected EntityCustomProjectile(EntityType<? extends ProjectileItemEntity> type, World world) {
    	super(type, world);
    }

    protected EntityCustomProjectile(EntityType<? extends ProjectileItemEntity> type, LivingEntity entity, World world) {
    	super(type, entity, world);
    }

    protected EntityCustomProjectile(EntityType<? extends ProjectileItemEntity> type, double x, double y, double z, World worldIn) {
    	super(type, x, y, z, worldIn);
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
		this.remove();
    }
    
	@Override
	protected Item getDefaultItem() {
		return Items.COBBLESTONE;
	}

}
