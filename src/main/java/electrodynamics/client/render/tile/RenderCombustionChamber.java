package electrodynamics.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.DeferredRegisters;
import electrodynamics.client.ClientRegister;
import electrodynamics.common.tile.TileCombustionChamber;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.utilities.UtilitiesRendering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;

public class RenderCombustionChamber implements BlockEntityRenderer<TileCombustionChamber> {
	public RenderCombustionChamber(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(TileCombustionChamber tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn,
			int combinedLightIn, int combinedOverlayIn) {
		matrixStackIn.pushPose();
		float prog = tileEntityIn.<ComponentFluidHandlerMulti>getComponent(ComponentType.FluidHandler)
				.getTankFromFluid(DeferredRegisters.fluidEthanol, true).getFluidAmount() / (float) TileCombustionChamber.TANK_CAPACITY;
		matrixStackIn.translate(0, 2.0 / 16.0, 0);
		BakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_COMBUSTIONCHAMBERETHANOL);
		UtilitiesRendering.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
		if (prog > 0) {
			UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn,
					combinedOverlayIn);
		}
		matrixStackIn.popPose();
	}

}
