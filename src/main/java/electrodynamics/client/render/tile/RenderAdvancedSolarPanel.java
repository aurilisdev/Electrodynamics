package electrodynamics.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.utilities.UtilitiesRendering;
import electrodynamics.client.ClientRegister;
import electrodynamics.common.tile.TileAdvancedSolarPanel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class RenderAdvancedSolarPanel extends TileEntityRenderer<TileAdvancedSolarPanel> {

    public RenderAdvancedSolarPanel(TileEntityRendererDispatcher rendererDispatcherIn) {
	super(rendererDispatcherIn);
    }

    @Override
    @Deprecated
    public void render(TileAdvancedSolarPanel tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn,
	    int combinedLightIn, int combinedOverlayIn) {
	matrixStackIn.translate(0.5, 2, 0.5);
	matrixStackIn.rotate(new Quaternion(new Vector3f(0, 1, 0), 90, true));
	long time = tileEntityIn.getWorld().getWorldInfo().getDayTime();
	if (time < 13000 || time > 23000) {
	    tileEntityIn.currentRotation.set(
		    tileEntityIn.currentRotation.get() + (time / 24000.0 * Math.PI * 2 - Math.PI / 2.0 - tileEntityIn.currentRotation.get()) / 40.0);
	}
	matrixStackIn.rotate(new Quaternion(new Vector3f(1, 0, 0), (float) -tileEntityIn.currentRotation.get(), false));
	matrixStackIn.scale(2, 2, 2);
	IBakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_ADVSOLARTOP);
	UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getSolid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }

}
