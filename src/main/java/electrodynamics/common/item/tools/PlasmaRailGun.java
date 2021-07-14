package electrodynamics.common.item.tools;

import java.util.List;

import electrodynamics.SoundRegister;
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

public class PlasmaRailGun extends ItemElectric{

	public static final double JOULES_PER_SHOT = 1000000.0;
	private static final int OVERHEAT_TEMPERATURE = 1000;
	private static final int TEMPERATURE_PER_SHOT = 200;
	private static final double TEMPERATURE_REDUCED_PER_TICK = 1.0;
	private static final double OVERHEAT_WARNING_THRESHOLD = 0.6;
	
	
	
	public PlasmaRailGun(ElectricItemProperties properties) {
		super(properties);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		PlasmaRailGun railgun = (PlasmaRailGun)stack.getItem();
		tooltip.add(new StringTextComponent("Temperature: " + railgun.getTemperatureStored(stack) + " C").mergeStyle(TextFormatting.YELLOW));
		if(railgun.getTemperatureStored(stack) >= OVERHEAT_TEMPERATURE * OVERHEAT_WARNING_THRESHOLD) {
			tooltip.add(new StringTextComponent("WARNING : OVERHEATING").mergeStyle(TextFormatting.RED,TextFormatting.BOLD));
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) { 
		
		ItemStack gunStack;
		
		switch(handIn) {
			case MAIN_HAND:
				gunStack = playerIn.getHeldItemMainhand();
				break;
			default:
				gunStack = playerIn.getHeldItemOffhand();
		}
		
		PlasmaRailGun railgun = (PlasmaRailGun)gunStack.getItem();
		
		if((railgun.getJoulesStored(gunStack) >= JOULES_PER_SHOT)  && (railgun.getTemperatureStored(gunStack) <= (OVERHEAT_TEMPERATURE - TEMPERATURE_PER_SHOT))) {
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
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		((PlasmaRailGun)stack.getItem()).decreaseTemperature(stack, TEMPERATURE_REDUCED_PER_TICK, false, 0.0);
	}
	
}
