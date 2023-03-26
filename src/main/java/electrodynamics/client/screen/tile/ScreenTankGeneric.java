package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerTankGeneric;
import electrodynamics.common.tile.tanks.GenericTileTank;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentFluid;
import electrodynamics.prefab.screen.component.ScreenComponentGeneric;
import electrodynamics.prefab.screen.component.ScreenComponentProgress.ProgressTextures;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerSimple;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenTankGeneric extends GenericScreen<ContainerTankGeneric> {

	public ScreenTankGeneric(ContainerTankGeneric screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);

		components.add(new ScreenComponentGeneric(ProgressTextures.ARROW_RIGHT_OFF, this, 52, 33));
		components.add(new ScreenComponentGeneric(ProgressTextures.ARROW_RIGHT_OFF, this, 102, 33));
		components.add(new ScreenComponentFluid(() -> {
			GenericTileTank boiler = menu.getHostFromIntArray();
			if (boiler != null) {
				return boiler.<ComponentFluidHandlerSimple>getComponent(ComponentType.FluidHandler);
			}
			return null;
		}, this, 81, 18));
	}

}
