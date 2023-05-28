package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.ContainerGasPipeFilter;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentGasFilter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenGasPipeFilter extends GenericScreen<ContainerGasPipeFilter> {

	public ScreenGasPipeFilter(ContainerGasPipeFilter screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);
		
		components.add(new ScreenComponentGasFilter(this, 30, 20, 0));
		components.add(new ScreenComponentGasFilter(this, 64, 20, 1));
		components.add(new ScreenComponentGasFilter(this, 99, 20, 2));
		components.add(new ScreenComponentGasFilter(this, 132, 20, 3));
		
	}

}
