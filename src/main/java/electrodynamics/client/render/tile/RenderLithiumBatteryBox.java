package electrodynamics.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.client.ClientRegister;
import electrodynamics.common.block.BlockGenericMachine;
import electrodynamics.common.tile.TileLithiumBatteryBox;
import electrodynamics.prefab.utilities.UtilitiesRendering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Quaternion;

public class RenderLithiumBatteryBox extends TileEntityRenderer<TileLithiumBatteryBox> {

    public RenderLithiumBatteryBox(TileEntityRendererDispatcher rendererDispatcherIn) {
	super(rendererDispatcherIn);
    }

    @Override
    @Deprecated
    public void render(TileLithiumBatteryBox tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn,
	    int combinedLightIn, int combinedOverlayIn) {
	IBakedModel ibakedmodel;
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

	switch (tileEntityIn.getBlockState().get(BlockGenericMachine.FACING)) {
	case NORTH:
	    matrixStackIn.rotate(new Quaternion(0, 90, 0, true));
	    matrixStackIn.translate(-1, 0, 0);
	    break;
	case SOUTH:
	    matrixStackIn.rotate(new Quaternion(0, 270, 0, true));
	    matrixStackIn.translate(0, 0, -1);
	    break;
	case WEST:
	    matrixStackIn.rotate(new Quaternion(0, 180, 0, true));
	    matrixStackIn.translate(-1, 0, -1);
	    break;
	default:
	    break;
	}
	matrixStackIn.translate(0.5, 0.5, 0.5);
	UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getSolid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }
}
