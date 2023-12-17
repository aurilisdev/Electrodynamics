package electrodynamics.common.entity.projectile.types;

import electrodynamics.common.damage.DamageSources;
import electrodynamics.common.entity.projectile.EntityCustomProjectile;
import electrodynamics.common.item.subtype.SubtypeRod;
import electrodynamics.registers.ElectrodynamicsEntities;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;

public class EntityMetalRod extends EntityCustomProjectile {

	/*
	 * 0 = Steel 1 = Stainless Steel 2 = HSLA Steel
	 */
	private int number = 0;

	public EntityMetalRod(EntityType<? extends ProjectileItemEntity> type, World world) {
		super(ElectrodynamicsEntities.ENTITY_METALROD.get(), world);
	}

	public EntityMetalRod(LivingEntity entity, World world, int number) {
		super(ElectrodynamicsEntities.ENTITY_METALROD.get(), entity, world);
		this.number = number;
	}

	public EntityMetalRod(double x, double y, double z, World worldIn, int number) {
		super(ElectrodynamicsEntities.ENTITY_METALROD.get(), x, y, z, worldIn);
		this.number = number;
	}
	
	@Override
	public void setPos(double x, double y, double z) {
		super.setPos(x, y, z);
	}

	@Override
	protected void onHitBlock(BlockRayTraceResult p_230299_1_) {
		BlockState state = level.getBlockState(p_230299_1_.getBlockPos());
		if (!ItemStack.isSame(new ItemStack(state.getBlock().asItem()), new ItemStack(Items.AIR))) {
			if (!level.isClientSide) {
				// Hardness of obsidian
				if (state.getDestroySpeed(level, p_230299_1_.getBlockPos()) < 50f && !ItemStack.isSame(new ItemStack(state.getBlock().asItem()), new ItemStack(Items.BEDROCK))) {
					level.removeBlock(p_230299_1_.getBlockPos(), false);
				}
				level.playSound(null, p_230299_1_.getBlockPos(), ElectrodynamicsSounds.SOUND_RODIMPACTINGGROUND.get(), SoundCategory.BLOCKS, 1f, 1f);
			}
			remove();
		}
	}

	@Override
	public void onHitEntity(EntityRayTraceResult hit) {
		switch (number) {
		case 0:
			hit.getEntity().hurt(DamageSources.ACCELERATED_BOLT, 16f);
			break;
		case 1:
			hit.getEntity().hurt(DamageSources.ACCELERATED_BOLT, 20f);
			break;
		case 2:
			hit.getEntity().hurt(DamageSources.ACCELERATED_BOLT_IGNOREARMOR, 4f);
			break;
		default:
		}
		super.onHitEntity(hit);
	}

	@Override
	protected Item getDefaultItem() {
		switch (number) {
		case 0:
			return ElectrodynamicsItems.SUBTYPEITEMREGISTER_MAPPINGS.get(SubtypeRod.steel).get();
		case 1:
			return ElectrodynamicsItems.SUBTYPEITEMREGISTER_MAPPINGS.get(SubtypeRod.stainlesssteel).get();
		case 2:
			return ElectrodynamicsItems.SUBTYPEITEMREGISTER_MAPPINGS.get(SubtypeRod.hslasteel).get();
		default:
			return super.getDefaultItem();
		}
	}

	public int getNumber() {
		return number;
	}
	
	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		super.writeSpawnData(buffer);
		buffer.writeInt(number);
	}
	
	@Override
	public void readSpawnData(PacketBuffer additionalData) {
		super.readSpawnData(additionalData);
		number = additionalData.readInt();
	}

}