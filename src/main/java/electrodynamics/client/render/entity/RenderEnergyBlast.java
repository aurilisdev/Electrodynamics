package electrodynamics.client.render.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import electrodynamics.common.entity.projectile.types.EntityEnergyBlast;
import electrodynamics.prefab.utilities.UtilitiesRendering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

public class RenderEnergyBlast extends EntityRenderer<EntityEnergyBlast> {

    public RenderEnergyBlast(EntityRenderDispatcher renderManager) {
	super(renderManager);
    }

    @Override
    public void render(EntityEnergyBlast entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn,
	    int packedLightIn) {
	GlStateManager._pushMatrix();
	RenderSystem.multMatrix(matrixStackIn.last().pose());
	matrixStackIn.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
	matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F));
	float scale = 0.015f * ((entityIn.tickCount + partialTicks) / 5.0f);
	GlStateManager._scalef(scale, scale, scale);
	UtilitiesRendering.renderStar(entityIn.tickCount + partialTicks, 100, 0.62f, 0.19f, 0.63f,
		0.3f / ((entityIn.tickCount + partialTicks) / 40.0f + 1), true);

	GlStateManager._popMatrix();
    }

    @Override
    public ResourceLocation getTextureLocation(EntityEnergyBlast entity) {
	return TextureAtlas.LOCATION_BLOCKS;
    }

}
