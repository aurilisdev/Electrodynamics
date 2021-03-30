package electrodynamics.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.tile.components.ComponentType;
import electrodynamics.api.tile.components.type.ComponentFluidHandler;
import electrodynamics.api.tile.components.type.ComponentProcessor;
import electrodynamics.api.utilities.UtilitiesRendering;
import electrodynamics.client.ClientRegister;
import electrodynamics.common.tile.TileChemicalMixer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.vector.Quaternion;

public class RenderChemicalMixer extends TileEntityRenderer<TileChemicalMixer> {

    public RenderChemicalMixer(TileEntityRendererDispatcher rendererDispatcherIn) {
	super(rendererDispatcherIn);
    }

    @Override
    @Deprecated
    public void render(TileChemicalMixer tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn,
	    int combinedOverlayIn) {
	IBakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CHEMICALMIXERBASE);
	matrixStackIn.push();
	UtilitiesRendering.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
	matrixStackIn.translate(0, 1 / 16.0, 0);
	UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getSolid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	matrixStackIn.pop();

	matrixStackIn.push();
	ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CHEMICALMIXERBLADES);
	matrixStackIn.translate(0.5, 7.0 / 16.0, 0.5);
	matrixStackIn.rotate(new Quaternion(0,
		(tileEntityIn.clientTicks
			+ (tileEntityIn.<ComponentProcessor>getComponent(ComponentType.Processor).operatingTicks > 0 ? partialTicks : 0)) * 10,
		0, true));
	UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getSolid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	matrixStackIn.pop();

	matrixStackIn.push();
	UtilitiesRendering.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
	matrixStackIn.translate(0.5, 0.2, 0.5);
	ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CHEMICALMIXERWATER);
	float prog = tileEntityIn.<ComponentFluidHandler>getComponent(ComponentType.FluidHandler).getStackFromFluid(Fluids.WATER).getAmount()
		/ (float) TileChemicalMixer.TANKCAPACITY;
	if (prog > 0) {
	    matrixStackIn.scale(1, prog / 16.0f * 4f, 1);
	    UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getSolid(), matrixStackIn, bufferIn, combinedLightIn,
		    combinedOverlayIn);
	}
	matrixStackIn.pop();
    }

}
