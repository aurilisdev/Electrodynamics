package electrodynamics.client.screen.tile;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.common.inventory.container.tile.ContainerGasCollector;
import electrodynamics.common.reloadlistener.GasCollectorChromoCardsRegister;
import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.common.tile.pipelines.gas.TileGasCollector;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.api.gas.GasStack;
import voltaic.prefab.screen.component.types.ScreenComponentCondensedFluid;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.gauges.ScreenComponentGasGauge;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentGasPressure;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentGasTemperature;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.screen.types.GenericMaterialScreen;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentGasHandlerSimple;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentProcessor;
import voltaic.prefab.utilities.VoltaicTextUtils;

public class ScreenGasCollector extends GenericMaterialScreen<ContainerGasCollector> {
    public ScreenGasCollector(ContainerGasCollector container, Inventory inv, Component titleIn) {
	super(container, inv, titleIn);
	addComponent(new ScreenComponentGasGauge(() -> {
	    TileGasCollector boiler = container.getSafeHost();
	    if (boiler != null) {
		return boiler.<ComponentGasHandlerSimple>getComponent(IComponentType.GasHandler);
	    }
	    return null;
	}, 90, 18));
	addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.FAN, () -> {
	    GenericTile furnace = container.getSafeHost();
	    if (furnace != null) {
		ComponentProcessor processor = furnace.getComponent(IComponentType.Processor);
		if (processor.isActive(0)) {
		    return 1.0;
		}
	    }
	    return 0;
	}, 57, 34).onTooltip((graphics, component, xAxis, yAxis) -> {
	    TileGasCollector boiler = container.getSafeHost();
	    if (boiler == null) {
		return;
	    }
	    ComponentProcessor processor = boiler.getComponent(IComponentType.Processor);
	    if (!processor.isActive(0)) {
		return;
	    }
	    ComponentInventory inventory = boiler.getComponent(IComponentType.Inventory);
	    GasCollectorChromoCardsRegister.AtmosphericResult result = GasCollectorChromoCardsRegister.INSTANCE
		    .getResult(inventory.getItem(TileGasCollector.CARD_SLOT).getItem());
	    GasStack stack = result.stack();
	    List<FormattedCharSequence> text = new ArrayList<>();
	    text.add(stack.getGas().getDescription().copy().withStyle(ChatFormatting.GRAY).getVisualOrderText());
	    text.add(VoltaicTextUtils
		    .ratio(ChatFormatter.getChatDisplayShort(stack.getAmount() / 1000.0, DisplayUnits.BUCKETS),
			    DisplayUnits.TIME_TICKS.getSymbol())
		    .withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
	    text.add(ChatFormatter.getChatDisplayShort(stack.getTemperature(), DisplayUnits.TEMPERATURE_KELVIN)
		    .withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
	    text.add(ChatFormatter.getChatDisplayShort(stack.getPressure(), DisplayUnits.PRESSURE_ATM)
		    .withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
	    graphics.renderTooltip(getFontRenderer(), text, xAxis, yAxis);
	}));
	addComponent(new ScreenComponentGasTemperature(-AbstractScreenComponentInfo.SIZE + 1,
		2 + AbstractScreenComponentInfo.SIZE * 2));
	addComponent(new ScreenComponentGasPressure(-AbstractScreenComponentInfo.SIZE + 1,
		2 + AbstractScreenComponentInfo.SIZE));
	addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2)
		.wattage(ElectrodynamicsConfig.INSTANCE.GAS_COLLECTOR_USAGE_PER_TICK.get() * 20));
	addComponent(new ScreenComponentCondensedFluid(() -> {
	    TileGasCollector electric = container.getSafeHost();
	    if (electric == null) {
		return null;
	    }

	    return electric.condensedFluidFromGas;

	}, 122, 20));
    }
}
