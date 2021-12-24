package electrodynamics.prefab.screen.component;

import java.awt.Rectangle;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.api.screen.component.TextPropertySupplier;
import electrodynamics.prefab.utilities.UtilitiesRendering;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ScreenComponentInfo extends ScreenComponent {
	public static final int SIZE = 26;
	protected TextPropertySupplier infoHandler;

	protected ScreenComponentInfo(TextPropertySupplier infoHandler, ResourceLocation resource, IScreenWrapper gui, int x, int y) {
		super(resource, gui, x, y);
		this.infoHandler = infoHandler;
	}

	@Override
	public Rectangle getBounds(int guiWidth, int guiHeight) {
		return new Rectangle(guiWidth + xLocation, guiHeight + yLocation, SIZE, SIZE);
	}

	@Override
	public void renderBackground(PoseStack stack, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		UtilitiesRendering.bindTexture(resource);

		gui.drawTexturedRect(stack, guiWidth + xLocation, guiHeight + yLocation, 0, 0, SIZE, SIZE);
	}

	@Override
	public void renderForeground(PoseStack stack, int xAxis, int yAxis) {
		if (isPointInRegion(xLocation + 3, yLocation + 3, xAxis, yAxis, 21, 20)) {
			displayTooltips(stack, getInfo(infoHandler.getInfo()), xAxis, yAxis);
		}
	}

	@Override
	public void preMouseClicked(double xAxis, double yAxis, int button) {

	}

	@Override
	public void mouseClicked(double xAxis, double yAxis, int button) {
		if (button == 0 && isPointInRegion(xLocation + 3, yLocation + 3, xAxis, yAxis, 21, 20)) {
			buttonClicked();
		}
	}

	@Override
	public void mouseClickMove(int mouseX, int mouseY, int button, long ticks) {

	}

	@Override
	public void mouseReleased(double x, double y, int type) {

	}

	@Override
	public void mouseWheel(double x, double y, double delta) {

	}

	protected abstract List<? extends FormattedCharSequence> getInfo(List<? extends FormattedCharSequence> list);

	protected void buttonClicked() {

	}
}