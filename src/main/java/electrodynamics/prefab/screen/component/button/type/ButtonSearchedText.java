package electrodynamics.prefab.screen.component.button.type;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;

//height was 20
public class ButtonSearchedText extends ButtonSpecificPage {

	private ITextComponent chapter = ElectroTextUtils.empty();
	private ITextProperties line = ElectroTextUtils.empty();

	public int specifiedPage;

	private boolean shouldShow = false;

	public ButtonSearchedText(int x, int y, int width, int height, int page) {
		super(x, y, width, height, page);
	}

	@Override
	public void renderBackground(MatrixStack stack, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		super.renderBackground(stack, xAxis, yAxis, guiWidth, guiHeight);
		drawCenteredStringNoShadow(stack, gui.getFontRenderer(), chapter.getContents(), this.x + this.width / 2 + guiWidth, this.y + guiHeight - 10, TextWrapperObject.DEFAULT_COLOR.color());
		drawCenteredStringNoShadow(stack, gui.getFontRenderer(), line.getString(), this.x + guiWidth + this.width / 2, this.y + guiHeight + (this.height - 8) / 2, color.color());

	}

	public void setChapter(ITextComponent chapter) {
		this.chapter = chapter;
	}

	public void setLine(ITextProperties line) {
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

	public static void drawCenteredStringNoShadow(MatrixStack stack, FontRenderer font, String text, int pX, int pY, int pColor) {
		drawString(stack, font, text, pX - font.width(text) / 2, pY, pColor);
	}

}
