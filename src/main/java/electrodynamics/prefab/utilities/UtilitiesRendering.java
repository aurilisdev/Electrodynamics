package electrodynamics.prefab.utilities;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Quaternion;

import electrodynamics.common.block.BlockGenericMachine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class UtilitiesRendering {

    @Deprecated
    public static void renderStar(float time, int starFrags, float r, float g, float b, float a, boolean star) {
	GlStateManager._pushMatrix();
	Tesselator tessellator = Tesselator.getInstance();
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
		bufferBuilder.begin(6, DefaultVertexFormat.POSITION_COLOR);
		bufferBuilder.vertex(0, 0, 0).color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
		bufferBuilder.vertex(-0.866 * f3, f2, -0.5 * f3).color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255))
			.endVertex();
		bufferBuilder.vertex(0.866 * f3, f2, -0.5 * f3).color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
		bufferBuilder.vertex(0, f2, 1 * f3).color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
		bufferBuilder.vertex(-0.866 * f3, f2, -0.5 * f3).color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255))
			.endVertex();
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

    public static void renderModel(BakedModel model, BlockEntity tile, RenderType type, PoseStack stack, MultiBufferSource buffer,
	    int combinedLightIn, int combinedOverlayIn) {
	Minecraft.getInstance().getItemRenderer().render(new ItemStack(Blocks.STONE), TransformType.NONE, false, stack, buffer, combinedLightIn,
		combinedOverlayIn, model);
    }

    public static void prepareRotationalTileModel(BlockEntity tile, PoseStack stack) {
	BlockState state = tile.getBlockState();
	stack.translate(0.5, 7.0 / 16.0, 0.5);
	if (state.hasProperty(BlockGenericMachine.FACING)) {
	    Direction facing = state.getValue(BlockGenericMachine.FACING);
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
	Minecraft.getInstance().textureManager.bindForSetup(resource);
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

    public static float[] getColorArray(int color) {
	return new float[] { getRed(color), getGreen(color), getBlue(color), getAlpha(color) };
    }

    public static void color(int color) {
	GL11.glColor4f(getRed(color), getGreen(color), getBlue(color), getAlpha(color));
    }
}
