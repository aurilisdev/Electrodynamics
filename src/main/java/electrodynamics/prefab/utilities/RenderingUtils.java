package electrodynamics.prefab.utilities;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.IVertexConsumer;

import electrodynamics.prefab.block.GenericEntityBlock;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;

public class RenderingUtils {

	public static void renderStar(float time, int starFrags, float r, float g, float b, float a, boolean star) {
		GlStateManager._pushMatrix();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuilder();
		GlStateManager._disableTexture();
		GlStateManager._shadeModel(GL11.GL_SMOOTH);
		GlStateManager._enableBlend();
		if (star) {
			GlStateManager._blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		} else {
			GlStateManager._blendFunc(GL11.GL_DST_COLOR, GL11.GL_ONE_MINUS_DST_COLOR);
		}
		GlStateManager._disableAlphaTest();
		GlStateManager._enableCull();
		GlStateManager._enableDepthTest();
		GlStateManager._pushMatrix();
		try {
			float par2 = time * 3 % 180;
			float var41 = (5.0F + par2) / 200.0F;
			float var51 = 0.0F;
			if (var41 > 0.8F) {
				var51 = (var41 - 0.8F) / 0.2F;
			}
			Random rand = new Random(432L);
			for (int i1 = 0; i1 < starFrags; i1++) {
				GL11.glRotatef(rand.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(rand.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(rand.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(rand.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(rand.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(rand.nextFloat() * 360.0F + var41 * 90.0F, 0.0F, 0.0F, 1.0F);
				final float f2 = rand.nextFloat() * 20 + 5 + var51 * 10;
				final float f3 = rand.nextFloat() * 2 + 1 + var51 * 2 + (star ? 0 : 10);
				bufferBuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
				bufferBuilder.vertex(0, 0, 0).color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
				bufferBuilder.vertex(-0.866 * f3, f2, -0.5 * f3).color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
				bufferBuilder.vertex(0.866 * f3, f2, -0.5 * f3).color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
				bufferBuilder.vertex(0, f2, 1 * f3).color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
				bufferBuilder.vertex(-0.866 * f3, f2, -0.5 * f3).color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
				tessellator.end();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		GlStateManager._popMatrix();
		GlStateManager._disableDepthTest();
		GlStateManager._disableBlend();
		GlStateManager._shadeModel(GL11.GL_FLAT);
		GlStateManager._color4f(1, 1, 1, 1);
		GlStateManager._enableTexture();
		GlStateManager._enableAlphaTest();
		GlStateManager._popMatrix();
	}

	public static void renderModel(IBakedModel model, TileEntity tile, RenderType type, MatrixStack stack, IRenderTypeBuffer buffer, int combinedLightIn, int combinedOverlayIn) {
		Minecraft.getInstance().getItemRenderer().render(new ItemStack(type == RenderType.translucent() ? Items.BLACK_STAINED_GLASS : Blocks.STONE), TransformType.NONE, false, stack, buffer, combinedLightIn, combinedOverlayIn, model);
	}

	public static void prepareRotationalTileModel(TileEntity tile, MatrixStack stack) {
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

	public static void renderSolidColorBox(MatrixStack stack, Minecraft minecraft, IVertexBuilder builder, AxisAlignedBB box, float r, float g, float b, float a, int light, int overlay) {
		TextureAtlasSprite sp = minecraft.getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(new ResourceLocation("block/white_wool"));
		renderFilledBox(stack, builder, box, r, g, b, a, sp.getU0(), sp.getV0(), sp.getU1(), sp.getV1(), light, overlay);
	}

	public static void renderFluidBox(MatrixStack stack, Minecraft minecraft, IVertexBuilder builder, AxisAlignedBB box, FluidStack fluidStack, int light, int overlay) {
		FluidAttributes attributes = fluidStack.getFluid().getAttributes();

		TextureAtlasSprite sp = minecraft.getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(attributes.getStillTexture());
		Color color = new Color(attributes.getColor(fluidStack));
		renderFilledBox(stack, builder, box, color.rFloat(), color.gFloat(), color.bFloat(), color.aFloat(), sp.getU0(), sp.getV0(), sp.getU1(), sp.getV1(), light, overlay);
	}

	public static void renderFilledBox(MatrixStack stack, IVertexBuilder builder, AxisAlignedBB box, float r, float g, float b, float a, float uMin, float vMin, float uMax, float vMax, int light, int overlay) {
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

	public static void renderFilledBoxNoOverlay(MatrixStack stack, IVertexConsumer builder, AxisAlignedBB box, float r, float g, float b, float a, float uMin, float vMin, float uMax, float vMax, int light) {
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

	public static void renderItemScaled(Item item, int x, int y, float scale) {
		ItemStack stack = new ItemStack(item);
		Minecraft minecraft = Minecraft.getInstance();
		ItemRenderer itemRenderer = minecraft.getItemRenderer();
		IBakedModel model = itemRenderer.getModel(stack, (World) null, (LivingEntity) null);
		TextureManager manager = minecraft.getTextureManager();

		RenderSystem.pushMatrix();
		manager.bind(AtlasTexture.LOCATION_BLOCKS);
		manager.getTexture(AtlasTexture.LOCATION_BLOCKS).setFilter(false, false);
		RenderSystem.enableRescaleNormal();
		RenderSystem.enableAlphaTest();
		RenderSystem.defaultAlphaFunc();
		RenderSystem.enableBlend();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.translatef((float) x, (float) y, 100.0F);
		RenderSystem.translatef(8.0F, 8.0F, 0.0F);
		RenderSystem.scalef(1.0F, -1.0F, 1.0F);
		RenderSystem.scalef(scale, scale, scale);
		RenderSystem.scalef(16.0F, 16.0F, 16.0F);
		MatrixStack matrixstack = new MatrixStack();
		IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
		boolean flag = !model.usesBlockLight();
		if (flag) {
			RenderHelper.setupForFlatItems();
		}

		itemRenderer.render(stack, ItemCameraTransforms.TransformType.GUI, false, matrixstack, irendertypebuffer$impl, 15728880, OverlayTexture.NO_OVERLAY, model);
		irendertypebuffer$impl.endBatch();
		RenderSystem.enableDepthTest();
		if (flag) {
			RenderHelper.setupFor3DItems();
		}

		RenderSystem.disableAlphaTest();
		RenderSystem.disableRescaleNormal();
		RenderSystem.popMatrix();

	}

	public static void bindTexture(ResourceLocation resource) {
		Minecraft.getInstance().textureManager.bind(resource);
	}

	public static void setShaderColor(Color color) {
		RenderSystem.color4f(color.rFloat(), color.gFloat(), color.bFloat(), color.aFloat());
	}

	public static RenderType beaconType() {
		return RenderType.beaconBeam(new ResourceLocation("textures/entity/beacon_beam.png"), true);
	}

	public static void resetShaderColor() {
		RenderingUtils.setShaderColor(Color.WHITE);
	}
}
