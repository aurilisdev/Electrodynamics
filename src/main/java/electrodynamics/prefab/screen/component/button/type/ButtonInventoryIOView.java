package electrodynamics.prefab.screen.component.button.type;

import electrodynamics.api.screen.ITexture;
import electrodynamics.prefab.screen.component.button.ScreenComponentButton;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.IconType;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentGuiTab.GuiInfoTabTextures;
import net.minecraft.client.gui.GuiGraphics;

public class ButtonInventoryIOView extends ScreenComponentButton<ButtonInventoryIOView> {

	public boolean isPressed = false;

	public ButtonInventoryIOView(int x, int y) {
		super(GuiInfoTabTextures.REGULAR, x, y);
	}

	@Override
	public void renderBackground(GuiGraphics graphics, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		super.renderBackground(graphics, xAxis, yAxis, guiWidth, guiHeight);

		ITexture icon = IconType.INVENTORY_IO;

		int slotXOffset = (texture.imageWidth() - icon.imageWidth()) / 2;
		int slotYOffset = (texture.imageHeight() - icon.imageHeight()) / 2;
		graphics.blit(icon.getLocation(), guiWidth + xLocation + slotXOffset, guiHeight + yLocation + slotYOffset, icon.textureU(), icon.textureV(), icon.textureWidth(), icon.textureHeight(), icon.imageWidth(), icon.imageHeight());
	}

}
