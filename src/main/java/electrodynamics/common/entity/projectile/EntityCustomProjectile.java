package electrodynamics.common.entity.projectile;

import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public abstract class EntityCustomProjectile extends ThrowableItemProjectile {

    protected EntityCustomProjectile(EntityType<? extends ThrowableItemProjectile> type, Level world) {
	super(type, world);
    }

    protected EntityCustomProjectile(EntityType<? extends ThrowableItemProjectile> type, LivingEntity entity, Level world) {
	super(type, entity, world);
    }

    protected EntityCustomProjectile(EntityType<? extends ThrowableItemProjectile> type, double x, double y, double z, Level worldIn) {
	super(type, x, y, z, worldIn);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
	return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onHitBlock(BlockHitResult p_230299_1_) {
    	this.remove(Entity.RemovalReason.DISCARDED);
    }

    @Override
    public void onHitEntity(EntityHitResult p_213868_1_) {
    	this.remove(Entity.RemovalReason.DISCARDED);
    }

    @Override
    protected Item getDefaultItem() {
	return Items.COBBLESTONE;
    }

}
