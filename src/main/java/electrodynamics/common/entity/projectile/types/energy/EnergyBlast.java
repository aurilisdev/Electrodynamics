package electrodynamics.common.entity.projectile.types.energy;

import electrodynamics.common.damage.DamageSources;
import electrodynamics.common.entity.EntityRegistry;
import electrodynamics.common.entity.projectile.ElectrodynamicsProjectile;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;

public class EnergyBlast extends ElectrodynamicsProjectile{

	private static final float DAMAGE_DEALT = 4f;
	private static final DamageSource DAMAGE_SOURCE = DamageSources.PLASMA_BOLT;
	private static Item ITEM = Items.COBBLESTONE;
	
	public EnergyBlast(EntityType<? extends ProjectileItemEntity> type, World world) {
		super(type, world, ITEM, DAMAGE_SOURCE, DAMAGE_DEALT);
	}
	
	public EnergyBlast(LivingEntity entity, World world) {
		super(EntityRegistry.PROJECTILE_ENERGYBLAST.get(), entity, world, ITEM, DAMAGE_SOURCE, DAMAGE_DEALT);
	}
	
	public EnergyBlast(double x, double y, double z, World worldIn) {
		super(EntityRegistry.PROJECTILE_ENERGYBLAST.get(), x, y, z, worldIn, ITEM, DAMAGE_SOURCE, DAMAGE_DEALT);
	}
	
	@Override
	protected void func_230299_a_(BlockRayTraceResult p_230299_1_) {
		BlockState state = this.world.getBlockState(p_230299_1_.getPos());
		if(!ItemStack.areItemsEqual(new ItemStack(state.getBlock().asItem()), new ItemStack(Items.AIR))) {
			if(!world.isRemote) {
				world.createExplosion(null, p_230299_1_.getPos().getX(), p_230299_1_.getPos().getY(),
					p_230299_1_.getPos().getZ(), 1f, true, Mode.DESTROY);
			}
			this.remove();
		}
	}

}
