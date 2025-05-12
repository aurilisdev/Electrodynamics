package electrodynamics.client.render.tile;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import electrodynamics.common.tile.pipelines.fluid.tank.GenericTileFluidTank;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.renderer.Atlases;
import net.minecraftforge.fluids.FluidStack;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerSimple;
import voltaic.prefab.utilities.RenderingUtils;

public class RenderTankGeneric extends AbstractTileRenderer<GenericTileFluidTank> {

	private static final float MIN_X = 2.0F / 16.0F;
	private static final float MAX_X = 14.0F / 16.0F;
	private static final float MIN_Y = 2.001F / 16.0F;
	private static final float MAX_Y = 13.99F / 16.0F;
	private static final float MIN_Z = 2.0F / 16.0F;
	private static final float MAX_Z = 14.0F / 16.0F;

	public RenderTankGeneric(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(GenericTileFluidTank entity, float ticks, @Nonnull MatrixStack stack, @Nonnull IRenderTypeBuffer source, int light, int overlay) {
		ComponentFluidHandlerSimple tank = entity.getComponent(IComponentType.FluidHandler);
		if (!tank.isEmpty() && tank.getFluidAmount() > 0) {
			FluidStack fluid = tank.getFluid();
			float yHeight = MathHelper.clamp((float) tank.getFluidAmount() / (float) tank.getCapacity(), MIN_Y + 0.065F, MAX_Y);
			AxisAlignedBB aabb = new AxisAlignedBB(MIN_X, MIN_Y, MIN_Z, MAX_X, yHeight, MAX_Z);
			IVertexBuilder builder = source.getBuffer(Atlases.translucentCullBlockSheet());
			RenderingUtils.renderFluidBox(stack, Minecraft.getInstance(), builder, aabb, fluid, light, overlay, RenderingUtils.ALL_FACES);
		}
	}

}
