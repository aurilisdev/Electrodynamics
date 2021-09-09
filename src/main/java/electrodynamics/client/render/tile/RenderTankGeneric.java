package electrodynamics.client.render.tile;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.client.ClientRegister;
import electrodynamics.common.tile.generic.TileGenericTank;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.utils.AbstractFluidHandler;
import electrodynamics.prefab.utilities.UtilitiesRendering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class RenderTankGeneric extends TileEntityRenderer<TileGenericTank>{

	private static final float xMin = 0;
	private static final float xMax = 0.75f;
	private static final float zMin = 0;
	private static final float zMax = 0.75f;
	private static final float yMin = 0;
	
	//private static final float[] X_VERTS = {xMin, xMax}; 
	//private static final float[] Z_VERTS = {zMin, zMax};
	//private static float[] Y_VERTS = {yMin, 0};
	
	public RenderTankGeneric(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public void render(TileGenericTank tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		
		AbstractFluidHandler<?> tank = tileEntityIn.getComponent(ComponentType.FluidHandler);
		FluidTank fluidTank = tank.getTankFromFluid(null, true);
		if(!fluidTank.getFluid().getFluid().isEquivalentTo(Fluids.EMPTY)) {
			float fluidRatio = (float) fluidTank.getFluidAmount() / (float) fluidTank.getCapacity();
			float fluidLevel = ((float) fluidRatio * 12f) / 16f; //at most can be 0.75
			//Y_VERTS[1] = fluidLevel;
			//List<float[]> coordArray = new ArrayList<>();
			//coordArray.add(X_VERTS);
			//coordArray.add(Y_VERTS);
			//coordArray.add(Z_VERTS);
			//UtilitiesRendering.renderFluidCube(coordArray, tileEntityIn, partialTicks, matrixStackIn, bufferIn, 
			//	fluidTank.getFluid().getFluid(), combinedLightIn, combinedOverlayIn);
		}
	}

}
