package electrodynamics.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.common.tile.generic.TileGenericTank;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.utils.AbstractFluidHandler;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.fluid.Fluids;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class RenderTankGeneric extends TileEntityRenderer<TileGenericTank> {

    // private static final float[] X_VERTS = {xMin, xMax};
    // private static final float[] Z_VERTS = {zMin, zMax};
    // private static float[] Y_VERTS = {yMin, 0};

    public RenderTankGeneric(TileEntityRendererDispatcher rendererDispatcherIn) {
	super(rendererDispatcherIn);
    }

    @Override
    public void render(TileGenericTank tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn,
	    int combinedOverlayIn) {

	AbstractFluidHandler<?> tank = tileEntityIn.getComponent(ComponentType.FluidHandler);
	FluidTank fluidTank = tank.getTankFromFluid(null, true);
	if (!fluidTank.getFluid().getFluid().isEquivalentTo(Fluids.EMPTY)) {
	    // float fluidRatio = (float) fluidTank.getFluidAmount() / (float)
	    // fluidTank.getCapacity();
	}
    }

}
