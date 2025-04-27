package electrodynamics.prefab.screen.component;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.common.tile.pipelines.gas.TileGasPipeFilter;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import voltaic.api.gas.GasAction;
import voltaic.api.gas.GasStack;
import voltaic.api.gas.IGasHandlerItem;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.ScreenComponentGeneric;
import voltaic.prefab.screen.component.types.gauges.ScreenComponentGasGauge;
import voltaic.prefab.utilities.CapabilityUtils;
import voltaic.registers.VoltaicCapabilities;

public class ScreenComponentGasFilter extends ScreenComponentGeneric {

    private final int index;

    public ScreenComponentGasFilter(int x, int y, int index) {
        super(ScreenComponentGasGauge.GasGaugeTextures.BACKGROUND_DEFAULT, x, y);
        this.index = index;
    }

    @Override
    public void renderBackground(GuiGraphics graphics, int xAxis, int yAxis, int guiWidth, int guiHeight) {
        super.renderBackground(graphics, xAxis, yAxis, guiWidth, guiHeight);

        TileGasPipeFilter filter = (TileGasPipeFilter) ((GenericContainerBlockEntity<?>) ((GenericScreen<?>) gui).getMenu()).getSafeHost();

        if (filter == null) {
            return;
        }

        SingleProperty<GasStack> property = filter.filteredGases[index];

        if (!property.getValue().isEmpty()) {

            ScreenComponentGasGauge.renderMercuryTexture(graphics, guiWidth + xLocation + 1, guiHeight + yLocation + 1, 1);

        }

        ScreenComponentGasGauge.GasGaugeTextures texture = ScreenComponentGasGauge.GasGaugeTextures.LEVEL_DEFAULT;

        graphics.blit(texture.getLocation(), guiWidth + xLocation, guiHeight + yLocation, texture.textureU(), texture.textureV(), texture.textureWidth(), texture.textureHeight(), texture.imageWidth(), texture.imageHeight());

    }

    @Override
    public void renderForeground(GuiGraphics graphics, int xAxis, int yAxis, int guiWidth, int guiHeight) {
        if (!isPointInRegion(xLocation, yLocation, xAxis, yAxis, super.texture.textureWidth(), super.texture.textureHeight())) {
            return;
        }

        TileGasPipeFilter filter = (TileGasPipeFilter) ((GenericContainerBlockEntity<?>) ((GenericScreen<?>) gui).getMenu()).getSafeHost();

        if (filter == null) {
            return;
        }

        List<FormattedCharSequence> tooltips = new ArrayList<>();

        SingleProperty<GasStack> property = filter.filteredGases[index];

        tooltips.add(property.getValue().getGas().getDescription().getVisualOrderText());

        graphics.renderTooltip(gui.getFontRenderer(), tooltips, xAxis, yAxis);
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

        TileGasPipeFilter filter = (TileGasPipeFilter) ((GenericContainerBlockEntity<?>) screen.getMenu()).getSafeHost();

        if (filter == null) {
            return;
        }

        SingleProperty<GasStack> property = filter.filteredGases[index];

        ItemStack holding = screen.getMenu().getCarried();

        if (holding.isEmpty()) {

            if (!Screen.hasShiftDown()) {
                return;
            }
            property.setValue(GasStack.EMPTY);

            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ElectrodynamicsSounds.SOUND_PRESSURERELEASE.get(), 1.0F));

            return;

        }

        IGasHandlerItem handler = holding.getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_ITEM).orElse(CapabilityUtils.EMPTY_GAS_ITEM);

        if (handler == CapabilityUtils.EMPTY_GAS_ITEM) {
            return;
        }

        GasStack taken = handler.drain(Integer.MAX_VALUE, GasAction.SIMULATE);

        if (taken.isEmpty()) {
            return;
        }

        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ElectrodynamicsSounds.SOUND_PRESSURERELEASE.get(), 1.0F));

        property.setValue(taken);

    }

}
