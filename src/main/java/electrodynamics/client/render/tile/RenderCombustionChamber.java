package electrodynamics.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.client.ClientRegister;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.common.tile.TileCombustionChamber;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraftforge.fluids.FluidStack;

public class RenderCombustionChamber implements BlockEntityRenderer<TileCombustionChamber> {
	public RenderCombustionChamber(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(TileCombustionChamber tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		matrixStackIn.pushPose();
		FluidStack fuel = tileEntityIn.<ComponentFluidHandlerMulti>getComponent(ComponentType.FluidHandler).getFluidInTank(0, true);
		float prog = fuel.getAmount() / (float) TileCombustionChamber.TANK_CAPACITY;
		matrixStackIn.translate(0, 2.0 / 16.0, 0);
		RenderingUtils.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
		if (prog > 0) {
			BakedModel ibakedmodel;
			if (ElectrodynamicsTags.Fluids.ETHANOL.getValues().contains(fuel.getFluid())) {
				ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_COMBUSTIONCHAMBERETHANOL);
			} else {
				ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_COMBUSTIONCHAMBERHYDROGEN);
			}
			RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);

		}
		matrixStackIn.popPose();
	}

}
