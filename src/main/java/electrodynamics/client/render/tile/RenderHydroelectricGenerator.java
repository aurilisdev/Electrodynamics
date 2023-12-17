package electrodynamics.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.client.ClientRegister;
import electrodynamics.common.tile.electricitygrid.generators.TileHydroelectricGenerator;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Quaternion;

public class RenderHydroelectricGenerator extends AbstractTileRenderer<TileHydroelectricGenerator> {

	public RenderHydroelectricGenerator(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(TileHydroelectricGenerator tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		IBakedModel ibakedmodel = getModel(ClientRegister.MODEL_HYDROELECTRICGENERATORBLADES);
		RenderingUtils.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
		float partial = (float) (partialTicks * tileEntityIn.rotationSpeed * (tileEntityIn.directionFlag.get() ? 1 : -1));
		matrixStackIn.mulPose(new Quaternion((float) (-(tileEntityIn.savedTickRotation + partial) * 5f), 0, 0, true));
		RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	}
}