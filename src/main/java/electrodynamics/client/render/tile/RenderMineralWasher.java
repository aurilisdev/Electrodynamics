package electrodynamics.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.common.tile.TileMineralWasher;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;

public class RenderMineralWasher extends TileEntityRenderer<TileMineralWasher> {

    public RenderMineralWasher(TileEntityRendererDispatcher rendererDispatcherIn) {
	super(rendererDispatcherIn);
    }

    @Override
    @Deprecated
    public void render(TileMineralWasher tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn,
	    int combinedOverlayIn) {
	ItemStack stack = tileEntityIn.<ComponentProcessor>getComponent(ComponentType.Processor).getInput();
	if (!stack.isEmpty()) {
	    Direction dir = tileEntityIn.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	    matrixStackIn.push();
	    double scale = 12;
	    matrixStackIn.translate(0.5 + dir.getXOffset() / scale, stack.getItem() instanceof BlockItem ? 0.48 : 0.39,
		    0.5 + dir.getZOffset() / scale);
	    matrixStackIn.scale(0.35f, 0.35f, 0.35f);
	    matrixStackIn.scale(0.3f, 0.3f, 0.3f);
	    matrixStackIn.translate(0, -0.2, 0);
	    Minecraft.getInstance().getItemRenderer().renderItem(stack, TransformType.NONE, combinedLightIn, combinedOverlayIn, matrixStackIn,
		    bufferIn);
	    matrixStackIn.pop();
	}
    }
}
