package electrodynamics.api.utilities;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import electrodynamics.common.block.BlockGenericMachine;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;

public class UtilitiesRendering {

    @Deprecated
    public static void renderStar(float time, int starFrags, float r, float g, float b, float a, boolean star) {
	GlStateManager.pushMatrix();
	Tessellator tessellator = Tessellator.getInstance();
	BufferBuilder bufferBuilder = tessellator.getBuffer();
	GlStateManager.disableTexture();
	GlStateManager.shadeModel(GL11.GL_SMOOTH);
	GlStateManager.enableBlend();
	if (star) {
	    GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
	} else {
	    GlStateManager.blendFunc(GL11.GL_DST_COLOR, GL11.GL_ONE_MINUS_DST_COLOR);
	}
	GlStateManager.disableAlphaTest();
	GlStateManager.enableCull();
	GlStateManager.enableDepthTest();

	GlStateManager.pushMatrix();
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
		bufferBuilder.pos(0, 0, 0).color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
		bufferBuilder.pos(-0.866 * f3, f2, -0.5 * f3).color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
		bufferBuilder.pos(0.866 * f3, f2, -0.5 * f3).color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
		bufferBuilder.pos(0, f2, 1 * f3).color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
		bufferBuilder.pos(-0.866 * f3, f2, -0.5 * f3).color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
		tessellator.draw();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	GlStateManager.popMatrix();
	GlStateManager.disableDepthTest();
	GlStateManager.disableBlend();
	GlStateManager.shadeModel(GL11.GL_FLAT);
	GlStateManager.color4f(1, 1, 1, 1);
	GlStateManager.enableTexture();
	GlStateManager.enableAlphaTest();
	GlStateManager.popMatrix();
    }

    public static void renderModel(IBakedModel model, TileEntity tile, RenderType type, MatrixStack stack, IRenderTypeBuffer buffer,
	    int combinedLightIn, int combinedOverlayIn) {
	Minecraft.getInstance().getItemRenderer().renderItem(new ItemStack(Blocks.STONE), TransformType.NONE, false, stack, buffer, combinedLightIn,
		combinedOverlayIn, model);
    }

    public static void prepareRotationalTileModel(TileEntity tile, MatrixStack stack) {
	BlockState state = tile.getBlockState();
	stack.translate(0.5, 7.0 / 16.0, 0.5);
	if (state.hasProperty(BlockGenericMachine.FACING)) {
	    Direction facing = state.get(BlockGenericMachine.FACING);
	    if (facing == Direction.NORTH) {
		stack.rotate(new Quaternion(0, 90, 0, true));
	    } else if (facing == Direction.SOUTH) {
		stack.rotate(new Quaternion(0, 270, 0, true));
	    } else if (facing == Direction.WEST) {
		stack.rotate(new Quaternion(0, 180, 0, true));
	    }
	}
    }

    public static void bindTexture(ResourceLocation resource) {
	Minecraft.getInstance().textureManager.bindTexture(resource);
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

    public static void color(int color) {
	RenderSystem.color4f(getRed(color), getGreen(color), getBlue(color), getAlpha(color));
    }
}
