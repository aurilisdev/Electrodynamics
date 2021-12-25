package electrodynamics.client.screen.item;

import electrodynamics.common.inventory.container.item.ContainerSeismicScanner;
import electrodynamics.prefab.screen.GenericScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenSeismicScanner extends GenericScreen<ContainerSeismicScanner> {

	public ScreenSeismicScanner(ContainerSeismicScanner screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);
	}

}
