package electrodynamics.client.screen.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.inventory.container.tile.ContainerCreativeFluidSource;
import electrodynamics.common.tile.TileCreativeFluidSource;
import electrodynamics.prefab.screen.component.ScreenComponentFluidGauge;
import electrodynamics.prefab.screen.component.ScreenComponentGeneric;
import electrodynamics.prefab.screen.component.ScreenComponentProgress.ProgressTextures;
import electrodynamics.prefab.screen.types.GenericMaterialScreen;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerSimple;
import electrodynamics.prefab.utilities.TextUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenCreativeFluidSource extends GenericMaterialScreen<ContainerCreativeFluidSource> {

	public ScreenCreativeFluidSource(ContainerCreativeFluidSource container, Inventory inv, Component titleIn) {
		super(container, inv, titleIn);
		addComponent(new ScreenComponentGeneric(ProgressTextures.ARROW_RIGHT_OFF, 102, 33));
		addComponent(new ScreenComponentFluidGauge(() -> {
			TileCreativeFluidSource boiler = menu.getHostFromIntArray();
			if (boiler != null) {
				return boiler.<ComponentFluidHandlerSimple>getComponent(ComponentType.FluidHandler);
			}
			return null;
		}, 81, 18));
	}

	@Override
	protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
		super.renderLabels(matrixStack, mouseX, mouseY);
		TileCreativeFluidSource boiler = menu.getHostFromIntArray();
		if (boiler != null) {
			font.draw(matrixStack, TextUtils.gui("creativefluidsource.setfluid"), 13, 38.5f, 4210752);
		}
	}

}
