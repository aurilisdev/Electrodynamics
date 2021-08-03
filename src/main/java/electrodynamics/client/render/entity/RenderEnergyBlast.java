package electrodynamics.client.render.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import electrodynamics.common.entity.projectile.types.energy.EnergyBlast;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class RenderEnergyBlast extends EntityRenderer<EnergyBlast>{
	
	public RenderEnergyBlast(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public void render(EnergyBlast entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn,
		    int packedLightIn) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();

		GlStateManager.pushMatrix();
		RenderSystem.multMatrix(matrixStackIn.getLast().getMatrix());
		matrixStackIn.rotate(Minecraft.getInstance().getRenderManager().getCameraOrientation());
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F));
		matrixStackIn.translate(-0.5, -0.5, -0.5);
		
		float scale = 0.03f;
		GlStateManager.scalef(scale, scale, scale);

		GlStateManager.disableTexture();
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GlStateManager.disableAlphaTest();
		GlStateManager.enableCull();
		GlStateManager.enableDepthTest();

		GlStateManager.pushMatrix();
		try {
		    float par2 = entityIn.world.getWorldInfo().getGameTime() * 3 % 180;
		    float var41 = (5.0F + par2) / 200.0F;
		    float var51 = 0.0F;
		    if (var41 > 0.8F) {
			var51 = (var41 - 0.8F) / 0.2F;
		    }
		    Random rand = new Random(432L);
		    for (int i1 = 0; i1 < 100; i1++) {
			GL11.glRotatef(rand.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(rand.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(rand.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(rand.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(rand.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(rand.nextFloat() * 360.0F + var41 * 90.0F, 0.0F, 0.0F, 1.0F);
			final float f2 = rand.nextFloat() * 20 + 5 + var51 * 10;
			final float f3 = rand.nextFloat() * 2 + 1 + var51 * 2;
			bufferBuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
			bufferBuilder.pos(0, 0, 0).color(239, 255, 0, 200).endVertex();
			bufferBuilder.pos(-0.866 * f3, f2, -0.5 * f3).color(193, 147, 22, 0).endVertex();
			bufferBuilder.pos(0.866 * f3, f2, -0.5 * f3).color(193, 147, 22, 0).endVertex();
			bufferBuilder.pos(0, f2, 1 * f3).color(193, 147, 22, 0).endVertex();
			bufferBuilder.pos(-0.866 * f3, f2, -0.5 * f3).color(193, 147, 22, 0).endVertex();
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

	@Override
	public ResourceLocation getEntityTexture(EnergyBlast entity) {
		return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
	}

}
