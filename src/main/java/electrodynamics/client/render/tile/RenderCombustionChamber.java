package electrodynamics.client.render.tile;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import electrodynamics.common.tile.electricitygrid.generators.TileCombustionChamber;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fluids.FluidStack;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.utilities.RenderingUtils;

public class RenderCombustionChamber extends AbstractTileRenderer<TileCombustionChamber> {

	public RenderCombustionChamber(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(TileCombustionChamber tileEntityIn, float partialTicks, MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		matrixStackIn.pushPose();
		FluidStack fuel = tileEntityIn.<ComponentFluidHandlerMulti>getComponent(IComponentType.FluidHandler).getFluidInTank(0, true);
		float prog = fuel.getAmount() / (float) TileCombustionChamber.TANK_CAPACITY;
		if (prog > 0) {

			Direction facing = tileEntityIn.getFacing();
			AxisAlignedBB box;
			if (facing == Direction.EAST || facing == Direction.WEST) {
				box = new AxisAlignedBB(7.0D / 16.0D, 12.0D / 16.0D, 6.0D / 16.0D, 10.0D / 16.0D, 14.0D / 16.0D, 11.0D / 16.0D);
			} else {
				box = new AxisAlignedBB(6.0D / 16.0D, 12.0D / 16.0D, 7.0D / 16.0D, 11.0D / 16.0D, 14.0D / 16.0D, 10.0D / 16.0D);
			}

			IVertexBuilder builder = bufferIn.getBuffer(Atlases.translucentCullBlockSheet());
			RenderingUtils.renderFluidBox(matrixStackIn, Minecraft.getInstance(), builder, box, fuel, combinedLightIn, combinedOverlayIn, RenderingUtils.ALL_FACES);
		}
		matrixStackIn.popPose();
	}

}
