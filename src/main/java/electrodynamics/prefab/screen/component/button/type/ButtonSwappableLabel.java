package electrodynamics.prefab.screen.component.button.type;

import java.util.function.Supplier;

import electrodynamics.api.screen.ITexture;
import electrodynamics.prefab.screen.component.button.ScreenComponentButton;
import net.minecraft.util.text.ITextComponent;

public class ButtonSwappableLabel extends ScreenComponentButton<ButtonSwappableLabel> {

	private final ITextComponent labelOn;
	private final Supplier<Boolean> initialToggleStatus;

	private Boolean toggle;

	public ButtonSwappableLabel(int xLoc, int yLoc, int length, int width, ITextComponent labelOff, ITextComponent labelOn, Supplier<Boolean> initialStatus) {
		super(xLoc, yLoc, length, width);
		this.label = labelOff;
		this.labelOn = labelOn;
		initialToggleStatus = initialStatus;
	}

	public ButtonSwappableLabel(ITexture texture, int xLoc, int yLoc, ITextComponent labelOff, ITextComponent labelOn, Supplier<Boolean> initialStatus) {
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
	public ITextComponent getLabel() {
		if (toggle == null) {
			toggle = initialToggleStatus.get();
		}
		if (toggle) {
			return labelOn;
		}
		return super.getLabel();
	}

}
