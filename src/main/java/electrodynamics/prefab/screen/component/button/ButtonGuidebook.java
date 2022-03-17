package electrodynamics.prefab.screen.component.button;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.References;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class ButtonGuidebook extends PageButton {

	private static final ResourceLocation TEXTURE = new ResourceLocation(References.ID, "textures/screen/component/buttons.png");

	private ButtonType type;

	public ButtonGuidebook(int pX, int pY, OnPress pOnPress, ButtonType type) {
		super(pX, pY, false, pOnPress, true);
		this.type = type;
	}

	@Override
	public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, TEXTURE);
		int x = type.xOffset;
		int y = type.yOffset;

		if (isHoveredOrFocused()) {
			x = type.xOffsetHover;
			y = type.yOffsetHover;
		}

		this.blit(pPoseStack, this.x, this.y, x, y, 11, 10);
	}

	public enum ButtonType {

		HOME(0, 0, 11, 0),
		CHAPTERS(0, 10, 11, 10);

		public final int xOffset;
		public final int yOffset;
		public final int xOffsetHover;
		public final int yOffsetHover;

		ButtonType(int xOffset, int yOffset, int xOffsetHover, int yOffsetHover) {
			this.xOffset = xOffset;
			this.yOffset = yOffset;
			this.xOffsetHover = xOffsetHover;
			this.yOffsetHover = yOffsetHover;
		}

	}

}
