package electrodynamics.common.item.gear.tools.electric.utils;

import java.util.List;

import electrodynamics.api.item.IItemTemperate;
import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.prefab.item.ItemElectric;
import electrodynamics.prefab.item.TemperateItemProperties;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

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
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		ItemRailgun railgun = (ItemRailgun) stack.getItem();
		tooltip.add(new TranslatableComponent("tooltip.electrodynamics.railguntemp").withStyle(ChatFormatting.YELLOW)
				.append(new TextComponent(railgun.getTemperatureStored(stack) + " C")));
		tooltip.add(new TranslatableComponent("tooltip.electrodynamics.railgunmaxtemp").withStyle(ChatFormatting.YELLOW)
				.append(new TextComponent(overheatTemperature + " C")));
		if (railgun.getTemperatureStored(stack) >= getOverheatTemp()) {
			tooltip.add(new TranslatableComponent("tooltip.electrodynamics.railgunoverheat").withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
		}
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		super.fillItemCategory(group, items);
		for (ItemStack stack : items) {
			if(stack.getItem() instanceof ItemRailgun) {
				IItemTemperate.setTemperature(stack, 0);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
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