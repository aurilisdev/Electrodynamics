package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerDecompressor;
import electrodynamics.common.tile.gastransformer.compressor.GenericTileCompressor;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentCondensedFluid;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentGasGauge;
import electrodynamics.prefab.screen.component.ScreenComponentGasInput;
import electrodynamics.prefab.screen.component.ScreenComponentGasPressure;
import electrodynamics.prefab.screen.component.ScreenComponentGasTemperature;
import electrodynamics.prefab.screen.component.ScreenComponentGeneric;
import electrodynamics.prefab.screen.component.ScreenComponentProgress.ProgressTextures;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentGasHandlerMulti;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenDecompressor extends GenericScreen<ContainerDecompressor> {

	public ScreenDecompressor(ContainerDecompressor container, Inventory inv, Component titleIn) {
		super(container, inv, titleIn);
		components.add(new ScreenComponentGeneric(ProgressTextures.DECOMPRESS_ARROW_OFF, this, 65, 40));
		components.add(new ScreenComponentGasInput(() -> {
			GenericTileCompressor boiler = container.getHostFromIntArray();
			if (boiler != null) {
				return boiler.<ComponentGasHandlerMulti>getComponent(ComponentType.GasHandler).getInputTanks()[0];
			}
			return null;
		}, this, 41, 18));
		components.add(new ScreenComponentGasGauge(() -> {
			GenericTileCompressor boiler = container.getHostFromIntArray();
			if (boiler != null) {
				return boiler.<ComponentGasHandlerMulti>getComponent(ComponentType.GasHandler).getOutputTanks()[0];
			}
			return null;
		}, this, 90, 18));
		components.add(new ScreenComponentGasTemperature(this, -AbstractScreenComponentInfo.SIZE + 1, 2 + AbstractScreenComponentInfo.SIZE * 2));
		components.add(new ScreenComponentGasPressure(this, -AbstractScreenComponentInfo.SIZE + 1, 2 + AbstractScreenComponentInfo.SIZE));
		components.add(new ScreenComponentElectricInfo(this, -AbstractScreenComponentInfo.SIZE + 1, 2));
		components.add(new ScreenComponentCondensedFluid(this, () -> {
			GenericTileCompressor generic = container.getHostFromIntArray();
			if (generic == null) {
				return null;
			}

			return generic.condensedFluidFromGas;

		}, 110, 20));
	}

}
