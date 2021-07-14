package electrodynamics.common.entity.projectile.types;

import electrodynamics.DeferredRegisters;
import electrodynamics.Electrodynamics;
import electrodynamics.common.damage.DamageSources;
import electrodynamics.common.entity.EntityRegistry;
import electrodynamics.common.entity.projectile.ElectrodynamicsProjectile;
import electrodynamics.common.item.subtype.SubtypeRod;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class SteelRod extends ElectrodynamicsProjectile{

	private static final float DAMAGE_DEALT = 8f;
	
	public SteelRod(EntityType<? extends ProjectileItemEntity> type, World world) {
		super(type,world);
	}
	
	public SteelRod(LivingEntity entity, World world) {
		super(EntityRegistry.PROJECTILE_STEELROD.get(), entity, world);
		Electrodynamics.LOGGER.info("Spawning rod");
	}
	
	public SteelRod(double x, double y, double z, World worldIn) {
		super(EntityRegistry.PROJECTILE_STEELROD.get(),x,y,z,worldIn);
	}

	@Override
	protected Item getDefaultItem() {
		return DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeRod.steel);
	}
	
	protected void onImpact(RayTraceResult result) {
		Electrodynamics.LOGGER.info("Collision");
		if(result.getType() == RayTraceResult.Type.ENTITY) {
			Electrodynamics.LOGGER.info("Entity Collision");
			Entity entity = ((EntityRayTraceResult)result).getEntity();
			entity.attackEntityFrom(DamageSources.ACCELERATED_BOLT, DAMAGE_DEALT);
			if(!this.world.isRemote) {
				this.remove();
			}
		}else if(result.getType() == RayTraceResult.Type.BLOCK) {
			BlockRayTraceResult blockTrace = (BlockRayTraceResult)result;
			BlockState state = this.world.getBlockState(blockTrace.getPos());
			if(state.isAir(world, blockTrace.getPos())) {
				Electrodynamics.LOGGER.info("Air Collision");
			}else {
				state.getBlock().replaceBlock(state, Blocks.OBSIDIAN.getDefaultState(), world, blockTrace.getPos(), 0);
				this.remove();
			}
		}
	}
	
}
