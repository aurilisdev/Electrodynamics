package electrodynamics.prefab.screen.component.utils;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.api.screen.ITexture;
import electrodynamics.api.screen.component.IGuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractScreenComponent implements IGuiComponent {
	protected ITexture texture;
	protected IScreenWrapper gui;
	protected int xLocation;
	protected int yLocation;

	protected AbstractScreenComponent(ITexture texture, IScreenWrapper gui, int x, int y) {
		this.texture = texture;
		this.gui = gui;

		xLocation = x;
		yLocation = y;
	}

	public void displayTooltip(PoseStack stack, Component tooltip, int xAxis, int yAxis) {
		gui.displayTooltip(stack, tooltip, xAxis, yAxis);
	}

	public void displayTooltips(PoseStack stack, List<? extends FormattedCharSequence> tooltips, int xAxis, int yAxis) {
		gui.displayTooltips(stack, tooltips, xAxis, yAxis);
	}

	public void renderScaledText(PoseStack stack, String text, int x, int y, int color, int maxX) {
		int length = gui.getFontRenderer().width(text);

		if (length <= maxX) {
			gui.getFontRenderer().draw(stack, text, x, y, color);
		} else {
			float scale = (float) maxX / length;
			float reverse = 1 / scale;
			float yAdd = 4 - scale * 8 / 2F;

			stack.pushPose();

			stack.scale(scale, scale, scale);
			gui.getFontRenderer().draw(stack, text, (int) (x * reverse), (int) (y * reverse + yAdd), color);

			stack.popPose();
		}
	}

	protected boolean isPointInRegion(int x, int y, double xAxis, double yAxis, int width, int height) {
		return xAxis >= x && xAxis <= x + width - 1 && yAxis >= y && yAxis <= y + height - 1;
	}

}