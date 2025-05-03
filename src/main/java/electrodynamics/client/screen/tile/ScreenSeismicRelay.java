package electrodynamics.client.screen.tile;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.inventory.container.tile.ContainerSeismicRelay;
import electrodynamics.common.tile.machines.quarry.TileSeismicRelay;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.types.ScreenComponentMultiLabel;
import voltaic.prefab.screen.component.types.ScreenComponentSimpleLabel;
import voltaic.prefab.utilities.math.Color;

public class ScreenSeismicRelay extends GenericScreen<ContainerSeismicRelay> {

	public ScreenSeismicRelay(ContainerSeismicRelay container, Inventory inv, Component titleIn) {
		super(container, inv, titleIn);
		addComponent(new ScreenComponentSimpleLabel(70, 20, 10, Color.TEXT_GRAY, ElectroTextUtils.gui("seismicrelay.dataheader")));
		addComponent(new ScreenComponentMultiLabel(0, 0, poseStack -> {

			TileSeismicRelay relay = menu.getSafeHost();

			if (relay != null) {
				List<BlockPos> markers = relay.markerLocs.getValue();
				if (!markers.isEmpty()) {
					renderCoordinate(poseStack, markers.get(0), 0, 1);
				} else {
					renderNotFound(poseStack, 0, 1);
				}
				if (markers.size() > 1) {
					renderCoordinate(poseStack, markers.get(1), 10, 2);
				} else {
					renderNotFound(poseStack, 10, 2);
				}
				if (markers.size() > 2) {
					renderCoordinate(poseStack, markers.get(2), 20, 3);
				} else {
					renderNotFound(poseStack, 20, 3);
				}
				if (markers.size() > 3) {
					renderCoordinate(poseStack, markers.get(3), 30, 4);
				} else {
					renderNotFound(poseStack, 30, 4);
				}
			} else {
				renderNotFound(poseStack, 0, 1);
				renderNotFound(poseStack, 10, 2);
				renderNotFound(poseStack, 20, 3);
				renderNotFound(poseStack, 30, 4);
			}
		}));
	}

	private void renderNotFound(PoseStack poseStack, int offset, int index) {
		font.draw(poseStack, ElectroTextUtils.gui("seismicrelay.posnotfound", index), 80, 30 + offset, Color.TEXT_GRAY.color());
	}

	private void renderCoordinate(PoseStack poseStack, BlockPos pos, int offset, int index) {
		font.draw(poseStack, ElectroTextUtils.gui("seismicrelay.posfound", index, pos.toShortString()), 80, 30 + offset, Color.TEXT_GRAY.color());
	}

}
