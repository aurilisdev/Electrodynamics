package electrodynamics.client.render.tile;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import electrodynamics.common.tile.pipelines.fluid.TileFluidPipePump;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import voltaic.client.VoltaicClientRegister;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.utilities.RenderingUtils;

public class RenderFluidPipePump extends AbstractTileRenderer<TileFluidPipePump> {

	public RenderFluidPipePump(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(@Nonnull TileFluidPipePump tile, float partialTick, @Nonnull MatrixStack poseStack, @Nonnull IRenderTypeBuffer bufferSource, int packedLight, int packedOverlay) {

		poseStack.pushPose();

		Direction dir = tile.getFacing();

		AxisAlignedBB box1, box2;

		double offset1 = 3;
		double offset2 = 0;

		if (tile.isPowered()) {

			offset2 = 3;

			long time = System.currentTimeMillis();

			int time1 = (int) (time % 1000);
			int time2 = (int) ((time + 500) % 1000);

			offset1 -= MathHelper.sin((float) (time1 * Math.PI / 1000.0F)) * 3.0D;
			offset2 -= MathHelper.sin((float) (time2 * Math.PI / 1000.0F)) * 3.0D;

		}

		if (dir == Direction.NORTH) {
			box1 = aabb(2, 13 - offset1, 5, 4, 17 - offset1, 7);
			box2 = aabb(2, 13 - offset2, 9, 4, 17 - offset2, 11);
		} else if (dir == Direction.SOUTH) {
			box1 = aabb(12, 13 - offset1, 9, 14, 17 - offset1, 11);
			box2 = aabb(12, 13 - offset2, 5, 14, 17 - offset2, 7);
		} else if (dir == Direction.EAST) {
			box1 = aabb(9, 13 - offset1, 2, 11, 17 - offset1, 4);
			box2 = aabb(5, 13 - offset2, 2, 7, 17 - offset2, 4);
		} else {
			box1 = aabb(5, 13 - offset1, 12, 7, 17 - offset1, 14);
			box2 = aabb(9, 13 - offset2, 12, 11, 17 - offset2, 14);
		}

		TextureAtlasSprite whiteTexture = VoltaicClientRegister.whiteSprite();
		float u0 = whiteTexture.getU0();
		float u1 = whiteTexture.getU1();
		float v0 = whiteTexture.getV0();
		float v1 = whiteTexture.getV1();

		IVertexBuilder builder = bufferSource.getBuffer(Atlases.solidBlockSheet());

		RenderingUtils.renderFilledBox(poseStack, builder, box1, 67.0F / 256.0F, 67.0F / 256.0F, 67.0F / 256.0F, 1, u0, v0, u1, v1, packedLight, packedOverlay, RenderingUtils.ALL_FACES);
		RenderingUtils.renderFilledBox(poseStack, builder, box2, 67.0F / 256.0F, 67.0F / 256.0F, 67.0F / 256.0F, 1, u0, v0, u1, v1, packedLight, packedOverlay, RenderingUtils.ALL_FACES);

		poseStack.popPose();

	}

}
