package electrodynamics.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import electrodynamics.client.ClientRegister;
import electrodynamics.common.tile.TileLathe;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;

public class RenderLathe implements BlockEntityRenderer<TileLathe> {
	public RenderLathe(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(TileLathe tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		matrixStackIn.pushPose();
		BakedModel lathe = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_LATHE);
		RenderingUtils.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
		matrixStackIn.translate(0f, 1.0 / 16.0, 0f);
		RenderingUtils.renderModel(lathe, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);

		double progress = Math.sin(0.05 * Math.PI * partialTicks);
		float progressDegrees = 0.0F;
		if (tileEntityIn.getProcessor(0).operatingTicks > 0) {
			progressDegrees = 360.0f * (float) progress;
		}

		matrixStackIn.mulPose(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), progressDegrees, true));
		lathe = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_LATHESHAFT);
		RenderingUtils.renderModel(lathe, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		matrixStackIn.popPose();
		ItemStack stack = tileEntityIn.<ComponentInventory>getComponent(ComponentType.Inventory).getInputContents().get(0).get(0);
		if (!stack.isEmpty()) {
			matrixStackIn.pushPose();
			matrixStackIn.translate(0.5f, 0.78f, 0.5f);
			matrixStackIn.scale(0.35f, 0.35f, 0.35f);
			matrixStackIn.mulPose(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), progressDegrees, true));
			Minecraft.getInstance().getItemRenderer().renderStatic(stack, TransformType.NONE, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, 0);
			matrixStackIn.popPose();
		}
	}

}
