package electrodynamics.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import electrodynamics.common.tile.TileSeismicRelay;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;

public class RenderSeismicRelay extends AbstractTileRenderer<TileSeismicRelay> {

	private static final AABB LEFT = new AABB(0.1875F, 0.9375F, 0.1875F, 0.8125F, 0.98F, 0.3125F);
	private static final AABB UP = new AABB(0.1875F, 0.9375F, 0.3125F, 0.3125F, 0.98F, 0.6875F);
	private static final AABB RIGHT = new AABB(0.1875F, 0.9375F, 0.6875F, 0.8125F, 0.98F, 0.8125F);
	private static final AABB DOWN = new AABB(0.6875F, 0.9375F, 0.3125F, 0.8125F, 0.98F, 0.6875F);

	public RenderSeismicRelay(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(TileSeismicRelay entity, float tick, PoseStack stack, MultiBufferSource source, int light, int overlay) {
		if (entity.clientLocs.size() > 3) {
			Minecraft minecraft = Minecraft.getInstance();
			VertexConsumer sheetBuilder = source.getBuffer(RenderingUtils.beaconType());
			RenderingUtils.renderSolidColorBox(stack, minecraft, sheetBuilder, LEFT, 1.0F, 0F, 0F, 1.0F, 255, 0);
			RenderingUtils.renderSolidColorBox(stack, minecraft, sheetBuilder, UP, 1.0F, 0F, 0F, 1.0F, 255, 0);
			RenderingUtils.renderSolidColorBox(stack, minecraft, sheetBuilder, RIGHT, 1.0F, 0F, 0F, 1.0F, 255, 0);
			RenderingUtils.renderSolidColorBox(stack, minecraft, sheetBuilder, DOWN, 1.0F, 0F, 0F, 1.0F, 255, 0);
		}
	}

}
