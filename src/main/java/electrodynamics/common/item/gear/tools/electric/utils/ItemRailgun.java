package electrodynamics.common.item.gear.tools.electric.utils;

import java.util.List;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.item.IItemTemperate;
import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.prefab.item.ItemElectric;
import electrodynamics.prefab.item.TemperateItemProperties;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
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
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(ElectroTextUtils.tooltip("railguntemp", ChatFormatter.getChatDisplayShort(IItemTemperate.getTemperature(stack), DisplayUnit.TEMPERATURE_CELCIUS)).withStyle(TextFormatting.YELLOW));
		tooltip.add(ElectroTextUtils.tooltip("railgunmaxtemp", ChatFormatter.getChatDisplayShort(overheatTemperature, DisplayUnit.TEMPERATURE_CELCIUS)).withStyle(TextFormatting.YELLOW));
		if (IItemTemperate.getTemperature(stack) >= getOverheatTemp()) {
			tooltip.add(ElectroTextUtils.tooltip("railgunoverheat").withStyle(TextFormatting.RED, TextFormatting.BOLD));
		}
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		super.fillItemCategory(group, items);

		if (!allowdedIn(group)) {
			return;
		}

		for (ItemStack stack : items) {
			if (stack.getItem() instanceof ItemRailgun) {
				IItemTemperate.setTemperature(stack, 0);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
		((ItemRailgun) stack.getItem()).loseHeat(stack, tempPerTick, 0.0, false);
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