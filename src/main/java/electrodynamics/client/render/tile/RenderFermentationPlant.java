package electrodynamics.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.DeferredRegisters;
import electrodynamics.client.ClientRegister;
import electrodynamics.common.tile.TileFermentationPlant;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.utilities.UtilitiesRendering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.material.Fluids;

public class RenderFermentationPlant implements BlockEntityRenderer<TileFermentationPlant> {

    @Override
    public void render(TileFermentationPlant tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn,
	    int combinedLightIn, int combinedOverlayIn) {
	matrixStackIn.pushPose();
	float prog = tileEntityIn.<ComponentFluidHandlerMulti>getComponent(ComponentType.FluidHandler)
		.getStackFromFluid(DeferredRegisters.fluidEthanol, false).getAmount() / (float) TileFermentationPlant.MAX_TANK_CAPACITY;
	matrixStackIn.translate(0, 1.0 / 16.0 - 2.5 / 16.0 * (1 - prog), 0);
	BakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_FERMENTATIONPLANTETHANOL);
	UtilitiesRendering.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
	if (prog > 0) {
	    matrixStackIn.scale(1, prog / 16.0f * 12f, 1);
	    UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn,
		    combinedOverlayIn);
	}
	matrixStackIn.popPose();
	matrixStackIn.pushPose();
	matrixStackIn.translate(0, 1.0 / 16.0, 0);
	ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_FERMENTATIONPLANTWATER);
	UtilitiesRendering.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
	prog = tileEntityIn.<ComponentFluidHandlerMulti>getComponent(ComponentType.FluidHandler).getStackFromFluid(Fluids.WATER, true).getAmount()
		/ (float) TileFermentationPlant.MAX_TANK_CAPACITY;
	if (prog > 0) {
	    matrixStackIn.scale(1, prog / 16.0f * 12f, 1);
	    UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn,
		    combinedOverlayIn);
	}
	matrixStackIn.popPose();
    }

}
