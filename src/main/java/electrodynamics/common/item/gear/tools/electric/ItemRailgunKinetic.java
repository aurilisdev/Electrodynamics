package electrodynamics.common.item.gear.tools.electric;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.SoundRegister;
import electrodynamics.api.item.ItemUtils;
import electrodynamics.common.entity.projectile.EntityCustomProjectile;
import electrodynamics.common.entity.projectile.types.EntityMetalRod;
import electrodynamics.common.item.gear.tools.electric.utils.Railgun;
import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemRailgunKinetic extends Railgun {

    public static final double JOULES_PER_SHOT = 100000.0;
    private static final int OVERHEAT_TEMPERATURE = 400;
    private static final int TEMPERATURE_PER_SHOT = 300;
    private static final double TEMPERATURE_REDUCED_PER_TICK = 2.0;
    private static final double OVERHEAT_WARNING_THRESHOLD = 0.75;
    
    public ItemRailgunKinetic(ElectricItemProperties properties) {
    	super(properties, OVERHEAT_TEMPERATURE, OVERHEAT_WARNING_THRESHOLD, TEMPERATURE_REDUCED_PER_TICK);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack gunStack;
		ItemStack ammoStack;
	
		if (handIn == Hand.MAIN_HAND) {
		    gunStack = playerIn.getHeldItemMainhand();
		    ammoStack = playerIn.getHeldItemOffhand();
		} else {
		    gunStack = playerIn.getHeldItemOffhand();
		    ammoStack = playerIn.getHeldItemMainhand();
		}
	
		if (!worldIn.isRemote) {
		    ItemRailgunKinetic railgun = (ItemRailgunKinetic) gunStack.getItem();
	
			if (railgun.getJoulesStored(gunStack) >= JOULES_PER_SHOT && !ammoStack.isEmpty() 
				&& railgun.getTemperatureStored(gunStack) <= OVERHEAT_TEMPERATURE - TEMPERATURE_PER_SHOT) {
			    
				EntityCustomProjectile projectile = null;
				int i = 0;
	
				for(Ingredient ammo : getRailgunAmmo()) {
					if(ammo.test(ammoStack)) {
						projectile = new EntityMetalRod(playerIn, worldIn, i);
						break;
					}
					i++;
				}
				
			    if(projectile == null) {
			    	worldIn.playSound(null, playerIn.getPosition(), SoundRegister.SOUND_RAILGUNKINETIC_NOAMMO.get(), SoundCategory.PLAYERS, 1, 1);
			    } else {
					railgun.extractPower(gunStack, JOULES_PER_SHOT, false);
					worldIn.playSound(null, playerIn.getPosition(), SoundRegister.SOUND_RAILGUNKINETIC.get(), SoundCategory.PLAYERS, 1, 1);
					projectile.setItem(ammoStack);
					projectile.setNoGravity(true);
					projectile.setShooter(playerIn);
					projectile.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0f, 20f, 1.0F);
					if (!worldIn.isRemote) {
					    worldIn.addEntity(projectile);
					}
					railgun.recieveHeat(gunStack, TransferPack.temperature(TEMPERATURE_PER_SHOT), false);
					ammoStack.shrink(1);
			    }
			} else {
			    worldIn.playSound(null, playerIn.getPosition(), SoundRegister.SOUND_RAILGUNKINETIC_NOAMMO.get(), SoundCategory.PLAYERS, 1, 1);
			}
		}
		return ActionResult.resultPass(gunStack);
    }
    
    /*
     * Allows easy addition of ammo types in the future 
     * Uses the Ingredient of the item to allow cross-mod compatibility 
     */
    public List<Ingredient> getRailgunAmmo(){
    	List<Ingredient> railgunAmmo = new ArrayList<>();
    	railgunAmmo.add(ItemUtils.getIngredientFromTag("forge","rods/steel"));
    	railgunAmmo.add(ItemUtils.getIngredientFromTag("forge","rods/stainlesssteel"));
    	railgunAmmo.add(ItemUtils.getIngredientFromTag("forge","rods/hslasteel"));
    	return railgunAmmo;
    }
}
