package electrodynamics.client.render.tile;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.client.ElectrodynamicsClientRegister;
import electrodynamics.common.tile.machines.mineralcrusher.TileMineralCrusherDouble;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentProcessor;
import voltaic.prefab.utilities.RenderingUtils;

public class RenderMineralCrusherDouble extends AbstractTileRenderer<TileMineralCrusherDouble> {

	public RenderMineralCrusherDouble(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(@Nonnull TileMineralCrusherDouble tile, float partialTicks, MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

		matrixStackIn.pushPose();

		RenderingUtils.prepareRotationalTileModel(tile, matrixStackIn);

		matrixStackIn.translate(0, 1.0 / 16.0, 0);

		double ticks = (tile.clientRunningTicks + (tile.<ComponentProcessor>getComponent(IComponentType.Processor).isAnyActive() ? partialTicks : 0)) % 20;

		double progress = ticks < 10.010392739868964 ? Math.sin(0.05 * Math.PI * ticks) : (Math.sin(0.29 * Math.PI * ticks) + 1) / 1.3;

		matrixStackIn.translate(0, progress / 8.0 - 1 / 8.0, 0);

		IBakedModel ibakedmodel = getModel(ElectrodynamicsClientRegister.MODEL_MINERALCRUSHERDOUBLEHANDLE);

		RenderingUtils.renderModel(ibakedmodel, tile, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);

		matrixStackIn.popPose();

		ComponentInventory inv = tile.getComponent(IComponentType.Inventory);

		ItemStack stack = inv.getInputsForProcessor(0).get(0);

		Direction dir = tile.getFacing();

		if (!stack.isEmpty()) {

			matrixStackIn.pushPose();

			double scale = stack.getItem() instanceof BlockItem ? 5.3 : 8.0;

			matrixStackIn.translate(0.5 + dir.getStepX() / scale - (dir.getStepZ() != 0 ? 0.07 : 0), stack.getItem() instanceof BlockItem ? 0.48 : 0.39, 0.5 + dir.getStepZ() / scale - (dir.getStepX() != 0 ? 0.07 : 0));

			matrixStackIn.scale(0.35f, 0.35f, 0.35f);

			if (!(stack.getItem() instanceof BlockItem)) {

				matrixStackIn.mulPose(Vector3f.XN.rotationDegrees(90));

			} else {

				matrixStackIn.scale(0.3f, 0.3f, 0.3f);
				matrixStackIn.translate(0, -0.5, 0);

			}

			renderItem(stack, TransformType.NONE, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, tile.getLevel(), 0);

			matrixStackIn.popPose();
		}

		stack = inv.getInputsForProcessor(1).get(0);

		if (!stack.isEmpty()) {

			matrixStackIn.pushPose();

			double scale = stack.getItem() instanceof BlockItem ? 5.3 : 8.0;

			matrixStackIn.translate(0.5 + dir.getStepX() / scale + (dir.getStepZ() != 0 ? 0.07 : 0), stack.getItem() instanceof BlockItem ? 0.48 : 0.39, 0.5 + dir.getStepZ() / scale + (dir.getStepX() != 0 ? 0.07 : 0));

			matrixStackIn.scale(0.35f, 0.35f, 0.35f);

			if (!(stack.getItem() instanceof BlockItem)) {

				matrixStackIn.mulPose(Vector3f.XN.rotationDegrees(90));

			} else {

				matrixStackIn.scale(0.3f, 0.3f, 0.3f);
				matrixStackIn.translate(0, -0.5, 0);

			}

			renderItem(stack, TransformType.NONE, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, tile.getLevel(), 0);

			matrixStackIn.popPose();

		}
	}
}
