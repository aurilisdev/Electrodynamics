package electrodynamics.client.render.tile;

import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.tile.electricitygrid.TileMultimeterBlock;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.math.Color;
import electrodynamics.prefab.utilities.math.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;

public class RenderMultimeterBlock extends AbstractTileRenderer<TileMultimeterBlock> {

	public static final Direction[] DIRS_TO_CHECK = { Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST };

	public RenderMultimeterBlock(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(@NotNull TileMultimeterBlock multimeter, float partialTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource buffer, int combinedLight, int combinedOverlay) {

		for (Direction dir : DIRS_TO_CHECK) {

			if (dir == multimeter.getFacing()) {
				continue;
			}

			/* TRANSFER */

			Font font = Minecraft.getInstance().font;

			stack.pushPose();

			stack.translate(0.5 + dir.getStepX() / 1.999, 0.85 + dir.getStepY() / 2.0, 0.5 + dir.getStepZ() / 1.999);

			rotateMatrix(stack, dir);

			Component transfer = ElectroTextUtils.gui("multimeterblock.transfer", ChatFormatter.getDisplayShort(multimeter.joules.get() * 20.0, DisplayUnit.WATT, 2));

			float scale = 0.0215f / (font.width(transfer) / 32f);

			stack.scale(-scale, -scale, -scale);

			Matrix4f matrix4f = stack.last().pose();

			float textX = -font.width(transfer) / 2.0f;

			font.drawInBatch(transfer, textX, 0, Color.WHITE.color(), false, matrix4f, buffer, DisplayMode.NORMAL, 0, combinedLight);

			stack.popPose();

			/* VOLTAGE */

			stack.pushPose();

			stack.translate(0.5 + dir.getStepX() / 1.999, 0.70 + dir.getStepY() / 2.0, 0.5 + dir.getStepZ() / 1.999);

			rotateMatrix(stack, dir);

			Component voltage = ElectroTextUtils.gui("multimeterblock.voltage", ChatFormatter.getDisplayShort(multimeter.voltage.get(), DisplayUnit.VOLTAGE, 2));

			scale = 0.0215f / (font.width(voltage) / 32f);

			stack.scale(-scale, -scale, -scale);

			matrix4f = stack.last().pose();

			textX = -font.width(voltage) / 2.0f;

			font.drawInBatch(voltage, textX, 0, Color.WHITE.color(), false, matrix4f, buffer, DisplayMode.NORMAL, 0, combinedLight);

			stack.popPose();

			/* MINIMUM VOLTAGE */

			stack.pushPose();

			stack.translate(0.5 + dir.getStepX() / 1.999, 0.55 + dir.getStepY() / 2.0, 0.5 + dir.getStepZ() / 1.999);

			rotateMatrix(stack, dir);

			double minVolt = multimeter.minVoltage.get();
			if (minVolt < 0) {
				minVolt = multimeter.voltage.get();
			}

			Component minVoltage = ElectroTextUtils.gui("multimeterblock.minvoltage", ChatFormatter.getDisplayShort(minVolt, DisplayUnit.VOLTAGE, 2));

			scale = 0.0215f / (font.width(minVoltage) / 32f);

			stack.scale(-scale, -scale, -scale);

			matrix4f = stack.last().pose();

			textX = -font.width(minVoltage) / 2.0f;

			font.drawInBatch(minVoltage, textX, 0, Color.WHITE.color(), false, matrix4f, buffer, DisplayMode.NORMAL, 0, combinedLight);

			stack.popPose();

			/* RESISTANCE */

			stack.pushPose();

			stack.translate(0.5 + dir.getStepX() / 1.999, 0.40 + dir.getStepY() / 2.0, 0.5 + dir.getStepZ() / 1.999);

			rotateMatrix(stack, dir);

			Component resistance = ElectroTextUtils.gui("multimeterblock.resistance", ChatFormatter.getDisplayShort(multimeter.resistance.get(), DisplayUnit.RESISTANCE, 2));

			scale = 0.0215f / (font.width(resistance) / 32f);

			stack.scale(-scale, -scale, -scale);

			matrix4f = stack.last().pose();

			textX = -font.width(resistance) / 2.0f;

			font.drawInBatch(resistance, textX, 0, Color.WHITE.color(), false, matrix4f, buffer, DisplayMode.NORMAL, 0, combinedLight);

			stack.popPose();

			/* LOSS (is this) */

			stack.pushPose();

			stack.translate(0.5 + dir.getStepX() / 1.999, 0.25 + dir.getStepY() / 2.0, 0.5 + dir.getStepZ() / 1.999);

			rotateMatrix(stack, dir);

			Component loss = ElectroTextUtils.gui("multimeterblock.loss", ChatFormatter.getDisplayShort(multimeter.loss.get() * 20, DisplayUnit.WATT, 2));

			scale = 0.0215f / (font.width(loss) / 32f);

			stack.scale(-scale, -scale, -scale);

			matrix4f = stack.last().pose();

			textX = -font.width(loss) / 2.0f;

			font.drawInBatch(loss, textX, 0, Color.WHITE.color(), false, matrix4f, buffer, DisplayMode.NORMAL, 0, combinedLight);

			stack.popPose();

		}

	}

	private void rotateMatrix(PoseStack stack, Direction dir) {
		switch (dir) {
		case EAST -> stack.mulPose(MathUtils.rotQuaternionDeg(0, -90, 0));// stack.mulPose(new Quaternion(0, -90, 0, true));
		case SOUTH -> stack.mulPose(MathUtils.rotQuaternionDeg(0, 180, 0));// stack.mulPose(new Quaternion(0, 180, 0, true));
		case WEST -> stack.mulPose(MathUtils.rotQuaternionDeg(0, 90, 0));// stack.mulPose(new Quaternion(0, 90, 0, true));
		default -> {
		}
		}
	}

}
