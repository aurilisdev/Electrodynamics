package electrodynamics.client.render.tile;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import electrodynamics.common.tile.machines.TileElectrolyticSeparator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.client.renderer.Atlases;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.utilities.RenderingUtils;

public class RenderElectrolyticSeparator extends AbstractTileRenderer<TileElectrolyticSeparator> {

	private static final float MIN_X = 4.0F / 16.0F;
	private static final float MAX_X = 12.0F / 16.0F;
	private static final float MIN_Y = 6.00F / 16.0F;
	private static final float MAX_Y = 11.0F / 16.0F;
	private static final float MIN_Z = 4.0F / 16.0F;
	private static final float MAX_Z = 12.0F / 16.0F;

	public RenderElectrolyticSeparator(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(TileElectrolyticSeparator entity, float ticks, @Nonnull MatrixStack stack, @Nonnull IRenderTypeBuffer source, int light, int overlay) {
		FluidTank tank = entity.<ComponentFluidHandlerMulti>getComponent(IComponentType.FluidHandler).getInputTanks()[0];
		if (!tank.isEmpty() && tank.getFluidAmount() > 0) {
			FluidStack fluid = tank.getFluid();
			float yHeight = Math.max(Math.min((float) tank.getFluidAmount() / (float) tank.getCapacity(), MAX_Y), MIN_Y);
			AxisAlignedBB aabb = new AxisAlignedBB(MIN_X, MIN_Y, MIN_Z, MAX_X, yHeight, MAX_Z);
			IVertexBuilder builder = source.getBuffer(Atlases.translucentCullBlockSheet());
			RenderingUtils.renderFluidBox(stack, Minecraft.getInstance(), builder, aabb, fluid, light, overlay, RenderingUtils.ALL_FACES);
		}
	}

}
