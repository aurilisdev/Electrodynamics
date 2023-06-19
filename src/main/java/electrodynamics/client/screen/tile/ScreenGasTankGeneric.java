package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerGasTankGeneric;
import electrodynamics.common.tile.tanks.gas.GenericTileGasTank;
import electrodynamics.prefab.screen.component.ScreenComponentCondensedFluid;
import electrodynamics.prefab.screen.component.ScreenComponentGasGauge;
import electrodynamics.prefab.screen.component.ScreenComponentGasPressure;
import electrodynamics.prefab.screen.component.ScreenComponentGasTemperature;
import electrodynamics.prefab.screen.component.ScreenComponentGeneric;
import electrodynamics.prefab.screen.component.ScreenComponentProgress.ProgressTextures;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.screen.types.GenericMaterialScreen;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentGasHandlerSimple;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenGasTankGeneric extends GenericMaterialScreen<ContainerGasTankGeneric> {

	public ScreenGasTankGeneric(ContainerGasTankGeneric container, Inventory inv, Component titleIn) {
		super(container, inv, titleIn);
		addComponent(new ScreenComponentGeneric(ProgressTextures.ARROW_RIGHT_OFF, 52, 33));
		addComponent(new ScreenComponentGeneric(ProgressTextures.ARROW_RIGHT_OFF, 102, 33));
		addComponent(new ScreenComponentGasGauge(() -> {
			GenericTileGasTank boiler = menu.getHostFromIntArray();
			if (boiler != null) {
				return boiler.<ComponentGasHandlerSimple>getComponent(ComponentType.GasHandler);
			}
			return null;
		}, 81, 18));
		addComponent(new ScreenComponentGasTemperature(-AbstractScreenComponentInfo.SIZE + 1, 2 + AbstractScreenComponentInfo.SIZE));
		addComponent(new ScreenComponentGasPressure(-AbstractScreenComponentInfo.SIZE + 1, 2));
		addComponent(new ScreenComponentCondensedFluid(() -> {
			GenericTileGasTank generic = container.getHostFromIntArray();
			if (generic == null) {
				return null;
			}

			return generic.condensedFluidFromGas;

		}, 134, 55));
	}

}
