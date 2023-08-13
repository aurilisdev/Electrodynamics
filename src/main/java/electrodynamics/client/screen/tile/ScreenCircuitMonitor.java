package electrodynamics.client.screen.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.inventory.container.tile.ContainerCircuitMonitor;
import electrodynamics.common.tile.generators.TileCreativePowerSource;
import electrodynamics.common.tile.network.electric.TileCircuitMonitor;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.AbstractScreenComponent;
import electrodynamics.prefab.screen.component.button.ScreenComponentButton;
import electrodynamics.prefab.screen.component.editbox.ScreenComponentEditBox;
import electrodynamics.prefab.screen.component.types.ScreenComponentMultiLabel;
import electrodynamics.prefab.screen.component.types.ScreenComponentSimpleLabel;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenCircuitMonitor extends GenericScreen<ContainerCircuitMonitor> {

	private ScreenComponentEditBox value;
	
	private boolean needsUpdate = true;
	
	public ScreenCircuitMonitor(ContainerCircuitMonitor container, Inventory inv, Component title) {
		super(container, inv, title);

		imageHeight += 40;

		addComponent(new ScreenComponentMultiLabel(0, 0, stack -> {

			TileCircuitMonitor monitor = menu.getHostFromIntArray();

			if (monitor == null) {
				return;
			}

			Component operatorLabel = getOperatorLabel(monitor.booleanOperator.get()).copy().withStyle(ChatFormatting.BOLD);

			int width = getFontRenderer().width(operatorLabel);

			font.draw(stack, operatorLabel, (float) ((imageWidth - width) / 2.0), 22, 0);

			Component propertyLabel = getPropertyLabel(monitor.networkProperty.get()).copy().withStyle(ChatFormatting.BOLD);

			font.draw(stack, propertyLabel, 13, 22, 0);
			
			DisplayUnit units = getUnit(monitor.networkProperty.get());
			
			Component value = ChatFormatter.getChatDisplayShort(monitor.value.get(), units).withStyle(ChatFormatting.BOLD);
			
			font.draw(stack, value, 163 - font.width(value), 22, 0);
			
			Component symbol = units.getSymbol();
			
			font.draw(stack, symbol, 163 - font.width(symbol), 175, 4210752);

		}));

		addComponent(new ScreenComponentSimpleLabel(13, 38, 10, 4210752, ElectroTextUtils.gui("property")));
		addComponent(new ScreenComponentSimpleLabel(13, 118, 10, 4210752, ElectroTextUtils.gui("operator")));
		addComponent(new ScreenComponentSimpleLabel(13, 158, 10, 4210752, ElectroTextUtils.gui("value")));

		// network value

		addComponent(new ScreenComponentButton<>(13, 50, 70, 20).setLabel(getPropertyLabel(0)).setOnPress(button -> {
			TileCircuitMonitor monitor = menu.getHostFromIntArray();
			if (monitor == null) {
				return;
			}
			monitor.networkProperty.set(0);
			monitor.networkProperty.updateServer();
		}));
		addComponent(new ScreenComponentButton<>(13, 70, 70, 20).setLabel(getPropertyLabel(1)).setOnPress(button -> {
			TileCircuitMonitor monitor = menu.getHostFromIntArray();
			if (monitor == null) {
				return;
			}
			monitor.networkProperty.set(1);
			monitor.networkProperty.updateServer();
		}));
		addComponent(new ScreenComponentButton<>(13, 90, 70, 20).setLabel(getPropertyLabel(2)).setOnPress(button -> {
			TileCircuitMonitor monitor = menu.getHostFromIntArray();
			if (monitor == null) {
				return;
			}
			monitor.networkProperty.set(2);
			monitor.networkProperty.updateServer();
		}));
		addComponent(new ScreenComponentButton<>(93, 50, 70, 20).setLabel(getPropertyLabel(3)).setOnPress(button -> {
			TileCircuitMonitor monitor = menu.getHostFromIntArray();
			if (monitor == null) {
				return;
			}
			monitor.networkProperty.set(3);
			monitor.networkProperty.updateServer();
		}));
		addComponent(new ScreenComponentButton<>(93, 70, 70, 20).setLabel(getPropertyLabel(4)).setOnPress(button -> {
			TileCircuitMonitor monitor = menu.getHostFromIntArray();
			if (monitor == null) {
				return;
			}
			monitor.networkProperty.set(4);
			monitor.networkProperty.updateServer();
		}));
		addComponent(new ScreenComponentButton<>(93, 90, 70, 20).setLabel(getPropertyLabel(5)).setOnPress(button -> {
			TileCircuitMonitor monitor = menu.getHostFromIntArray();
			if (monitor == null) {
				return;
			}
			monitor.networkProperty.set(5);
			monitor.networkProperty.updateServer();
		}));

		// boolean operator

		addComponent(new ScreenComponentButton<>(13, 130, 20, 20).setLabel(getOperatorLabel(0)).setOnPress(button -> {
			TileCircuitMonitor monitor = menu.getHostFromIntArray();
			if (monitor == null) {
				return;
			}
			monitor.booleanOperator.set(0);
			monitor.booleanOperator.updateServer();
		}));
		addComponent(new ScreenComponentButton<>(39, 130, 20, 20).setLabel(getOperatorLabel(1)).setOnPress(button -> {
			TileCircuitMonitor monitor = menu.getHostFromIntArray();
			if (monitor == null) {
				return;
			}
			monitor.booleanOperator.set(1);
			monitor.booleanOperator.updateServer();
		}));
		addComponent(new ScreenComponentButton<>(65, 130, 20, 20).setLabel(getOperatorLabel(2)).setOnPress(button -> {
			TileCircuitMonitor monitor = menu.getHostFromIntArray();
			if (monitor == null) {
				return;
			}
			monitor.booleanOperator.set(2);
			monitor.booleanOperator.updateServer();
		}));
		addComponent(new ScreenComponentButton<>(91, 130, 20, 20).setLabel(getOperatorLabel(3)).setOnPress(button -> {
			TileCircuitMonitor monitor = menu.getHostFromIntArray();
			if (monitor == null) {
				return;
			}
			monitor.booleanOperator.set(3);
			monitor.booleanOperator.updateServer();
		}));
		addComponent(new ScreenComponentButton<>(117, 130, 20, 20).setLabel(getOperatorLabel(4)).setOnPress(button -> {
			TileCircuitMonitor monitor = menu.getHostFromIntArray();
			if (monitor == null) {
				return;
			}
			monitor.booleanOperator.set(4);
			monitor.booleanOperator.updateServer();
		}));
		addComponent(new ScreenComponentButton<>(143, 130, 20, 20).setLabel(getOperatorLabel(5)).setOnPress(button -> {
			TileCircuitMonitor monitor = menu.getHostFromIntArray();
			if (monitor == null) {
				return;
			}
			monitor.booleanOperator.set(5);
			monitor.booleanOperator.updateServer();
		}));

		// entered value
		addEditBox(value = new ScreenComponentEditBox(13, 170, 134, 20, getFontRenderer()).setFilter(ScreenComponentEditBox.POSITIVE_DECIMAL).setMaxLength(30).setTextColor(-1).setTextColorUneditable(-1).setResponder(this::handleValue));

	}

	@Override
	protected void renderLabels(PoseStack stack, int mouseX, int mouseY) {
		this.font.draw(stack, this.title, (float) this.titleLabelX, (float) this.titleLabelY, 4210752);
		int guiWidth = (int) getGuiWidth();
		int guiHeight = (int) getGuiHeight();
		int xAxis = mouseX - guiWidth;
		int yAxis = mouseY - guiHeight;
		for (AbstractScreenComponent component : getComponents()) {
			component.renderForeground(stack, xAxis, yAxis, guiWidth, guiHeight);
		}
	}
	
	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		if (needsUpdate) {
			needsUpdate = false;
			TileCircuitMonitor monitor = menu.getHostFromIntArray();
			if (monitor != null) {
				value.setValue("" + monitor.value.get());
			}
		}
	}
	
	private void handleValue(String val) {
		if(val == null || val.isEmpty()) {
			return;
		}
		
		TileCircuitMonitor monitor = menu.getHostFromIntArray();
		
		if(monitor == null) {
			return;
		}
		
		double value = 0;
		
		try {
			value = Double.parseDouble(val);
		} catch (Exception e) {
			
		}
		
		monitor.value.set(value);
		monitor.value.updateServer();
		
	}

	private Component getPropertyLabel(int label) {
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

	private Component getOperatorLabel(int label) {
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

	private DisplayUnit getUnit(int label) {
		return switch (label) {
		case 0, 5 -> DisplayUnit.WATT;
		case 1, 3 -> DisplayUnit.VOLTAGE;
		case 2 -> DisplayUnit.AMPERE;
		case 4 -> DisplayUnit.RESISTANCE;

		default -> DisplayUnit.WATT;
		};
	}

}
