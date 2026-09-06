package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerGasPipeFilter;
import electrodynamics.prefab.screen.component.ScreenComponentGasFilter;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.button.ScreenComponentButton;

public class ScreenGasPipeFilter extends GenericScreen<ContainerGasPipeFilter> {

    public ScreenGasPipeFilter(ContainerGasPipeFilter screenContainer, Inventory inv, Component titleIn) {
	super(screenContainer, inv, titleIn);
	imageHeight += 20;
	inventoryLabelY += 20;
	int[] filterPositions = { 30, 64, 99, 132 };
	for (int i = 0; i < filterPositions.length; i++)
	    addComponent(new ScreenComponentGasFilter(filterPositions[i], 18, i));
	addComponent(new ScreenComponentButton<>(38, 70, 100, 20)
		.setLabel(() -> menu.getSafeHost()
			.map(filter -> filter.isWhitelist.getValue() ? ElectroTextUtils.gui("filter.whitelist")
				: ElectroTextUtils.gui("filter.blacklist"))
			.orElseGet(Component::empty))
		.setOnPress(button -> menu.getSafeHost()
			.ifPresent(filter -> filter.isWhitelist.setValue(!filter.isWhitelist.getValue()))));
    }
}