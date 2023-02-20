package electrodynamics.prefab.screen.component.button;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.References;
import electrodynamics.api.screen.ITexture;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ButtonModuleSelector extends ButtonSpecificPage {

	private boolean selected = false;

	public ButtonModuleSelector(int x, int y, int page, boolean selected) {
		super(x, y, 9, 9, page, Component.empty(), button -> {
			ButtonModuleSelector selector = (ButtonModuleSelector) button;
			selector.selected = !selector.selected;
		});
		this.selected = selected;
	}

	@Override
	public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderingUtils.resetColor();

		ITexture texture = GuidebookButtonTextures.CHECKBOX_OFF;

		if (selected) {
			texture = GuidebookButtonTextures.CHECKBOX_ON;
		}
		RenderingUtils.bindTexture(texture.getLocation());
		blit(pPoseStack, this.x, this.y, texture.textureU(), texture.textureV(), texture.textureWidth(), texture.textureHeight(), texture.imageWidth(), texture.imageHeight());
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public static enum GuidebookButtonTextures implements ITexture {
		CHECKBOX_OFF(9, 9, 0, 0, 9, 9, "checkboxoff"), CHECKBOX_ON(9, 9, 0, 0, 9, 9, "checkboxon");

		private final int textureWidth;
		private final int textureHeight;
		private final int textureU;
		private final int textureV;
		private final int imageWidth;
		private final int imageHeight;
		private final ResourceLocation loc;

		private GuidebookButtonTextures(int textureWidth, int textureHeight, int textureU, int textureV, int imageWidth, int imageHeight, String name) {
			this.textureWidth = textureWidth;
			this.textureHeight = textureHeight;
			this.textureU = textureU;
			this.textureV = textureV;
			this.imageWidth = imageWidth;
			this.imageHeight = imageHeight;
			loc = new ResourceLocation(References.ID + ":textures/screen/guidebook/buttons/" + name + ".png");
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
