package electrodynamics.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.common.tile.TileMultimeterBlock;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.text.StringTextComponent;

public class RenderMultimeterBlock extends TileEntityRenderer<TileMultimeterBlock> {

    public RenderMultimeterBlock(TileEntityRendererDispatcher rendererDispatcherIn) {
	super(rendererDispatcherIn);
    }

    @Override
    public void render(TileMultimeterBlock tilemultimeter, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn,
	    int combinedLightIn, int combinedOverlayIn) {
	for (Direction dir : Direction.values()) {
	    if (dir != Direction.UP && dir != Direction.DOWN
		    && dir != tilemultimeter.<ComponentDirection>getComponent(ComponentType.Direction).getDirection()) {
		matrixStackIn.push();
		matrixStackIn.translate(0.5 + dir.getXOffset() / 1.999, 0.85 + dir.getYOffset() / 2.0, 0.5 + dir.getZOffset() / 1.999);
		switch (dir) {
		case EAST:
		    matrixStackIn.rotate(new Quaternion(0, -90, 0, true));
		    break;
		case NORTH:
		    break;
		case SOUTH:
		    matrixStackIn.rotate(new Quaternion(0, 180, 0, true));
		    break;
		case WEST:
		    matrixStackIn.rotate(new Quaternion(0, 90, 0, true));
		    break;
		default:
		    break;
		}
		StringTextComponent displayNameIn = new StringTextComponent(
			"Transfer: " + ChatFormatter.getDisplayShort(tilemultimeter.joules * 20, ElectricUnit.WATT, 2));
		FontRenderer fontrenderer = Minecraft.getInstance().fontRenderer;
		float scale = 0.0215f / (fontrenderer.getStringPropertyWidth(displayNameIn) / 32f);
		matrixStackIn.scale(-scale, -scale, -scale);
		Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
		float f2 = -fontrenderer.getStringPropertyWidth(displayNameIn) / 2.0f;
		fontrenderer.func_243247_a(displayNameIn, f2, 0, 0xffffff, false, matrix4f, bufferIn, false, 0, combinedLightIn);
		matrixStackIn.pop();
	    }
	}
	for (Direction dir : Direction.values()) {
	    if (dir != Direction.UP && dir != Direction.DOWN
		    && dir != tilemultimeter.<ComponentDirection>getComponent(ComponentType.Direction).getDirection()) {
		matrixStackIn.push();
		matrixStackIn.translate(0.5 + dir.getXOffset() / 1.999, 0.65 + dir.getYOffset() / 2.0, 0.5 + dir.getZOffset() / 1.999);
		switch (dir) {
		case EAST:
		    matrixStackIn.rotate(new Quaternion(0, -90, 0, true));
		    break;
		case NORTH:
		    break;
		case SOUTH:
		    matrixStackIn.rotate(new Quaternion(0, 180, 0, true));
		    break;
		case WEST:
		    matrixStackIn.rotate(new Quaternion(0, 90, 0, true));
		    break;
		default:
		    break;
		}
		StringTextComponent displayNameIn = new StringTextComponent(
			"Voltage: " + ChatFormatter.getDisplayShort(tilemultimeter.voltage, ElectricUnit.VOLTAGE, 2));
		FontRenderer fontrenderer = Minecraft.getInstance().fontRenderer;
		float scale = 0.0215f / (fontrenderer.getStringPropertyWidth(displayNameIn) / 32f);
		matrixStackIn.scale(-scale, -scale, -scale);
		Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
		float f2 = -fontrenderer.getStringPropertyWidth(displayNameIn) / 2.0f;
		fontrenderer.func_243247_a(displayNameIn, f2, 0, 0xffffff, false, matrix4f, bufferIn, false, 0, combinedLightIn);
		matrixStackIn.pop();
	    }
	}
	for (Direction dir : Direction.values()) {
	    if (dir != Direction.UP && dir != Direction.DOWN
		    && dir != tilemultimeter.<ComponentDirection>getComponent(ComponentType.Direction).getDirection()) {
		matrixStackIn.push();
		matrixStackIn.translate(0.5 + dir.getXOffset() / 1.999, 0.45 + dir.getYOffset() / 2.0, 0.5 + dir.getZOffset() / 1.999);
		switch (dir) {
		case EAST:
		    matrixStackIn.rotate(new Quaternion(0, -90, 0, true));
		    break;
		case NORTH:
		    break;
		case SOUTH:
		    matrixStackIn.rotate(new Quaternion(0, 180, 0, true));
		    break;
		case WEST:
		    matrixStackIn.rotate(new Quaternion(0, 90, 0, true));
		    break;
		default:
		    break;
		}
		StringTextComponent displayNameIn = new StringTextComponent(
			"Resistance: " + ChatFormatter.getDisplayShort(tilemultimeter.resistance, ElectricUnit.RESISTANCE, 2));
		FontRenderer fontrenderer = Minecraft.getInstance().fontRenderer;
		float scale = 0.0215f / (fontrenderer.getStringPropertyWidth(displayNameIn) / 32f);
		matrixStackIn.scale(-scale, -scale, -scale);
		Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
		float f2 = -fontrenderer.getStringPropertyWidth(displayNameIn) / 2.0f;
		fontrenderer.func_243247_a(displayNameIn, f2, 0, 0xffffff, false, matrix4f, bufferIn, false, 0, combinedLightIn);
		matrixStackIn.pop();
	    }
	}
	for (Direction dir : Direction.values()) {
	    if (dir != Direction.UP && dir != Direction.DOWN
		    && dir != tilemultimeter.<ComponentDirection>getComponent(ComponentType.Direction).getDirection()) {
		matrixStackIn.push();
		matrixStackIn.translate(0.5 + dir.getXOffset() / 1.999, 0.25 + dir.getYOffset() / 2.0, 0.5 + dir.getZOffset() / 1.999);
		switch (dir) {
		case EAST:
		    matrixStackIn.rotate(new Quaternion(0, -90, 0, true));
		    break;
		case NORTH:
		    break;
		case SOUTH:
		    matrixStackIn.rotate(new Quaternion(0, 180, 0, true));
		    break;
		case WEST:
		    matrixStackIn.rotate(new Quaternion(0, 90, 0, true));
		    break;
		default:
		    break;
		}
		StringTextComponent displayNameIn = new StringTextComponent(
			"Loss: " + ChatFormatter.getDisplayShort(tilemultimeter.loss * 20, ElectricUnit.WATT, 2));
		FontRenderer fontrenderer = Minecraft.getInstance().fontRenderer;
		float scale = 0.0215f / (fontrenderer.getStringPropertyWidth(displayNameIn) / 32f);
		matrixStackIn.scale(-scale, -scale, -scale);
		Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
		float f2 = -fontrenderer.getStringPropertyWidth(displayNameIn) / 2.0f;
		fontrenderer.func_243247_a(displayNameIn, f2, 0, 0xffffff, false, matrix4f, bufferIn, false, 0, combinedLightIn);
		matrixStackIn.pop();
	    }
	}
    }

}
