package electrodynamics.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import electrodynamics.common.tile.machines.TileFermentationPlant;
import electrodynamics.prefab.block.GenericEntityBlock;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class RenderFermentationPlant extends AbstractTileRenderer<TileFermentationPlant> {

	public RenderFermentationPlant(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(TileFermentationPlant tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		matrixStackIn.pushPose();
		ComponentFluidHandlerMulti multi = tileEntityIn.getComponent(IComponentType.FluidHandler);

		Direction facing = tileEntityIn.getBlockState().getValue(GenericEntityBlock.FACING);

		FluidStack input = null;
		for (FluidTank tank : multi.getInputTanks()) {
			if (!tank.isEmpty()) {
				input = tank.getFluid();
				break;
			}
		}
		if (input != null) {
			AxisAlignedBB box;
			if (facing == Direction.WEST) {
				box = new AxisAlignedBB(7.0D / 16.0D, 9.0D / 16.0D, 11.0D / 16.0D, 9.0D / 16.0D, 14.0D / 16.0D, 12.0D / 16.0D);
			} else if (facing == Direction.EAST) {
				box = new AxisAlignedBB(7.0D / 16.0D, 9.0D / 16.0D, 4.0D / 16.0D, 9.0D / 16.0D, 14.0D / 16.0D, 5.0D / 16.0D);
			} else if (facing == Direction.SOUTH) {
				box = new AxisAlignedBB(11.0D / 16.0D, 9.0D / 16.0D, 7.0D / 16.0D, 12.0D / 16.0D, 14.0D / 16.0D, 9.0D / 16.0D);
			} else {
				box = new AxisAlignedBB(4.0D / 16.0D, 9.0D / 16.0D, 7.0D / 16.0D, 5.0D / 16.0D, 14.0D / 16.0D, 9.0D / 16.0D);
			}
			IVertexBuilder builder = bufferIn.getBuffer(Atlases.translucentCullBlockSheet());
			RenderingUtils.renderFluidBox(matrixStackIn, Minecraft.getInstance(), builder, box, input, combinedLightIn, combinedOverlayIn);
		}

		FluidStack output = null;
		for (FluidTank tank : multi.getOutputTanks()) {
			if (!tank.isEmpty()) {
				output = tank.getFluid();
				break;
			}
		}
		if (output != null) {
			AxisAlignedBB box;
			if (facing == Direction.WEST) {
				box = new AxisAlignedBB(6.0D / 16.0D, 5.0D / 16.0D, 2.0D / 16.0D, 10.0D / 16.0D, 7.0D / 16.0D, 7.0D / 16.0D);
			} else if (facing == Direction.EAST) {
				box = new AxisAlignedBB(6.0D / 16.0D, 5.0D / 16.0D, 9.0D / 16.0D, 10.0D / 16.0D, 7.0D / 16.0D, 14.0D / 16.0D);
			} else if (facing == Direction.SOUTH) {
				box = new AxisAlignedBB(2.0D / 16.0D, 5.0D / 16.0D, 6.0D / 16.0D, 7.0D / 16.0D, 7.0D / 16.0D, 10.0D / 16.0D);
			} else {
				box = new AxisAlignedBB(9.0D / 16.0D, 5.0D / 16.0D, 6.0D / 16.0D, 14.0D / 16.0D, 7.0D / 16.0D, 10.0D / 16.0D);
			}
			IVertexBuilder builder = bufferIn.getBuffer(Atlases.translucentCullBlockSheet());
			RenderingUtils.renderFluidBox(matrixStackIn, Minecraft.getInstance(), builder, box, output, combinedLightIn, combinedOverlayIn);
		}
		matrixStackIn.popPose();

	}

}