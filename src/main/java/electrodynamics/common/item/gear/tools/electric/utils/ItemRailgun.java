package electrodynamics.common.item.gear.tools.electric.utils;

import java.util.List;
import java.util.function.Supplier;

import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.api.item.IItemTemperate;
import voltaic.prefab.item.ElectricItemProperties;
import voltaic.prefab.item.ItemElectric;
import voltaic.prefab.item.TemperateItemProperties;

public class ItemRailgun extends ItemElectric implements IItemTemperate {

	private final TemperateItemProperties temperateProperties = new TemperateItemProperties();
	private double overheatTemperature = 0;
	private double tempThreshold = 0;
	private double tempPerTick = 0;

	public ItemRailgun(ElectricItemProperties properties, Supplier<ItemGroup> creativeTab, double overheatTemperature, double tempThreshold, double tempPerTick) {
		super(properties, creativeTab);
		this.overheatTemperature = overheatTemperature;
		this.tempThreshold = tempThreshold;
		this.tempPerTick = tempPerTick;
	}

	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(ElectroTextUtils.tooltip("railguntemp", ChatFormatter.getChatDisplayShort(IItemTemperate.getTemperature(stack), DisplayUnits.TEMPERATURE_CELCIUS)).withStyle(TextFormatting.YELLOW));
		tooltip.add(ElectroTextUtils.tooltip("railgunmaxtemp", ChatFormatter.getChatDisplayShort(overheatTemperature, DisplayUnits.TEMPERATURE_CELCIUS)).withStyle(TextFormatting.YELLOW));
		if (IItemTemperate.getTemperature(stack) >= getOverheatTemp()) {
			tooltip.add(ElectroTextUtils.tooltip("railgunoverheat").withStyle(TextFormatting.RED, TextFormatting.BOLD));
		}
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		NonNullList<ItemStack> superItems = NonNullList.create();
		super.fillItemCategory(group, superItems);
		for (ItemStack stack : superItems) {
			if (stack.getItem() instanceof ItemRailgun) {
				IItemTemperate.setTemperature(stack, 0);
			}
		}
		if(allowdedIn(group)) {
			items.addAll(superItems);
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
		((ItemRailgun) stack.getItem()).loseHeat(stack, tempPerTick, 0.0, false);
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return oldStack.getItem() != newStack.getItem();
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