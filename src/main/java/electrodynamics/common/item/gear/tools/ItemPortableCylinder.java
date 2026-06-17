package electrodynamics.common.item.gear.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.api.gas.Gas;
import voltaic.api.gas.GasHandlerItemStack;
import voltaic.api.gas.GasStack;
import voltaic.api.inventory.InventoryTickConsumer;
import voltaic.common.item.ItemVoltaic;
import voltaic.prefab.utilities.VoltaicTextUtils;
import voltaic.registers.VoltaicCapabilities;
import voltaic.registers.VoltaicRegistries;

public class ItemPortableCylinder extends ItemVoltaic {

    public static final int MAX_GAS_CAPCITY = 5000;

    public static final int MAX_TEMPERATURE = 1000;
    public static final int MAX_PRESSURE = 1000;

    public static final List<InventoryTickConsumer> INVENTORY_TICK_CONSUMERS = new ArrayList<>();

    public ItemPortableCylinder(Properties properties, Supplier<CreativeModeTab> creativeTab) {
	super(properties, creativeTab);
    }

    @Override
    public void addCreativeModeItems(CreativeModeTab group, List<ItemStack> items) {

	items.add(new ItemStack(this));
	if (VoltaicCapabilities.CAPABILITY_GASHANDLER_ITEM != null) {

	    for (Gas gas : VoltaicRegistries.gasRegistry().getValues()) {
		if (gas.isEmpty()) {
		    continue;
		}
		ItemStack temp = new ItemStack(this);
		temp.getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_ITEM)
			.ifPresent(cap -> ((GasHandlerItemStack) cap).setGas(new GasStack(gas, MAX_GAS_CAPCITY,
				gas.getCondensationTemp() >= Gas.ROOM_TEMPERATURE ? gas.getCondensationTemp() + 1
					: Gas.ROOM_TEMPERATURE,
				Gas.PRESSURE_AT_SEA_LEVEL)));
		items.add(temp);

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
	stack.getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_ITEM).ifPresent(cap -> {
	    GasStack gas = cap.getGasInTank(0);
	    if (gas.isEmpty()) {
		tooltips.add(VoltaicTextUtils
			.ratio(Component.literal("0"), ChatFormatter.formatFluidMilibuckets(MAX_GAS_CAPCITY))
			.withStyle(ChatFormatting.DARK_GRAY));
	    } else {
		tooltips.add(gas.getGas().getDescription().copy().withStyle(ChatFormatting.GRAY));
		tooltips.add(VoltaicTextUtils
			.ratio(ChatFormatter.formatFluidMilibuckets(gas.getAmount()),
				ChatFormatter.formatFluidMilibuckets(MAX_GAS_CAPCITY))
			.withStyle(ChatFormatting.DARK_GRAY));
		tooltips.add(ChatFormatter.getChatDisplayShort(gas.getTemperature(), DisplayUnits.TEMPERATURE_KELVIN)
			.withStyle(ChatFormatting.DARK_GRAY));
		tooltips.add(ChatFormatter.getChatDisplayShort(gas.getPressure(), DisplayUnits.PRESSURE_ATM)
			.withStyle(ChatFormatting.DARK_GRAY));
	    }
	    if (Screen.hasShiftDown()) {
		tooltips.add(ElectroTextUtils
			.tooltip("maxpressure",
				ChatFormatter.getChatDisplayShort(MAX_PRESSURE, DisplayUnits.PRESSURE_ATM))
			.withStyle(ChatFormatting.GRAY));
		tooltips.add(ElectroTextUtils
			.tooltip("maxtemperature",
				ChatFormatter.getChatDisplayShort(MAX_TEMPERATURE, DisplayUnits.TEMPERATURE_KELVIN))
			.withStyle(ChatFormatting.GRAY));
	    }

	});
	super.appendHoverText(stack, level, tooltips, isAdvanced);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
	return (int) Math.round(stack.getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_ITEM)
		.map(cap -> (13.0 * cap.getGasInTank(0).getAmount() / cap.getTankCapacity(0))).orElse(13.0));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
	return stack.getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_ITEM)
		.map(cap -> (13.0 * cap.getGasInTank(0).getAmount() / cap.getTankCapacity(0) < 13.0)).orElse(false);
    }

}
