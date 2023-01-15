package electrodynamics.prefab.screen.component.utils;

import java.util.Collections;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.api.screen.ITexture;
import electrodynamics.api.screen.component.TextPropertySupplier;
import electrodynamics.prefab.screen.component.ScreenComponentGeneric;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractScreenComponentInfo extends ScreenComponentGeneric {
	public static final int SIZE = 26;
	protected TextPropertySupplier infoHandler;
	
	public static final TextPropertySupplier EMPTY = Collections::emptyList;

	public AbstractScreenComponentInfo(ITexture texture, TextPropertySupplier infoHandler, IScreenWrapper gui, int x, int y) {
		super(texture, gui, x, y);
		this.infoHandler = infoHandler;
	}

	@Override
	public void renderForeground(PoseStack stack, int xAxis, int yAxis) {
		if (isPointInRegion(xLocation, yLocation, xAxis, yAxis, texture.textureWidth(), texture.textureHeight())) {
			displayTooltips(stack, getInfo(infoHandler.getInfo()), xAxis, yAxis);
		}
	}

	@Override
	public void preMouseClicked(double xAxis, double yAxis, int button) {

	}

	@Override
	public void mouseClicked(double xAxis, double yAxis, int button) {
		if (button == 0 && isPointInRegion(xLocation, yLocation, xAxis, yAxis, texture.textureWidth(), texture.textureHeight())) {
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