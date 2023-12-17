package electrodynamics.common.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class EntityCustomProjectile extends ProjectileItemEntity implements IEntityAdditionalSpawnData {

	public Vector3d clientDeltaX = Vector3d.ZERO;

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
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void onHitBlock(BlockRayTraceResult par) {
		remove();
	}

	@Override
	public void onHitEntity(EntityRayTraceResult par) {
		remove();
	}

	@Override
	protected Item getDefaultItem() {
		return Items.COBBLESTONE;
	}

	@Override
	public void tick() {
		if (level.isClientSide) {
			setDeltaMovement(clientDeltaX);
		}
		super.tick();
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		buffer.writeDouble(getDeltaMovement().x);
		buffer.writeDouble(getDeltaMovement().y);
		buffer.writeDouble(getDeltaMovement().z);
		buffer.writeFloat(xRot);
		buffer.writeFloat(yRot);
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
		clientDeltaX = new Vector3d(additionalData.readDouble(), additionalData.readDouble(), additionalData.readDouble());
		xRot = additionalData.readFloat();
		yRot = additionalData.readFloat();
	}

}
