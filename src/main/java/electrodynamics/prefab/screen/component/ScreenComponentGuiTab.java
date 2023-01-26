package electrodynamics.prefab.screen.component;

import java.util.List;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.References;
import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.api.screen.ITexture;
import electrodynamics.api.screen.component.TextPropertySupplier;
import electrodynamics.prefab.screen.component.ScreenComponentSlot.IconType;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

public class ScreenComponentGuiTab extends AbstractScreenComponentInfo {

	public static final ResourceLocation TEXTURE = new ResourceLocation(References.ID + ":textures/screen/component/screentabs.png");
	private final ITexture iconType;

	public ScreenComponentGuiTab(ITexture texture, ITexture icon, @Nonnull TextPropertySupplier infoHandler, IScreenWrapper gui, int x, int y) {
		super(texture, infoHandler, gui, x, y);
		iconType = icon;
	}

	@Override
	protected List<? extends FormattedCharSequence> getInfo(List<? extends FormattedCharSequence> list) {
		return infoHandler.getInfo();
	}

	@Override
	public void renderBackground(PoseStack stack, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		super.renderBackground(stack, xAxis, yAxis, guiWidth, guiHeight);
		if(iconType == IconType.NONE) {
			return;
		}
		RenderingUtils.bindTexture(iconType.getLocation());
		int slotXOffset = (texture.imageWidth() - iconType.imageWidth()) / 2;
		int slotYOffset = (texture.imageHeight() - iconType.imageHeight()) / 2;
		gui.drawTexturedRect(stack, guiWidth + xLocation + slotXOffset, guiHeight + yLocation + slotYOffset, iconType.textureU(), iconType.textureV(), iconType.textureWidth(), iconType.textureHeight(), iconType.imageWidth(), iconType.imageHeight());
	}

	public static enum GuiInfoTabTextures implements ITexture {
		REGULAR(26, 26, 0, 0, 26, 26, "tab_regular");

		private final int textureWidth;
		private final int textureHeight;
		private final int textureU;
		private final int textureV;
		private final int imageWidth;
		private final int imageHeight;
		private final ResourceLocation loc;

		private GuiInfoTabTextures(int textureWidth, int textureHeight, int textureU, int textureV, int imageWidth, int imageHeight, String name) {
			this.textureWidth = textureWidth;
			this.textureHeight = textureHeight;
			this.textureU = textureU;
			this.textureV = textureV;
			this.imageWidth = imageWidth;
			this.imageHeight = imageHeight;
			this.loc = new ResourceLocation(References.ID + ":textures/screen/component/guitab/" + name + ".png");
		}

		@Override
		public ResourceLocation getLocation() {
			return loc;
		}

		@Override
		public int imageHeight() {
			return imageHeight;
		}

		@Override
		public int imageWidth() {
			return imageWidth;
		}

		@Override
		public int textureHeight() {
			return textureHeight;
		}

		@Override
		public int textureU() {
			return textureU;
		}

		@Override
		public int textureV() {
			return textureV;
		}

		@Override
		public int textureWidth() {
			return textureWidth;
		}

	}

}