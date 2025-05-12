package electrodynamics.client.render.tile;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.client.ElectrodynamicsClientRegister;
import electrodynamics.common.tile.machines.mineralgrinder.TileMineralGrinder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Quaternion;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentProcessor;
import voltaic.prefab.utilities.RenderingUtils;

public class RenderMineralGrinderTriple extends AbstractTileRenderer<TileMineralGrinder> {

	public RenderMineralGrinderTriple(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(TileMineralGrinder tile, float partialTicks, MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

		double progress = (tile.clientRunningTicks + (tile.<ComponentProcessor>getComponent(IComponentType.Processor).isAnyActive() ? partialTicks : 0)) * 10;

		IBakedModel ibakedmodel = getModel(ElectrodynamicsClientRegister.MODEL_MINERALGRINDERWHEEL);

		matrixStackIn.pushPose();

		RenderingUtils.prepareRotationalTileModel(tile, matrixStackIn);
		matrixStackIn.translate(0.0, 7.0 / 16.0, 2.5 / 16.0);
		matrixStackIn.mulPose(new Quaternion((float) -progress, 0, 0, true));
		RenderingUtils.renderModel(ibakedmodel, tile, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);

		matrixStackIn.popPose();

		matrixStackIn.pushPose();

		RenderingUtils.prepareRotationalTileModel(tile, matrixStackIn);
		matrixStackIn.translate(0.0, 7.0 / 16.0, -2.5 / 16.0);
		matrixStackIn.mulPose(new Quaternion((float) progress, 0, 0, true));
		RenderingUtils.renderModel(ibakedmodel, tile, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);

		matrixStackIn.popPose();

		matrixStackIn.pushPose();

		RenderingUtils.prepareRotationalTileModel(tile, matrixStackIn);
		matrixStackIn.translate(0, 1.0 / 16.0, 0);

		matrixStackIn.popPose();

	}
}
