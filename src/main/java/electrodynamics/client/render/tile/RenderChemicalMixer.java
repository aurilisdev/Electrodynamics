package electrodynamics.client.render.tile;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.tile.components.ComponentType;
import electrodynamics.api.tile.components.type.ComponentFluidHandler;
import electrodynamics.api.utilities.UtilitiesRendering;
import electrodynamics.client.ClientRegister;
import electrodynamics.common.block.BlockGenericMachine;
import electrodynamics.common.tile.TileChemicalMixer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Quaternion;

public class RenderChemicalMixer extends TileEntityRenderer<TileChemicalMixer> {

    public RenderChemicalMixer(TileEntityRendererDispatcher rendererDispatcherIn) {
	super(rendererDispatcherIn);
    }

    @Override
    @Deprecated
    public void render(TileChemicalMixer tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn,
	    int combinedOverlayIn) {
	matrixStackIn.push();
	IBakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CHEMICALMIXERBASE);
	Direction face = tileEntityIn.getBlockState().get(BlockGenericMachine.FACING);
	UtilitiesRendering.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
	UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getSolid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	matrixStackIn.pop();
	matrixStackIn.push();
	ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CHEMICALMIXERBLADES);
	matrixStackIn.translate(0.5, 0.4, 0.5);
	matrixStackIn.rotate(new Quaternion(0, tileEntityIn.clientTicks + partialTicks, 0, true));
	UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getSolid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	matrixStackIn.pop();
	matrixStackIn.push();
	matrixStackIn.translate(0.5, 0.4, 0.5);
	ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CHEMICALMIXERWATER);
	matrixStackIn.translate(0.5, 8.5 / 16.0, 0.5);
	if (face == Direction.NORTH) {
	    matrixStackIn.translate(2.0 / 8.0, 0, 0);
	}
	if (face == Direction.EAST) {
	    matrixStackIn.translate(0, 0, 2.0 / 8.0);
	}
	if (face == Direction.NORTH || face == Direction.SOUTH) {
	    matrixStackIn.rotate(new Quaternion(0, 90, 0, true));
	}
	float prog = tileEntityIn.<ComponentFluidHandler>getComponent(ComponentType.FluidHandler).getStackFromFluid(Fluids.WATER).getAmount()
		/ (float) TileChemicalMixer.TANKCAPACITY;
	if (prog > 0) {
	    matrixStackIn.scale(1, prog / 16.0f * 12f, 1);
	    Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(tileEntityIn.getWorld(), ibakedmodel,
		    tileEntityIn.getBlockState(), tileEntityIn.getPos(), matrixStackIn, bufferIn.getBuffer(RenderType.getCutout()), false,
		    tileEntityIn.getWorld().rand, new Random().nextLong(), 1);
	}
	matrixStackIn.pop();
    }

}
