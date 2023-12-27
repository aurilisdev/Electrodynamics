package electrodynamics.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import electrodynamics.common.tile.electricitygrid.generators.TileCombustionChamber;
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

public class RenderCombustionChamber extends AbstractTileRenderer<TileCombustionChamber> {

	public RenderCombustionChamber(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(TileCombustionChamber tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		matrixStackIn.pushPose();
		FluidStack fuel = tileEntityIn.<ComponentFluidHandlerMulti>getComponent(IComponentType.FluidHandler).getFluidInTank(0, true);
		float prog = fuel.getAmount() / (float) TileCombustionChamber.TANK_CAPACITY;
		if (prog > 0) {

			Direction facing = tileEntityIn.getBlockState().getValue(GenericEntityBlock.FACING);
			AxisAlignedBB box;
			if (facing == Direction.EAST || facing == Direction.WEST) {
				box = new AxisAlignedBB(7.0D / 16.0D, 12.0D / 16.0D, 6.0D / 16.0D, 10.0D / 16.0D, 14.0D / 16.0D, 11.0D / 16.0D);
			} else {
				box = new AxisAlignedBB(6.0D / 16.0D, 12.0D / 16.0D, 7.0D / 16.0D, 11.0D / 16.0D, 14.0D / 16.0D, 10.0D / 16.0D);
			}

			IVertexBuilder builder = bufferIn.getBuffer(Atlases.translucentCullBlockSheet());
			RenderingUtils.renderFluidBox(matrixStackIn, Minecraft.getInstance(), builder, box, fuel, combinedLightIn, combinedOverlayIn);
		}
		matrixStackIn.popPose();
	}

}