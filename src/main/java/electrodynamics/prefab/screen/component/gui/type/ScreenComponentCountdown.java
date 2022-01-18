package electrodynamics.prefab.screen.component.gui.type;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleSupplier;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.References;
import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.prefab.screen.component.gui.ScreenComponentInfo;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

public class ScreenComponentCountdown extends ScreenComponentInfo {

	private static final int XLOC_BORDER = 0;
	private static final int YLOC_BORDER = 0;

	private static final int XLOC_BAR = 0;
	private static final int YLOC_BAR = 12;

	private static final int LENGTH_BORDER = 60;
	private static final int WIDTH_BORDER = 12;

	private static final int LENGTH_BAR = 58;
	private static final int WIDTH_BAR = 10;

	private final DoubleSupplier progressInfoHandler;
	private final String langKey;

	private static final String TEXTURE_LOC = ":textures/screen/component/countdown.png";

	public ScreenComponentCountdown(final DoubleSupplier progressInfoHandler, final IScreenWrapper gui, final int x, final int y,
			final String langKey) {
		super(null, new ResourceLocation(References.ID + TEXTURE_LOC), gui, x, y);
		this.progressInfoHandler = progressInfoHandler;
		infoHandler = this::getTooltips;
		this.langKey = langKey;
	}

	@Override
	public Rectangle getBounds(final int guiWidth, final int guiHeight) {
		return new Rectangle(guiWidth + xLocation, guiHeight + yLocation, LENGTH_BORDER, WIDTH_BORDER);
	}

	@Override
	public void renderForeground(PoseStack stack, int xAxis, int yAxis) {
		if (isPointInRegion(xLocation, yLocation, xAxis, yAxis, LENGTH_BORDER, WIDTH_BORDER)) {
			displayTooltips(stack, getInfo(infoHandler.getInfo()), xAxis, yAxis);
		}
	}

	@Override
	public void renderBackground(PoseStack stack, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		RenderingUtils.bindTexture(resource);
		gui.drawTexturedRect(stack, guiWidth + xLocation, guiHeight + yLocation, XLOC_BORDER, YLOC_BORDER, LENGTH_BORDER, WIDTH_BORDER);
		int lengthBar = (int) (progressInfoHandler.getAsDouble() * LENGTH_BAR);
		gui.drawTexturedRect(stack, guiWidth + xLocation + 1, guiHeight + yLocation + 1, XLOC_BAR, YLOC_BAR, lengthBar, WIDTH_BAR);

	}

	@Override
	protected List<? extends FormattedCharSequence> getInfo(List<? extends FormattedCharSequence> list) {
		return getTooltips();
	}

	private List<? extends FormattedCharSequence> getTooltips() {
		List<FormattedCharSequence> tips = new ArrayList<>();
		if (progressInfoHandler != null) {
			tips.add(new TranslatableComponent(langKey, (int) (100 * progressInfoHandler.getAsDouble()) + "%").withStyle(ChatFormatting.GRAY)
					.getVisualOrderText());
		}
		return tips;
	}

}
