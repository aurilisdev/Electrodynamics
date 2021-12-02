package electrodynamics.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.tile.generic.TileGenericTank;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class RenderTankGeneric implements BlockEntityRenderer<TileGenericTank> {

    // private static final float[] X_VERTS = {xMin, xMax};
    // private static final float[] Z_VERTS = {zMin, zMax};
    // private static float[] Y_VERTS = {yMin, 0};
    public RenderTankGeneric(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileGenericTank tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn,
	    int combinedOverlayIn) {

	/*
	 * //AbstractFluidHandler<?> tank =
	 * tileEntityIn.getComponent(ComponentType.FluidHandler); //FluidTank fluidTank
	 * = tank.getTankFromFluid(null, true); if
	 * (!fluidTank.getFluid().getFluid().isSame(Fluids.EMPTY)) { // float fluidRatio
	 * = (float) fluidTank.getFluidAmount() / (float) // fluidTank.getCapacity(); }
	 */
    }

}
