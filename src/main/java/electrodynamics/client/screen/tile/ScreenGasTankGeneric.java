package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerGasTankGeneric;
import electrodynamics.common.tile.pipelines.gas.tank.GenericTileGasTank;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import voltaic.prefab.screen.component.ScreenComponentGeneric;
import voltaic.prefab.screen.component.types.ScreenComponentCondensedFluid;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.gauges.ScreenComponentGasGauge;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentGasPressure;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentGasTemperature;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.screen.types.GenericMaterialScreen;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentGasHandlerSimple;

public class ScreenGasTankGeneric extends GenericMaterialScreen<ContainerGasTankGeneric> {

    public ScreenGasTankGeneric(ContainerGasTankGeneric container, Inventory inv, Component titleIn) {
	super(container, inv, titleIn);
	addComponent(new ScreenComponentGeneric(ScreenComponentProgress.ProgressTextures.ARROW_RIGHT_OFF, 52, 19));
	addComponent(new ScreenComponentGeneric(ScreenComponentProgress.ProgressTextures.ARROW_LEFT_OFF, 52, 49));
	addComponent(new ScreenComponentGasGauge(() -> {
	    GenericTileGasTank boiler = menu.getSafeHost();
	    if (boiler != null) {
		return boiler.<ComponentGasHandlerSimple>getComponent(IComponentType.GasHandler);
	    }
	    return null;
	}, 81, 18));
	addComponent(new ScreenComponentGasTemperature(-AbstractScreenComponentInfo.SIZE + 1,
		2 + AbstractScreenComponentInfo.SIZE));
	addComponent(new ScreenComponentGasPressure(-AbstractScreenComponentInfo.SIZE + 1, 2));
	addComponent(new ScreenComponentCondensedFluid(() -> {
	    GenericTileGasTank generic = container.getSafeHost();
	    if (generic == null) {
		return null;
	    }

	    return generic.condensedFluidFromGas;

	}, 105, 36));
    }

}
