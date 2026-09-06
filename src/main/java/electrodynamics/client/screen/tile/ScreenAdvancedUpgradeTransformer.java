package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerAdvancedUpgradeTransformer;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import voltaic.api.screen.ITexture;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.ScreenComponentGeneric;
import voltaic.prefab.screen.component.button.ScreenComponentButton;
import voltaic.prefab.screen.component.types.ScreenComponentMultiLabel;
import voltaic.prefab.utilities.math.Color;

public class ScreenAdvancedUpgradeTransformer extends GenericScreen<ContainerAdvancedUpgradeTransformer> {

    public ScreenAdvancedUpgradeTransformer(ContainerAdvancedUpgradeTransformer container, Inventory inv,
	    Component title) {
	super(container, inv, title);
	imageHeight += 30;
	inventoryLabelY += 30;
	addComponent(new ScreenComponentGeneric(ITexture.Textures.TRANSFORMER_SYMBOL, 20, 43));
	addComponent(new ScreenComponentMultiLabel(0, 0, graphics -> {
	    int width = ITexture.Textures.TRANSFORMER_SYMBOL.textureWidth();
	    int xStart = 20;
	    Component top = ElectroTextUtils.gui("coilratio");
	    int offset = (int) ((width - font.width(top)) / 2.0F);
	    graphics.drawString(font, top, xStart + offset, 28, Color.TEXT_GRAY.color(), false);
	    menu.getSafeHost().ifPresent(xfmr -> {
		double coilRatio = xfmr.coilRatio.getValue();
		if (coilRatio <= 0)
		    coilRatio = xfmr.defaultCoilRatio;
		int wholeRatio = (int) (coilRatio < 1 ? 1.0 / coilRatio : coilRatio);
		Component bottom = Component.literal("1 : " + wholeRatio).withStyle(ChatFormatting.BOLD);
		int bottomOffset = (int) ((width - font.width(bottom)) / 2.0F);
		graphics.drawString(font, bottom, xStart + bottomOffset, 81, Color.TEXT_GRAY.color(), false);
	    });
	}));
	int[] ratios = { 2, 4, 8, 16, 32, 64, 128, 256 };
	for (int i = 0; i < ratios.length; i++) {
	    int ratio = ratios[i];
	    addComponent(new ScreenComponentButton<>(75 + i / 4 * 45, 20 + i % 4 * 20, 40, 20)
		    .setLabel(Component.literal("1 : " + ratio)).setOnPress(
			    button -> menu.getSafeHost().ifPresent(xfmr -> xfmr.coilRatio.setValue((double) ratio))));
	}
    }
}