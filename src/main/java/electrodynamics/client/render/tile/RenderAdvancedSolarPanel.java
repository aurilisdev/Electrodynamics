package electrodynamics.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.client.ClientRegister;
import electrodynamics.common.tile.electricitygrid.generators.TileAdvancedSolarPanel;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class RenderAdvancedSolarPanel extends AbstractTileRenderer<TileAdvancedSolarPanel> {

	public RenderAdvancedSolarPanel(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(TileAdvancedSolarPanel solarPanel, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

		matrixStackIn.translate(0.5, 2, 0.5);

		matrixStackIn.mulPose(new Quaternion(new Vector3f(0, 1, 0), 90, true));

		long time = solarPanel.getLevel().getLevelData().getDayTime();

		if (time < 13000 || time > 23000) {
			solarPanel.currentRotation.setValue(solarPanel.currentRotation.getValue() + (time / 24000.0 * Math.PI * 2 - Math.PI / 2.0 - solarPanel.currentRotation.getValue()) / 40.0);
		}

		matrixStackIn.mulPose(new Quaternion(new Vector3f(1, 0, 0), (float) -solarPanel.currentRotation.getValue(), false));

		matrixStackIn.scale(2, 2, 2);

		IBakedModel ibakedmodel = getModel(ClientRegister.MODEL_ADVSOLARTOP);

		RenderingUtils.renderModel(ibakedmodel, solarPanel, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	}

}