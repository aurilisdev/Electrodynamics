package electrodynamics.client.render.tile;

import java.util.Random;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.common.tile.electricitygrid.TileLogisticalWire;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.math.BlockPos;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.common.block.states.VoltaicBlockStates;

public class RenderLogisticalWire extends AbstractTileRenderer<TileLogisticalWire> {

	public RenderLogisticalWire(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(@Nonnull TileLogisticalWire tile, float partialTick, @Nonnull MatrixStack poseStack, @Nonnull IRenderTypeBuffer bufferSource, int packedLight, int packedOverlay) {

		if (!tile.getBlockState().getValue(VoltaicBlockStates.LIT)) {
			return;
		}

		Minecraft minecraft = minecraft();

		BlockPos pos = tile.getBlockPos();

		Random random = minecraft.level.getRandom();

		if (random.nextFloat() > 0.02) {
			return;
		}

		minecraft.level.addParticle(RedstoneParticleData.REDSTONE, pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble(), 0, 0, 0);

	}

}
