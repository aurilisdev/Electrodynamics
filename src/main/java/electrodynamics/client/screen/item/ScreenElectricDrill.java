package electrodynamics.client.screen.item;

import electrodynamics.common.inventory.container.item.ContainerElectricDrill;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import voltaic.prefab.screen.GenericScreen;

public class ScreenElectricDrill extends GenericScreen<ContainerElectricDrill> {

	public ScreenElectricDrill(ContainerElectricDrill screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);
	}

}
