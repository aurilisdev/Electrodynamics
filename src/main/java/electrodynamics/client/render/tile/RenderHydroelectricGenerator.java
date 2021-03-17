package electrodynamics.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.utilities.UtilitiesRendering;
import electrodynamics.client.ClientRegister;
import electrodynamics.common.tile.TileHydroelectricGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Quaternion;

public class RenderHydroelectricGenerator extends TileEntityRenderer<TileHydroelectricGenerator> {

    public RenderHydroelectricGenerator(TileEntityRendererDispatcher rendererDispatcherIn) {
	super(rendererDispatcherIn);
    }

    @Override
    @Deprecated
    public void render(TileHydroelectricGenerator tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn,
	    int combinedLightIn, int combinedOverlayIn) {
	IBakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_HYDROELECTRICGENERATORBLADES);
	UtilitiesRendering.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
	matrixStackIn.rotate(new Quaternion(
		(float) ((tileEntityIn.savedTickRotation + partialTicks * (tileEntityIn.isGenerating ? tileEntityIn.directionFlag ? 1 : -1 : 0))
			* 5f),
		0, 0, true));
	UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getSolid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }
}
