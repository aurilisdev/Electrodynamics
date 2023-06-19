package electrodynamics.client.screen.tile;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.inventory.container.tile.ContainerSeismicRelay;
import electrodynamics.common.tile.quarry.TileSeismicRelay;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.ScreenComponentMultiLabel;
import electrodynamics.prefab.screen.component.types.ScreenComponentSimpleLabel;
import electrodynamics.prefab.utilities.TextUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenSeismicRelay extends GenericScreen<ContainerSeismicRelay> {

	public ScreenSeismicRelay(ContainerSeismicRelay container, Inventory inv, Component titleIn) {
		super(container, inv, titleIn);
		addComponent(new ScreenComponentSimpleLabel(70, 20, 10, 4210752, TextUtils.gui("seismicrelay.dataheader")));
		addComponent(new ScreenComponentMultiLabel(0, 0, stack -> {

			TileSeismicRelay relay = menu.getHostFromIntArray();

			if (relay != null) {
				List<BlockPos> markers = relay.markerLocs.get();
				if (!markers.isEmpty()) {
					renderCoordinate(stack, markers.get(0), 0, 1);
				} else {
					renderNotFound(stack, 0, 1);
				}
				if (markers.size() > 1) {
					renderCoordinate(stack, markers.get(1), 10, 2);
				} else {
					renderNotFound(stack, 10, 2);
				}
				if (markers.size() > 2) {
					renderCoordinate(stack, markers.get(2), 20, 3);
				} else {
					renderNotFound(stack, 20, 3);
				}
				if (markers.size() > 3) {
					renderCoordinate(stack, markers.get(3), 30, 4);
				} else {
					renderNotFound(stack, 30, 4);
				}
			} else {
				renderNotFound(stack, 0, 1);
				renderNotFound(stack, 10, 2);
				renderNotFound(stack, 20, 3);
				renderNotFound(stack, 30, 4);
			}
		}));
	}

	private void renderNotFound(PoseStack stack, int offset, int index) {
		font.draw(stack, TextUtils.gui("seismicrelay.posnotfound", index), 80, 30 + offset, 4210752);
	}

	private void renderCoordinate(PoseStack stack, BlockPos pos, int offset, int index) {
		font.draw(stack, TextUtils.gui("seismicrelay.posfound", index, pos.toShortString()), 80, 30 + offset, 4210752);
	}

}
