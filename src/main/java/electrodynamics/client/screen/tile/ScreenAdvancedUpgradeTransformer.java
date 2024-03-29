package electrodynamics.client.screen.tile;

import electrodynamics.api.screen.ITexture.Textures;
import electrodynamics.common.inventory.container.tile.ContainerAdvancedUpgradeTransformer;
import electrodynamics.common.tile.electricitygrid.transformer.TileAdvancedTransformer.TileAdvancedUpgradeTransformer;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.button.ScreenComponentButton;
import electrodynamics.prefab.screen.component.types.ScreenComponentGeneric;
import electrodynamics.prefab.screen.component.types.ScreenComponentMultiLabel;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenAdvancedUpgradeTransformer extends GenericScreen<ContainerAdvancedUpgradeTransformer> {

	public ScreenAdvancedUpgradeTransformer(ContainerAdvancedUpgradeTransformer container, Inventory inv, Component title) {
		super(container, inv, title);
		imageHeight += 30;
		inventoryLabelY += 30;
		addComponent(new ScreenComponentGeneric(Textures.TRANSFORMER_SYMBOL, 20, 43));
		addComponent(new ScreenComponentMultiLabel(0, 0, graphics -> {

			int width = Textures.TRANSFORMER_SYMBOL.textureWidth();
			int xStart = 20;

			Component top = ElectroTextUtils.gui("coilratio");

			int offset = (int) ((width - font.width(top)) / 2.0F);

			graphics.drawString(font, top, xStart + offset, 28, 4210752, false);

			TileAdvancedUpgradeTransformer xfmr = menu.getHostFromIntArray();
			if (xfmr == null) {
				return;
			}
			double coilRatio = xfmr.coilRatio.get();
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

			graphics.drawString(font, bottom, xStart + offset, 81, 4210752, false);

		}));
		addComponent(new ScreenComponentButton<>(75, 20, 40, 20).setLabel(Component.literal("1 : 2")).setOnPress(button -> {
			TileAdvancedUpgradeTransformer xfmr = menu.getHostFromIntArray();
			if (xfmr == null) {
				return;
			}
			xfmr.coilRatio.set(2);
			xfmr.coilRatio.updateServer();
		}));
		addComponent(new ScreenComponentButton<>(75, 40, 40, 20).setLabel(Component.literal("1 : 4")).setOnPress(button -> {
			TileAdvancedUpgradeTransformer xfmr = menu.getHostFromIntArray();
			if (xfmr == null) {
				return;
			}
			xfmr.coilRatio.set(4);
			xfmr.coilRatio.updateServer();
		}));
		addComponent(new ScreenComponentButton<>(75, 60, 40, 20).setLabel(Component.literal("1 : 8")).setOnPress(button -> {
			TileAdvancedUpgradeTransformer xfmr = menu.getHostFromIntArray();
			if (xfmr == null) {
				return;
			}
			xfmr.coilRatio.set(8);
			xfmr.coilRatio.updateServer();
		}));
		addComponent(new ScreenComponentButton<>(75, 80, 40, 20).setLabel(Component.literal("1 : 16")).setOnPress(button -> {
			TileAdvancedUpgradeTransformer xfmr = menu.getHostFromIntArray();
			if (xfmr == null) {
				return;
			}
			xfmr.coilRatio.set(16);
			xfmr.coilRatio.updateServer();
		}));
		addComponent(new ScreenComponentButton<>(120, 20, 40, 20).setLabel(Component.literal("1 : 32")).setOnPress(button -> {
			TileAdvancedUpgradeTransformer xfmr = menu.getHostFromIntArray();
			if (xfmr == null) {
				return;
			}
			xfmr.coilRatio.set(32);
			xfmr.coilRatio.updateServer();
		}));
		addComponent(new ScreenComponentButton<>(120, 40, 40, 20).setLabel(Component.literal("1 : 64")).setOnPress(button -> {
			TileAdvancedUpgradeTransformer xfmr = menu.getHostFromIntArray();
			if (xfmr == null) {
				return;
			}
			xfmr.coilRatio.set(64);
			xfmr.coilRatio.updateServer();
		}));
		addComponent(new ScreenComponentButton<>(120, 60, 40, 20).setLabel(Component.literal("1 : 128")).setOnPress(button -> {
			TileAdvancedUpgradeTransformer xfmr = menu.getHostFromIntArray();
			if (xfmr == null) {
				return;
			}
			xfmr.coilRatio.set(128);
			xfmr.coilRatio.updateServer();
		}));
		addComponent(new ScreenComponentButton<>(120, 80, 40, 20).setLabel(Component.literal("1 : 256")).setOnPress(button -> {
			TileAdvancedUpgradeTransformer xfmr = menu.getHostFromIntArray();
			if (xfmr == null) {
				return;
			}
			xfmr.coilRatio.set(256);
			xfmr.coilRatio.updateServer();
		}));
	}

}
