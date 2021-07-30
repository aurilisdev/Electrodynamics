package electrodynamics.common.item.gear.tools.electric;

import electrodynamics.SoundRegister;
import electrodynamics.common.entity.projectile.ElectrodynamicsProjectile;
import electrodynamics.common.entity.projectile.types.energy.EnergyBlast;
import electrodynamics.common.item.gear.tools.electric.utils.Railgun;
import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class PlasmaRailGun extends Railgun{

	public static final double JOULES_PER_SHOT = 1000000.0;
	private static final int OVERHEAT_TEMPERATURE = 500;
	private static final int TEMPERATURE_PER_SHOT = 200;
	private static final double TEMPERATURE_REDUCED_PER_TICK = 1.0;
	private static final double OVERHEAT_WARNING_THRESHOLD = 0.5;
	
	public PlasmaRailGun(ElectricItemProperties properties) {
		super(properties, OVERHEAT_TEMPERATURE, OVERHEAT_WARNING_THRESHOLD, TEMPERATURE_REDUCED_PER_TICK);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) { 
		
		ItemStack gunStack;
		
		if(handIn == Hand.MAIN_HAND) {
			gunStack = playerIn.getHeldItemMainhand();
		}else {
			gunStack = playerIn.getHeldItemOffhand();
		}
		
		PlasmaRailGun railgun = (PlasmaRailGun)gunStack.getItem();
		
		if((railgun.getJoulesStored(gunStack) >= JOULES_PER_SHOT)  && (railgun.getTemperatureStored(gunStack) <= (OVERHEAT_TEMPERATURE - TEMPERATURE_PER_SHOT))) {
			
			ElectrodynamicsProjectile projectile = new EnergyBlast(playerIn,worldIn);
			projectile.setNoGravity(true);
			projectile.setShooter(playerIn);
			projectile.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0F, 5f, 1.0F);
			worldIn.addEntity(projectile);
			
			
			railgun.extractPower(gunStack, JOULES_PER_SHOT, false);
			worldIn.playSound(null, playerIn.getPosition(),
					SoundRegister.SOUND_RAILGUNPLASMA.get(), 
					SoundCategory.PLAYERS, 1, 1);
			railgun.recieveHeat(gunStack, TransferPack.temperature(TEMPERATURE_PER_SHOT), false);
		}else {
			worldIn.playSound(null, playerIn.getPosition(),
					SoundRegister.SOUND_RAILGUNKINETIC_NOAMMO.get(), 
					SoundCategory.PLAYERS, 1, 1);
		}
		
		return ActionResult.resultPass(playerIn.getHeldItemMainhand());
	}
	
}
