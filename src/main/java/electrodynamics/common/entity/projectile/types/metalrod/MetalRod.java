package electrodynamics.common.entity.projectile.types.metalrod;

import electrodynamics.SoundRegister;
import electrodynamics.common.entity.projectile.ElectrodynamicsProjectile;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class MetalRod extends ElectrodynamicsProjectile{
	
	public MetalRod(EntityType<? extends ProjectileItemEntity> type, World world, Item item, DamageSource damageSource,
			float damageAmount) {
		super(type, world, item, damageSource, damageAmount);
	}
	
	public MetalRod(EntityType<? extends ProjectileItemEntity> type, LivingEntity entity, World world,
			Item item, DamageSource damageSource, float damageAmount) {
		super(type, entity, world, item, damageSource, damageAmount);		
	}
	
	public MetalRod(EntityType<? extends ProjectileItemEntity> type, double x, double y, double z, 
			World worldIn, Item item, DamageSource damageSource, float damageAmount) {
		super(type,x, y, z, worldIn, item, damageSource, damageAmount);
	}
	
	@Override
	protected void func_230299_a_(BlockRayTraceResult p_230299_1_) {
		BlockState state = this.world.getBlockState(p_230299_1_.getPos());
		if(!ItemStack.areItemsEqual(new ItemStack(state.getBlock().asItem()), new ItemStack(Items.AIR))) {
			if(!world.isRemote) {
				//Hardness of obsidian
				if(state.getBlockHardness(this.world, p_230299_1_.getPos()) < 50f) {
					this.world.removeBlock(p_230299_1_.getPos(), false);
				}
				this.world.playSound(null, p_230299_1_.getPos(), SoundRegister.SOUND_RODIMPACTINGGROUND.get(), 
						SoundCategory.BLOCKS, 1f, 1f);
			}
			this.remove();
		}
	}

}
