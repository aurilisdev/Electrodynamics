package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerAdvancedCompressor;
import electrodynamics.common.tile.pipelines.gas.gastransformer.compressor.GenericTileAdvancedCompressor;
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

public class ScreenAdvancedCompressor extends GenericMaterialScreen<ContainerAdvancedCompressor> {
    public ScreenAdvancedCompressor(ContainerAdvancedCompressor container, Inventory inv, Component titleIn) {
	super(container, inv, titleIn);
	inventoryLabelY += 47;
	imageHeight += 47;
	addComponent(new ScreenComponentGeneric(ScreenComponentProgress.ProgressTextures.COMPRESS_ARROW_OFF, 65, 40)
		.onTooltip((graphics, component, xAxis, yAxis) -> graphics.renderTooltip(getFontRenderer(),
			Component.literal("x" + container.getSafeHost().pressureMultiplier.getValue()), xAxis, yAxis)));
	addComponent(new ScreenComponentGasGauge(() -> {
	    GenericTileAdvancedCompressor.TileAdvancedCompressor boiler = container.getSafeHost();
	    if (boiler != null) {
		return boiler.<ComponentGasHandlerMulti>getComponent(IComponentType.GasHandler).getInputTanks()[0];
	    }
	    return null;
	}, 41, 18));
	addComponent(new ScreenComponentGasGauge(() -> {
	    GenericTileAdvancedCompressor.TileAdvancedCompressor boiler = container.getSafeHost();
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
	    GenericTileAdvancedCompressor.TileAdvancedCompressor generic = container.getSafeHost();
	    if (generic == null) {
		return null;
	    }

	    return generic.condensedFluidFromGas;

	}, 110, 20));

	addComponent(new ScreenComponentButton<>(8, 75, 40, 20).setLabel(Component.literal("x2")).setOnPress(button -> {
	    GenericTileAdvancedCompressor.TileAdvancedCompressor compressor = menu.getSafeHost();
	    if (compressor == null) {
		return;
	    }
	    compressor.pressureMultiplier.setValue(2.0);
	}));
	addComponent(
		new ScreenComponentButton<>(48, 75, 40, 20).setLabel(Component.literal("x4")).setOnPress(button -> {
		    GenericTileAdvancedCompressor.TileAdvancedCompressor compressor = menu.getSafeHost();
		    if (compressor == null) {
			return;
		    }
		    compressor.pressureMultiplier.setValue(4.0);
		}));
	addComponent(
		new ScreenComponentButton<>(88, 75, 40, 20).setLabel(Component.literal("x8")).setOnPress(button -> {
		    GenericTileAdvancedCompressor.TileAdvancedCompressor compressor = menu.getSafeHost();
		    if (compressor == null) {
			return;
		    }
		    compressor.pressureMultiplier.setValue(8.0);
		}));
	addComponent(
		new ScreenComponentButton<>(128, 75, 40, 20).setLabel(Component.literal("x16")).setOnPress(button -> {
		    GenericTileAdvancedCompressor.TileAdvancedCompressor compressor = menu.getSafeHost();
		    if (compressor == null) {
			return;
		    }
		    compressor.pressureMultiplier.setValue(16.0);
		}));
	addComponent(
		new ScreenComponentButton<>(8, 95, 40, 20).setLabel(Component.literal("x32")).setOnPress(button -> {
		    GenericTileAdvancedCompressor.TileAdvancedCompressor compressor = menu.getSafeHost();
		    if (compressor == null) {
			return;
		    }
		    compressor.pressureMultiplier.setValue(32.0);
		}));
	addComponent(
		new ScreenComponentButton<>(48, 95, 40, 20).setLabel(Component.literal("x64")).setOnPress(button -> {
		    GenericTileAdvancedCompressor.TileAdvancedCompressor compressor = menu.getSafeHost();
		    if (compressor == null) {
			return;
		    }
		    compressor.pressureMultiplier.setValue(64.0);
		}));
	addComponent(
		new ScreenComponentButton<>(88, 95, 40, 20).setLabel(Component.literal("x128")).setOnPress(button -> {
		    GenericTileAdvancedCompressor.TileAdvancedCompressor compressor = menu.getSafeHost();
		    if (compressor == null) {
			return;
		    }
		    compressor.pressureMultiplier.setValue(128.0);
		}));
	addComponent(
		new ScreenComponentButton<>(128, 95, 40, 20).setLabel(Component.literal("x256")).setOnPress(button -> {
		    GenericTileAdvancedCompressor.TileAdvancedCompressor compressor = menu.getSafeHost();
		    if (compressor == null) {
			return;
		    }
		    compressor.pressureMultiplier.setValue(256.0);
		}));
    }
}
