package electrodynamics.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;

import electrodynamics.client.ClientRegister;
import electrodynamics.common.tile.TileCarbyneBatteryBox;
import electrodynamics.prefab.block.GenericEntityBlock;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;

public class RenderCarbyneBatteryBox implements BlockEntityRenderer<TileCarbyneBatteryBox> {
	public RenderCarbyneBatteryBox(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(TileCarbyneBatteryBox tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		BakedModel ibakedmodel;
		int stored = (int) (tileEntityIn.clientJoules.get() / tileEntityIn.clientMaxJoulesStored.get() * 6);
		ibakedmodel = switch (stored) {
		case 0 -> Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CARBYNEBATTERYBOX);
		case 1 -> Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CARBYNEBATTERYBOX2);
		case 2 -> Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CARBYNEBATTERYBOX3);
		case 3 -> Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CARBYNEBATTERYBOX4);
		case 4 -> Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CARBYNEBATTERYBOX5);
		case 5 -> Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CARBYNEBATTERYBOX6);
		case 6 -> Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CARBYNEBATTERYBOX7);
		default -> Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_BATTERYBOX);
		};

		switch (tileEntityIn.getBlockState().getValue(GenericEntityBlock.FACING)) {
		case NORTH:
			matrixStackIn.mulPose(new Quaternion(0, 90, 0, true));
			matrixStackIn.translate(-1, 0, 0);
			break;
		case SOUTH:
			matrixStackIn.mulPose(new Quaternion(0, 270, 0, true));
			matrixStackIn.translate(0, 0, -1);
			break;
		case WEST:
			matrixStackIn.mulPose(new Quaternion(0, 180, 0, true));
			matrixStackIn.translate(-1, 0, -1);
			break;
		default:
			break;
		}
		matrixStackIn.translate(0.5, 0.5, 0.5);
		RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	}
}
