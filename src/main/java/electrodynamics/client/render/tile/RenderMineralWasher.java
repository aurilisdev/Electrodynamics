package electrodynamics.client.render.tile;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.client.ElectrodynamicsClientRegister;
import electrodynamics.common.tile.machines.TileMineralWasher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentProcessor;
import voltaic.prefab.utilities.math.Color;

public class RenderMineralWasher extends AbstractTileRenderer<TileMineralWasher> {

    public RenderMineralWasher(TileEntityRendererDispatcher context) {
        super(context);
    }

    @Override
    public void render(TileMineralWasher tile, float partialTicks, @Nonnull MatrixStack matrix, @Nonnull IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

        ItemStack stack = tile.<ComponentInventory>getComponent(IComponentType.Inventory).getInputsForProcessor(0).get(0);

        if (stack.isEmpty()) {

            return;

        }

        Direction dir = tile.getFacing();

        matrix.pushPose();

        double scale = 12;

        matrix.translate(0.5 + dir.getStepX() / scale, stack.getItem() instanceof BlockItem ? 0.48 : 0.39, 0.5 + dir.getStepZ() / scale);

        matrix.scale(0.35f, 0.35f, 0.35f);

        matrix.scale(0.3f, 0.3f, 0.3f);

        matrix.translate(0, -0.2, 0);

        renderItem(stack, TransformType.NONE, combinedLightIn, combinedOverlayIn, matrix, bufferIn, tile.getLevel(), 0);

        matrix.popPose();

        ComponentFluidHandlerMulti multi = tile.getComponent(IComponentType.FluidHandler);

        FluidStack fluid = multi.getFluidInTank(0, true);

        if (!tile.<ComponentProcessor>getComponent(IComponentType.Processor).isActive(0) || fluid.isEmpty() || level().getRandom().nextDouble() > 0.15) {
            return;
        }

        matrix.pushPose();

        FluidAttributes attributes = fluid.getFluid().getAttributes();

        TextureAtlasSprite sp = minecraft().getTextureAtlas(ElectrodynamicsClientRegister.BLOCK_ATLAS).apply(attributes.getStillTexture());

        Vector3f color = new Color(attributes.getColor(fluid)).multiply(Color.fromABGR(sp.getPixelRGBA(1, level().getRandom().nextInt(17), level().getRandom().nextInt(17)))).getFloatVector();

        for (int i = 0; i < 2; i++) {
            double x = 0.5 + level().random.nextDouble() * 0.4 - 0.2;
            double y = 0.5 + level().random.nextDouble() * 0.3 - 0.15;
            double z = 0.5 + level().random.nextDouble() * 0.4 - 0.2;
            level().addParticle(new RedstoneParticleData(color.x(), color.y(), color.z(), 1), tile.getBlockPos().getX() + x, tile.getBlockPos().getY() + y, tile.getBlockPos().getZ() + z, level().random.nextDouble() * 0.2 - 0.1, level().random.nextDouble() * 0.2 - 0.1, level().random.nextDouble() * 0.2 - 0.1);
        }

        matrix.popPose();


    }
    
}
