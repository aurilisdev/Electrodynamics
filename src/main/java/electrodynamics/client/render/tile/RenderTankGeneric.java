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
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

public class RenderTankGeneric extends AbstractTileRenderer<GenericTileTank> {

    private static final float MIN_X = 2.0F / 16.0F;
    private static final float MAX_X = 14.0F / 16.0F;
    private static final float MIN_Y = 2.001F / 16.0F;
    private static final float MAX_Y = 13.99F / 16.0F;
    private static final float MIN_Z = 2.0F / 16.0F;
    private static final float MAX_Z = 14.0F / 16.0F;

    public RenderTankGeneric(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(GenericTileTank entity, float ticks, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int light, int overlay) {
        ComponentFluidHandlerSimple tank = entity.getComponent(ComponentType.FluidHandler);
        if (!tank.isEmpty() && tank.getFluidAmount() > 0) {
            FluidStack fluid = tank.getFluid();
            float yHeight = Mth.clamp(MIN_Y + 0.065F, (float) tank.getFluidAmount() / (float) tank.getCapacity(), MAX_Y);
            AABB aabb = new AABB(MIN_X, MIN_Y, MIN_Z, MAX_X, yHeight, MAX_Z);
            VertexConsumer builder = source.getBuffer(Sheets.translucentCullBlockSheet());
            RenderingUtils.renderFluidBox(stack, Minecraft.getInstance(), builder, aabb, fluid, light, overlay);
        }
    }

}
