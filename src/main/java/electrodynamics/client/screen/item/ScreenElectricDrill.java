package electrodynamics.client.screen.item;

import electrodynamics.common.inventory.container.item.ContainerElectricDrill;
import electrodynamics.prefab.screen.GenericScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenElectricDrill extends GenericScreen<ContainerElectricDrill> {

	public ScreenElectricDrill(ContainerElectricDrill screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);
	}

}
