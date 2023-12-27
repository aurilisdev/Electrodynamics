package electrodynamics.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.common.tile.machines.TileMineralWasher;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;

public class RenderMineralWasher extends AbstractTileRenderer<TileMineralWasher> {

	public RenderMineralWasher(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(TileMineralWasher tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

		ItemStack stack = tileEntityIn.<ComponentInventory>getComponent(IComponentType.Inventory).getInputsForProcessor(0).get(0);

		if (stack.isEmpty()) {

			return;

		}

		Direction dir = tileEntityIn.getFacing();

		matrixStackIn.pushPose();

		double scale = 12;

		matrixStackIn.translate(0.5 + dir.getStepX() / scale, stack.getItem() instanceof BlockItem ? 0.48 : 0.39, 0.5 + dir.getStepZ() / scale);

		matrixStackIn.scale(0.35f, 0.35f, 0.35f);

		matrixStackIn.scale(0.3f, 0.3f, 0.3f);

		matrixStackIn.translate(0, -0.2, 0);

		renderItem(stack, TransformType.NONE, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, tileEntityIn.getLevel(), 0);

		matrixStackIn.popPose();

	}
}