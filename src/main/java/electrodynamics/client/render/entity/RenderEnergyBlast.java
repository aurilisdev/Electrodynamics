package electrodynamics.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import electrodynamics.common.entity.projectile.types.EntityEnergyBlast;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class RenderEnergyBlast extends EntityRenderer<EntityEnergyBlast> {

	public RenderEnergyBlast(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public void render(EntityEnergyBlast entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		GlStateManager._pushMatrix();
		RenderSystem.multMatrix(matrixStackIn.last().pose());
		matrixStackIn.mulPose(Minecraft.getInstance().gameRenderer.getMainCamera().rotation());
		matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F));
		float scale = 0.015f * ((entityIn.tickCount + partialTicks) / 5.0f);
		GlStateManager._scalef(scale, scale, scale);
		RenderingUtils.renderStar(entityIn.tickCount + partialTicks, 100, 0.62f, 0.19f, 0.63f, 0.3f / ((entityIn.tickCount + partialTicks) / 40.0f + 1), true);

		GlStateManager._popMatrix();
	}

	@Override
	public ResourceLocation getTextureLocation(EntityEnergyBlast pEntity) {
		return AtlasTexture.LOCATION_BLOCKS;
	}

}
