package electrodynamics.client.render.tile;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import electrodynamics.common.tile.machines.quarry.TileCoolantResavoir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.client.renderer.Atlases;
import net.minecraftforge.fluids.FluidStack;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerSimple;
import voltaic.prefab.utilities.RenderingUtils;

public class RenderCoolantResavoir extends AbstractTileRenderer<TileCoolantResavoir> {

	private static final float MIN_X = 1.0F / 16.0F;
	private static final float MAX_X = 15.0F / 16.0F;
	private static final float MIN_Y = 1.001F / 16.0F;
	private static final float MAX_Y = 14.99F / 16.0F;
	private static final float MIN_Z = 1.0F / 16.0F;
	private static final float MAX_Z = 15.0F / 16.0F;

	public RenderCoolantResavoir(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(TileCoolantResavoir entity, float tick, @Nonnull MatrixStack stack, @Nonnull IRenderTypeBuffer source, int light, int overlay) {
		ComponentFluidHandlerSimple tank = entity.getComponent(IComponentType.FluidHandler);
		if (!tank.isEmpty() && tank.getFluidAmount() > 0) {
			FluidStack fluid = tank.getFluid();
			float yHeight = Math.max(Math.min((float) tank.getFluidAmount() / (float) tank.getCapacity(), MAX_Y), MIN_Y);
			AxisAlignedBB aabb = new AxisAlignedBB(MIN_X, MIN_Y, MIN_Z, MAX_X, yHeight, MAX_Z);
			IVertexBuilder builder = source.getBuffer(Atlases.translucentCullBlockSheet());
			RenderingUtils.renderFluidBox(stack, Minecraft.getInstance(), builder, aabb, fluid, light, overlay, RenderingUtils.ALL_FACES);
		}
	}

}
