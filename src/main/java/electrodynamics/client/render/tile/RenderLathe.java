package electrodynamics.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.client.ClientRegister;
import electrodynamics.common.tile.machines.TileLathe;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class RenderLathe extends AbstractTileRenderer<TileLathe> {

	public RenderLathe(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(TileLathe tile, float partialTicks, MatrixStack poseStack, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

		poseStack.pushPose();

		RenderingUtils.prepareRotationalTileModel(tile, poseStack);

		poseStack.translate(0f, 1.0 / 16.0, 0f);

		double progress = Math.sin(0.05 * Math.PI * partialTicks);

		float progressDegrees = 0.0F;

		if (tile.isProcessorActive()) {

			progressDegrees = 360.0f * (float) progress;

		}

		poseStack.mulPose(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), progressDegrees, true));

		IBakedModel lathe = getModel(ClientRegister.MODEL_LATHESHAFT);

		RenderingUtils.renderModel(lathe, tile, RenderType.solid(), poseStack, bufferIn, combinedLightIn, combinedOverlayIn);

		poseStack.popPose();

		ItemStack stack = tile.<ComponentInventory>getComponent(IComponentType.Inventory).getInputsForProcessor(0).get(0);

		if (stack.isEmpty()) {

			return;

		}

		poseStack.pushPose();

		if (stack.getItem() instanceof BlockItem) {

			poseStack.translate(0.5f, 0.66f, 0.5f);
			poseStack.scale(0.5f, 0.5f, 0.5f);

		} else {

			poseStack.translate(0.5f, 0.71f, 0.5f);
			poseStack.scale(0.35f, 0.35f, 0.35f);

		}

		poseStack.mulPose(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), progressDegrees, true));

		renderItem(stack, TransformType.GROUND, combinedLightIn, combinedOverlayIn, poseStack, bufferIn, tile.getLevel(), 0);

		poseStack.popPose();
	}

}