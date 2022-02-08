package electrodynamics.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import electrodynamics.common.tile.TileCoolantResavoir;
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

public class RenderCoolantResavoir implements BlockEntityRenderer<TileCoolantResavoir> {

	private static final float MIN_X = 1.0F / 16.0F;
	private static final float MAX_X = 15.0F / 16.0F;
	private static final float MIN_Y = 1.001F / 16.0F;
	private static final float MAX_Y = 14.99F / 16.0F;
	private static final float MIN_Z = 1.0F / 16.0F;
	private static final float MAX_Z = 15.0F / 16.0F;
	
	public RenderCoolantResavoir(BlockEntityRendererProvider.Context context) {}
	
	@Override
	public void render(TileCoolantResavoir entity, float tick, PoseStack stack, MultiBufferSource source, int light, int overlay) {
		FluidTank tank = ((ComponentFluidHandlerSimple)entity.getComponent(ComponentType.FluidHandler)).getOutputTanks()[0];
		if(!tank.isEmpty() && tank.getFluidAmount() > 0) {
			FluidStack fluid = tank.getFluid();
			float yHeight = Math.max(Math.min((float) tank.getFluidAmount() / (float) tank.getCapacity(), MAX_Y), MIN_Y);
			AABB aabb = new AABB(MIN_X, MIN_Y, MIN_Z, MAX_X, yHeight, MAX_Z);
			VertexConsumer builder = source.getBuffer(Sheets.translucentCullBlockSheet());
			RenderingUtils.renderFluidBox(stack, Minecraft.getInstance(), builder, aabb, fluid, light, overlay);
		}
	}

}