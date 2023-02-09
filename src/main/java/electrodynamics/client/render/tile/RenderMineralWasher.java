package electrodynamics.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.tile.TileMineralWasher;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

public class RenderMineralWasher extends AbstractTileRenderer<TileMineralWasher> {

	public RenderMineralWasher(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(TileMineralWasher tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		ItemStack stack = tileEntityIn.<ComponentInventory>getComponent(ComponentType.Inventory).getInputContents().get(0).get(0);
		if (!stack.isEmpty()) {
			Direction dir = tileEntityIn.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
			matrixStackIn.pushPose();
			double scale = 12;
			matrixStackIn.translate(0.5 + dir.getStepX() / scale, stack.getItem() instanceof BlockItem ? 0.48 : 0.39, 0.5 + dir.getStepZ() / scale);
			matrixStackIn.scale(0.35f, 0.35f, 0.35f);
			matrixStackIn.scale(0.3f, 0.3f, 0.3f);
			matrixStackIn.translate(0, -0.2, 0);
			Minecraft.getInstance().getItemRenderer().renderStatic(stack, TransformType.NONE, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, 0);
			matrixStackIn.popPose();
		}
	}
}
