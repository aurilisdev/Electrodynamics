package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerFluidPipeFilter;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentFluidFilter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenFluidPipeFilter extends GenericScreen<ContainerFluidPipeFilter> {

	public ScreenFluidPipeFilter(ContainerFluidPipeFilter screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);
		
		components.add(new ScreenComponentFluidFilter(this, 30, 20, 0));
		components.add(new ScreenComponentFluidFilter(this, 64, 20, 1));
		components.add(new ScreenComponentFluidFilter(this, 99, 20, 2));
		components.add(new ScreenComponentFluidFilter(this, 132, 20, 3));
		
	}

}
