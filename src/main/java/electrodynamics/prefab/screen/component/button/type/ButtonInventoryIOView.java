package electrodynamics.prefab.screen.component.button.type;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.screen.ITexture;
import electrodynamics.prefab.screen.component.button.ScreenComponentButton;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.IconType;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentGuiTab.GuiInfoTabTextures;
import electrodynamics.prefab.utilities.RenderingUtils;

public class ButtonInventoryIOView extends ScreenComponentButton<ButtonInventoryIOView> {

	public boolean isPressed = false;

	public ButtonInventoryIOView(int x, int y) {
		super(GuiInfoTabTextures.REGULAR, x, y);
	}

	@Override
	public void renderBackground(MatrixStack stack, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		super.renderBackground(stack, xAxis, yAxis, guiWidth, guiHeight);

		ITexture icon = IconType.INVENTORY_IO;

		int slotXOffset = (texture.imageWidth() - icon.imageWidth()) / 2;
		int slotYOffset = (texture.imageHeight() - icon.imageHeight()) / 2;
		RenderingUtils.bindTexture(icon.getLocation());
		blit(stack, guiWidth + this.x + slotXOffset, guiHeight + this.y + slotYOffset, icon.textureU(), icon.textureV(), icon.textureWidth(), icon.textureHeight(), icon.imageWidth(), icon.imageHeight());
		RenderingUtils.resetShaderColor();
	}

}
