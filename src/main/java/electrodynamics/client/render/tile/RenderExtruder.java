package electrodynamics.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.client.ClientRegister;
import electrodynamics.common.tile.TileExtruder;
import electrodynamics.prefab.utilities.UtilitiesRendering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class RenderExtruder extends TileEntityRenderer<TileExtruder>{

	public RenderExtruder(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public void render(TileExtruder tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		
		matrixStackIn.push();
		IBakedModel extruder = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_EXTRUDER);
		UtilitiesRendering.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
		matrixStackIn.translate(0f, 1.0 / 16.0, 0f);
		UtilitiesRendering.renderModel(extruder, tileEntityIn, RenderType.getSolid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		
		double progress = Math.sin(0.05 * Math.PI * partialTicks);
		float progressDegrees = 0.0F;
		if(tileEntityIn.getProcessor(0).operatingTicks > 0) {
			progressDegrees = 360.0f * (float)progress;
		}
		
		matrixStackIn.rotate(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), progressDegrees, true));
		extruder = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_EXTRUDERSHAFT);
		UtilitiesRendering.renderModel(extruder, tileEntityIn, RenderType.getSolid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		matrixStackIn.pop();
		ItemStack stack = tileEntityIn.getProcessor(0).getInput();
		if(!stack.isEmpty()) {
			matrixStackIn.push();
		    matrixStackIn.translate(0.5f, 0.78f, 0.5f);
		    matrixStackIn.scale(0.35f, 0.35f, 0.35f);
		    matrixStackIn.rotate(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), progressDegrees, true));
		    Minecraft.getInstance().getItemRenderer().renderItem(stack, TransformType.NONE, combinedLightIn, combinedOverlayIn, matrixStackIn,
				    bufferIn);
		    matrixStackIn.pop();
		}
	}

}
