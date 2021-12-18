package electrodynamics.common.entity.projectile.types;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.common.damage.DamageSources;
import electrodynamics.common.entity.projectile.EntityCustomProjectile;
import electrodynamics.common.item.subtype.SubtypeRod;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class EntityMetalRod extends EntityCustomProjectile {

	/*
	 * 0 = Steel 1 = Stainless Steel 2 = HSLA Steel
	 */
	private static final EntityDataAccessor<Integer> NUMBER = SynchedEntityData.defineId(EntityMetalRod.class, EntityDataSerializers.INT);
	private int number = 0;

	public EntityMetalRod(EntityType<? extends ThrowableItemProjectile> type, Level world) {
		super(DeferredRegisters.ENTITY_METALROD.get(), world);
	}

	public EntityMetalRod(LivingEntity entity, Level world, int number) {
		super(DeferredRegisters.ENTITY_METALROD.get(), entity, world);
		this.number = number;
	}

	public EntityMetalRod(double x, double y, double z, Level worldIn, int number) {
		super(DeferredRegisters.ENTITY_METALROD.get(), x, y, z, worldIn);
		this.number = number;
	}

	@Override
	public void tick() {
		super.tick();
		if (!level.isClientSide) {
			entityData.set(NUMBER, number);
		} else if (!entityData.isEmpty()) {
			number = entityData.get(NUMBER);
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult p_230299_1_) {
		BlockState state = level.getBlockState(p_230299_1_.getBlockPos());
		if (!ItemStack.isSame(new ItemStack(state.getBlock().asItem()), new ItemStack(Items.AIR))) {
			if (!level.isClientSide) {
				// Hardness of obsidian
				if (state.getDestroySpeed(level, p_230299_1_.getBlockPos()) < 50f
						&& !ItemStack.isSame(new ItemStack(state.getBlock().asItem()), new ItemStack(Items.BEDROCK))) {
					level.removeBlock(p_230299_1_.getBlockPos(), false);
				}
				level.playSound(null, p_230299_1_.getBlockPos(), SoundRegister.SOUND_RODIMPACTINGGROUND.get(), SoundSource.BLOCKS, 1f, 1f);
			}
			remove(Entity.RemovalReason.DISCARDED);
		}
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(NUMBER, number);
	}

	// It bugs for some reason if I don't have the break
	@Override
	public void onHitEntity(EntityHitResult p_213868_1_) {
		switch (number) {
		case 0:
			p_213868_1_.getEntity().hurt(DamageSources.ACCELERATED_BOLT, 16f);
			break;
		case 1:
			p_213868_1_.getEntity().hurt(DamageSources.ACCELERATED_BOLT, 20f);
			break;
		case 2:
			p_213868_1_.getEntity().hurt(DamageSources.ACCELERATED_BOLT_IGNOREARMOR, 4f);
			break;
		default:
		}
		super.onHitEntity(p_213868_1_);
	}

	@Override
	protected Item getDefaultItem() {
		switch (number) {
		case 0:
			return DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeRod.steel);
		case 1:
			return DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeRod.stainlesssteel);
		case 2:
			return DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeRod.hslasteel);
		default:
			return super.getDefaultItem();
		}
	}

	public int getNumber() {
		return number;
	}

}
