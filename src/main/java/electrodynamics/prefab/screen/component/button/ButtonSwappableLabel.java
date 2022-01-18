package electrodynamics.prefab.screen.component.button;

import java.util.function.Supplier;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class ButtonSwappableLabel extends Button {

	private Component labelOff;
	private Component labelOn;

	Supplier<Boolean> initialToggleStatus;

	private Boolean toggle;

	public ButtonSwappableLabel(int xLoc, int yLoc, int length, int width, Component labelOff, Component labelOn, Supplier<Boolean> initialStatus,
			OnPress onPressed) {
		super(xLoc, yLoc, length, width, labelOff, onPressed);
		this.labelOff = labelOff;
		this.labelOn = labelOn;
		initialToggleStatus = initialStatus;
	}

	public ButtonSwappableLabel(int xLoc, int yLoc, int length, int width, Component labelOff, Component labelOn, Supplier<Boolean> initialStatus,
			OnPress onPressed, OnTooltip tooltip) {
		super(xLoc, yLoc, length, width, labelOff, onPressed, tooltip);
		this.labelOff = labelOff;
		this.labelOn = labelOn;
		initialToggleStatus = initialStatus;
	}

	@Override
	public void onPress() {
		super.onPress();
		toggle = !toggle;
	}

	@Override
	public Component getMessage() {
		if (toggle == null) {
			toggle = initialToggleStatus.get();
		}
		if (Boolean.TRUE.equals(toggle)) {
			return labelOn;
		}
		return labelOff;
	}

}
