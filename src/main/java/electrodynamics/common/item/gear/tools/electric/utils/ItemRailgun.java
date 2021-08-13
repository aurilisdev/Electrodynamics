package electrodynamics.common.item.gear.tools.electric.utils;

import java.util.List;

import electrodynamics.api.item.IItemTemperate;
import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.prefab.item.ItemElectric;
import electrodynamics.prefab.item.TemperateItemProperties;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ItemRailgun extends ItemElectric implements IItemTemperate {

    private final TemperateItemProperties temperateProperties = new TemperateItemProperties();
    private double overheatTemperature = 0;
    private double tempThreshold = 0;
    private double tempPerTick = 0;

    public ItemRailgun(ElectricItemProperties properties, double overheatTemperature, double tempThreshold, double tempPerTick) {
	super(properties);
	this.overheatTemperature = overheatTemperature;
	this.tempThreshold = tempThreshold;
	this.tempPerTick = tempPerTick;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
	super.addInformation(stack, worldIn, tooltip, flagIn);
	ItemRailgun railgun = (ItemRailgun) stack.getItem();
	tooltip.add(new TranslationTextComponent("tooltip.electrodynamics.railguntemp").mergeStyle(TextFormatting.YELLOW)
		.append(new StringTextComponent(railgun.getTemperatureStored(stack) + " C")));
	tooltip.add(new TranslationTextComponent("tooltip.electrodynamics.railgunmaxtemp").mergeStyle(TextFormatting.YELLOW)
		.append(new StringTextComponent(overheatTemperature + " C")));
	if (railgun.getTemperatureStored(stack) >= getOverheatTemp()) {
	    tooltip.add(new TranslationTextComponent("tooltip.electrodynamics.railgunoverheat").mergeStyle(TextFormatting.RED, TextFormatting.BOLD));
	}
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
	super.fillItemGroup(group, items);
	for (ItemStack stack : items) {
	    IItemTemperate.setTemperature(stack, 0);
	}
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
	super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	((ItemRailgun) stack.getItem()).decreaseTemperature(stack, tempPerTick, false, 0.0);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
	return slotChanged;
    }

    public double getMaxTemp() {
	return overheatTemperature;
    }

    public double getOverheatTemp() {
	return overheatTemperature * tempThreshold;
    }

    @Override
    public TemperateItemProperties getTemperteProperties() {
	return temperateProperties;
    }

}
