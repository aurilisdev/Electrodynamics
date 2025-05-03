package electrodynamics.client.render.tile;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;

import electrodynamics.client.ElectrodynamicsClientRegister;
import electrodynamics.common.tile.electricitygrid.generators.TileWindmill;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.utilities.RenderingUtils;

public class RenderWindmill extends AbstractTileRenderer<TileWindmill> {

	public RenderWindmill(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(@NotNull TileWindmill tileEntityIn, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		BakedModel ibakedmodel = getModel(ElectrodynamicsClientRegister.MODEL_WINDMILLBLADES);
		RenderingUtils.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
		matrixStackIn.translate(0, 22.0 / 16.0, 0);
		float partial = (float) (partialTicks * tileEntityIn.rotationSpeed * (tileEntityIn.directionFlag.getValue() ? 1 : -1));

		matrixStackIn.mulPose(new Quaternion((float) ((tileEntityIn.savedTickRotation + partial) * tileEntityIn.generating.getValue() * 1.5), 0, 0, true));
		RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	}

}
