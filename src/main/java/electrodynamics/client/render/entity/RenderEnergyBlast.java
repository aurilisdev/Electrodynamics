package electrodynamics.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import electrodynamics.common.entity.projectile.types.EntityEnergyBlast;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

public class RenderEnergyBlast extends EntityRenderer<EntityEnergyBlast> {

	public RenderEnergyBlast(Context renderManager) {
		super(renderManager);
	}

	@Override
	public void render(EntityEnergyBlast entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
		matrixStackIn.pushPose();
		matrixStackIn.translate(entityIn.getX(), entityIn.getY(), entityIn.getX());
		matrixStackIn.mulPoseMatrix(matrixStackIn.last().pose());
		matrixStackIn.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
		matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F));
		float scale = 0.015f * ((entityIn.tickCount + partialTicks) / 5.0f);
		matrixStackIn.scale(scale, scale, scale);
		RenderingUtils.renderStar(matrixStackIn, bufferIn, entityIn.tickCount + partialTicks, 100, 0.62f, 0.19f, 0.63f, 0.3f / ((entityIn.tickCount + partialTicks) / 40.0f + 1), true);
		matrixStackIn.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(EntityEnergyBlast entity) {
		return InventoryMenu.BLOCK_ATLAS;
	}

}
