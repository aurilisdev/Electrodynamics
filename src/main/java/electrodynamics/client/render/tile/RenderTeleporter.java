package electrodynamics.client.render.tile;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import electrodynamics.client.ClientRegister;
import electrodynamics.common.tile.TileTeleporter;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.UtilitiesRendering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Vector3f;

public class RenderTeleporter extends TileEntityRenderer<TileTeleporter> {

    public RenderTeleporter(TileEntityRendererDispatcher rendererDispatcherIn) {
	super(rendererDispatcherIn);
    }

    @Override
    @Deprecated
    public void render(TileTeleporter tileEntityIn, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int combinedLightIn,
	    int combinedOverlayIn) {
	stack.push();
	IBakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_TELEPORTER);
	stack.translate(0.5, 0.5, 0.5);
	ComponentElectrodynamic electro = tileEntityIn.getComponent(ComponentType.Electrodynamic);
	if (electro.getJoulesStored() > 0) {
	    ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_TELEPORTERON);
	}
	UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getSolid(), stack, bufferIn, combinedLightIn, combinedOverlayIn);
	stack.push();
	stack.scale(0.5f, 0.5f, 0.5f);

	GlStateManager.pushMatrix();
	RenderSystem.multMatrix(stack.getLast().getMatrix());
	stack.rotate(Minecraft.getInstance().getRenderManager().getCameraOrientation());
	stack.rotate(Vector3f.YP.rotationDegrees(180.0F));

	GlStateManager.pushMatrix();
	Tessellator tessellator = Tessellator.getInstance();
	BufferBuilder bufferBuilder = tessellator.getBuffer();
	GlStateManager.disableTexture();
	GlStateManager.disableLighting();
	GlStateManager.shadeModel(GL11.GL_SMOOTH);
	GlStateManager.enableBlend();
	GlStateManager.glBlendFuncSeparate(770, 771, 1, 0);
	GlStateManager.blendFunc(770, 1);
	GlStateManager.disableAlphaTest();
	GlStateManager.enableCull();
	GlStateManager.enableDepthTest();

	GlStateManager.pushMatrix();
	float scale = 10;
	Random rand = tileEntityIn.getWorld().rand;
	GlStateManager.scaled(0.05, 0.08, 0.05);
	double r = 1;
	double g = rand.nextFloat() * 0.4;
	double b = 0;
	double a = 1;
	try {
	    GlStateManager.translated(0, 40, 0);
	    for (int i1 = 0; i1 < (int) scale * 2; i1++) {
		bufferBuilder.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_COLOR);
		GL11.glLineWidth(1 + rand.nextInt(2));
		bufferBuilder
			.pos(rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5,
				rand.nextFloat() * 30 - 15 + rand.nextFloat() * 20 * 2 - 20,
				rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5)
			.color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
		bufferBuilder
			.pos(rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5,
				rand.nextFloat() * 30 - 15 + rand.nextFloat() * 20 * 2 - 20,
				rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5)
			.color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
		bufferBuilder
			.pos(rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5,
				rand.nextFloat() * 30 - 15 + rand.nextFloat() * 20 * 2 - 20,
				rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5)
			.color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
		tessellator.draw();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	GlStateManager.popMatrix();
	GlStateManager.pushMatrix();
	GlStateManager.scaled(0.05, 0.08, 0.05);
	g = rand.nextFloat() * 0.8;
	try {
	    GlStateManager.translated(0, 40, 0);
	    GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
	    for (int i1 = 0; i1 < (int) scale * 2; i1++) {
		bufferBuilder.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_COLOR);
		GL11.glLineWidth(1 + rand.nextInt(2));
		bufferBuilder
			.pos(rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5,
				rand.nextFloat() * 30 - 15 + rand.nextFloat() * 20 * 2 - 20,
				rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5)
			.color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
		bufferBuilder
			.pos(rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5,
				rand.nextFloat() * 30 - 15 + rand.nextFloat() * 20 * 2 - 20,
				rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5)
			.color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
		bufferBuilder
			.pos(rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5,
				rand.nextFloat() * 30 - 15 + rand.nextFloat() * 20 * 2 - 20,
				rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5)
			.color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
		tessellator.draw();
	    }
	    GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	GlStateManager.popMatrix();

	GlStateManager.disableDepthTest();
	GlStateManager.disableBlend();
	GlStateManager.shadeModel(GL11.GL_FLAT);
	GlStateManager.color4f(1, 1, 1, 1);
	GlStateManager.enableTexture();
	GlStateManager.enableLighting();
	GlStateManager.enableAlphaTest();
	GlStateManager.popMatrix();
	GlStateManager.popMatrix();
	stack.pop();
	stack.pop();
    }
}
