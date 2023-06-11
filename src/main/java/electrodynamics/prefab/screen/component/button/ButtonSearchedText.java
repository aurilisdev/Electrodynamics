package electrodynamics.prefab.screen.component.button;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

public class ButtonSearchedText extends ButtonSpecificPage {

	private Component chapter = Component.empty();
	private FormattedText line = Component.empty();
	
	public int specifiedPage;

	private boolean shouldShow = false;

	public ButtonSearchedText(int x, int y, int width, int page, OnPress onPress) {
		super(x, y, width, 20, page, Component.empty(), onPress);
	}

	@Override
	public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
		super.renderButton(pPoseStack, pMouseX, pMouseY, pPartialTick);
		Font font = Minecraft.getInstance().font;
		drawCenteredStringNoShadow(pPoseStack, font, chapter, this.x + this.width / 2, this.y - 10, TextWrapperObject.DEFAULT_COLOR);
		drawCenteredString(pPoseStack, font, Language.getInstance().getVisualOrder(line), this.x + this.width / 2, this.y + (this.height - 8) / 2, getFGColor() | Mth.ceil(this.alpha * 255.0F) << 24);

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

	public static void drawCenteredStringNoShadow(PoseStack pPoseStack, Font pFont, Component pText, int pX, int pY, int pColor) {
		FormattedCharSequence formattedcharsequence = pText.getVisualOrderText();
		pFont.draw(pPoseStack, formattedcharsequence, pX - pFont.width(formattedcharsequence) / 2, pY, pColor);
	}

}
