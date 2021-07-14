package electrodynamics.common.item.tools;

import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.common.entity.projectile.ElectrodynamicsProjectile;
import electrodynamics.common.entity.projectile.types.HSLASteelRod;
import electrodynamics.common.entity.projectile.types.StainlessSteelRod;
import electrodynamics.common.entity.projectile.types.SteelRod;
import electrodynamics.common.item.subtype.SubtypeRod;
import electrodynamics.prefab.item.ItemElectric;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class KineticRailGun extends ItemElectric{

	public static final double JOULES_PER_SHOT = 100000.0;
	private static final int OVERHEAT_TEMPERATURE = 1000;
	private static final int TEMPERATURE_PER_SHOT = 300;
	private static final double TEMPERATURE_REDUCED_PER_TICK = 1.0;
	private static final double OVERHEAT_WARNING_THRESHOLD = 0.6;
	
	
	public KineticRailGun(ElectricItemProperties properties) {
		super(properties);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		KineticRailGun railgun = (KineticRailGun)stack.getItem();
		tooltip.add(new StringTextComponent("Temperature: " + railgun.getTemperatureStored(stack) + " C").mergeStyle(TextFormatting.YELLOW));
		if(railgun.getTemperatureStored(stack) >= OVERHEAT_TEMPERATURE * OVERHEAT_WARNING_THRESHOLD) {
			tooltip.add(new StringTextComponent("WARNING : OVERHEATING").mergeStyle(TextFormatting.RED,TextFormatting.BOLD));
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) { 
		
		if(!worldIn.isRemote) {
			ItemStack gunStack;
			ItemStack ammoStack;
			switch(handIn) {
				case MAIN_HAND:
					gunStack = playerIn.getHeldItemMainhand();
					ammoStack = playerIn.getHeldItemOffhand();
					break;
				default:
					gunStack = playerIn.getHeldItemOffhand();
					ammoStack = playerIn.getHeldItemMainhand();
			}
			
			KineticRailGun railgun = (KineticRailGun)gunStack.getItem();
			
			if(railgun.getJoulesStored(gunStack) >= JOULES_PER_SHOT) {
				if(validAmmo(ammoStack) && (railgun.getTemperatureStored(gunStack) <= (OVERHEAT_TEMPERATURE - TEMPERATURE_PER_SHOT))) {
					ElectrodynamicsProjectile projectile = null;
					
					if(ItemStack.areItemsEqual(ammoStack, new ItemStack(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeRod.steel)))) {
						projectile = new SteelRod(playerIn,worldIn);
					}else if(ItemStack.areItemsEqual(ammoStack, new ItemStack(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeRod.stainlesssteel)))) {
						projectile = new StainlessSteelRod(playerIn,worldIn);
					}else if(ItemStack.areItemsEqual(ammoStack, new ItemStack(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeRod.hslasteel)))) {
						projectile = new HSLASteelRod(playerIn,worldIn);
					}
					
					if(projectile != null) {
						railgun.extractPower(gunStack, JOULES_PER_SHOT, false);
						worldIn.playSound(null, playerIn.getPosition(),
								SoundRegister.SOUND_RAILGUNKINETIC.get(), 
								SoundCategory.PLAYERS, 1, 1);
						projectile.setItem(ammoStack);
						projectile.setShooter(playerIn);
						projectile.shoot(playerIn.rotationPitch,playerIn.rotationYaw,0.0f,1f,0.0f);
						worldIn.addEntity(projectile);
						railgun.recieveHeat(gunStack, TransferPack.temperature(TEMPERATURE_PER_SHOT), false);
						ammoStack.shrink(1);
					}
					
				}else {
					worldIn.playSound(null,playerIn.getPosition(),
						SoundRegister.SOUND_RAILGUNKINETIC_NOAMMO.get(),
						SoundCategory.PLAYERS,1,1);
				}	
			}else {
				worldIn.playSound(null,playerIn.getPosition(),
						SoundRegister.SOUND_RAILGUNKINETIC_NOAMMO.get(),
						SoundCategory.PLAYERS,1,1);
			}
		}
		return ActionResult.resultPass(playerIn.getHeldItemMainhand());
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
		((KineticRailGun)stack.getItem()).decreaseTemperature(stack, TEMPERATURE_REDUCED_PER_TICK, false, 0.0);
	}
	
	private boolean validAmmo(ItemStack ammoStack) {
		return true;
	}
	
	

}
