package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerAdvancedUpgradeTransformer;
import electrodynamics.common.tile.electricitygrid.transformer.TileAdvancedTransformer.TileAdvancedUpgradeTransformer;
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

	public ScreenAdvancedUpgradeTransformer(ContainerAdvancedUpgradeTransformer container, Inventory inv, Component title) {
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

			TileAdvancedUpgradeTransformer xfmr = menu.getSafeHost();
			if (xfmr == null) {
				return;
			}
			double coilRatio = xfmr.coilRatio.getValue();
			if (coilRatio <= 0) {
				coilRatio = xfmr.defaultCoilRatio;
			}
			int wholeRatio;
			if (coilRatio < 1) {
				wholeRatio = (int) (1.0 / coilRatio);
			} else {
				wholeRatio = (int) coilRatio;
			}

			Component bottom = Component.literal("1 : " + wholeRatio).withStyle(ChatFormatting.BOLD);

			offset = (int) ((width - font.width(bottom)) / 2.0F);

			graphics.drawString(font, bottom, xStart + offset, 81, Color.TEXT_GRAY.color(), false);

		}));
		addComponent(new ScreenComponentButton<>(75, 20, 40, 20).setLabel(Component.literal("1 : 2")).setOnPress(button -> {
			TileAdvancedUpgradeTransformer xfmr = menu.getSafeHost();
			if (xfmr == null) {
				return;
			}
			xfmr.coilRatio.setValue(2.0);
		}));
		addComponent(new ScreenComponentButton<>(75, 40, 40, 20).setLabel(Component.literal("1 : 4")).setOnPress(button -> {
			TileAdvancedUpgradeTransformer xfmr = menu.getSafeHost();
			if (xfmr == null) {
				return;
			}
			xfmr.coilRatio.setValue(4.0);
		}));
		addComponent(new ScreenComponentButton<>(75, 60, 40, 20).setLabel(Component.literal("1 : 8")).setOnPress(button -> {
			TileAdvancedUpgradeTransformer xfmr = menu.getSafeHost();
			if (xfmr == null) {
				return;
			}
			xfmr.coilRatio.setValue(8.0);
		}));
		addComponent(new ScreenComponentButton<>(75, 80, 40, 20).setLabel(Component.literal("1 : 16")).setOnPress(button -> {
			TileAdvancedUpgradeTransformer xfmr = menu.getSafeHost();
			if (xfmr == null) {
				return;
			}
			xfmr.coilRatio.setValue(16.0);
		}));
		addComponent(new ScreenComponentButton<>(120, 20, 40, 20).setLabel(Component.literal("1 : 32")).setOnPress(button -> {
			TileAdvancedUpgradeTransformer xfmr = menu.getSafeHost();
			if (xfmr == null) {
				return;
			}
			xfmr.coilRatio.setValue(32.0);
		}));
		addComponent(new ScreenComponentButton<>(120, 40, 40, 20).setLabel(Component.literal("1 : 64")).setOnPress(button -> {
			TileAdvancedUpgradeTransformer xfmr = menu.getSafeHost();
			if (xfmr == null) {
				return;
			}
			xfmr.coilRatio.setValue(64.0);
		}));
		addComponent(new ScreenComponentButton<>(120, 60, 40, 20).setLabel(Component.literal("1 : 128")).setOnPress(button -> {
			TileAdvancedUpgradeTransformer xfmr = menu.getSafeHost();
			if (xfmr == null) {
				return;
			}
			xfmr.coilRatio.setValue(128.0);
		}));
		addComponent(new ScreenComponentButton<>(120, 80, 40, 20).setLabel(Component.literal("1 : 256")).setOnPress(button -> {
			TileAdvancedUpgradeTransformer xfmr = menu.getSafeHost();
			if (xfmr == null) {
				return;
			}
			xfmr.coilRatio.setValue(256.0);
		}));
	}

}
