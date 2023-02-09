package electrodynamics.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import electrodynamics.client.ClientRegister;
import electrodynamics.common.tile.TileAdvancedSolarPanel;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;

public class RenderAdvancedSolarPanel extends AbstractTileRenderer<TileAdvancedSolarPanel> {
	
	public RenderAdvancedSolarPanel(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(TileAdvancedSolarPanel tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		matrixStackIn.translate(0.5, 2, 0.5);
		matrixStackIn.mulPose(new Quaternion(new Vector3f(0, 1, 0), 90, true));
		long time = tileEntityIn.getLevel().getLevelData().getDayTime();
		if (time < 13000 || time > 23000) {
			tileEntityIn.currentRotation.set(tileEntityIn.currentRotation.getValue().get() + (time / 24000.0 * Math.PI * 2 - Math.PI / 2.0 - tileEntityIn.currentRotation.getValue().get()) / 40.0);
		}
		matrixStackIn.mulPose(new Quaternion(new Vector3f(1, 0, 0), (float) -tileEntityIn.currentRotation.getValue().get(), false));
		matrixStackIn.scale(2, 2, 2);
		BakedModel ibakedmodel = getModel(ClientRegister.MODEL_ADVSOLARTOP);
		RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	}

}
