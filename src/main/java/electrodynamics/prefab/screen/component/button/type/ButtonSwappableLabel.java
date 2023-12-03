package electrodynamics.prefab.screen.component.button.type;

import java.util.function.Supplier;

import electrodynamics.api.screen.ITexture;
import electrodynamics.prefab.screen.component.button.ScreenComponentButton;
import net.minecraft.network.chat.Component;

public class ButtonSwappableLabel extends ScreenComponentButton<ButtonSwappableLabel> {

	private final Component labelOn;
	private final Supplier<Boolean> initialToggleStatus;

	private Boolean toggle;

	public ButtonSwappableLabel(int xLoc, int yLoc, int length, int width, Component labelOff, Component labelOn, Supplier<Boolean> initialStatus) {
		super(xLoc, yLoc, length, width);
		this.label = labelOff;
		this.labelOn = labelOn;
		initialToggleStatus = initialStatus;
	}

	public ButtonSwappableLabel(ITexture texture, int xLoc, int yLoc, Component labelOff, Component labelOn, Supplier<Boolean> initialStatus) {
		super(texture, xLoc, yLoc);
		this.label = labelOff;
		this.labelOn = labelOn;
		initialToggleStatus = initialStatus;
	}

	@Override
	public void onPress() {
		super.onPress();
		toggle = !toggle;
	}

	@Override
	public Component getLabel() {
		if (toggle == null) {
			toggle = initialToggleStatus.get();
		}
		if (toggle) {
			return labelOn;
		}
		return super.getLabel();
	}

}
