package electrodynamics.api.utilities;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class RenderUtilities {

    @Deprecated
    public static void renderStar(float time, int starFrags, float r, float g, float b, float a, boolean star) {
	GlStateManager.pushMatrix();
	Tessellator tessellator = Tessellator.getInstance();
	BufferBuilder bufferBuilder = tessellator.getBuffer();
	GlStateManager.disableTexture();
	GlStateManager.shadeModel(GL11.GL_SMOOTH);
	GlStateManager.enableBlend();
	if (star) {
	    GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
	} else {
	    GlStateManager.blendFunc(GL11.GL_DST_COLOR, GL11.GL_ONE_MINUS_DST_COLOR);
	}
	GlStateManager.disableAlphaTest();
	GlStateManager.enableCull();
	GlStateManager.enableDepthTest();

	GlStateManager.pushMatrix();
	try {
	    float par2 = time * 3 % 180;
	    float var41 = (5.0F + par2) / 200.0F;
	    float var51 = 0.0F;
	    if (var41 > 0.8F) {
		var51 = (var41 - 0.8F) / 0.2F;
	    }
	    Random rand = new Random(432L);
	    for (int i1 = 0; i1 < starFrags; i1++) {
		GL11.glRotatef(rand.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(rand.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(rand.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(rand.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(rand.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(rand.nextFloat() * 360.0F + var41 * 90.0F, 0.0F, 0.0F, 1.0F);
		final float f2 = rand.nextFloat() * 20 + 5 + var51 * 10;
		final float f3 = rand.nextFloat() * 2 + 1 + var51 * 2 + (star ? 0 : 10);
		bufferBuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
		bufferBuilder.pos(0, 0, 0).color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255))
			.endVertex();
		bufferBuilder.pos(-0.866 * f3, f2, -0.5 * f3)
			.color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
		bufferBuilder.pos(0.866 * f3, f2, -0.5 * f3)
			.color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
		bufferBuilder.pos(0, f2, 1 * f3)
			.color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
		bufferBuilder.pos(-0.866 * f3, f2, -0.5 * f3)
			.color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
		tessellator.draw();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	GlStateManager.popMatrix();
	GlStateManager.disableDepthTest();
	GlStateManager.disableBlend();
	GlStateManager.shadeModel(GL11.GL_FLAT);
	GlStateManager.color4f(1, 1, 1, 1);
	GlStateManager.enableTexture();
	GlStateManager.enableAlphaTest();
	GlStateManager.popMatrix();
    }

}
