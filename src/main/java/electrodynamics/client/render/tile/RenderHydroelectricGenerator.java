package electrodynamics.client.render.tile;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.tile.components.ComponentType;
import electrodynamics.api.tile.components.type.ComponentDirection;
import electrodynamics.api.tile.components.type.ComponentTickable;
import electrodynamics.client.ClientRegister;
import electrodynamics.common.tile.TileHydroelectricGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;

public class RenderHydroelectricGenerator extends TileEntityRenderer<TileHydroelectricGenerator> {

    public RenderHydroelectricGenerator(TileEntityRendererDispatcher rendererDispatcherIn) {
	super(rendererDispatcherIn);
    }

    @Override
    @Deprecated
    public void render(TileHydroelectricGenerator tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn,
	    int combinedLightIn, int combinedOverlayIn) {
	IBakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_HYDROELECTRICGENERATORBLADES);
	Direction facing = tileEntityIn.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	switch (facing) {
	case EAST:
	    matrixStackIn.rotate(new Quaternion(0, -90, 0, true));
	    matrixStackIn.translate(0.5, 7.25 / 16.0, -1 - 3 / 16.0);
	    break;
	case NORTH:
	    matrixStackIn.translate(0.5, 7.25 / 16.0, -(3 / 16.0));
	    break;
	case SOUTH:
	    matrixStackIn.rotate(new Quaternion(0, 180, 0, true));
	    matrixStackIn.translate(-0.5, 7.25 / 16.0, -1 - 3 / 16.0);
	    break;
	case WEST:
	    matrixStackIn.rotate(new Quaternion(0, 90, 0, true));
	    matrixStackIn.translate(-0.5, 7.25 / 16.0, -(3 / 16.0));
	    break;
	default:
	    break;
	}
	if (tileEntityIn.hasWater) {
	    boolean flag = false;
	    BlockPos shift = tileEntityIn.getPos().offset(facing);
	    BlockState onShift = tileEntityIn.getWorld().getBlockState(shift);
	    if (onShift.getBlock() instanceof FlowingFluidBlock) {
		int amount = tileEntityIn.getWorld().getBlockState(shift).get(FlowingFluidBlock.LEVEL);
		for (Direction dir : Direction.values()) {
		    if (dir != Direction.UP && dir != Direction.DOWN) {
			BlockState next = tileEntityIn.getWorld().getBlockState(shift.offset(dir));
			if (next.getBlock() instanceof FlowingFluidBlock) {
			    int nextAmount = tileEntityIn.getWorld().getBlockState(shift.offset(dir)).get(FlowingFluidBlock.LEVEL);
			    if (nextAmount < amount) {
				if (dir == facing.rotateY()) {
				    flag = true;
				}
				break;
			    }
			}
		    }
		}
	    }
	    long ticks = (flag ? 1 : -1) * tileEntityIn.<ComponentTickable>getComponent(ComponentType.Tickable).getTicks();
	    matrixStackIn.rotate(new Quaternion(0, 0, ticks % 360 * 5f, true));
	    Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(tileEntityIn.getWorld(), ibakedmodel,
		    tileEntityIn.getBlockState(), tileEntityIn.getPos(), matrixStackIn, bufferIn.getBuffer(RenderType.getSolid()), false,
		    tileEntityIn.getWorld().rand, new Random().nextLong(), 1);
	} else {
	    Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(tileEntityIn.getWorld(), ibakedmodel,
		    tileEntityIn.getBlockState(), tileEntityIn.getPos(), matrixStackIn, bufferIn.getBuffer(RenderType.getSolid()), false,
		    tileEntityIn.getWorld().rand, new Random().nextLong(), 1);
	}

    }

}
