package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerFluidPipeFilter;
import electrodynamics.prefab.screen.component.ScreenComponentFluidFilter;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.button.ScreenComponentButton;

public class ScreenFluidPipeFilter extends GenericScreen<ContainerFluidPipeFilter> {

    public ScreenFluidPipeFilter(ContainerFluidPipeFilter screenContainer, Inventory inv, Component titleIn) {
	super(screenContainer, inv, titleIn);
	imageHeight += 20;
	inventoryLabelY += 20;
	int[] filterPositions = { 30, 64, 99, 132 };
	for (int i = 0; i < filterPositions.length; i++)
	    addComponent(new ScreenComponentFluidFilter(filterPositions[i], 18, i));
	addComponent(new ScreenComponentButton<>(38, 70, 100, 20)
		.setLabel(() -> menu.getSafeHost()
			.map(filter -> filter.isWhitelist.getValue() ? ElectroTextUtils.gui("filter.whitelist")
				: ElectroTextUtils.gui("filter.blacklist"))
			.orElseGet(Component::empty))
		.setOnPress(button -> menu.getSafeHost()
			.ifPresent(filter -> filter.isWhitelist.setValue(!filter.isWhitelist.getValue()))));
    }
}