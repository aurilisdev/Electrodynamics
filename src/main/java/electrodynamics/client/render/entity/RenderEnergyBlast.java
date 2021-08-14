package electrodynamics.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import electrodynamics.common.entity.projectile.types.EntityEnergyBlast;
import electrodynamics.prefab.utilities.UtilitiesRendering;
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
    public void render(EntityEnergyBlast entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn,
	    int packedLightIn) {
	GlStateManager.pushMatrix();
	RenderSystem.multMatrix(matrixStackIn.getLast().getMatrix());
	matrixStackIn.rotate(Minecraft.getInstance().getRenderManager().getCameraOrientation());
	matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F));
	float scale = 0.015f * ((entityIn.ticksExisted + partialTicks) / 5.0f);
	GlStateManager.scalef(scale, scale, scale);
	UtilitiesRendering.renderStar(entityIn.ticksExisted + partialTicks, 100, 0.62f, 0.19f, 0.63f,
		0.3f / ((entityIn.ticksExisted + partialTicks) / 40.0f + 1), true);

	GlStateManager.popMatrix();
    }

    @Override
    public ResourceLocation getEntityTexture(EntityEnergyBlast entity) {
	return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }

}
