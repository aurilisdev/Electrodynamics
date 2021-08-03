package electrodynamics.common.item.gear.tools.electric.utils;

import java.util.List;
import java.util.function.Consumer;

import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.prefab.item.ItemElectric;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;


public class Railgun extends ItemElectric{

	private double OVERHEAT_TEMPERATURE = 0;
	private double OVERHEAT_WARNING_THRESHOLD = 0;
	private double TEMPERATURE_REDUCED_PER_TICK = 0;
	
	public Railgun(ElectricItemProperties properties, double overheatTemperature, double tempThreshold,
		double tempPerTick) {
		super(properties);
		this.OVERHEAT_TEMPERATURE = overheatTemperature;
		this.OVERHEAT_WARNING_THRESHOLD = tempThreshold;
		this.TEMPERATURE_REDUCED_PER_TICK = tempPerTick;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		Railgun railgun = (Railgun)stack.getItem();
		tooltip.add(new TranslationTextComponent("tooltip.electrodynamics.railguntemp").mergeStyle(TextFormatting.YELLOW)
			.append(new StringTextComponent(railgun.getTemperatureStored(stack) + " C")));
		tooltip.add(new TranslationTextComponent("tooltip.electrodynamics.railgunmaxtemp").mergeStyle(TextFormatting.YELLOW)
			.append(new StringTextComponent(OVERHEAT_TEMPERATURE + " C")));
		if(railgun.getTemperatureStored(stack) >= this.getOverheatTemp()) {
			tooltip.add(new TranslationTextComponent("tooltip.electrodynamics.railgunoverheat").mergeStyle(TextFormatting.RED,TextFormatting.BOLD));
		}
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
		((Railgun)stack.getItem()).decreaseTemperature(stack, TEMPERATURE_REDUCED_PER_TICK, false, 0.0);
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		if(slotChanged) {
			return true;
		}
		return false;
	}
	
	public double getMaxTemp() {
		return OVERHEAT_TEMPERATURE;
	}
	
	public double getOverheatTemp() {
		return OVERHEAT_TEMPERATURE * OVERHEAT_WARNING_THRESHOLD;
	}

}
