package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerDecompressor;
import electrodynamics.common.tile.pipelines.gas.gastransformer.compressor.GenericTileBasicCompressor;
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

public class ScreenDecompressor extends GenericMaterialScreen<ContainerDecompressor> {

    public ScreenDecompressor(ContainerDecompressor container, Inventory inv, Component titleIn) {
	super(container, inv, titleIn);
	addComponent(new ScreenComponentGeneric(ScreenComponentProgress.ProgressTextures.DECOMPRESS_ARROW_OFF, 65, 40));
	addComponent(new ScreenComponentGasGauge(() -> {
	    GenericTileBasicCompressor.TileDecompressor boiler = container.getSafeHost();
	    if (boiler != null) {
		return boiler.<ComponentGasHandlerMulti>getComponent(IComponentType.GasHandler).getInputTanks()[0];
	    }
	    return null;
	}, 41, 18));
	addComponent(new ScreenComponentGasGauge(() -> {
	    GenericTileBasicCompressor.TileDecompressor boiler = container.getSafeHost();
	    if (boiler != null) {
		return boiler.<ComponentGasHandlerMulti>getComponent(IComponentType.GasHandler).getOutputTanks()[0];
	    }
	    return null;
	}, 90, 18));
	addComponent(new ScreenComponentGasTemperature(-AbstractScreenComponentInfo.SIZE + 1,
		2 + AbstractScreenComponentInfo.SIZE * 2));
	addComponent(new ScreenComponentGasPressure(-AbstractScreenComponentInfo.SIZE + 1,
		2 + AbstractScreenComponentInfo.SIZE));
	addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2));
	addComponent(new ScreenComponentCondensedFluid(() -> {
	    GenericTileBasicCompressor.TileDecompressor generic = container.getSafeHost();
	    if (generic == null) {
		return null;
	    }

	    return generic.condensedFluidFromGas;

	}, 110, 20));
    }

}
