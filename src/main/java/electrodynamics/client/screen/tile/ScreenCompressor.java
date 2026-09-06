package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerCompressor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import voltaic.prefab.screen.component.ScreenComponentGeneric;
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

public class ScreenCompressor extends GenericMaterialScreen<ContainerCompressor> {

    public ScreenCompressor(ContainerCompressor container, Inventory inv, Component titleIn) {
	super(container, inv, titleIn);
	addComponent(new ScreenComponentGeneric(ScreenComponentProgress.ProgressTextures.COMPRESS_ARROW_OFF, 65, 40));
	addComponent(new ScreenComponentGasGauge(() -> container.getSafeHost().map(
		tile -> tile.<ComponentGasHandlerMulti>requireComponent(IComponentType.GasHandler).getInputTanks()[0])
		.orElse(null), 41, 18));
	addComponent(new ScreenComponentGasGauge(() -> container.getSafeHost().map(
		tile -> tile.<ComponentGasHandlerMulti>requireComponent(IComponentType.GasHandler).getOutputTanks()[0])
		.orElse(null), 90, 18));
	addComponent(new ScreenComponentGasTemperature(-AbstractScreenComponentInfo.SIZE + 1,
		2 + AbstractScreenComponentInfo.SIZE * 2));
	addComponent(new ScreenComponentGasPressure(-AbstractScreenComponentInfo.SIZE + 1,
		2 + AbstractScreenComponentInfo.SIZE));
	addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2));
	addComponent(new ScreenComponentCondensedFluid(
		() -> container.getSafeHost().map(tile -> tile.condensedFluidFromGas).orElse(null), 110, 20));
    }
}