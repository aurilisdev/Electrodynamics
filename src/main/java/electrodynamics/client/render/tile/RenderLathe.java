package electrodynamics.client.render.tile;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.client.ClientRegister;
import electrodynamics.common.tile.machines.TileLathe;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.utilities.RenderingUtils;
import electrodynamics.prefab.utilities.math.MathUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class RenderLathe extends AbstractTileRenderer<TileLathe> {

	public RenderLathe(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(@NotNull TileLathe tile, float partialTicks, PoseStack poseStack, @NotNull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {

		poseStack.pushPose();

		RenderingUtils.prepareRotationalTileModel(tile, poseStack);

		poseStack.translate(0f, 1.0 / 16.0, 0f);

		double progress = Math.sin(0.05 * Math.PI * partialTicks);

		float progressDegrees = 0.0F;

		if (tile.isProcessorActive()) {

			progressDegrees = 360.0f * (float) progress;

		}

		poseStack.mulPose(MathUtils.rotVectorQuaternionDeg(progressDegrees, MathUtils.YP));
		// matrixStackIn.mulPose(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), progressDegrees, true));

		BakedModel lathe = getModel(ClientRegister.MODEL_LATHESHAFT);

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

		poseStack.mulPose(MathUtils.rotVectorQuaternionDeg(progressDegrees, MathUtils.YP));
		// matrixStackIn.mulPose(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), progressDegrees, true));

		renderItem(stack, ItemDisplayContext.GROUND, combinedLightIn, combinedOverlayIn, poseStack, bufferIn, tile.getLevel(), 0);

		poseStack.popPose();
	}

}
