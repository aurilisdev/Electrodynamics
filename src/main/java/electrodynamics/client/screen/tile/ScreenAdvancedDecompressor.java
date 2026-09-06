package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerAdvancedDecompressor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import voltaic.prefab.screen.component.ScreenComponentGeneric;
import voltaic.prefab.screen.component.button.ScreenComponentButton;
import voltaic.prefab.screen.component.types.ScreenComponentCondensedFluid;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.gauges.ScreenComponentGasGauge;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentGasPressure;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentGasTemperature;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.screen.types.GenericMaterialScreen;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentGasHandlerMulti;

public class ScreenAdvancedDecompressor extends GenericMaterialScreen<ContainerAdvancedDecompressor> {

    public ScreenAdvancedDecompressor(ContainerAdvancedDecompressor container, Inventory inv, Component titleIn) {
	super(container, inv, titleIn);
	inventoryLabelY += 47;
	imageHeight += 47;
	addComponent(new ScreenComponentGeneric(ScreenComponentProgress.ProgressTextures.DECOMPRESS_ARROW_OFF, 65, 40)
		.onTooltip((graphics, component, xAxis, yAxis) -> graphics.renderTooltip(getFontRenderer(),
			Component.literal("1.0 / " + container.getSafeHost()
				.map(compressor -> 1.0 / compressor.pressureMultiplier.getValue()).orElse(1.0)),
			xAxis, yAxis)));
	addComponent(new ScreenComponentGasGauge(() -> container
		.getSafeHost().map(boiler -> boiler
			.<ComponentGasHandlerMulti>requireComponent(IComponentType.GasHandler).getInputTanks()[0])
		.orElse(null), 41, 18));
	addComponent(new ScreenComponentGasGauge(() -> container
		.getSafeHost().map(boiler -> boiler
			.<ComponentGasHandlerMulti>requireComponent(IComponentType.GasHandler).getOutputTanks()[0])
		.orElse(null), 90, 18));
	addComponent(new ScreenComponentGasTemperature(-AbstractScreenComponentInfo.SIZE + 1,
		2 + AbstractScreenComponentInfo.SIZE * 2));
	addComponent(new ScreenComponentGasPressure(-AbstractScreenComponentInfo.SIZE + 1,
		2 + AbstractScreenComponentInfo.SIZE));
	addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2));
	addComponent(new ScreenComponentCondensedFluid(
		() -> container.getSafeHost().map(decompressor -> decompressor.condensedFluidFromGas).orElse(null), 110,
		20));
	int[] divisors = { 2, 4, 8, 16, 32, 64, 128, 256 };
	for (int i = 0; i < divisors.length; i++) {
	    int divisor = divisors[i];
	    addComponent(new ScreenComponentButton<>(8 + i % 4 * 40, 75 + i / 4 * 20, 40, 20)
		    .setLabel(Component.literal("1/" + divisor)).setOnPress(button -> menu.getSafeHost()
			    .ifPresent(decompressor -> decompressor.pressureMultiplier.setValue(1.0 / divisor))));
	}
    }
}