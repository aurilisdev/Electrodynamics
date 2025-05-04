package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerCircuitMonitor;
import electrodynamics.common.tile.electricitygrid.TileCircuitMonitor;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Inventory;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnit;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.button.ScreenComponentButton;
import voltaic.prefab.screen.component.editbox.ScreenComponentEditBox;
import voltaic.prefab.screen.component.types.ScreenComponentMultiLabel;
import voltaic.prefab.screen.component.types.ScreenComponentSimpleLabel;
import voltaic.prefab.utilities.math.Color;

public class ScreenCircuitMonitor extends GenericScreen<ContainerCircuitMonitor> {

	private ScreenComponentEditBox value;

	private boolean needsUpdate = true;

	public ScreenCircuitMonitor(ContainerCircuitMonitor container, Inventory inv, Component title) {
		super(container, inv, title);

		imageHeight += 40;

		addComponent(new ScreenComponentMultiLabel(0, 0, graphics -> {

			TileCircuitMonitor monitor = menu.getSafeHost();

			if (monitor == null) {
				return;
			}

			DisplayUnit units = getUnit(monitor.networkProperty.getValue());

			Component combined = getPropertyLabel(monitor.networkProperty.getValue()).append(" ").append(getOperatorLabel(monitor.booleanOperator.getValue())).append(" ").append(ChatFormatter.getChatDisplayShort(monitor.value.getValue(), units)).withStyle(ChatFormatting.BOLD);

			int offset = (int) ((150 - font.width(combined)) / 2.0);

			graphics.drawString(font, combined, 13 + offset, 22, 0, false);

			Component symbol = units.getSymbol();

			graphics.drawString(font, symbol, 163 - font.width(symbol), 175, Color.TEXT_GRAY.color(), false);

		}));

		addComponent(new ScreenComponentSimpleLabel(13, 38, 10, Color.TEXT_GRAY, ElectroTextUtils.gui("property")));
		addComponent(new ScreenComponentSimpleLabel(13, 118, 10, Color.TEXT_GRAY, ElectroTextUtils.gui("operator")));
		addComponent(new ScreenComponentSimpleLabel(13, 158, 10, Color.TEXT_GRAY, ElectroTextUtils.gui("value")));

		// network value

		addComponent(new ScreenComponentButton<>(13, 50, 70, 20).setLabel(getPropertyLabel(0)).setOnPress(button -> {
			TileCircuitMonitor monitor = menu.getSafeHost();
			if (monitor == null) {
				return;
			}
			monitor.networkProperty.setValue(0);
		}));
		addComponent(new ScreenComponentButton<>(13, 70, 70, 20).setLabel(getPropertyLabel(1)).setOnPress(button -> {
			TileCircuitMonitor monitor = menu.getSafeHost();
			if (monitor == null) {
				return;
			}
			monitor.networkProperty.setValue(1);
		}));
		addComponent(new ScreenComponentButton<>(13, 90, 70, 20).setLabel(getPropertyLabel(2)).setOnPress(button -> {
			TileCircuitMonitor monitor = menu.getSafeHost();
			if (monitor == null) {
				return;
			}
			monitor.networkProperty.setValue(2);
		}));
		addComponent(new ScreenComponentButton<>(93, 50, 70, 20).setLabel(getPropertyLabel(3)).setOnPress(button -> {
			TileCircuitMonitor monitor = menu.getSafeHost();
			if (monitor == null) {
				return;
			}
			monitor.networkProperty.setValue(3);
		}));
		addComponent(new ScreenComponentButton<>(93, 70, 70, 20).setLabel(getPropertyLabel(4)).setOnPress(button -> {
			TileCircuitMonitor monitor = menu.getSafeHost();
			if (monitor == null) {
				return;
			}
			monitor.networkProperty.setValue(4);
		}));
		addComponent(new ScreenComponentButton<>(93, 90, 70, 20).setLabel(getPropertyLabel(5)).setOnPress(button -> {
			TileCircuitMonitor monitor = menu.getSafeHost();
			if (monitor == null) {
				return;
			}
			monitor.networkProperty.setValue(5);
		}));

		// boolean operator

		addComponent(new ScreenComponentButton<>(13, 130, 20, 20).setLabel(getOperatorLabel(0)).setOnPress(button -> {
			TileCircuitMonitor monitor = menu.getSafeHost();
			if (monitor == null) {
				return;
			}
			monitor.booleanOperator.setValue(0);
		}));
		addComponent(new ScreenComponentButton<>(39, 130, 20, 20).setLabel(getOperatorLabel(1)).setOnPress(button -> {
			TileCircuitMonitor monitor = menu.getSafeHost();
			if (monitor == null) {
				return;
			}
			monitor.booleanOperator.setValue(1);
		}));
		addComponent(new ScreenComponentButton<>(65, 130, 20, 20).setLabel(getOperatorLabel(2)).setOnPress(button -> {
			TileCircuitMonitor monitor = menu.getSafeHost();
			if (monitor == null) {
				return;
			}
			monitor.booleanOperator.setValue(2);
		}));
		addComponent(new ScreenComponentButton<>(91, 130, 20, 20).setLabel(getOperatorLabel(3)).setOnPress(button -> {
			TileCircuitMonitor monitor = menu.getSafeHost();
			if (monitor == null) {
				return;
			}
			monitor.booleanOperator.setValue(3);
		}));
		addComponent(new ScreenComponentButton<>(117, 130, 20, 20).setLabel(getOperatorLabel(4)).setOnPress(button -> {
			TileCircuitMonitor monitor = menu.getSafeHost();
			if (monitor == null) {
				return;
			}
			monitor.booleanOperator.setValue(4);
		}));
		addComponent(new ScreenComponentButton<>(143, 130, 20, 20).setLabel(getOperatorLabel(5)).setOnPress(button -> {
			TileCircuitMonitor monitor = menu.getSafeHost();
			if (monitor == null) {
				return;
			}
			monitor.booleanOperator.setValue(5);
		}));

		// entered value
		addEditBox(value = new ScreenComponentEditBox(13, 170, 134, 20, getFontRenderer()).setFilter(ScreenComponentEditBox.POSITIVE_DECIMAL).setMaxLength(30).setTextColor(Color.WHITE).setTextColorUneditable(Color.WHITE).setResponder(this::handleValue));

		playerInvLabel.setVisible(false);

	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		super.render(graphics, mouseX, mouseY, partialTicks);
		if (needsUpdate) {
			needsUpdate = false;
			TileCircuitMonitor monitor = menu.getSafeHost();
			if (monitor != null) {
				value.setValue("" + monitor.value.getValue());
			}
		}
	}

	private void handleValue(String val) {
		if (val == null || val.isEmpty()) {
			return;
		}

		TileCircuitMonitor monitor = menu.getSafeHost();

		if (monitor == null) {
			return;
		}

		double value = 0;

		try {
			value = Double.parseDouble(val);
		} catch (Exception e) {

		}

		monitor.value.setValue(value);

	}

	private static MutableComponent getPropertyLabel(int label) {
		return switch (label) {
		case 0 -> ElectroTextUtils.gui("networkwattage");
		case 1 -> ElectroTextUtils.gui("networkvoltage");
		case 2 -> ElectroTextUtils.gui("networkampacity");
		case 3 -> ElectroTextUtils.gui("networkminimumvoltage");
		case 4 -> ElectroTextUtils.gui("networkresistance");
		case 5 -> ElectroTextUtils.gui("networkload");

		default -> Component.empty();
		};
	}

	private static MutableComponent getOperatorLabel(int label) {
		return switch (label) {
		case 0 -> ElectroTextUtils.gui("equals");
		case 1 -> ElectroTextUtils.gui("notequals");
		case 2 -> ElectroTextUtils.gui("lessthan");
		case 3 -> ElectroTextUtils.gui("greaterthan");
		case 4 -> ElectroTextUtils.gui("lessthanorequalto");
		case 5 -> ElectroTextUtils.gui("greaterthanorequalto");

		default -> Component.empty();
		};
	}

	private static DisplayUnit getUnit(int label) {
		return switch (label) {
		case 0, 5 -> DisplayUnits.WATT;
		case 1, 3 -> DisplayUnits.VOLTAGE;
		case 2 -> DisplayUnits.AMPERE;
		case 4 -> DisplayUnits.RESISTANCE;

		default -> DisplayUnits.WATT;
		};
	}

}
