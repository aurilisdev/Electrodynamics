package electrodynamics.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.tile.generic.GenericTileTank;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class RenderTankGeneric implements BlockEntityRenderer<GenericTileTank> {

	// private static final float[] X_VERTS = {xMin, xMax};
	// private static final float[] Z_VERTS = {zMin, zMax};
	// private static float[] Y_VERTS = {yMin, 0};
	public RenderTankGeneric(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(GenericTileTank tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {

		/*
		 * //AbstractFluidHandler<?> tank = tileEntityIn.getComponent(ComponentType.FluidHandler); //FluidTank fluidTank = tank.getTankFromFluid(null, true); if (!fluidTank.getFluid().getFluid().isSame(Fluids.EMPTY)) { // float fluidRatio = (float) fluidTank.getFluidAmount() / (float) // fluidTank.getCapacity(); }
		 */
	}

}
