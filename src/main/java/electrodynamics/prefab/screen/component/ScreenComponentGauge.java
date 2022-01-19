package electrodynamics.prefab.screen.component;

import java.awt.Rectangle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.References;
import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ScreenComponentGauge extends ScreenComponent {
	public static int WIDTH = 14;
	public static int HEIGHT = 49;

	protected ScreenComponentGauge(IScreenWrapper gui, int x, int y) {
		super(new ResourceLocation(References.ID + ":textures/screen/component/fluid.png"), gui, x, y);
	}

	@Override
	public Rectangle getBounds(int guiWidth, int guiHeight) {
		return new Rectangle(guiWidth + xLocation, guiHeight + yLocation, WIDTH, HEIGHT);
	}

	@Override
	@SuppressWarnings("java:S1874")
	public void renderBackground(PoseStack stack, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		RenderingUtils.bindTexture(resource);

		gui.drawTexturedRect(stack, guiWidth + xLocation, guiHeight + yLocation, 0, 0, WIDTH, HEIGHT);

		ResourceLocation texture = getTexture();
		int scale = getScaledLevel();

		if (texture != null && scale > 0) {
			ResourceLocation blocks = InventoryMenu.BLOCK_ATLAS;
			TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(blocks).apply(texture);
			RenderSystem.setShaderTexture(0, sprite.atlas().getId());
			applyColor();
			for (int i = 0; i < 16; i += 16) {
				for (int j = 0; j < scale; j += 16) {
					int drawWidth = Math.min(WIDTH - 2 - i, 16);
					int drawHeight = Math.min(scale - j, 16);

					int drawX = guiWidth + xLocation + 1;
					int drawY = guiHeight + yLocation - 1 + HEIGHT - Math.min(scale - j, HEIGHT);
					GuiComponent.blit(stack, drawX, drawY, 0, drawWidth, drawHeight, sprite);
				}
			}
			RenderSystem.setShaderColor(1, 1, 1, 1);
		}

		RenderingUtils.bindTexture(resource);

		gui.drawTexturedRect(stack, guiWidth + xLocation, guiHeight + yLocation, WIDTH, 0, WIDTH, HEIGHT);
	}

	protected abstract void applyColor();

	@Override
	public void renderForeground(PoseStack stack, int xAxis, int yAxis) {
		if (isPointInRegion(xLocation, yLocation, xAxis, yAxis, WIDTH, HEIGHT)) {
			Component tooltip = getTooltip();

			if (tooltip != null && !tooltip.getString().isEmpty()) {
				gui.displayTooltip(stack, tooltip, xAxis, yAxis);
			}
		}
	}

	protected abstract int getScaledLevel();

	protected abstract ResourceLocation getTexture();

	protected abstract Component getTooltip();
}