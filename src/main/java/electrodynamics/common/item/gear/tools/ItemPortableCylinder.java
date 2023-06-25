package electrodynamics.common.item.gear.tools;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.gas.Gas;
import electrodynamics.api.gas.GasHandlerItemStack;
import electrodynamics.api.gas.GasStack;
import electrodynamics.api.inventory.InventoryTickConsumer;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsRegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ItemPortableCylinder extends Item {

	public static final double MAX_GAS_CAPCITY = 5000;

	public static final double MAX_TEMPERATURE = 1000;
	public static final int MAX_PRESSURE = 1000;

	public static final List<InventoryTickConsumer> INVENTORY_TICK_CONSUMERS = new ArrayList<>();

	public ItemPortableCylinder(Properties properties) {
		super(properties);
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		if (allowedIn(group)) {

			items.add(new ItemStack(this));
			if (ElectrodynamicsCapabilities.GAS_HANDLER_ITEM != null) {

				for (Gas gas : ElectrodynamicsRegistries.gasRegistry().getValues()) {
					if(gas.isEmpty()) {
						continue;
					}
					ItemStack temp = new ItemStack(this);
					temp.getCapability(ElectrodynamicsCapabilities.GAS_HANDLER_ITEM).ifPresent(cap -> ((GasHandlerItemStack) cap).setGas(new GasStack(gas, MAX_GAS_CAPCITY, Gas.ROOM_TEMPERATURE, Gas.PRESSURE_AT_SEA_LEVEL)));
					items.add(temp);

				}

			}

		}
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
		super.inventoryTick(stack, level, entity, slot, isSelected);
		INVENTORY_TICK_CONSUMERS.forEach(consumer -> consumer.apply(stack, level, entity, slot, isSelected));
	}

	@Override
	public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
		return new GasHandlerItemStack(stack, MAX_GAS_CAPCITY, MAX_TEMPERATURE, MAX_PRESSURE);
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltips, TooltipFlag isAdvanced) {
		stack.getCapability(ElectrodynamicsCapabilities.GAS_HANDLER_ITEM).ifPresent(cap -> {
			GasStack gas = cap.getGasInTank(0);
			if (gas.isEmpty()) {
				tooltips.add(Component.literal("0" + " / " + ChatFormatter.formatFluidMilibuckets(MAX_GAS_CAPCITY)).withStyle(ChatFormatting.GRAY));
			} else {
				tooltips.add(gas.getGas().getDescription().copy().withStyle(ChatFormatting.GRAY));
				tooltips.add(Component.literal(ChatFormatter.formatFluidMilibuckets(gas.getAmount()) + " / " + ChatFormatter.formatFluidMilibuckets(MAX_GAS_CAPCITY)).withStyle(ChatFormatting.DARK_GRAY));
				tooltips.add(Component.literal(ChatFormatter.getChatDisplayShort(gas.getTemperature(), DisplayUnit.TEMPERATURE_KELVIN)).withStyle(ChatFormatting.DARK_GRAY));
				tooltips.add(Component.literal(ChatFormatter.getChatDisplayShort(gas.getPressure(), DisplayUnit.PRESSURE_ATM)).withStyle(ChatFormatting.DARK_GRAY));
			}

		});
		if (Screen.hasShiftDown()) {
			tooltips.add(ElectroTextUtils.tooltip("maxpressure", ChatFormatter.getChatDisplayShort(MAX_PRESSURE, DisplayUnit.PRESSURE_ATM)).withStyle(ChatFormatting.GRAY));
			tooltips.add(ElectroTextUtils.tooltip("maxtemperature", ChatFormatter.getChatDisplayShort(MAX_TEMPERATURE, DisplayUnit.TEMPERATURE_KELVIN)).withStyle(ChatFormatting.GRAY));
		}
		super.appendHoverText(stack, level, tooltips, isAdvanced);
	}

	@Override
	public int getBarWidth(ItemStack stack) {
		return (int) Math.round(stack.getCapability(ElectrodynamicsCapabilities.GAS_HANDLER_ITEM).map(cap -> {
			return 13.0 * cap.getGasInTank(0).getAmount() / cap.getTankCapacity(0);
		}).orElse(13.0));
	}

	@Override
	public boolean isBarVisible(ItemStack stack) {
		return stack.getCapability(ElectrodynamicsCapabilities.GAS_HANDLER_ITEM).map(cap -> {
			return 13.0 * cap.getGasInTank(0).getAmount() / cap.getTankCapacity(0) < 13.0;
		}).orElse(false);
	}

}
