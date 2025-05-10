package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerAdvancedUpgradeTransformer;
import electrodynamics.common.tile.electricitygrid.transformer.TileAdvancedTransformer.TileAdvancedUpgradeTransformer;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import voltaic.api.screen.ITexture;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.ScreenComponentGeneric;
import voltaic.prefab.screen.component.button.ScreenComponentButton;
import voltaic.prefab.screen.component.types.ScreenComponentMultiLabel;
import voltaic.prefab.utilities.math.Color;

public class ScreenAdvancedUpgradeTransformer extends GenericScreen<ContainerAdvancedUpgradeTransformer> {

	public ScreenAdvancedUpgradeTransformer(ContainerAdvancedUpgradeTransformer container, PlayerInventory inv, ITextComponent title) {
		super(container, inv, title);
		imageHeight += 30;
		inventoryLabelY += 30;
		addComponent(new ScreenComponentGeneric(ITexture.Textures.TRANSFORMER_SYMBOL, 20, 43));
		addComponent(new ScreenComponentMultiLabel(0, 0, poseStack -> {

			int width = ITexture.Textures.TRANSFORMER_SYMBOL.textureWidth();
			int xStart = 20;

			ITextComponent top = ElectroTextUtils.gui("coilratio");

			int offset = (int) ((width - font.width(top)) / 2.0F);

			font.draw(poseStack, top, xStart + offset, 28, Color.TEXT_GRAY.color());

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

			ITextComponent bottom = new StringTextComponent("1 : " + wholeRatio).withStyle(TextFormatting.BOLD);

			offset = (int) ((width - font.width(bottom)) / 2.0F);

			font.draw(poseStack, bottom, xStart + offset, 81, Color.TEXT_GRAY.color());

		}));
		addComponent(new ScreenComponentButton<>(75, 20, 40, 20).setLabel(new StringTextComponent("1 : 2")).setOnPress(button -> {
			TileAdvancedUpgradeTransformer xfmr = menu.getSafeHost();
			if (xfmr == null) {
				return;
			}
			xfmr.coilRatio.setValue(2.0);
		}));
		addComponent(new ScreenComponentButton<>(75, 40, 40, 20).setLabel(new StringTextComponent("1 : 4")).setOnPress(button -> {
			TileAdvancedUpgradeTransformer xfmr = menu.getSafeHost();
			if (xfmr == null) {
				return;
			}
			xfmr.coilRatio.setValue(4.0);
		}));
		addComponent(new ScreenComponentButton<>(75, 60, 40, 20).setLabel(new StringTextComponent("1 : 8")).setOnPress(button -> {
			TileAdvancedUpgradeTransformer xfmr = menu.getSafeHost();
			if (xfmr == null) {
				return;
			}
			xfmr.coilRatio.setValue(8.0);
		}));
		addComponent(new ScreenComponentButton<>(75, 80, 40, 20).setLabel(new StringTextComponent("1 : 16")).setOnPress(button -> {
			TileAdvancedUpgradeTransformer xfmr = menu.getSafeHost();
			if (xfmr == null) {
				return;
			}
			xfmr.coilRatio.setValue(16.0);
		}));
		addComponent(new ScreenComponentButton<>(120, 20, 40, 20).setLabel(new StringTextComponent("1 : 32")).setOnPress(button -> {
			TileAdvancedUpgradeTransformer xfmr = menu.getSafeHost();
			if (xfmr == null) {
				return;
			}
			xfmr.coilRatio.setValue(32.0);
		}));
		addComponent(new ScreenComponentButton<>(120, 40, 40, 20).setLabel(new StringTextComponent("1 : 64")).setOnPress(button -> {
			TileAdvancedUpgradeTransformer xfmr = menu.getSafeHost();
			if (xfmr == null) {
				return;
			}
			xfmr.coilRatio.setValue(64.0);
		}));
		addComponent(new ScreenComponentButton<>(120, 60, 40, 20).setLabel(new StringTextComponent("1 : 128")).setOnPress(button -> {
			TileAdvancedUpgradeTransformer xfmr = menu.getSafeHost();
			if (xfmr == null) {
				return;
			}
			xfmr.coilRatio.setValue(128.0);
		}));
		addComponent(new ScreenComponentButton<>(120, 80, 40, 20).setLabel(new StringTextComponent("1 : 256")).setOnPress(button -> {
			TileAdvancedUpgradeTransformer xfmr = menu.getSafeHost();
			if (xfmr == null) {
				return;
			}
			xfmr.coilRatio.setValue(256.0);
		}));
	}

}
