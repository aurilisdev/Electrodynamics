package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerCoolantResavoir;
import electrodynamics.common.tile.machines.quarry.TileCoolantResavoir;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import voltaic.prefab.screen.component.ScreenComponentGeneric;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.gauges.ScreenComponentFluidGauge;
import voltaic.prefab.screen.types.GenericMaterialScreen;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerSimple;

public class ScreenCoolantResavoir extends GenericMaterialScreen<ContainerCoolantResavoir> {

    public ScreenCoolantResavoir(ContainerCoolantResavoir container, Inventory inv, Component titleIn) {
	super(container, inv, titleIn);
	addComponent(new ScreenComponentGeneric(ScreenComponentProgress.ProgressTextures.ARROW_RIGHT_OFF, 72, 33));
	addComponent(new ScreenComponentFluidGauge(() -> {
	    TileCoolantResavoir boiler = menu.getSafeHost();
	    if (boiler != null) {
		return boiler.<ComponentFluidHandlerSimple>getComponent(IComponentType.FluidHandler);
	    }
	    return null;
	}, 101, 18));
    }

}
