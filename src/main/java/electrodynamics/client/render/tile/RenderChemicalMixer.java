package electrodynamics.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;

import electrodynamics.DeferredRegisters;
import electrodynamics.client.ClientRegister;
import electrodynamics.common.tile.TileChemicalMixer;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.utilities.UtilitiesRendering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.material.Fluids;

public class RenderChemicalMixer implements BlockEntityRenderer<TileChemicalMixer> {
	public RenderChemicalMixer(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(TileChemicalMixer tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn,
			int combinedOverlayIn) {

		BakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CHEMICALMIXERBASE);
		matrixStackIn.pushPose();
		UtilitiesRendering.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
		matrixStackIn.translate(0, 1 / 16.0, 0);
		UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		matrixStackIn.popPose();

		matrixStackIn.pushPose();
		ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CHEMICALMIXERBLADES);
		matrixStackIn.translate(0.5, 7.0 / 16.0, 0.5);
		matrixStackIn.mulPose(new Quaternion(0,
				(tileEntityIn.clientTicks
						+ (tileEntityIn.<ComponentProcessor>getComponent(ComponentType.Processor).operatingTicks > 0 ? partialTicks : 0)) * 10,
				0, true));
		UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		matrixStackIn.popPose();

		matrixStackIn.pushPose();
		UtilitiesRendering.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
		matrixStackIn.translate(0.5, 0.2, 0.5);
		ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CHEMICALMIXERWATER);
		float prog = (tileEntityIn.<ComponentFluidHandlerMulti>getComponent(ComponentType.FluidHandler).getTankFromFluid(Fluids.WATER, true)
				.getFluidAmount()
				+ tileEntityIn.<ComponentFluidHandlerMulti>getComponent(ComponentType.FluidHandler)
						.getTankFromFluid(DeferredRegisters.fluidSulfuricAcid, true).getFluidAmount())
				/ (float) TileChemicalMixer.MAX_TANK_CAPACITY;
		if (prog > 0) {
			matrixStackIn.scale(1, prog / 16.0f, 1);
			matrixStackIn.translate(0, prog / 8.0, 0);
			if (tileEntityIn.<ComponentFluidHandlerMulti>getComponent(ComponentType.FluidHandler)
					.getTankFromFluid(DeferredRegisters.fluidSulfuricAcid, true).getFluidAmount() > 0) {
				ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CHEMICALMIXERSULFURICACID);
			}
			UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn,
					combinedOverlayIn);
		}
		matrixStackIn.popPose();
	}

}
