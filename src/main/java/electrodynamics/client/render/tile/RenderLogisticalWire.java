package electrodynamics.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.tile.electricitygrid.TileLogisticalWire;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.common.block.states.VoltaicBlockStates;

public class RenderLogisticalWire extends AbstractTileRenderer<TileLogisticalWire> {

    public RenderLogisticalWire(Context context) {
	super(context);
    }

    @Override
    public void render(TileLogisticalWire tile, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource,
	    int packedLight, int packedOverlay) {
	if (!tile.getBlockState().getValue(VoltaicBlockStates.LIT))
	    return;

	Minecraft minecraft = minecraft();

	BlockPos pos = tile.getBlockPos();

	ClientLevel level = minecraft.level;
	if (level == null)
	    return;

	RandomSource random = level.getRandom();

	if (random.nextFloat() > 0.02)
	    return;

	level.addParticle(new DustParticleOptions(DustParticleOptions.REDSTONE_PARTICLE_COLOR, random.nextFloat()),
		pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble(), 0,
		0, 0);

    }

}
