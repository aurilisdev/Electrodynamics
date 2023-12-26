package electrodynamics.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.client.ClientRegister;
import electrodynamics.common.entity.projectile.types.EntityMetalRod;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderMetalRod extends EntityRenderer<EntityMetalRod> {

	public static final float[] STEEL_COLOR = { 134.0F / 255.0F, 134.0F / 255.0F, 134.0F / 255.0F };
	public static final float[] STAINLESS_COLOR = { 211.0F / 255.0F, 218.0F / 255.0F, 218.0F / 255.0F };
	public static final float[] HSLA_COLOR = { 191.0F / 255.0F, 211.0F / 255.0F, 228.0F / 255.0F };

	public static final AxisAlignedBB ROD = new AxisAlignedBB(0.0625, 0.46875, 0.46875, 0.9375, 0.53125, 0.53125);

	public RenderMetalRod(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public void render(EntityMetalRod entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int packedLightIn) {

		matrixStack.pushPose();

		TextureAtlasSprite sprite = ClientRegister.CACHED_TEXTUREATLASSPRITES.get(ClientRegister.TEXTURE_WHITE);

		double yaw = entity.yRotO + (entity.yRot - entity.yRotO) * partialTicks - 90.0F;// y lerp - 90
		double pitch = entity.xRotO + (entity.xRot - entity.xRotO) * partialTicks - 90; // x lerp - 90

		matrixStack.mulPose(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), (float) yaw, true));
		matrixStack.mulPose(new Quaternion(new Vector3f(0.0F, 0.0F, 1.0F), (float) pitch, true));
		matrixStack.mulPose(new Quaternion(new Vector3f(0.0F, 0.0F, 1.0F), 90.0F, true));
		
		matrixStack.translate(-0.5, -0.5, -0.5);

		float[] color = getColor(entity.getNumber());

		RenderingUtils.renderFilledBoxNoOverlay(matrixStack, bufferIn.getBuffer(RenderType.solid()), ROD, color[0], color[1], color[2], 1.0F, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), packedLightIn);

		matrixStack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(EntityMetalRod entity) {

		return AtlasTexture.LOCATION_BLOCKS;

	}

	public static float[] getColor(int number) {
		switch (number) {
		case 2:
			return HSLA_COLOR;
		case 1:
			return STAINLESS_COLOR;
		default:
			return STEEL_COLOR;
		}
	}

}
