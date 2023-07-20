package electrodynamics.prefab.utilities;

import java.util.Random;
import java.util.function.Supplier;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import electrodynamics.prefab.block.GenericEntityBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;

public class RenderingUtils {

	public static final int WHITE = RenderingUtils.getRGBA(255, 255, 255, 255);

	public static void renderStar(PoseStack stack, MultiBufferSource bufferIn, float time, int starFrags, float r, float g, float b, float a, boolean star) {
		stack.pushPose();
		try {
			float f5 = time / 200.0F;
			Random random = new Random(432L);
			VertexConsumer vertexconsumer2 = bufferIn.getBuffer(RenderType.lightning());
			stack.pushPose();
			stack.translate(0.0D, -1.0D, 0.0D);

			for (int i = 0; i < starFrags; ++i) {
				stack.mulPose(Vector3f.XP.rotationDegrees(random.nextFloat() * 360.0F));
				stack.mulPose(Vector3f.YP.rotationDegrees(random.nextFloat() * 360.0F));
				stack.mulPose(Vector3f.ZP.rotationDegrees(random.nextFloat() * 360.0F));
				stack.mulPose(Vector3f.XP.rotationDegrees(random.nextFloat() * 360.0F));
				stack.mulPose(Vector3f.YP.rotationDegrees(random.nextFloat() * 360.0F));
				stack.mulPose(Vector3f.ZP.rotationDegrees(random.nextFloat() * 360.0F + f5 * 90.0F));
				float f3 = random.nextFloat() * 20.0F + 1.0F;
				float f4 = random.nextFloat() * 2.0F + 1.0F + (star ? 0 : 100);
				Matrix4f matrix4f = stack.last().pose();
				vertexconsumer2.vertex(matrix4f, 0.0F, 0.0F, 0.0F).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
				vertexconsumer2.vertex(matrix4f, -0.866f * f4, f3, -0.5F * f4).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
				vertexconsumer2.vertex(matrix4f, -0.866f * f4, f3, -0.5F * f4).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
				vertexconsumer2.vertex(matrix4f, 0.0F, 0.0F, 0.0F).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
				vertexconsumer2.vertex(matrix4f, -0.866f * f4, f3, -0.5F * f4).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
				vertexconsumer2.vertex(matrix4f, 0.0F, f3, 1.0F * f4).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
				vertexconsumer2.vertex(matrix4f, 0.0F, 0.0F, 0.0F).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
				vertexconsumer2.vertex(matrix4f, -0.866f * f4, f3, -0.5F * f4).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
			}

			stack.popPose();
			if (bufferIn instanceof BufferSource source) {
				source.endBatch(RenderType.lightning());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		stack.popPose();
	}

	public static void renderModel(BakedModel model, BlockEntity tile, RenderType type, PoseStack stack, MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn) {
		Minecraft.getInstance().getItemRenderer().render(new ItemStack(type == RenderType.translucent() ? Items.BLACK_STAINED_GLASS : Blocks.STONE), TransformType.NONE, false, stack, buffer, combinedLightIn, combinedOverlayIn, model);
	}

	public static void prepareRotationalTileModel(BlockEntity tile, PoseStack stack) {
		BlockState state = tile.getBlockState();
		stack.translate(0.5, 7.0 / 16.0, 0.5);
		if (state.hasProperty(GenericEntityBlock.FACING)) {
			Direction facing = state.getValue(GenericEntityBlock.FACING);
			if (facing == Direction.NORTH) {
				stack.mulPose(new Quaternion(0, 90, 0, true));
			} else if (facing == Direction.SOUTH) {
				stack.mulPose(new Quaternion(0, 270, 0, true));
			} else if (facing == Direction.WEST) {
				stack.mulPose(new Quaternion(0, 180, 0, true));
			}
		}
	}

	public static void renderSolidColorBox(PoseStack stack, Minecraft minecraft, VertexConsumer builder, AABB box, float r, float g, float b, float a, int light, int overlay) {
		TextureAtlasSprite sp = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(new ResourceLocation("block/white_wool"));
		renderFilledBox(stack, builder, box, r, g, b, a, sp.getU0(), sp.getV0(), sp.getU1(), sp.getV1(), light, overlay);
	}

	public static void renderFluidBox(PoseStack stack, Minecraft minecraft, VertexConsumer builder, AABB box, FluidStack fluidStack, int light, int overlay) {
		IClientFluidTypeExtensions attributes = IClientFluidTypeExtensions.of(fluidStack.getFluid());

		TextureAtlasSprite sp = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(attributes.getStillTexture());
		float[] colors = getColorArray(attributes.getTintColor(fluidStack));
		renderFilledBox(stack, builder, box, colors[0], colors[1], colors[2], colors[3], sp.getU0(), sp.getV0(), sp.getU1(), sp.getV1(), light, overlay);
	}

	public static void renderFilledBox(PoseStack stack, VertexConsumer builder, AABB box, float r, float g, float b, float a, float uMin, float vMin, float uMax, float vMax, int light, int overlay) {
		Matrix4f matrix4f = stack.last().pose();
		Matrix3f matrix3f = stack.last().normal();

		float minX = (float) box.minX;
		float minY = (float) box.minY;
		float minZ = (float) box.minZ;
		float maxX = (float) box.maxX;
		float maxY = (float) box.maxY;
		float maxZ = (float) box.maxZ;

		// bottom
		builder.vertex(matrix4f, minX, minY, minZ).color(r, g, b, a).uv(uMin, vMin).overlayCoords(overlay).uv2(light).normal(matrix3f, 0, -1, 0).endVertex();
		builder.vertex(matrix4f, maxX, minY, minZ).color(r, g, b, a).uv(uMax, vMin).overlayCoords(overlay).uv2(light).normal(matrix3f, 0, -1, 0).endVertex();
		builder.vertex(matrix4f, maxX, minY, maxZ).color(r, g, b, a).uv(uMax, vMax).overlayCoords(overlay).uv2(light).normal(matrix3f, 0, -1, 0).endVertex();
		builder.vertex(matrix4f, minX, minY, maxZ).color(r, g, b, a).uv(uMin, vMax).overlayCoords(overlay).uv2(light).normal(matrix3f, 0, -1, 0).endVertex();

		// top
		builder.vertex(matrix4f, maxX, maxY, minZ).color(r, g, b, a).uv(uMin, vMin).overlayCoords(overlay).uv2(light).normal(matrix3f, 0, 1, 0).endVertex();
		builder.vertex(matrix4f, minX, maxY, minZ).color(r, g, b, a).uv(uMax, vMin).overlayCoords(overlay).uv2(light).normal(matrix3f, 0, 1, 0).endVertex();
		builder.vertex(matrix4f, minX, maxY, maxZ).color(r, g, b, a).uv(uMax, vMax).overlayCoords(overlay).uv2(light).normal(matrix3f, 0, 1, 0).endVertex();
		builder.vertex(matrix4f, maxX, maxY, maxZ).color(r, g, b, a).uv(uMin, vMax).overlayCoords(overlay).uv2(light).normal(matrix3f, 0, 1, 0).endVertex();

		// North
		builder.vertex(matrix4f, minX, maxY, minZ).color(r, g, b, a).uv(uMin, vMin).overlayCoords(overlay).uv2(light).normal(matrix3f, 0, 0, -1).endVertex();
		builder.vertex(matrix4f, maxX, maxY, minZ).color(r, g, b, a).uv(uMax, vMin).overlayCoords(overlay).uv2(light).normal(matrix3f, 0, 0, -1).endVertex();
		builder.vertex(matrix4f, maxX, minY, minZ).color(r, g, b, a).uv(uMax, vMax).overlayCoords(overlay).uv2(light).normal(matrix3f, 0, 0, -1).endVertex();
		builder.vertex(matrix4f, minX, minY, minZ).color(r, g, b, a).uv(uMin, vMax).overlayCoords(overlay).uv2(light).normal(matrix3f, 0, 0, -1).endVertex();

		// South
		builder.vertex(matrix4f, maxX, maxY, maxZ).color(r, g, b, a).uv(uMin, vMin).overlayCoords(overlay).uv2(light).normal(matrix3f, 0, 0, 1).endVertex();
		builder.vertex(matrix4f, minX, maxY, maxZ).color(r, g, b, a).uv(uMax, vMin).overlayCoords(overlay).uv2(light).normal(matrix3f, 0, 0, 1).endVertex();
		builder.vertex(matrix4f, minX, minY, maxZ).color(r, g, b, a).uv(uMax, vMax).overlayCoords(overlay).uv2(light).normal(matrix3f, 0, 0, 1).endVertex();
		builder.vertex(matrix4f, maxX, minY, maxZ).color(r, g, b, a).uv(uMin, vMax).overlayCoords(overlay).uv2(light).normal(matrix3f, 0, 0, 1).endVertex();

		// East
		builder.vertex(matrix4f, maxX, maxY, minZ).color(r, g, b, a).uv(uMin, vMin).overlayCoords(overlay).uv2(light).normal(matrix3f, 1, 0, 0).endVertex();
		builder.vertex(matrix4f, maxX, maxY, maxZ).color(r, g, b, a).uv(uMax, vMin).overlayCoords(overlay).uv2(light).normal(matrix3f, 1, 0, 0).endVertex();
		builder.vertex(matrix4f, maxX, minY, maxZ).color(r, g, b, a).uv(uMax, vMax).overlayCoords(overlay).uv2(light).normal(matrix3f, 1, 0, 0).endVertex();
		builder.vertex(matrix4f, maxX, minY, minZ).color(r, g, b, a).uv(uMin, vMax).overlayCoords(overlay).uv2(light).normal(matrix3f, 1, 0, 0).endVertex();

		// West
		builder.vertex(matrix4f, minX, maxY, maxZ).color(r, g, b, a).uv(uMin, vMin).overlayCoords(overlay).uv2(light).normal(matrix3f, -1, 0, 0).endVertex();
		builder.vertex(matrix4f, minX, maxY, minZ).color(r, g, b, a).uv(uMax, vMin).overlayCoords(overlay).uv2(light).normal(matrix3f, -1, 0, 0).endVertex();
		builder.vertex(matrix4f, minX, minY, minZ).color(r, g, b, a).uv(uMax, vMax).overlayCoords(overlay).uv2(light).normal(matrix3f, -1, 0, 0).endVertex();
		builder.vertex(matrix4f, minX, minY, maxZ).color(r, g, b, a).uv(uMin, vMax).overlayCoords(overlay).uv2(light).normal(matrix3f, -1, 0, 0).endVertex();

	}

	public static void renderFilledBoxNoOverlay(PoseStack stack, VertexConsumer builder, AABB box, float r, float g, float b, float a, float uMin, float vMin, float uMax, float vMax, int light) {
		Matrix4f matrix4f = stack.last().pose();
		Matrix3f matrix3f = stack.last().normal();

		float minX = (float) box.minX;
		float minY = (float) box.minY;
		float minZ = (float) box.minZ;
		float maxX = (float) box.maxX;
		float maxY = (float) box.maxY;
		float maxZ = (float) box.maxZ;

		// bottom
		builder.vertex(matrix4f, minX, minY, minZ).color(r, g, b, a).uv(uMin, vMin).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0, -1, 0).endVertex();
		builder.vertex(matrix4f, maxX, minY, minZ).color(r, g, b, a).uv(uMax, vMin).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0, -1, 0).endVertex();
		builder.vertex(matrix4f, maxX, minY, maxZ).color(r, g, b, a).uv(uMax, vMax).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0, -1, 0).endVertex();
		builder.vertex(matrix4f, minX, minY, maxZ).color(r, g, b, a).uv(uMin, vMax).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0, -1, 0).endVertex();

		// top
		builder.vertex(matrix4f, maxX, maxY, minZ).color(r, g, b, a).uv(uMin, vMin).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0, 1, 0).endVertex();
		builder.vertex(matrix4f, minX, maxY, minZ).color(r, g, b, a).uv(uMax, vMin).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0, 1, 0).endVertex();
		builder.vertex(matrix4f, minX, maxY, maxZ).color(r, g, b, a).uv(uMax, vMax).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0, 1, 0).endVertex();
		builder.vertex(matrix4f, maxX, maxY, maxZ).color(r, g, b, a).uv(uMin, vMax).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0, 1, 0).endVertex();

		// North
		builder.vertex(matrix4f, minX, maxY, minZ).color(r, g, b, a).uv(uMin, vMin).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0, 0, -1).endVertex();
		builder.vertex(matrix4f, maxX, maxY, minZ).color(r, g, b, a).uv(uMax, vMin).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0, 0, -1).endVertex();
		builder.vertex(matrix4f, maxX, minY, minZ).color(r, g, b, a).uv(uMax, vMax).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0, 0, -1).endVertex();
		builder.vertex(matrix4f, minX, minY, minZ).color(r, g, b, a).uv(uMin, vMax).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0, 0, -1).endVertex();

		// South
		builder.vertex(matrix4f, maxX, maxY, maxZ).color(r, g, b, a).uv(uMin, vMin).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0, 0, 1).endVertex();
		builder.vertex(matrix4f, minX, maxY, maxZ).color(r, g, b, a).uv(uMax, vMin).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0, 0, 1).endVertex();
		builder.vertex(matrix4f, minX, minY, maxZ).color(r, g, b, a).uv(uMax, vMax).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0, 0, 1).endVertex();
		builder.vertex(matrix4f, maxX, minY, maxZ).color(r, g, b, a).uv(uMin, vMax).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0, 0, 1).endVertex();

		// East
		builder.vertex(matrix4f, maxX, maxY, minZ).color(r, g, b, a).uv(uMin, vMin).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 1, 0, 0).endVertex();
		builder.vertex(matrix4f, maxX, maxY, maxZ).color(r, g, b, a).uv(uMax, vMin).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 1, 0, 0).endVertex();
		builder.vertex(matrix4f, maxX, minY, maxZ).color(r, g, b, a).uv(uMax, vMax).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 1, 0, 0).endVertex();
		builder.vertex(matrix4f, maxX, minY, minZ).color(r, g, b, a).uv(uMin, vMax).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 1, 0, 0).endVertex();

		// West
		builder.vertex(matrix4f, minX, maxY, maxZ).color(r, g, b, a).uv(uMin, vMin).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, -1, 0, 0).endVertex();
		builder.vertex(matrix4f, minX, maxY, minZ).color(r, g, b, a).uv(uMax, vMin).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, -1, 0, 0).endVertex();
		builder.vertex(matrix4f, minX, minY, minZ).color(r, g, b, a).uv(uMax, vMax).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, -1, 0, 0).endVertex();
		builder.vertex(matrix4f, minX, minY, maxZ).color(r, g, b, a).uv(uMin, vMax).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, -1, 0, 0).endVertex();

	}

	public static void renderItemScaled(PoseStack posestack, Item item, int x, int y, float scale) {
		ItemStack stack = new ItemStack(item);
		Minecraft minecraft = Minecraft.getInstance();
		ItemRenderer itemRenderer = minecraft.getItemRenderer();
		BakedModel model = itemRenderer.getModel(stack, (Level) null, (LivingEntity) null, 0);
		TextureManager manager = minecraft.getTextureManager();

		manager.getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
		RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
		RenderSystem.enableBlend();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		posestack.pushPose();
		posestack.translate(x, y, 100.0F + itemRenderer.blitOffset);
		posestack.translate(8.0D, 8.0D, 0.0D);
		posestack.scale(1.0F, -1.0F, 1.0F);
		posestack.scale(16.0F, 16.0F, 16.0F);
		posestack.scale(scale, scale, scale);
		RenderSystem.applyModelViewMatrix();
		PoseStack posestack1 = new PoseStack();
		MultiBufferSource.BufferSource buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
		boolean flag = !model.usesBlockLight();
		if (flag) {
			Lighting.setupForFlatItems();
		}

		itemRenderer.render(stack, ItemTransforms.TransformType.GUI, false, posestack1, buffersource, 15728880, OverlayTexture.NO_OVERLAY, model);
		buffersource.endBatch();
		RenderSystem.enableDepthTest();
		if (flag) {
			Lighting.setupFor3DItems();
		}

		posestack.popPose();
		RenderSystem.applyModelViewMatrix();

	}

	public static void blitCustomShader(PoseStack matrixStack, int x, int y, int u, int v, int textWidth, int textHeight, int imgWidth, int imgHeight, Supplier<ShaderInstance> shader) {
		// blit(matrixStack, x, y, textWidth, textHeight, u, v, textWidth, textHeight, imgWidth, imgHeight);
		// innerBlit(matrixStack, x, x + textWidth, y, y + textHeight, 0, textWidth, textHeight, u, v, imgWidth, imgHeight);
		// innerBlit(matrixStack.last().pose(), x, x + textWidth, y, y + textHeight, 0, (u + 0.0F) / (float) imgWidth, (u + (float)
		// textWidth) / (float) imgWidth, (v + 0.0F) / (float) imgHeight, (v + (float) textHeight) / (float) imgHeight);

		float x0 = x;
		float x1 = x + textWidth;
		float y0 = y;
		float y1 = y + textHeight;

		float blitOffset = 0;

		float minU = (u + 0.0F) / imgWidth;
		float maxU = (u + (float) textWidth) / imgWidth;
		float minV = (v + 0.0F) / imgHeight;
		float maxV = (v + (float) textHeight) / imgHeight;

		Matrix4f matrix = matrixStack.last().pose();

		RenderSystem.setShader(shader);

		BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();

		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

		bufferbuilder.vertex(matrix, x0, y1, blitOffset).uv(minU, maxV).endVertex();
		bufferbuilder.vertex(matrix, x1, y1, blitOffset).uv(maxU, maxV).endVertex();
		bufferbuilder.vertex(matrix, x1, y0, blitOffset).uv(maxU, minV).endVertex();
		bufferbuilder.vertex(matrix, x0, y0, blitOffset).uv(minU, minV).endVertex();

		BufferUploader.drawWithShader(bufferbuilder.end());

		RenderSystem.setShader(GameRenderer::getPositionTexShader);

	}

	public static void renderItemScaled(Item item, int x, int y, float scale) {
		renderItemScaled(RenderSystem.getModelViewStack(), item, x, y, scale);

	}

	public static void bindTexture(ResourceLocation resource) {
		RenderSystem.setShaderTexture(0, resource);
	}

	public static float getRed(int color) {
		return (color >> 16 & 0xFF) / 255.0F;
	}

	public static float getGreen(int color) {
		return (color >> 8 & 0xFF) / 255.0F;
	}

	public static float getBlue(int color) {
		return (color & 0xFF) / 255.0F;
	}

	public static float getAlpha(int color) {
		return (color >> 24 & 0xFF) / 255.0F;
	}

	public static int getRGBA(int a, int r, int g, int b) {
		return (a << 24) + (r << 16) + (g << 8) + b;
	}

	public static float[] getColorArray(int color) {
		return new float[] { getRed(color), getGreen(color), getBlue(color), getAlpha(color) };
	}

	public static void color(int color) {
		RenderSystem.setShaderColor(getRed(color), getGreen(color), getBlue(color), getAlpha(color));
	}

	public static RenderType beaconType() {
		return RenderType.beaconBeam(new ResourceLocation("textures/entity/beacon_beam.png"), true);
	}

	public static void resetColor() {
		RenderingUtils.color(WHITE);
	}
}
