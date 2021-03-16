package electrodynamics.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.utilities.UtilitiesRendering;
import electrodynamics.client.ClientRegister;
import electrodynamics.common.block.BlockGenericMachine;
import electrodynamics.common.tile.TileBatteryBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Quaternion;

public class RenderBatteryBox extends TileEntityRenderer<TileBatteryBox> {

    public RenderBatteryBox(TileEntityRendererDispatcher rendererDispatcherIn) {
	super(rendererDispatcherIn);
    }

    @Override
    @Deprecated
    public void render(TileBatteryBox tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn,
	    int combinedOverlayIn) {
	IBakedModel ibakedmodel;
	int stored = (int) (tileEntityIn.getEnergyStored() / tileEntityIn.clientMaxJoulesStored * 6);
	switch (stored) {
	default:
	case 0:
	    ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_BATTERYBOX);
	    break;
	case 1:
	    ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_BATTERYBOX2);
	    break;
	case 2:
	    ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_BATTERYBOX3);
	    break;
	case 3:
	    ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_BATTERYBOX4);
	    break;
	case 4:
	    ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_BATTERYBOX5);
	    break;
	case 5:
	    ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_BATTERYBOX6);
	    break;
	case 6:
	    ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_BATTERYBOX7);
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
	UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getSolid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }
}
