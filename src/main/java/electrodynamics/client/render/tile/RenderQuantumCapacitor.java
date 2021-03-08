package electrodynamics.client.render.tile;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import electrodynamics.common.tile.quantumcapacitor.TileQuantumCapacitor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Vector3f;

public class RenderQuantumCapacitor extends TileEntityRenderer<TileQuantumCapacitor> {

    public RenderQuantumCapacitor(TileEntityRendererDispatcher rendererDispatcherIn) {
	super(rendererDispatcherIn);
    }

    @Deprecated
    public static void renderStar(float time, int starFrags, float r, float g, float b, float a, boolean star) {
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
    }

    @Override
    @Deprecated
    public void render(TileQuantumCapacitor tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
	    IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
	matrixStackIn.translate(0.5, 0.5, 0.5);
	GlStateManager.pushMatrix();
	RenderSystem.multMatrix(matrixStackIn.getLast().getMatrix());
	matrixStackIn.rotate(Minecraft.getInstance().getRenderManager().getCameraOrientation());
	matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F));
	float scale = 0.005f;
	GlStateManager.scalef(scale, scale, scale);
	float distance = (float) Math
		.sqrt(1 + Minecraft.getInstance().player.getDistanceSq(tileEntityIn.getPos().getX() + 0.5,
			tileEntityIn.getPos().getY() + 0.5, tileEntityIn.getPos().getZ() + 0.5));
	renderStar(tileEntityIn.getWorld().getWorldInfo().getDayTime(), (int) (250 / distance),
		tileEntityIn.getWorld().rand.nextFloat() * 0.2f + 0.2f, 0, 0, 1, false);
	renderStar(tileEntityIn.getWorld().getWorldInfo().getDayTime() + 20, (int) (250 / distance),
		tileEntityIn.getWorld().rand.nextFloat() * 0.1f + 0.4f, 0, 0, 1, false);
	renderStar(tileEntityIn.getWorld().getWorldInfo().getDayTime() + 40, (int) (250 / distance),
		tileEntityIn.getWorld().rand.nextFloat() * 0.3f + 0.5f, 0, 0, 1, false);
	GlStateManager.popMatrix();
    }

}
