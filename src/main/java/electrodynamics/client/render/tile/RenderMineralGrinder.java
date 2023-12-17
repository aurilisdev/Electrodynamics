package electrodynamics.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.client.ClientRegister;
import electrodynamics.common.tile.machines.mineralgrinder.TileMineralGrinder;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Quaternion;

public class RenderMineralGrinder extends AbstractTileRenderer<TileMineralGrinder> {

	public RenderMineralGrinder(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(TileMineralGrinder tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		double progress = (tileEntityIn.clientRunningTicks + (tileEntityIn.getProcessor(0).operatingTicks.get() > 0 ? partialTicks : 0)) * 10;
		IBakedModel ibakedmodel = getModel(ClientRegister.MODEL_MINERALGRINDERWHEEL);
		matrixStackIn.pushPose();
		RenderingUtils.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
		matrixStackIn.translate(0.0, 7.0 / 16.0, 2.5 / 16.0);
		matrixStackIn.mulPose(new Quaternion((float) -progress, 0, 0, true));
		RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		matrixStackIn.popPose();
		matrixStackIn.pushPose();
		RenderingUtils.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
		matrixStackIn.translate(0.0, 7.0 / 16.0, -2.5 / 16.0);
		matrixStackIn.mulPose(new Quaternion((float) progress, 0, 0, true));
		RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		matrixStackIn.popPose();
		matrixStackIn.pushPose();
		RenderingUtils.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
		matrixStackIn.translate(0, 1.0 / 16.0, 0);
		matrixStackIn.popPose();

	}
}