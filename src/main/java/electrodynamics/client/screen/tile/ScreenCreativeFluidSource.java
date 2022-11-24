package electrodynamics.client.screen.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.inventory.container.tile.ContainerCreativeFluidSource;
import electrodynamics.common.tile.TileCreativeFluidSource;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentFluid;
import electrodynamics.prefab.screen.component.ScreenComponentProgress;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.generic.AbstractFluidHandler;
import electrodynamics.prefab.utilities.TextUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenCreativeFluidSource extends GenericScreen<ContainerCreativeFluidSource> {

	public ScreenCreativeFluidSource(ContainerCreativeFluidSource container, Inventory inv, Component titleIn) {
		super(container, inv, titleIn);
		components.add(new ScreenComponentProgress(() -> 0, this, 102, 33));
		components.add(new ScreenComponentFluid(() -> {
			TileCreativeFluidSource boiler = menu.getHostFromIntArray();
			if (boiler != null) {
				return ((AbstractFluidHandler<?>) boiler.getComponent(ComponentType.FluidHandler)).getOutputTanks()[0];
			}
			return null;
		}, this, 81, 18));
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
