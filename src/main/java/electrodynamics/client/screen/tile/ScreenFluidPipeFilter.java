package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerFluidPipeFilter;
import electrodynamics.common.tile.network.fluid.TileFluidPipeFilter;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentFluidFilter;
import electrodynamics.prefab.screen.component.button.type.ButtonSwappableLabel;
import electrodynamics.prefab.utilities.TextUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenFluidPipeFilter extends GenericScreen<ContainerFluidPipeFilter> {

	public ScreenFluidPipeFilter(ContainerFluidPipeFilter screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);

		imageHeight += 20;
		inventoryLabelY += 20;

		addComponent(new ScreenComponentFluidFilter(30, 18, 0));
		addComponent(new ScreenComponentFluidFilter(64, 18, 1));
		addComponent(new ScreenComponentFluidFilter(99, 18, 2));
		addComponent(new ScreenComponentFluidFilter(132, 18, 3));

		addComponent(new ButtonSwappableLabel(38, 70, 100, 20, TextUtils.gui("filter.blacklist"), TextUtils.gui("filter.whitelist"), () -> {
			TileFluidPipeFilter filter = menu.getHostFromIntArray();
			if (filter == null) {
				return false;
			}
			return filter.isWhitelist.get();
		}).setOnPress(button -> {
			TileFluidPipeFilter filter = menu.getHostFromIntArray();
			if (filter == null) {
				return;
			}
			filter.isWhitelist.set(!filter.isWhitelist.get());
			filter.isWhitelist.updateServer();

		}).setScreen(this));

	}

}
