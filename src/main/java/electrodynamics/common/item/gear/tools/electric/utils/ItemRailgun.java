package electrodynamics.common.item.gear.tools.electric.utils;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
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

	public ItemRailgun(ElectricItemProperties properties, Supplier<CreativeModeTab> creativeTab, double overheatTemperature, double tempThreshold, double tempPerTick, Function<Item, Item> getBatteryItem) {
		super(properties, creativeTab, getBatteryItem);
		this.overheatTemperature = overheatTemperature;
		this.tempThreshold = tempThreshold;
		this.tempPerTick = tempPerTick;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(ElectroTextUtils.tooltip("railguntemp", ChatFormatter.getChatDisplayShort(IItemTemperate.getTemperature(stack), DisplayUnits.TEMPERATURE_CELCIUS)).withStyle(ChatFormatting.YELLOW));
		tooltip.add(ElectroTextUtils.tooltip("railgunmaxtemp", ChatFormatter.getChatDisplayShort(overheatTemperature, DisplayUnits.TEMPERATURE_CELCIUS)).withStyle(ChatFormatting.YELLOW));
		if (IItemTemperate.getTemperature(stack) >= getOverheatTemp()) {
			tooltip.add(ElectroTextUtils.tooltip("railgunoverheat").withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
		}
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		NonNullList<ItemStack> superItems = NonNullList.create();
		super.fillItemCategory(group, superItems);
		for (ItemStack stack : superItems) {
			if (stack.getItem() instanceof ItemRailgun) {
				IItemTemperate.setTemperature(stack, 0);
			}
		}
		if(allowedIn(group)) {
			items.addAll(superItems);
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
		((ItemRailgun) stack.getItem()).loseHeat(stack, tempPerTick, 0.0, false);
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return !oldStack.is(newStack.getItem());
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