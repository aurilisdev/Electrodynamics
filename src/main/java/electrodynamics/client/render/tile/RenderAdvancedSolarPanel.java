package electrodynamics.client.render.tile;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;

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
    public void render(TileAdvancedSolarPanel tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
	    IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
	IBakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_ADVSOLARBASE);
	Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(
		tileEntityIn.getWorld(), ibakedmodel, tileEntityIn.getBlockState(), tileEntityIn.getPos(),
		matrixStackIn, bufferIn.getBuffer(RenderType.getSolid()), false, tileEntityIn.getWorld().rand,
		new Random().nextLong(), 1);
	matrixStackIn.translate(0.5, 1.8, 0.5);
	matrixStackIn.rotate(new Quaternion(new Vector3f(0, 1, 0), 90, true));
	long time = tileEntityIn.getWorld().getWorldInfo().getDayTime() % 24000;
	if (time < 13000 || time > 23000) {
	    matrixStackIn.rotate(new Quaternion(new Vector3f(1, 0, 0),
		    -tileEntityIn.getWorld().getCelestialAngleRadians(partialTicks), false));
	}
	matrixStackIn.scale(2, 2, 2);
	ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_ADVSOLARTOP);
	Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(
		tileEntityIn.getWorld(), ibakedmodel, tileEntityIn.getBlockState(), tileEntityIn.getPos(),
		matrixStackIn, bufferIn.getBuffer(RenderType.getSolid()), false, tileEntityIn.getWorld().rand,
		new Random().nextLong(), 1);
    }

}
