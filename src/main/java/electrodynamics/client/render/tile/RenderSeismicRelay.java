package electrodynamics.client.render.tile;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import electrodynamics.common.tile.machines.quarry.TileQuarry;
import electrodynamics.common.tile.machines.quarry.TileSeismicRelay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import voltaic.client.VoltaicClientRegister;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.utilities.RenderingUtils;

public class RenderSeismicRelay extends AbstractTileRenderer<TileSeismicRelay> {

	private static final AxisAlignedBB LEFT = new AxisAlignedBB(0.1875F, 0.9375F, 0.1875F, 0.8125F, 0.98F, 0.3125F);
	private static final AxisAlignedBB UP = new AxisAlignedBB(0.1875F, 0.9375F, 0.3125F, 0.3125F, 0.98F, 0.6875F);
	private static final AxisAlignedBB RIGHT = new AxisAlignedBB(0.1875F, 0.9375F, 0.6875F, 0.8125F, 0.98F, 0.8125F);
	private static final AxisAlignedBB DOWN = new AxisAlignedBB(0.6875F, 0.9375F, 0.3125F, 0.8125F, 0.98F, 0.6875F);

	public RenderSeismicRelay(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(TileSeismicRelay tile, float tick, @Nonnull MatrixStack stack, @Nonnull IRenderTypeBuffer source, int light, int overlay) {
		if (tile.markerLocs.getValue().size() > 3) {
			Minecraft minecraft = Minecraft.getInstance();
			IVertexBuilder sheetBuilder = source.getBuffer(RenderingUtils.beaconType());
			RenderingUtils.renderSolidColorBox(stack, minecraft, sheetBuilder, LEFT, 1.0F, 0F, 0F, 1.0F, 255, 0, RenderingUtils.ALL_FACES);
			RenderingUtils.renderSolidColorBox(stack, minecraft, sheetBuilder, UP, 1.0F, 0F, 0F, 1.0F, 255, 0, RenderingUtils.ALL_FACES);
			RenderingUtils.renderSolidColorBox(stack, minecraft, sheetBuilder, RIGHT, 1.0F, 0F, 0F, 1.0F, 255, 0, RenderingUtils.ALL_FACES);
			RenderingUtils.renderSolidColorBox(stack, minecraft, sheetBuilder, DOWN, 1.0F, 0F, 0F, 1.0F, 255, 0, RenderingUtils.ALL_FACES);

			Direction facing = tile.getFacing().getOpposite();

			boolean doesQuarryHaveRing = false;
			TileEntity entity = level().getBlockEntity(tile.getBlockPos().relative(facing.getClockWise()));
			if (entity instanceof TileQuarry) {
				doesQuarryHaveRing = ((TileQuarry) entity).hasRing.getValue();
			}
			entity = level().getBlockEntity(tile.getBlockPos().relative(facing.getCounterClockWise()));
			if (entity instanceof TileQuarry) {
				doesQuarryHaveRing = ((TileQuarry) entity).hasRing.getValue();
			}

			if (!doesQuarryHaveRing) {
				return;
			}

			AxisAlignedBB beam;
			if (facing == Direction.NORTH) {
				beam = new AxisAlignedBB(0.4375, 0.5625, 0, 0.5625, 0.6875, -0.375);
			} else if (facing == Direction.SOUTH) {
				beam = new AxisAlignedBB(0.4375, 0.5625, 1, 0.5625, 0.6875, 1.375);
			} else if (facing == Direction.EAST) {
				beam = new AxisAlignedBB(1, 0.5625, 0.4375, 1.375, 0.6875, 0.5625);
			} else {
				beam = new AxisAlignedBB(0, 0.5625, 0.4375, -0.375, 0.6875, 0.5625);
			}

			int time = 200;
			int cutoff = 180;
			int half = (time - cutoff) / 2;

			float alpha = getGameTime() % time;

			if (alpha < cutoff) {
				return;
			}

			alpha = time - alpha;
			if (alpha <= half) {
				alpha = alpha / half;
			} else {
				alpha = alpha - half;
				alpha = 1.0F - alpha / half;
			}

			TextureAtlasSprite whiteTexture = VoltaicClientRegister.whiteSprite();

			RenderingUtils.renderFilledBoxNoOverlay(stack, sheetBuilder, beam, 1.0F, 0, 0, alpha, whiteTexture.getU0(), whiteTexture.getV0(), whiteTexture.getV1(), whiteTexture.getV1(), 255, RenderingUtils.ALL_FACES);
		}
	}

}
