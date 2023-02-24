package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerCoolantResavoir;
import electrodynamics.common.tile.TileCoolantResavoir;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentFluidInput;
import electrodynamics.prefab.screen.component.ScreenComponentGeneric;
import electrodynamics.prefab.screen.component.ScreenComponentProgress.ProgressTextures;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerSimple;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenCoolantResavoir extends GenericScreen<ContainerCoolantResavoir> {

	public ScreenCoolantResavoir(ContainerCoolantResavoir container, Inventory inv, Component titleIn) {
		super(container, inv, titleIn);
		components.add(new ScreenComponentGeneric(ProgressTextures.ARROW_RIGHT_OFF, this, 72, 33));
		components.add(new ScreenComponentFluidInput(() -> {
			TileCoolantResavoir boiler = menu.getHostFromIntArray();
			if (boiler != null) {
				return boiler.<ComponentFluidHandlerSimple>getComponent(ComponentType.FluidHandler);
			}
			return null;
		}, this, 101, 18));
	}

}
