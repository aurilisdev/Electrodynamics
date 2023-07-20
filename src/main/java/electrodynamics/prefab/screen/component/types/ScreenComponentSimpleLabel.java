package electrodynamics.prefab.screen.component.types;

import java.util.function.Supplier;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.prefab.screen.component.AbstractScreenComponent;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.network.chat.Component;

public class ScreenComponentSimpleLabel extends AbstractScreenComponent {

	private Supplier<Component> text = Component::empty;
	public int color = RenderingUtils.WHITE;

	private float x;
	private float y;

	public ScreenComponentSimpleLabel(float x, float y, int height, int color, Component text) {
		this(x, y, height, color, () -> text);
	}

	public ScreenComponentSimpleLabel(float x, float y, int height, int color, Supplier<Component> text) {
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
	public void renderForeground(PoseStack stack, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		gui.getFontRenderer().draw(stack, text.get(), x, y, color);
	}

}
