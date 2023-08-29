package electrodynamics.client.render.entity;

import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import electrodynamics.Electrodynamics;
import electrodynamics.api.References;
import electrodynamics.common.entity.projectile.types.EntityEnergyBlast;
import electrodynamics.prefab.utilities.math.MathUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class RenderEnergyBlast extends EntityRenderer<EntityEnergyBlast> {

	public RenderEnergyBlast(Context renderManager) {
		super(renderManager);
	}

	@Override
	public void render(EntityEnergyBlast entity, float entityYaw, float partialTicks, PoseStack matrixStack, @NotNull MultiBufferSource buffer, int packedLight) {

		int r = Electrodynamics.RANDOM.nextInt(0, 50);
		int g = Electrodynamics.RANDOM.nextInt(10, 40);
		int b = Electrodynamics.RANDOM.nextInt(60, 100);

		matrixStack.pushPose();
		float u0 = 0;
		float u1 = 1;
		float v0 = 0;
		float v1 = 1;
		int red = 235 - r;
		int green = 120 - g;
		int blue = 245 - b;
		int alpha = 255;
		matrixStack.translate(0.0D, 0.1F, 0.0D);
		matrixStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
		matrixStack.mulPose(MathUtils.rotVectorQuaternionDeg(180.0F, MathUtils.YP));
		//matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
		matrixStack.scale(2.0F, 2.0F, 2.0F);
		VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.beaconBeam(new ResourceLocation(References.ID, "textures/custom/plasmaorb.png"), true));
		PoseStack.Pose posestack$pose = matrixStack.last();
		Matrix4f matrix4f = posestack$pose.pose();
		Matrix3f matrix3f = posestack$pose.normal();
		vertex(vertexconsumer, matrix4f, matrix3f, -0.5F, -0.25F, red, green, blue, alpha, u0, v1, packedLight);
		vertex(vertexconsumer, matrix4f, matrix3f, 0.5F, -0.25F, red, green, blue, alpha, u1, v1, packedLight);
		vertex(vertexconsumer, matrix4f, matrix3f, 0.5F, 0.75F, red, green, blue, alpha, u1, v0, packedLight);
		vertex(vertexconsumer, matrix4f, matrix3f, -0.5F, 0.75F, red, green, blue, alpha, u0, v0, packedLight);
		matrixStack.popPose();

	}

	private static void vertex(VertexConsumer buffer, Matrix4f matrix, Matrix3f matrixNormal, float x, float y, int r, int g, int b, int a, float u, float v, int light) {
		buffer.vertex(matrix, x, y, 0.0F).color(r, g, b, a).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrixNormal, 0.0F, 1.0F, 0.0F).endVertex();
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull EntityEnergyBlast entity) {
		return new ResourceLocation(References.ID, "custom/plasmaorb");
	}

}
