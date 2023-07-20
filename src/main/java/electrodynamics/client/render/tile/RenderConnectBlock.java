package electrodynamics.client.render.tile;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.prefab.tile.types.GenericConnectTile;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.world.level.block.state.BlockState;

public class RenderConnectBlock extends AbstractTileRenderer<GenericConnectTile> {

	public RenderConnectBlock(Context context) {
		super(context);
	}

	@Override
	public void render(@NotNull GenericConnectTile tile, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
		BlockState state = tile.getBlockState();

		if (!state.getValue(ElectrodynamicsBlockStates.HAS_SCAFFOLDING)) {
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
