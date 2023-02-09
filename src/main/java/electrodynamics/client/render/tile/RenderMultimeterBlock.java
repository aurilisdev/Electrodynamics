package electrodynamics.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.tile.TileMultimeterBlock;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;

public class RenderMultimeterBlock extends AbstractTileRenderer<TileMultimeterBlock> {
	
	public RenderMultimeterBlock(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(TileMultimeterBlock tilemultimeter, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		for (Direction dir : Direction.values()) {
			if (dir != Direction.UP && dir != Direction.DOWN && dir != tilemultimeter.<ComponentDirection>getComponent(ComponentType.Direction).getDirection()) {
				matrixStackIn.pushPose();
				matrixStackIn.translate(0.5 + dir.getStepX() / 1.999, 0.85 + dir.getStepY() / 2.0, 0.5 + dir.getStepZ() / 1.999);
				switch (dir) {
				case EAST:
					matrixStackIn.mulPose(new Quaternion(0, -90, 0, true));
					break;
				case NORTH:
					break;
				case SOUTH:
					matrixStackIn.mulPose(new Quaternion(0, 180, 0, true));
					break;
				case WEST:
					matrixStackIn.mulPose(new Quaternion(0, 90, 0, true));
					break;
				default:
					break;
				}
				Component displayNameIn = Component.literal("Transfer: " + ChatFormatter.getDisplayShort(tilemultimeter.joules.get() * 20, DisplayUnit.WATT, 2));
				Font fontrenderer = Minecraft.getInstance().font;
				float scale = 0.0215f / (fontrenderer.width(displayNameIn) / 32f);
				matrixStackIn.scale(-scale, -scale, -scale);
				Matrix4f matrix4f = matrixStackIn.last().pose();
				float f2 = -fontrenderer.width(displayNameIn) / 2.0f;
				fontrenderer.drawInBatch(displayNameIn, f2, 0, 0xffffff, false, matrix4f, bufferIn, false, 0, combinedLightIn);
				matrixStackIn.popPose();
			}
		}
		for (Direction dir : Direction.values()) {
			if (dir != Direction.UP && dir != Direction.DOWN && dir != tilemultimeter.<ComponentDirection>getComponent(ComponentType.Direction).getDirection()) {
				matrixStackIn.pushPose();
				matrixStackIn.translate(0.5 + dir.getStepX() / 1.999, 0.65 + dir.getStepY() / 2.0, 0.5 + dir.getStepZ() / 1.999);
				switch (dir) {
				case EAST:
					matrixStackIn.mulPose(new Quaternion(0, -90, 0, true));
					break;
				case NORTH:
					break;
				case SOUTH:
					matrixStackIn.mulPose(new Quaternion(0, 180, 0, true));
					break;
				case WEST:
					matrixStackIn.mulPose(new Quaternion(0, 90, 0, true));
					break;
				default:
					break;
				}
				Component displayNameIn = Component.literal("Voltage: " + ChatFormatter.getDisplayShort(tilemultimeter.voltage.get(), DisplayUnit.VOLTAGE, 2));
				Font fontrenderer = Minecraft.getInstance().font;
				float scale = 0.0215f / (fontrenderer.width(displayNameIn) / 32f);
				matrixStackIn.scale(-scale, -scale, -scale);
				Matrix4f matrix4f = matrixStackIn.last().pose();
				float f2 = -fontrenderer.width(displayNameIn) / 2.0f;
				fontrenderer.drawInBatch(displayNameIn, f2, 0, 0xffffff, false, matrix4f, bufferIn, false, 0, combinedLightIn);
				matrixStackIn.popPose();
			}
		}
		for (Direction dir : Direction.values()) {
			if (dir != Direction.UP && dir != Direction.DOWN && dir != tilemultimeter.<ComponentDirection>getComponent(ComponentType.Direction).getDirection()) {
				matrixStackIn.pushPose();
				matrixStackIn.translate(0.5 + dir.getStepX() / 1.999, 0.45 + dir.getStepY() / 2.0, 0.5 + dir.getStepZ() / 1.999);
				switch (dir) {
				case EAST:
					matrixStackIn.mulPose(new Quaternion(0, -90, 0, true));
					break;
				case NORTH:
					break;
				case SOUTH:
					matrixStackIn.mulPose(new Quaternion(0, 180, 0, true));
					break;
				case WEST:
					matrixStackIn.mulPose(new Quaternion(0, 90, 0, true));
					break;
				default:
					break;
				}
				Component displayNameIn = Component.literal("Resistance: " + ChatFormatter.getDisplayShort(tilemultimeter.resistance.get(), DisplayUnit.RESISTANCE, 2));
				Font fontrenderer = Minecraft.getInstance().font;
				float scale = 0.0215f / (fontrenderer.width(displayNameIn) / 32f);
				matrixStackIn.scale(-scale, -scale, -scale);
				Matrix4f matrix4f = matrixStackIn.last().pose();
				float f2 = -fontrenderer.width(displayNameIn) / 2.0f;
				fontrenderer.drawInBatch(displayNameIn, f2, 0, 0xffffff, false, matrix4f, bufferIn, false, 0, combinedLightIn);
				matrixStackIn.popPose();
			}
		}
		for (Direction dir : Direction.values()) {
			if (dir != Direction.UP && dir != Direction.DOWN && dir != tilemultimeter.<ComponentDirection>getComponent(ComponentType.Direction).getDirection()) {
				matrixStackIn.pushPose();
				matrixStackIn.translate(0.5 + dir.getStepX() / 1.999, 0.25 + dir.getStepY() / 2.0, 0.5 + dir.getStepZ() / 1.999);
				switch (dir) {
				case EAST:
					matrixStackIn.mulPose(new Quaternion(0, -90, 0, true));
					break;
				case NORTH:
					break;
				case SOUTH:
					matrixStackIn.mulPose(new Quaternion(0, 180, 0, true));
					break;
				case WEST:
					matrixStackIn.mulPose(new Quaternion(0, 90, 0, true));
					break;
				default:
					break;
				}
				Component displayNameIn = Component.literal("Loss: " + ChatFormatter.getDisplayShort(tilemultimeter.loss.get() * 20, DisplayUnit.WATT, 2));
				Font fontrenderer = Minecraft.getInstance().font;
				float scale = 0.0215f / (fontrenderer.width(displayNameIn) / 32f);
				matrixStackIn.scale(-scale, -scale, -scale);
				Matrix4f matrix4f = matrixStackIn.last().pose();
				float f2 = -fontrenderer.width(displayNameIn) / 2.0f;
				fontrenderer.drawInBatch(displayNameIn, f2, 0, 0xffffff, false, matrix4f, bufferIn, false, 0, combinedLightIn);
				matrixStackIn.popPose();
			}
		}
	}

}
