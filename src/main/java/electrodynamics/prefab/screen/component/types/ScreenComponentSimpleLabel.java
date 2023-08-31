package electrodynamics.prefab.screen.component.types;

import java.util.function.Supplier;

import electrodynamics.prefab.screen.component.AbstractScreenComponent;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class ScreenComponentSimpleLabel extends AbstractScreenComponent {

	private Supplier<Component> text = Component::empty;
	public int color = RenderingUtils.WHITE;

	private final int x;
	private final int y;

	public ScreenComponentSimpleLabel(int x, int y, int height, int color, Component text) {
		this(x, y, height, color, () -> text);
	}

	public ScreenComponentSimpleLabel(int x, int y, int height, int color, Supplier<Component> text) {
		super((int) x, (int) y, 0, height);
		this.x = x;
		this.y = y;
		this.text = text;
		this.color = color;
	}

	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		return isPointInRegion(xLocation, yLocation, mouseX - gui.getGuiWidth(), mouseY - gui.getGuiHeight(), gui.getFontRenderer().width(text.get()), height);
	}

	@Override
	public void renderForeground(GuiGraphics graphics, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		graphics.drawString(gui.getFontRenderer(), text.get(), x, y, color, false);
	}

}
