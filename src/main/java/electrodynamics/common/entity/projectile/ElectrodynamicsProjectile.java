package electrodynamics.common.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class ElectrodynamicsProjectile extends ProjectileItemEntity{

	public ElectrodynamicsProjectile(EntityType<? extends ProjectileItemEntity> type, World world) {
		super(type,world);
	}
	
	public ElectrodynamicsProjectile(EntityType<? extends ProjectileItemEntity> type, LivingEntity entity, World world) {
		super(type, entity, world);
	}
	
	public ElectrodynamicsProjectile(EntityType<? extends ProjectileItemEntity> type, double x, double y, double z, World worldIn) {
		super(type,x, y, z, worldIn);
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
}
