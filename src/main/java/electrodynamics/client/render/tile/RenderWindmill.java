package electrodynamics.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;

import electrodynamics.client.ClientRegister;
import electrodynamics.common.tile.TileWindmill;
import electrodynamics.prefab.utilities.UtilitiesRendering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;

public class RenderWindmill implements BlockEntityRenderer<TileWindmill> {

    @Override
    public void render(TileWindmill tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn,
	    int combinedOverlayIn) {
	BakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_WINDMILLBLADES);
	UtilitiesRendering.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
	matrixStackIn.translate(0, 22.0 / 16.0, 0);
	float partial = (float) (partialTicks * tileEntityIn.rotationSpeed * (tileEntityIn.directionFlag ? 1 : -1));
	matrixStackIn.mulPose(new Quaternion((float) ((tileEntityIn.savedTickRotation + partial) * tileEntityIn.generating * 1.5), 0, 0, true));
	UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }
}
