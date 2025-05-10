package electrodynamics.client.render.tile;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.common.tile.electricitygrid.transformer.TileAdvancedTransformer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.utilities.math.Color;

public abstract class RenderAdvancedTransformer extends AbstractTileRenderer<TileAdvancedTransformer> {

	public RenderAdvancedTransformer(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(@Nonnull TileAdvancedTransformer tile, float partialTick, @Nonnull MatrixStack poseStack, @Nonnull IRenderTypeBuffer bufferSource, int packedLight, int packedOverlay) {

		Direction facing = tile.getFacing();

		FontRenderer font = Minecraft.getInstance().font;

		ITextComponent transfer = new StringTextComponent(getTurnsString(tile));

		float scale = 0.0215f / (font.width(transfer) / 16f);

		float textX = -font.width(transfer) / 2.0f;

		poseStack.pushPose();

		Direction clockwise = facing.getClockWise();

		poseStack.translate(0.5 + clockwise.getStepX() * 0.438, 0.55 + clockwise.getStepY() / 2.0, 0.5 + clockwise.getStepZ() * 0.438);

		rotateMatrix(poseStack, clockwise);

		poseStack.scale(-scale, -scale, -scale);

		Matrix4f matrix4f = poseStack.last().pose();

		font.drawInBatch(transfer, textX, 0, Color.WHITE.color(), false, matrix4f, bufferSource, false, 0, packedLight);

		poseStack.popPose();

		poseStack.pushPose();

		Direction counterClockwise = facing.getCounterClockWise();

		poseStack.translate(0.5 - clockwise.getStepX() * 0.438, 0.55 + clockwise.getStepY() / 2.0, 0.5 - clockwise.getStepZ() * 0.438);

		rotateMatrix(poseStack, counterClockwise);

		poseStack.scale(-scale, -scale, -scale);

		matrix4f = poseStack.last().pose();

		font.drawInBatch(transfer, textX, 0, Color.WHITE.color(), false, matrix4f, bufferSource, false, 0, packedLight);

		poseStack.popPose();

	}

	public abstract String getTurnsString(TileAdvancedTransformer transformer);

	private void rotateMatrix(MatrixStack stack, Direction dir) {
		switch (dir) {
		case EAST:
			stack.mulPose(new Quaternion(0, -90, 0, true));
			break;
		case SOUTH:
			stack.mulPose(new Quaternion(0, 180, 0, true));
			break;
		case WEST:
			stack.mulPose(new Quaternion(0, 90, 0, true));
			break;
		default:
			break;
		}
	}

	public static class RenderAdvancedUpgradeTransformer extends RenderAdvancedTransformer {

		public RenderAdvancedUpgradeTransformer(TileEntityRendererDispatcher context) {
			super(context);
		}

		@Override
		public String getTurnsString(TileAdvancedTransformer transformer) {
			int ratio = transformer.coilRatio.getValue().intValue();

			return "1 : " + ratio;
		}
	}

	public static class RenderAdvancedDowngradeTransformer extends RenderAdvancedTransformer {

		public RenderAdvancedDowngradeTransformer(TileEntityRendererDispatcher context) {
			super(context);
		}

		@Override
		public String getTurnsString(TileAdvancedTransformer transformer) {
			int ratio = (int) (1.0 / transformer.coilRatio.getValue());

			return ratio + " : 1";
		}
	}
}
