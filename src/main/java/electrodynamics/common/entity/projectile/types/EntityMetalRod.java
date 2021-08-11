package electrodynamics.common.entity.projectile.types;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.common.damage.DamageSources;
import electrodynamics.common.entity.projectile.EntityCustomProjectile;
import electrodynamics.common.item.subtype.SubtypeRod;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;


public class EntityMetalRod extends EntityCustomProjectile {

	/* 
	 * 0 = Steel 
	 * 1 = Stainless Steel
	 * 2 = HSLA Steel
	 */
	private static final DataParameter<Integer> NUMBER = EntityDataManager.createKey(EntityMetalRod.class, DataSerializers.VARINT);
	private int number = 0;
	
    public EntityMetalRod(EntityType<? extends ProjectileItemEntity> type, World world) {
    	super(DeferredRegisters.ENTITY_METALROD.get(), world);
    }

    public EntityMetalRod(LivingEntity entity, World world, int number) {
    	super(DeferredRegisters.ENTITY_METALROD.get(), entity, world);
    	this.number = number;
    }

    public EntityMetalRod(double x, double y, double z, World worldIn, int number) {
    	super(DeferredRegisters.ENTITY_METALROD.get(), x, y, z, worldIn);
    	this.number = number;
    }
    
    @Override
    public void tick() {
    	super.tick();
    	if(!world.isRemote) {
    		dataManager.set(NUMBER, number);
    	}else {
    		if(!dataManager.isEmpty()) {
    			number = dataManager.get(NUMBER);
    		}
    	}
    }

    @Override
    protected void func_230299_a_(BlockRayTraceResult p_230299_1_) {
		BlockState state = world.getBlockState(p_230299_1_.getPos());
		if (!ItemStack.areItemsEqual(new ItemStack(state.getBlock().asItem()), new ItemStack(Items.AIR))) {
		    if (!world.isRemote) {
			// Hardness of obsidian
			if (state.getBlockHardness(world, p_230299_1_.getPos()) < 50f
				&& !ItemStack.areItemsEqual(new ItemStack(state.getBlock().asItem()), new ItemStack(Items.BEDROCK))) {
			    world.removeBlock(p_230299_1_.getPos(), false);
			}
			world.playSound(null, p_230299_1_.getPos(), SoundRegister.SOUND_RODIMPACTINGGROUND.get(), SoundCategory.BLOCKS, 1f, 1f);
		    }
		    this.remove();
		}
    }
    
    @Override
    protected void registerData() {
    	super.registerData();
    	dataManager.register(NUMBER, number);
    }
    
    //It bugs for some reason if I don't have the break
    @Override
    public void onEntityHit(EntityRayTraceResult p_213868_1_) {
    	switch(number){
    		case 0:
    			p_213868_1_.getEntity().attackEntityFrom(DamageSources.ACCELERATED_BOLT, 16f);
    			break;
    		case 1:
    			p_213868_1_.getEntity().attackEntityFrom(DamageSources.ACCELERATED_BOLT, 20f);
    			break;
    		case 2:
    			p_213868_1_.getEntity().attackEntityFrom(DamageSources.ACCELERATED_BOLT_IGNOREARMOR, 4f);
    			break;
    		default:
    	}
    	super.onEntityHit(p_213868_1_);
    }

	@Override
	protected Item getDefaultItem() {
		switch(number) {
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
