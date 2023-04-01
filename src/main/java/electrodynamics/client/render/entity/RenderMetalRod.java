package electrodynamics.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import electrodynamics.Electrodynamics;
import electrodynamics.client.ClientRegister;
import electrodynamics.common.entity.projectile.types.EntityMetalRod;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class RenderMetalRod extends EntityRenderer<EntityMetalRod> {

	// private AABB ROD = new AABB(0.46875, 0.46875, 0.0625, 0.53125, 0.53125,
	// 0.9375);

	// new AABB(0.46875, 0.46875, 0.0625, 0.53125, 0.53125, 0.9375)
	// new AABB(0.46875, 0.0625, 0.46875, 0.53125, 0.9375, 0.53125)
	// new AABB(0.0625, 0.46875, 0.46875, 0.9375, 0.53125, 0.53125)

	public RenderMetalRod(Context renderManager) {
		super(renderManager);
	}

	@Override
	public void render(EntityMetalRod entity, float entityYaw, float partialTicks, PoseStack matrixStack, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
		
		ResourceLocation model = getTextureLocation(entity);

		matrixStack.pushPose();

		matrixStack.translate(-0.5, -0.5, -0.5);

		TextureAtlasSprite sprite = ClientRegister.CACHED_TEXTUREATLASSPRITES.get(ClientRegister.TEXTURE_QUARRYARM);

		double yaw = entity.yRotO + (entity.getYRot() - entity.yRotO) * partialTicks - 90.0F;// y lerp - 90
		double pitch = entity.xRotO + (entity.getXRot() - entity.xRotO) * partialTicks - 90; // x lerp - 90

		matrixStack.mulPose(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), (float) yaw, true));
		matrixStack.mulPose(new Quaternion(new Vector3f(0.0F, 0.0F, 1.0F), (float) pitch, true));
		matrixStack.mulPose(new Quaternion(new Vector3f(0.0F, 0.0F, 1.0F), 90.0F, true));

		RenderingUtils.renderFilledBox(matrixStack, bufferIn.getBuffer(RenderType.solid()), new AABB(0.0625, 0.46875, 0.46875, 0.9375, 0.53125, 0.53125), 1.0F, 1.0F, 1.0F, 1.0F, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), OverlayTexture.NO_OVERLAY, packedLightIn);

		matrixStack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(EntityMetalRod entity) {
		return switch (entity.getNumber()) {

		case 0 -> ClientRegister.MODEL_RODSTEEL;
		case 1 -> ClientRegister.MODEL_RODSTAINLESSSTEEL;
		case 2 -> ClientRegister.MODEL_RODHSLASTEEL;
		default -> TextureAtlas.LOCATION_BLOCKS;
		};
	}

}
