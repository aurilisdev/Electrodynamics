package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerCoolantResavoir;
import electrodynamics.common.tile.quarry.TileCoolantResavoir;
import electrodynamics.prefab.screen.component.ScreenComponentFluidGaugeInput;
import electrodynamics.prefab.screen.component.ScreenComponentGeneric;
import electrodynamics.prefab.screen.component.ScreenComponentProgress.ProgressTextures;
import electrodynamics.prefab.screen.types.GenericMaterialScreen;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerSimple;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenCoolantResavoir extends GenericMaterialScreen<ContainerCoolantResavoir> {

	public ScreenCoolantResavoir(ContainerCoolantResavoir container, Inventory inv, Component titleIn) {
		super(container, inv, titleIn);
		addComponent(new ScreenComponentGeneric(ProgressTextures.ARROW_RIGHT_OFF, 72, 33));
		addComponent(new ScreenComponentFluidGaugeInput(() -> {
			TileCoolantResavoir boiler = menu.getHostFromIntArray();
			if (boiler != null) {
				return boiler.<ComponentFluidHandlerSimple>getComponent(ComponentType.FluidHandler);
			}
			return null;
		}, 101, 18));
	}

}
