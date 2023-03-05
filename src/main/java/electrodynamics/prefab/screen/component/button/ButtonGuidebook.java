package electrodynamics.prefab.screen.component.button;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.References;
import electrodynamics.api.screen.ITexture;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

public class ButtonGuidebook extends Button {

	private GuidebookButtonType type;

	public ButtonGuidebook(int pX, int pY, OnPress pOnPress, GuidebookButtonType type) {
		super(pX, pY, type.off.textureWidth, type.on.textureHeight, Component.empty(), pOnPress);
		this.type = type;
	}

	@Override
	public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderingUtils.resetColor();

		ITexture texture = type.off;

		if (isHoveredOrFocused()) {
			texture = type.on;
		}
		RenderingUtils.bindTexture(texture.getLocation());
		blit(pPoseStack, this.x, this.y, texture.textureU(), texture.textureV(), texture.textureWidth(), texture.textureHeight(), texture.imageWidth(), texture.imageHeight());
	}

	public static enum GuidebookButtonType {

		HOME(GuidebookButtonTextures.HOME_OFF, GuidebookButtonTextures.HOME_ON), CHAPTERS(GuidebookButtonTextures.CHAPTERS_OFF, GuidebookButtonTextures.CHAPTERS_ON), SEARCH(GuidebookButtonTextures.SEARCH_OFF, GuidebookButtonTextures.SEARCH_ON);

		public final GuidebookButtonTextures off;
		public final GuidebookButtonTextures on;

		GuidebookButtonType(GuidebookButtonTextures off, GuidebookButtonTextures on) {
			this.off = off;
			this.on = on;
		}

	}

	@Override
	public void playDownSound(SoundManager pHandler) {
		pHandler.play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1.0F));

	}

	public static enum GuidebookButtonTextures implements ITexture {
		HOME_OFF(11, 10, 0, 0, 11, 10, "homeoff"), HOME_ON(11, 10, 0, 0, 11, 10, "homeon"), CHAPTERS_OFF(11, 10, 0, 0, 11, 10, "chaptersoff"), CHAPTERS_ON(11, 10, 0, 0, 11, 10, "chapterson"), SEARCH_OFF(11, 10, 0, 0, 11, 10, "searchoff"), SEARCH_ON(11, 10, 0, 0, 11, 10, "searchon");

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
