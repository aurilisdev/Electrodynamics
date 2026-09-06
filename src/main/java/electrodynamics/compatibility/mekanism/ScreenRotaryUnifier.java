package electrodynamics.compatibility.mekanism;

import electrodynamics.common.inventory.container.tile.ContainerRotaryUnifier;
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
		var host = menu.getSafeHost();
		if (host.isEmpty()) {
		    super.renderBackground(graphics, xAxis, yAxis, guiWidth, guiHeight);
		    return;
		}
		var unifier = host.get();
		ComponentProcessor processor = unifier.<ComponentProcessor>requireComponent(IComponentType.Processor);
		boolean flipped = unifier.conversionIsFlipped.getValue();
		ITexture texture;
		if (processor.isActive(0))
		    texture = flipped ? ScreenComponentProgress.ProgressTextures.ARROW_LEFT_ON
			    : ScreenComponentProgress.ProgressTextures.ARROW_RIGHT_ON;
		else
		    texture = flipped ? ScreenComponentProgress.ProgressTextures.ARROW_LEFT_OFF
			    : ScreenComponentProgress.ProgressTextures.ARROW_RIGHT_OFF;
		graphics.blit(texture.getLocation(), guiWidth + xLocation, guiHeight + yLocation, texture.textureU(),
			texture.textureV(), texture.textureWidth(), texture.textureHeight(), texture.imageWidth(),
			texture.imageHeight());
	    }
	}.setOnPress(button -> menu.getSafeHost()
		.ifPresent(unifier -> unifier.conversionIsFlipped.setValue(!unifier.conversionIsFlipped.getValue())))
		.onTooltip((graphics, component, xAxis, yAxis) -> menu.getSafeHost()
			.ifPresent(unifier -> graphics.renderTooltip(getFontRenderer(),
				ElectroTextUtils.tooltip("rotaryunifier.toggle"), xAxis, yAxis))));
	addComponent(new ScreenComponentGasGauge(
		() -> container.getSafeHost().map(unifier -> unifier.gasTank).orElse(null), 30, 18));
	addComponent(new ScreenComponentChemicalGauge(108, 21, () -> menu.getSafeHost()
		.map(unifier -> MekanismHandler.getProp(unifier).getValue()).orElse(ChemicalStack.EMPTY)));
	addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2));
    }
}