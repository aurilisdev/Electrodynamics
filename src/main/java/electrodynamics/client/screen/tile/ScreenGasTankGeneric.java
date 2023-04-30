package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerGasTankGeneric;
import electrodynamics.common.tile.tanks.gas.GenericTileGasTank;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentCondensedFluid;
import electrodynamics.prefab.screen.component.ScreenComponentGasGauge;
import electrodynamics.prefab.screen.component.ScreenComponentGasPressure;
import electrodynamics.prefab.screen.component.ScreenComponentGasTemperature;
import electrodynamics.prefab.screen.component.ScreenComponentGeneric;
import electrodynamics.prefab.screen.component.ScreenComponentProgress.ProgressTextures;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentGasHandlerSimple;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenGasTankGeneric extends GenericScreen<ContainerGasTankGeneric> {

	public ScreenGasTankGeneric(ContainerGasTankGeneric container, Inventory inv, Component titleIn) {
		super(container, inv, titleIn);
		components.add(new ScreenComponentGeneric(ProgressTextures.ARROW_RIGHT_OFF, this, 52, 33));
		components.add(new ScreenComponentGeneric(ProgressTextures.ARROW_RIGHT_OFF, this, 102, 33));
		components.add(new ScreenComponentGasGauge(() -> {
			GenericTileGasTank boiler = menu.getHostFromIntArray();
			if (boiler != null) {
				return boiler.<ComponentGasHandlerSimple>getComponent(ComponentType.GasHandler);
			}
			return null;
		}, this, 81, 18));
		components.add(new ScreenComponentGasTemperature(this, -AbstractScreenComponentInfo.SIZE + 1, 2 + AbstractScreenComponentInfo.SIZE));
		components.add(new ScreenComponentGasPressure(this, -AbstractScreenComponentInfo.SIZE + 1, 2));
		components.add(new ScreenComponentCondensedFluid(this, () -> {
			GenericTileGasTank generic = container.getHostFromIntArray();
			if (generic == null) {
				return null;
			}

			return generic.condensedFluidFromGas;

		}, 134, 55));
	}

}
