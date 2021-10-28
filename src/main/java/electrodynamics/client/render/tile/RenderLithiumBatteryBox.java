package electrodynamics.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;

import electrodynamics.client.ClientRegister;
import electrodynamics.common.block.BlockGenericMachine;
import electrodynamics.common.tile.TileLithiumBatteryBox;
import electrodynamics.prefab.utilities.UtilitiesRendering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;

public class RenderLithiumBatteryBox implements BlockEntityRenderer<TileLithiumBatteryBox> {

    @Override
    public void render(TileLithiumBatteryBox tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn,
	    int combinedLightIn, int combinedOverlayIn) {
	BakedModel ibakedmodel;
	int stored = (int) (tileEntityIn.clientJoules / tileEntityIn.clientMaxJoulesStored * 6);
	switch (stored) {
	default:
	case 0:
	    ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_LITHIUMBATTERYBOX);
	    break;
	case 1:
	    ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_LITHIUMBATTERYBOX2);
	    break;
	case 2:
	    ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_LITHIUMBATTERYBOX3);
	    break;
	case 3:
	    ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_LITHIUMBATTERYBOX4);
	    break;
	case 4:
	    ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_LITHIUMBATTERYBOX5);
	    break;
	case 5:
	    ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_LITHIUMBATTERYBOX6);
	    break;
	case 6:
	    ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_LITHIUMBATTERYBOX7);
	    break;
	}

	switch (tileEntityIn.getBlockState().getValue(BlockGenericMachine.FACING)) {
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
	UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }
}
