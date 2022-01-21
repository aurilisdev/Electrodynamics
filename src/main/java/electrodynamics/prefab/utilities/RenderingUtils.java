package electrodynamics.prefab.utilities;

import java.util.Random;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import electrodynamics.prefab.block.GenericEntityBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RenderingUtils {

	public static void renderStar(PoseStack stack, MultiBufferSource bufferIn, float time, int starFrags, float r, float g, float b, float a,
			boolean star) {
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
				vertexconsumer2.vertex(matrix4f, 0.0F, 0.0F, 0.0F).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a))
						.endVertex();
				vertexconsumer2.vertex(matrix4f, -0.866f * f4, f3, -0.5F * f4)
						.color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
				vertexconsumer2.vertex(matrix4f, -0.866f * f4, f3, -0.5F * f4)
						.color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
				vertexconsumer2.vertex(matrix4f, 0.0F, 0.0F, 0.0F).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a))
						.endVertex();
				vertexconsumer2.vertex(matrix4f, -0.866f * f4, f3, -0.5F * f4)
						.color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
				vertexconsumer2.vertex(matrix4f, 0.0F, f3, 1.0F * f4).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a))
						.endVertex();
				vertexconsumer2.vertex(matrix4f, 0.0F, 0.0F, 0.0F).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a))
						.endVertex();
				vertexconsumer2.vertex(matrix4f, -0.866f * f4, f3, -0.5F * f4)
						.color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
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

	public static void renderModel(BakedModel model, BlockEntity tile, RenderType type, PoseStack stack, MultiBufferSource buffer,
			int combinedLightIn, int combinedOverlayIn) {
		Minecraft.getInstance().getItemRenderer().render(new ItemStack(type == RenderType.translucent() ? Items.BLACK_STAINED_GLASS : Blocks.STONE),
				TransformType.NONE, false, stack, buffer, combinedLightIn, combinedOverlayIn, model);
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
}
