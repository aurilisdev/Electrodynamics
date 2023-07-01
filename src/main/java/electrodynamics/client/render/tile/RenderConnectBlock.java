package electrodynamics.client.render.tile;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.prefab.tile.types.GenericConnectTile;
import electrodynamics.registers.ElectrodynamicsBlocks;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class RenderConnectBlock extends AbstractTileRenderer<GenericConnectTile> {

	public RenderConnectBlock(Context context) {
		super(context);
	}

	@Override
	public void render(@NotNull GenericConnectTile tile, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
		BlockState state = tile.getBlockState();
		
		if(!state.getValue(ElectrodynamicsBlockStates.HAS_SCAFFOLDING)) {
			return;
		}
		
		Block blockToRender;
		
		if(Blocks.AIR.defaultBlockState().is(tile.getBlock())) {
			blockToRender = ElectrodynamicsBlocks.blockSteelScaffold;
		} else {
			blockToRender = tile.getBlock();
		}
		
		minecraft().getBlockRenderer().renderSingleBlock(blockToRender.defaultBlockState(), poseStack, bufferSource, packedLight, packedOverlay);
		
	}

}
