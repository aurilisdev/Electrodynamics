package electrodynamics.client.render.tile;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.client.ClientRegister;
import electrodynamics.common.tile.machines.mineralgrinder.TileMineralGrinder;
import electrodynamics.prefab.utilities.RenderingUtils;
import electrodynamics.prefab.utilities.math.MathUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;

public class RenderMineralGrinderDouble extends AbstractTileRenderer<TileMineralGrinder> {

	public RenderMineralGrinderDouble(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(TileMineralGrinder tileEntityIn, float partialTicks, PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		double progress = (tileEntityIn.clientRunningTicks + (tileEntityIn.getProcessor(0).operatingTicks.get() > 0 ? partialTicks : 0)) * 10;
		BakedModel ibakedmodel = getModel(ClientRegister.MODEL_MINERALGRINDERWHEEL);
		matrixStackIn.pushPose();
		RenderingUtils.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
		matrixStackIn.translate(0.0, 7.0 / 16.0, 2.5 / 16.0);
		matrixStackIn.mulPose(MathUtils.rotQuaternionDeg((float) -progress, 0, 0));
		// matrixStackIn.mulPose(new Quaternion((float) -progress, 0, 0, true));
		RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		matrixStackIn.popPose();
		matrixStackIn.pushPose();
		RenderingUtils.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
		matrixStackIn.translate(0.0, 7.0 / 16.0, -2.5 / 16.0);
		matrixStackIn.mulPose(MathUtils.rotQuaternionDeg((float) progress, 0, 0));
		// matrixStackIn.mulPose(new Quaternion((float) progress, 0, 0, true));
		RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		matrixStackIn.popPose();
		matrixStackIn.pushPose();
		RenderingUtils.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
		matrixStackIn.translate(0, 1.0 / 16.0, 0);
		matrixStackIn.popPose();

	}
}
