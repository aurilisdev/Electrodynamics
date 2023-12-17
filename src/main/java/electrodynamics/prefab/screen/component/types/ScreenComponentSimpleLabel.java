package electrodynamics.prefab.screen.component.types;

import java.util.function.Supplier;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.prefab.screen.component.AbstractScreenComponent;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.util.text.ITextComponent;

public class ScreenComponentSimpleLabel extends AbstractScreenComponent {

	private Supplier<ITextComponent> text = ElectroTextUtils::empty;
	public int color = Color.WHITE.color();

	public ScreenComponentSimpleLabel(int x, int y, int height, int color, ITextComponent text) {
		this(x, y, height, color, () -> text);
	}

	public ScreenComponentSimpleLabel(int x, int y, int height, int color, Supplier<ITextComponent> text) {
		super(x, y, 0, height);
		this.text = text;
		this.color = color;
	}

	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		return isPointInRegion(x, y, mouseX - gui.getGuiWidth(), mouseY - gui.getGuiHeight(), gui.getFontRenderer().width(text.get()), height);
	}

	@Override
	public void renderForeground(MatrixStack stack, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		if (isVisible()) {
			gui.getFontRenderer().draw(stack, text.get(), x, y, color);
		}
	}

}
