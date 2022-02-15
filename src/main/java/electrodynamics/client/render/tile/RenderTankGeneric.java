package electrodynamics.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import electrodynamics.common.tile.generic.GenericTileTank;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerSimple;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class RenderTankGeneric implements BlockEntityRenderer<GenericTileTank> {

	private static final float MIN_X = 2.0F / 16.0F;
	private static final float MAX_X = 14.0F / 16.0F;
	private static final float MIN_Y = 2.001F / 16.0F;
	private static final float MAX_Y = 13.99F / 16.0F;
	private static final float MIN_Z = 2.0F / 16.0F;
	private static final float MAX_Z = 14.0F / 16.0F;

	public RenderTankGeneric(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(GenericTileTank entity, float ticks, PoseStack stack, MultiBufferSource source, int light, int overlay) {
		FluidTank tank = ((ComponentFluidHandlerSimple) entity.getComponent(ComponentType.FluidHandler)).getOutputTanks()[0];
		if (!tank.isEmpty() && tank.getFluidAmount() > 0) {
			FluidStack fluid = tank.getFluid();
			float yHeight = Math.max(Math.min((float) tank.getFluidAmount() / (float) tank.getCapacity(), MAX_Y), MIN_Y);
			AABB aabb = new AABB(MIN_X, MIN_Y, MIN_Z, MAX_X, yHeight, MAX_Z);
			VertexConsumer builder = source.getBuffer(Sheets.translucentCullBlockSheet());
			RenderingUtils.renderFluidBox(stack, Minecraft.getInstance(), builder, aabb, fluid, light, overlay);
		}
	}

}
