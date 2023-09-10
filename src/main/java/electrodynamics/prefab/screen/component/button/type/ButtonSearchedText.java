package electrodynamics.prefab.screen.component.button.type;

import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;

//height was 20
public class ButtonSearchedText extends ButtonSpecificPage {

	private Component chapter = Component.empty();
	private FormattedText line = Component.empty();

	public int specifiedPage;

	private boolean shouldShow = false;

	public ButtonSearchedText(int x, int y, int width, int height, int page) {
		super(x, y, width, height, page);
	}

	@Override
	public void renderBackground(GuiGraphics graphics, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		super.renderBackground(graphics, xAxis, yAxis, guiWidth, guiHeight);
		drawCenteredStringNoShadow(graphics, gui.getFontRenderer(), chapter.getVisualOrderText(), this.xLocation + this.width / 2 + guiWidth, this.yLocation + guiHeight - 10, TextWrapperObject.DEFAULT_COLOR.color());
		drawCenteredStringNoShadow(graphics, gui.getFontRenderer(), Language.getInstance().getVisualOrder(line), this.xLocation + guiWidth + this.width / 2, this.yLocation + guiHeight + (this.height - 8) / 2, color.color());

	}

	public void setChapter(Component chapter) {
		this.chapter = chapter;
	}

	public void setLine(FormattedText line) {
		this.line = line;
	}

	public void setPage(int page) {
		specifiedPage = page;
	}

	public void setShouldShow(boolean show) {
		shouldShow = show;
	}

	@Override
	public boolean isVisible() {
		return super.isVisible() && shouldShow;
	}

	public static void drawCenteredStringNoShadow(GuiGraphics graphics, Font font, FormattedCharSequence text, int pX, int pY, int pColor) {
		graphics.drawString(font, text, pX - font.width(text) / 2, pY, pColor, false);
	}

}
