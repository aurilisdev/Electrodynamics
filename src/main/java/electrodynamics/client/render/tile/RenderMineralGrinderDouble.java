package electrodynamics.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.client.ClientRegister;
import electrodynamics.common.tile.TileMineralGrinder;
import electrodynamics.prefab.utilities.UtilitiesRendering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Quaternion;

public class RenderMineralGrinderDouble extends TileEntityRenderer<TileMineralGrinder> {

    public RenderMineralGrinderDouble(TileEntityRendererDispatcher rendererDispatcherIn) {
	super(rendererDispatcherIn);
    }

    @Override
    @Deprecated
    public void render(TileMineralGrinder tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn,
	    int combinedLightIn, int combinedOverlayIn) {
	double progress = (tileEntityIn.clientRunningTicks + (tileEntityIn.getProcessor(0).operatingTicks > 0 ? partialTicks : 0)) * 10;
	IBakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_MINERALGRINDERWHEEL);
	matrixStackIn.push();
	UtilitiesRendering.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
	matrixStackIn.translate(0.0, 7.0 / 16.0, 2.5 / 16.0);
	matrixStackIn.rotate(new Quaternion((float) -progress, 0, 0, true));
	UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getSolid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	matrixStackIn.pop();
	matrixStackIn.push();
	UtilitiesRendering.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
	matrixStackIn.translate(0.0, 7.0 / 16.0, -2.5 / 16.0);
	matrixStackIn.rotate(new Quaternion((float) progress, 0, 0, true));
	UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getSolid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	matrixStackIn.pop();
	matrixStackIn.push();
	UtilitiesRendering.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
	matrixStackIn.translate(0, 1.0 / 16.0, 0);
	ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_MINERALGRINDERDOUBLEBASE);
	UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getSolid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	matrixStackIn.translate(0, progress / 8.0 - 1 / 8.0, 0);
	matrixStackIn.pop();

    }
}
