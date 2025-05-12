package electrodynamics.client.render.tile;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.common.block.states.VoltaicBlockStates;
import voltaic.prefab.tile.types.GenericConnectTile;

public class RenderConnectBlock extends AbstractTileRenderer<GenericConnectTile> {

	public RenderConnectBlock(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(@Nonnull GenericConnectTile tile, float partialTick, @Nonnull MatrixStack poseStack, @Nonnull IRenderTypeBuffer bufferSource, int packedLight, int packedOverlay) {
		BlockState state = tile.getBlockState();

		if (!state.getValue(VoltaicBlockStates.HAS_SCAFFOLDING)) {
			return;
		}

		BlockState blockToRender;

		if (tile.isCamoAir()) {
			blockToRender = tile.getScaffoldBlock();
		} else {
			blockToRender = tile.getCamoBlock();
		}

		minecraft().getBlockRenderer().renderSingleBlock(blockToRender, poseStack, bufferSource, packedLight, packedOverlay);

	}

}
