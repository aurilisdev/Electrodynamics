package electrodynamics.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.tile.generic.TileGenericTank;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.utils.AbstractFluidHandler;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class RenderTankGeneric implements BlockEntityRenderer<TileGenericTank> {

    // private static final float[] X_VERTS = {xMin, xMax};
    // private static final float[] Z_VERTS = {zMin, zMax};
    // private static float[] Y_VERTS = {yMin, 0};
    public RenderTankGeneric(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileGenericTank tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn,
	    int combinedOverlayIn) {

	AbstractFluidHandler<?> tank = tileEntityIn.getComponent(ComponentType.FluidHandler);
	FluidTank fluidTank = tank.getTankFromFluid(null, true);
	if (!fluidTank.getFluid().getFluid().isSame(Fluids.EMPTY)) {
	    // float fluidRatio = (float) fluidTank.getFluidAmount() / (float)
	    // fluidTank.getCapacity();
	}
    }

}
