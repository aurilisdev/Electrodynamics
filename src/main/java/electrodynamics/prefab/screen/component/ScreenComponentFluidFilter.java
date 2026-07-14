package electrodynamics.prefab.screen.component;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.tile.pipelines.fluid.TileFluidPipeFilter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.ScreenComponentGeneric;
import voltaic.prefab.screen.component.types.gauges.AbstractScreenComponentGauge;
import voltaic.prefab.utilities.CapabilityUtils;
import voltaic.prefab.utilities.RenderingUtils;
import voltaic.prefab.utilities.math.Color;

public class ScreenComponentFluidFilter extends ScreenComponentGeneric {

    private final int index;

    public ScreenComponentFluidFilter(int x, int y, int index) {
        super(AbstractScreenComponentGauge.GaugeTextures.BACKGROUND_DEFAULT, x, y);
        this.index = index;
    }

    @Override
    public void renderBackground(PoseStack poseStack, int xAxis, int yAxis, int guiWidth, int guiHeight) {
        super.renderBackground(poseStack, xAxis, yAxis, guiWidth, guiHeight);

        TileFluidPipeFilter filter = (TileFluidPipeFilter) ((GenericContainerBlockEntity<?>) ((GenericScreen<?>) gui).getMenu()).getSafeHost();

        if (filter == null) {
            return;
        }

        SingleProperty<FluidStack> property = filter.filteredFluids[index];

        FluidStack fluid = property.getValue();

        if (!fluid.isEmpty()) {
        	
        	FluidAttributes attributes = fluid.getFluid().getAttributes();

            ResourceLocation fluidText = attributes.getStillTexture();

            if (fluidText != null) {

                ResourceLocation blocks = InventoryMenu.BLOCK_ATLAS;
                TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(blocks).apply(fluidText);
                RenderingUtils.bindTexture(sprite.atlas().location());

                int scale = AbstractScreenComponentGauge.GaugeTextures.BACKGROUND_DEFAULT.textureHeight() - 2;

                RenderingUtils.setShaderColor(new Color(attributes.getColor(fluid)));

                for (int i = 0; i < 16; i += 16) {
                    for (int j = 0; j < scale; j += 16) {
                        int drawWidth = Math.min(super.texture.textureWidth() - 2 - i, 16);
                        int drawHeight = Math.min(scale - j, 16);

                        int drawX = guiWidth + xLocation + 1;
                        int drawY = guiHeight + yLocation - 1 + super.texture.textureHeight() - Math.min(scale - j, super.texture.textureHeight());
                        blit(poseStack, drawX, drawY, 0, drawWidth, drawHeight, sprite);
                    }
                }
                RenderSystem.setShaderColor(1, 1, 1, 1);

            }

        }

        RenderingUtils.bindTexture(AbstractScreenComponentGauge.GaugeTextures.LEVEL_DEFAULT.getLocation());
        blit(
                poseStack,
                guiWidth + xLocation,
                guiHeight + yLocation,
                AbstractScreenComponentGauge.GaugeTextures.LEVEL_DEFAULT.textureU(),
                0,
                AbstractScreenComponentGauge.GaugeTextures.LEVEL_DEFAULT.textureWidth(),
                AbstractScreenComponentGauge.GaugeTextures.LEVEL_DEFAULT.textureHeight(),
                AbstractScreenComponentGauge.GaugeTextures.LEVEL_DEFAULT.imageWidth(),
                AbstractScreenComponentGauge.GaugeTextures.LEVEL_DEFAULT.imageHeight());

    }

    @Override
    public void renderForeground(PoseStack poseStack, int xAxis, int yAxis, int guiWidth, int guiHeight) {

        if (!isPointInRegion(xLocation, yLocation, xAxis, yAxis, super.texture.textureWidth(), super.texture.textureHeight())) {
            return;
        }

        TileFluidPipeFilter filter = (TileFluidPipeFilter) ((GenericContainerBlockEntity<?>) ((GenericScreen<?>) gui).getMenu()).getSafeHost();

        if (filter == null) {
            return;
        }

        SingleProperty<FluidStack> property = filter.filteredFluids[index];

        List<FormattedCharSequence> tooltips = new ArrayList<>();

        tooltips.add(new TranslatableComponent(property.getValue().getTranslationKey()).getVisualOrderText());

        gui.displayTooltips(poseStack, tooltips, xAxis, yAxis);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isActiveAndVisible() && isValidClick(button) && isInClickRegion(mouseX, mouseY)) {

            onMouseClick(mouseX, mouseY);

            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (isValidClick(button)) {
            onMouseRelease(mouseX, mouseY);
            return true;
        }
        return false;
    }

    @Override
    public void onMouseClick(double mouseX, double mouseY) {

        GenericScreen<?> screen = (GenericScreen<?>) gui;

        TileFluidPipeFilter filter = (TileFluidPipeFilter) ((GenericContainerBlockEntity<?>) screen.getMenu()).getSafeHost();

        if (filter == null) {
            return;
        }

        SingleProperty<FluidStack> property = filter.filteredFluids[index];

        ItemStack holding = screen.getMenu().getCarried();

        if (holding.isEmpty()) {

            if (!Screen.hasShiftDown()) {
                return;
            }
            property.setValue(FluidStack.EMPTY);

            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BUCKET_EMPTY, 1.0F));

            return;

        }

        IFluidHandlerItem handler = holding.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(CapabilityUtils.EMPTY_FLUID_ITEM);

        if (handler == CapabilityUtils.EMPTY_FLUID_ITEM) {
            return;
        }

        FluidStack taken = handler.drain(Integer.MAX_VALUE, FluidAction.SIMULATE);

        if (taken.isEmpty()) {
            return;
        }

        property.setValue(taken);

        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BUCKET_FILL, 1.0F));

    }

}
