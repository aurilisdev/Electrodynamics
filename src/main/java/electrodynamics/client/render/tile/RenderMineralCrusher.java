package electrodynamics.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.tile.components.ComponentType;
import electrodynamics.api.tile.components.type.ComponentDirection;
import electrodynamics.api.tile.components.type.ComponentProcessor;
import electrodynamics.api.utilities.UtilitiesRendering;
import electrodynamics.client.ClientRegister;
import electrodynamics.common.tile.TileMineralCrusher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;

public class RenderMineralCrusher extends TileEntityRenderer<TileMineralCrusher> {

    public RenderMineralCrusher(TileEntityRendererDispatcher rendererDispatcherIn) {
	super(rendererDispatcherIn);
    }

    @Override
    @Deprecated
    public void render(TileMineralCrusher tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn,
	    int combinedLightIn, int combinedOverlayIn) {
	matrixStackIn.push();
	IBakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_MINERALCRUSHERBASE);
	UtilitiesRendering.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
	matrixStackIn.translate(0, 1.0 / 16.0, 0);
	UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getSolid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	double progress = Math
		.sin(0.05 * Math.PI
			* ((tileEntityIn.clientRunningTicks
				+ (tileEntityIn.<ComponentProcessor>getComponent(ComponentType.Processor).operatingTicks > 0 ? partialTicks : 0))
				% 20));
	matrixStackIn.translate(0, progress / 8.0 - 1 / 8.0, 0);
	ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_MINERALCRUSHERHANDLE);
	UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getSolid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	matrixStackIn.pop();
	ItemStack stack = tileEntityIn.<ComponentProcessor>getComponent(ComponentType.Processor).getInput();
	if (!stack.isEmpty()) {
	    Direction dir = tileEntityIn.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	    matrixStackIn.push();
	    double scale = stack.getItem() instanceof BlockItem ? 5.3 : 8.0;
	    matrixStackIn.translate(0.5 + dir.getXOffset() / scale, (stack.getItem() instanceof BlockItem ? 0.48 : 0.39),
		    0.5 + dir.getZOffset() / scale);
	    matrixStackIn.scale(0.35f, 0.35f, 0.35f);
	    if (!(stack.getItem() instanceof BlockItem)) {
		matrixStackIn.rotate(Vector3f.XN.rotationDegrees(90));
	    } else {
		matrixStackIn.scale(0.3f, 0.3f, 0.3f);
		matrixStackIn.translate(0, -0.5, 0);
	    }
	    Minecraft.getInstance().getItemRenderer().renderItem(stack, TransformType.NONE, combinedLightIn, combinedOverlayIn, matrixStackIn,
		    bufferIn);
	    matrixStackIn.pop();
	}
    }
}
