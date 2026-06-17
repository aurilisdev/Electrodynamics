package electrodynamics.compatibility.mekanism;

import electrodynamics.common.inventory.container.tile.ContainerRotaryUnifier;
import electrodynamics.common.tile.compatibility.TileRotaryUnifier;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import mekanism.api.chemical.ChemicalStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import voltaic.api.screen.ITexture;
import voltaic.prefab.screen.component.button.ScreenComponentButton;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.gauges.ScreenComponentGasGauge;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.screen.types.GenericMaterialScreen;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentProcessor;

public class ScreenRotaryUnifier extends GenericMaterialScreen<ContainerRotaryUnifier> {
    public ScreenRotaryUnifier(ContainerRotaryUnifier container, Inventory inv, Component titleIn) {
	super(container, inv, titleIn);

	addComponent(new ScreenComponentButton<>(ScreenComponentProgress.ProgressTextures.ARROW_RIGHT_OFF, 65, 31) {
	    @Override
	    public void renderBackground(GuiGraphics graphics, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		TileRotaryUnifier unifier = menu.getSafeHost();
		if (unifier == null) {
		    super.renderBackground(graphics, xAxis, yAxis, guiWidth, guiHeight);
		    return;
		}
		ComponentProcessor proc = unifier.getComponent(IComponentType.Processor);

		ITexture texture;

		if (proc.isActive(0) && unifier.conversionIsFlipped.getValue()) {

		    texture = ScreenComponentProgress.ProgressTextures.ARROW_LEFT_ON;

		} else if (proc.isActive(0) && !unifier.conversionIsFlipped.getValue()) {

		    texture = ScreenComponentProgress.ProgressTextures.ARROW_RIGHT_ON;

		} else if (!unifier.conversionIsFlipped.getValue()) {

		    texture = ScreenComponentProgress.ProgressTextures.ARROW_RIGHT_OFF;

		} else {

		    texture = ScreenComponentProgress.ProgressTextures.ARROW_LEFT_OFF;

		}

		graphics.blit(texture.getLocation(), guiWidth + xLocation, guiHeight + yLocation, texture.textureU(),
			texture.textureV(), texture.textureWidth(), texture.textureHeight(), texture.imageWidth(),
			texture.imageHeight());

	    }
	}.setOnPress(button -> {
	    TileRotaryUnifier unifier = menu.getSafeHost();
	    if (unifier == null) {
		return;
	    }
	    unifier.conversionIsFlipped.setValue(!unifier.conversionIsFlipped.getValue());
	}).onTooltip((graphics, component, xAxis, yAxis) -> {
	    TileRotaryUnifier unifier = menu.getSafeHost();
	    if (unifier == null) {
		return;
	    }
	    graphics.renderTooltip(getFontRenderer(), ElectroTextUtils.tooltip("rotaryunifier.toggle"), xAxis, yAxis);
	}));
	addComponent(new ScreenComponentGasGauge(() -> {
	    TileRotaryUnifier boiler = container.getSafeHost();
	    if (boiler != null) {
		return boiler.gasTank;
	    }
	    return null;
	}, 30, 18));

	addComponent(new ScreenComponentChemicalGauge(108, 21, () -> {
	    TileRotaryUnifier unifier = menu.getSafeHost();
	    if (unifier == null) {
		return ChemicalStack.EMPTY;
	    }
	    return MekanismHandler.getProp(unifier).getValue();
	}));

	addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2));
    }
}
