package electrodynamics.prefab.screen.component;

import java.awt.Rectangle;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;

import electrodynamics.api.References;
import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.prefab.utilities.UtilitiesRendering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ScreenComponentGauge extends ScreenComponent {
    private static int WIDTH = 14;
    protected static int HEIGHT = 49;

    protected ScreenComponentGauge(IScreenWrapper gui, int x, int y) {
	super(new ResourceLocation(References.ID + ":textures/screen/component/fluid.png"), gui, x, y);
    }

    @Override
    public Rectangle getBounds(int guiWidth, int guiHeight) {
	return new Rectangle(guiWidth + xLocation, guiHeight + yLocation, WIDTH, HEIGHT);
    }

    @Override
    public void renderBackground(MatrixStack stack, int xAxis, int yAxis, int guiWidth, int guiHeight) {
	UtilitiesRendering.bindTexture(resource);

	gui.drawTexturedRect(stack, guiWidth + xLocation, guiHeight + yLocation, 0, 0, WIDTH, HEIGHT);

	ResourceLocation texture = getTexture();
	int scale = getScaledLevel();

	if (texture != null && scale > 0) {
	    ResourceLocation blocks = AtlasTexture.LOCATION_BLOCKS_TEXTURE;
	    UtilitiesRendering.bindTexture(blocks);
	    TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasSpriteGetter(blocks).apply(texture);
	    applyColor();
	    GlStateManager.enableBlend();
	    GlStateManager.enableAlphaTest();
	    for (int i = 0; i < 16; i += 16) {
		for (int j = 0; j < scale; j += 16) {
		    int drawWidth = Math.min(WIDTH - 2 - i, 16);
		    int drawHeight = Math.min(scale - j, HEIGHT);

		    int drawX = guiWidth + xLocation + 1;
		    int drawY = guiHeight + yLocation - 1 + HEIGHT - drawHeight;

		    int minU = (int) sprite.getMinU();
		    int maxU = (int) sprite.getMaxU();
		    int minV = (int) sprite.getMinV();
		    int maxV = (int) sprite.getMaxV();

		    Tessellator tessellator = Tessellator.getInstance();
		    BufferBuilder tes = tessellator.getBuffer();
		    tes.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		    tes.pos(drawX, drawY + drawHeight, 0).tex(minU, minV + (maxV - minV) * drawHeight / 16F).endVertex();
		    tes.pos(drawX + drawWidth, drawY + drawHeight, 0)
			    .tex(minU + (maxU - minU) * drawWidth / 16F, minV + (maxV - minV) * drawHeight / 16F).endVertex();
		    tes.pos(drawX + drawWidth, drawY, 0).tex(minU + (maxU - minU) * drawWidth / 16F, minV).endVertex();
		    tes.pos(drawX, drawY, 0).tex(minU, minV).endVertex();
		    tessellator.draw();
		}
	    }
	    GlStateManager.disableAlphaTest();
	    GlStateManager.disableBlend();
	    GlStateManager.color4f(1, 1, 1, 1);
	}

	UtilitiesRendering.bindTexture(resource);

	gui.drawTexturedRect(stack, guiWidth + xLocation, guiHeight + yLocation, WIDTH, 0, WIDTH, HEIGHT);
    }

    protected abstract void applyColor();

    @Override
    public void renderForeground(MatrixStack stack, int xAxis, int yAxis) {
	if (isPointInRegion(xLocation, yLocation, xAxis, yAxis, WIDTH, HEIGHT)) {
	    ITextComponent tooltip = getTooltip();

	    if (tooltip != null && !tooltip.getString().isEmpty()) {
		gui.displayTooltip(stack, tooltip, xAxis, yAxis);
	    }
	}
    }

    protected abstract int getScaledLevel();

    protected abstract ResourceLocation getTexture();

    protected abstract ITextComponent getTooltip();
}