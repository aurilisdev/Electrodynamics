package electrodynamics.prefab.screen.component.editbox;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import electrodynamics.api.References;
import electrodynamics.api.screen.ITexture;
import electrodynamics.prefab.screen.component.types.ScreenComponentGeneric;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
/**
 * A modified variant of the EditBox class integrated into the Electrodynamics render system and fixing certain issues with the
 * vanilla-provided class
 * 
 * That's a spicy copy-pasta
 * 
 * @author skip999
 */
public class ScreenComponentEditBox extends ScreenComponentGeneric {

	public static final ResourceLocation TEXTURE = new ResourceLocation(References.ID + ":textures/screen/component/textinputbar.png");

	public static final char[] POSITIVE_DECIMAL = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.' };
	public static final char[] DECIMAL = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-' };

	public static final char[] POSITIVE_INTEGER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	public static final char[] INTEGER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-' };

	public static final int BACKWARDS = -1;
	public static final int FORWARDS = 1;
	private static final int CURSOR_INSERT_WIDTH = 1;
	private static final int CURSOR_INSERT_COLOR = -3092272;
	private static final String CURSOR_APPEND_CHARACTER = "_";
	public static final int DEFAULT_TEXT_COLOR = 14737632;
	private static final int BORDER_COLOR_FOCUSED = -1;
	private static final int BORDER_COLOR = -6250336;
	private static final int BACKGROUND_COLOR = -16777216;
	private final Font font;
	/** Has the current text being edited on the textbox. */
	private String value = "";
	private int maxLength = 32;
	private int frame;
	/** if true the textbox can lose focus by clicking elsewhere on the screen */
	private boolean canLoseFocus = true;
	/** If this value is true along with isFocused, keyTyped will process the keys. */
	private boolean isEditable = true;
	private boolean shiftPressed;
	/** The current character index that should be used as start of the rendered text. */
	private int displayPos;
	private int cursorPos;
	/** other selection position, maybe the same as the cursor */
	private int highlightPos;
	private int textColor = 14737632;
	private int textColorUneditable = 7368816;
	@Nullable
	private String suggestion;
	@Nullable
	private Consumer<String> responder;
	/** Called to check if the text is valid */
	private Predicate<String> filter = Objects::nonNull;
	private BiFunction<String, Integer, FormattedCharSequence> formatter = (p_94147_, p_94148_) -> {
		return FormattedCharSequence.forward(p_94147_, Style.EMPTY);
	};

	public ScreenComponentEditBox(int x, int y, int width, int height, Font font) {
		super(x, y, width, height);
		texture = TextInputTextures.TEXT_INPUT_BASE;
		this.font = font;
	}

	public ScreenComponentEditBox setResponder(Consumer<String> responder) {
		this.responder = responder;
		return this;
	}

	public ScreenComponentEditBox setFormatter(BiFunction<String, Integer, FormattedCharSequence> textFormatter) {
		this.formatter = textFormatter;
		return this;
	}

	/**
	 * Increments the cursor counter
	 */
	public void tick() {
		++this.frame;
	}

	/**
	 * Sets the text of the textbox, and moves the cursor to the end.
	 */
	public ScreenComponentEditBox setValue(String text) {
		if (this.filter.test(text)) {
			if (text.length() > this.maxLength) {
				this.value = text.substring(0, this.maxLength);
			} else {
				this.value = text;
			}

			this.moveCursorToEnd();
			this.setHighlightPos(this.cursorPos);
			this.onValueChange(text);
		}
		return this;
	}

	/**
	 * Returns the contents of the textbox
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * Returns the text between the cursor and selectionEnd.
	 */
	public String getHighlighted() {
		int i = Math.min(this.cursorPos, this.highlightPos);
		int j = Math.max(this.cursorPos, this.highlightPos);
		return this.value.substring(i, j);
	}

	public ScreenComponentEditBox setFilter(Predicate<String> validator) {
		this.filter = validator;
		return this;
	}
	
	public ScreenComponentEditBox setFilter(char[] validChars) {
		return setFilter(getValidator(validChars));
	}

	/**
	 * Adds the given text after the cursor, or replaces the currently selected text if there is a selection.
	 */
	public void insertText(String textToWrite) {
		int i = Math.min(this.cursorPos, this.highlightPos);
		int j = Math.max(this.cursorPos, this.highlightPos);
		int k = this.maxLength - this.value.length() - (i - j);
		String s = SharedConstants.filterText(textToWrite);
		int l = s.length();
		if (k < l) {
			s = s.substring(0, k);
			l = k;
		}

		String s1 = (new StringBuilder(this.value)).replace(i, j, s).toString();
		if (this.filter.test(s1)) {
			this.value = s1;
			this.setCursorPosition(i + l);
			this.setHighlightPos(this.cursorPos);
			this.onValueChange(this.value);
		}
	}

	private void onValueChange(String newText) {
		if (this.responder != null) {
			this.responder.accept(newText);
		}

	}

	private void deleteText(int count) {
		if (Screen.hasControlDown()) {
			this.deleteWords(count);
		} else {
			this.deleteChars(count);
		}

	}

	/**
	 * Deletes the given number of words from the current cursor's position, unless there is currently a selection, in which case the
	 * selection is deleted instead.
	 */
	public void deleteWords(int num) {
		if (!this.value.isEmpty()) {
			if (this.highlightPos != this.cursorPos) {
				this.insertText("");
			} else {
				this.deleteChars(this.getWordPosition(num) - this.cursorPos);
			}
		}
	}

	/**
	 * Deletes the given number of characters from the current cursor's position, unless there is currently a selection, in which case
	 * the selection is deleted instead.
	 */
	public void deleteChars(int pNum) {
		if (!this.value.isEmpty()) {
			if (this.highlightPos != this.cursorPos) {
				this.insertText("");
			} else {
				int i = this.getCursorPos(pNum);
				int j = Math.min(i, this.cursorPos);
				int k = Math.max(i, this.cursorPos);
				if (j != k) {
					String s = (new StringBuilder(this.value)).delete(j, k).toString();
					if (this.filter.test(s)) {
						this.value = s;
						this.moveCursorTo(j);
					}
				}
			}
		}
	}

	/**
	 * Gets the starting index of the word at the specified number of words away from the cursor position.
	 */
	public int getWordPosition(int pNumWords) {
		return this.getWordPosition(pNumWords, this.getCursorPosition());
	}

	/**
	 * Gets the starting index of the word at a distance of the specified number of words away from the given position.
	 */
	private int getWordPosition(int pN, int pPos) {
		return this.getWordPosition(pN, pPos, true);
	}

	/**
	 * Like getNthWordFromPos (which wraps this), but adds option for skipping consecutive spaces
	 */
	private int getWordPosition(int pN, int pPos, boolean pSkipWs) {
		int i = pPos;
		boolean flag = pN < 0;
		int j = Math.abs(pN);

		for (int k = 0; k < j; ++k) {
			if (!flag) {
				int l = this.value.length();
				i = this.value.indexOf(32, i);
				if (i == -1) {
					i = l;
				} else {
					while (pSkipWs && i < l && this.value.charAt(i) == ' ') {
						++i;
					}
				}
			} else {
				while (pSkipWs && i > 0 && this.value.charAt(i - 1) == ' ') {
					--i;
				}

				while (i > 0 && this.value.charAt(i - 1) != ' ') {
					--i;
				}
			}
		}

		return i;
	}

	/**
	 * Moves the text cursor by a specified number of characters and clears the selection
	 */
	public void moveCursor(int pDelta) {
		this.moveCursorTo(this.getCursorPos(pDelta));
	}

	private int getCursorPos(int pDelta) {
		return Util.offsetByCodepoints(this.value, this.cursorPos, pDelta);
	}

	/**
	 * Sets the current position of the cursor.
	 */
	public void moveCursorTo(int pPos) {
		this.setCursorPosition(pPos);
		if (!this.shiftPressed) {
			this.setHighlightPos(this.cursorPos);
		}

		this.onValueChange(this.value);
	}

	public void setCursorPosition(int pPos) {
		this.cursorPos = Mth.clamp(pPos, 0, this.value.length());
	}

	/**
	 * Moves the cursor to the very start of this text box.
	 */
	public void moveCursorToStart() {
		this.moveCursorTo(0);
	}

	/**
	 * Moves the cursor to the very end of this text box.
	 */
	public void moveCursorToEnd() {
		this.moveCursorTo(this.value.length());
	}

	@Override
	public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
		if (!this.canConsumeInput()) {
			return false;
		} else {
			this.shiftPressed = Screen.hasShiftDown();
			if (Screen.isSelectAll(pKeyCode)) {
				this.moveCursorToEnd();
				this.setHighlightPos(0);
				return true;
			} else if (Screen.isCopy(pKeyCode)) {
				Minecraft.getInstance().keyboardHandler.setClipboard(this.getHighlighted());
				return true;
			} else if (Screen.isPaste(pKeyCode)) {
				if (this.isEditable) {
					this.insertText(Minecraft.getInstance().keyboardHandler.getClipboard());
				}

				return true;
			} else if (Screen.isCut(pKeyCode)) {
				Minecraft.getInstance().keyboardHandler.setClipboard(this.getHighlighted());
				if (this.isEditable) {
					this.insertText("");
				}

				return true;
			} else {
				switch (pKeyCode) {
				case 259:
					if (this.isEditable) {
						this.shiftPressed = false;
						this.deleteText(-1);
						this.shiftPressed = Screen.hasShiftDown();
					}

					return true;
				case 260:
				case 264:
				case 265:
				case 266:
				case 267:
				default:
					return false;
				case 261:
					if (this.isEditable) {
						this.shiftPressed = false;
						this.deleteText(1);
						this.shiftPressed = Screen.hasShiftDown();
					}

					return true;
				case 262:
					if (Screen.hasControlDown()) {
						this.moveCursorTo(this.getWordPosition(1));
					} else {
						this.moveCursor(1);
					}

					return true;
				case 263:
					if (Screen.hasControlDown()) {
						this.moveCursorTo(this.getWordPosition(-1));
					} else {
						this.moveCursor(-1);
					}

					return true;
				case 268:
					this.moveCursorToStart();
					return true;
				case 269:
					this.moveCursorToEnd();
					return true;
				}
			}
		}
	}

	public boolean canConsumeInput() {
		return this.isVisible() && this.isFocused() && this.isEditable();
	}

	@Override
	public boolean charTyped(char pCodePoint, int pModifiers) {
		if (!this.canConsumeInput()) {
			return false;
		} else if (SharedConstants.isAllowedChatCharacter(pCodePoint)) {
			if (this.isEditable) {
				this.insertText(Character.toString(pCodePoint));
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (!this.isVisible()) {
			return false;
		} else {
			boolean mouseOver = isMouseOver(mouseX, mouseY);
			if (this.canLoseFocus) {
				this.setFocus(mouseOver);
			}

			if (this.isFocused() && mouseOver && button == 0) {
				int i = Mth.floor(mouseX) - this.xLocation - ((int) gui.getGuiWidth()) - 4;

				String s = this.font.plainSubstrByWidth(this.value.substring(this.displayPos), this.getInnerWidth());
				this.moveCursorTo(this.font.plainSubstrByWidth(s, i).length() + this.displayPos);
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Sets focus to this gui element
	 */
	public ScreenComponentEditBox setFocus(boolean isFocused) {
		this.setFocused(isFocused);
		return this;
	}

	@Override
	public void renderBackground(PoseStack stack, int xAxis, int yAxis, int guiWidth, int guiHeight) {

		RenderingUtils.bindTexture(texture.getLocation());

		drawExpandedBox(stack, this.xLocation + guiWidth, this.yLocation + guiHeight, width, height);

		int i2 = this.isEditable ? this.textColor : this.textColorUneditable;
		int j = this.cursorPos - this.displayPos;
		int k = this.highlightPos - this.displayPos;
		String s = this.font.plainSubstrByWidth(this.value.substring(this.displayPos), this.getInnerWidth());
		boolean flag = j >= 0 && j <= s.length();
		boolean flag1 = this.isFocused() && this.frame / 6 % 2 == 0 && flag;
		int l = this.xLocation + guiWidth + 4;
		int i1 = this.yLocation + guiHeight + (this.height - 8) / 2;
		int j1 = l;
		if (k > s.length()) {
			k = s.length();
		}

		if (!s.isEmpty()) {
			String s1 = flag ? s.substring(0, j) : s;
			j1 = this.font.drawShadow(stack, this.formatter.apply(s1, this.displayPos), (float) l, (float) i1, i2);
		}

		boolean flag2 = this.cursorPos < this.value.length() || this.value.length() >= this.getMaxLength();
		int k1 = j1;
		if (!flag) {
			k1 = j > 0 ? l + this.width : l;
		} else if (flag2) {
			k1 = j1 - 1;
			--j1;
		}

		if (!s.isEmpty() && flag && j < s.length()) {
			this.font.drawShadow(stack, this.formatter.apply(s.substring(j), this.cursorPos), (float) j1, (float) i1, i2);
		}

		if (!flag2 && this.suggestion != null) {
			this.font.drawShadow(stack, this.suggestion, (float) (k1 - 1), (float) i1, -8355712);
		}

		if (flag1) {
			if (flag2) {
				GuiComponent.fill(stack, k1, i1 - 1, k1 + 1, i1 + 1 + 9, -3092272);
			} else {
				this.font.drawShadow(stack, "_", (float) k1, (float) i1, i2);
			}
		}

		if (k != j) {
			int l1 = l + this.font.width(s.substring(0, k));
			this.renderHighlight(k1, i1 - 1, l1 - 1, i1 + 1 + 9);
		}

	}

	/**
	 * Draws the blue selection box.
	 */
	private void renderHighlight(int startX, int startY, int endX, int endY) {
		if (startX < endX) {
			int i = startX;
			startX = endX;
			endX = i;
		}

		if (startY < endY) {
			int j = startY;
			startY = endY;
			endY = j;
		}

		if (endX > this.xLocation + this.width + gui.getGuiWidth()) {
			endX = (int) (this.xLocation + this.width + gui.getGuiWidth());
		}

		if (startX > this.xLocation + this.width + gui.getGuiHeight()) {
			startX = (int) (this.xLocation + this.width + gui.getGuiHeight());
		}

		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tesselator.getBuilder();
		RenderSystem.setShader(GameRenderer::getPositionShader);
		RenderSystem.setShaderColor(0.0F, 0.0F, 1.0F, 1.0F);
		RenderSystem.disableTexture();
		RenderSystem.enableColorLogicOp();
		RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
		bufferbuilder.vertex((double) startX, (double) endY, 0.0D).endVertex();
		bufferbuilder.vertex((double) endX, (double) endY, 0.0D).endVertex();
		bufferbuilder.vertex((double) endX, (double) startY, 0.0D).endVertex();
		bufferbuilder.vertex((double) startX, (double) startY, 0.0D).endVertex();
		tesselator.end();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.disableColorLogicOp();
		RenderSystem.enableTexture();
	}

	/**
	 * Sets the maximum length for the text in this text box. If the current text is longer than this length, the current text will be
	 * trimmed.
	 */
	public ScreenComponentEditBox setMaxLength(int length) {
		this.maxLength = length;
		if (this.value.length() > length) {
			this.value = this.value.substring(0, length);
			this.onValueChange(this.value);
		}
		return this;

	}

	/**
	 * Returns the maximum number of character that can be contained in this textbox.
	 */
	public int getMaxLength() {
		return this.maxLength;
	}

	/**
	 * Returns the current position of the cursor.
	 */
	public int getCursorPosition() {
		return this.cursorPos;
	}

	/**
	 * Sets the color to use when drawing this text box's text. A different color is used if this text box is disabled.
	 */
	public ScreenComponentEditBox setTextColor(int pColor) {
		this.textColor = pColor;
		return this;
	}

	/**
	 * Sets the color to use for text in this text box when this text box is disabled.
	 */
	public ScreenComponentEditBox setTextColorUneditable(int pColor) {
		this.textColorUneditable = pColor;
		return this;
	}

	public boolean changeFocus(boolean pFocus) {
		return isVisible() && this.isEditable ? super.changeFocus(pFocus) : false;
	}

	protected void onFocusedChanged(boolean pFocused) {
		if (pFocused) {
			this.frame = 0;
		}
	}

	private boolean isEditable() {
		return this.isEditable;
	}

	/**
	 * Sets whether this text box is enabled. Disabled text boxes cannot be typed in.
	 */
	public void setEditable(boolean pEnabled) {
		this.isEditable = pEnabled;
	}

	/**
	 * Returns the width of the textbox depending on if background drawing is enabled.
	 */
	public int getInnerWidth() {
		return this.width - 8;
	}

	/**
	 * Sets the position of the selection anchor (the selection anchor and the cursor position mark the edges of the selection). If
	 * the anchor is set beyond the bounds of the current text, it will be put back inside.
	 */
	public void setHighlightPos(int position) {
		int i = this.value.length();
		this.highlightPos = Mth.clamp(position, 0, i);
		if (this.font != null) {
			if (this.displayPos > i) {
				this.displayPos = i;
			}

			int j = this.getInnerWidth();
			String s = this.font.plainSubstrByWidth(this.value.substring(this.displayPos), j);
			int k = s.length() + this.displayPos;
			if (this.highlightPos == this.displayPos) {
				this.displayPos -= this.font.plainSubstrByWidth(this.value, j, true).length();
			}

			if (this.highlightPos > k) {
				this.displayPos += this.highlightPos - k;
			} else if (this.highlightPos <= this.displayPos) {
				this.displayPos -= this.displayPos - this.highlightPos;
			}

			this.displayPos = Mth.clamp(this.displayPos, 0, i);
		}

	}

	/**
	 * Sets whether this text box loses focus when something other than it is clicked.
	 */
	public void setCanLoseFocus(boolean canLoseFocus) {
		this.canLoseFocus = canLoseFocus;
	}

	public void setSuggestion(@Nullable String suggestion) {
		this.suggestion = suggestion;
	}

	public int getScreenX(int charNum) {
		return (int) (charNum > this.value.length() ? this.xLocation + gui.getGuiWidth() : this.xLocation + gui.getGuiWidth() + this.font.width(this.value.substring(0, charNum)));
	}

	public void setX(int pX) {
		this.xLocation = pX;
	}

	public static void drawExpandedBox(PoseStack stack, int x, int y, int boxWidth, int boxHeight) {
		if (boxWidth < 18) {
			if (boxHeight < 18) {
				Screen.blit(stack, x, y, boxWidth, boxHeight, 0, 0, boxWidth, boxHeight, boxWidth, boxHeight);
			} else {
				Screen.blit(stack, x, y, boxWidth, 7, 0, 0, boxWidth, 7, boxWidth, 18);

				int sectionHeight = boxHeight - 14;
				int heightIterations = sectionHeight / 4;
				int remainderHeight = sectionHeight % 4;

				int heightOffset = 7;
				for (int i = 0; i < heightIterations; i++) {
					Screen.blit(stack, x, y + heightOffset, boxWidth, 4, 0, 7, boxWidth, 4, boxWidth, 18);
					heightOffset += 4;
				}
				Screen.blit(stack, x, y + heightOffset, boxWidth, remainderHeight, 0, 7, boxWidth, remainderHeight, boxWidth, 18);

				Screen.blit(stack, x, y + boxHeight - 7, boxWidth, 7, 0, 11, boxWidth, 7, boxWidth, 18);
			}
		} else if (boxHeight < 18) {
			Screen.blit(stack, x, y, 7, boxHeight, 0, 0, 7, boxHeight, 18, boxHeight);

			int sectionWidth = boxWidth - 14;
			int widthIterations = sectionWidth / 4;
			int remainderWidth = sectionWidth % 4;

			int widthOffset = 7;
			for (int i = 0; i < widthIterations; i++) {
				Screen.blit(stack, x + widthOffset, y, 4, boxHeight, 7, 0, 4, boxHeight, 18, boxHeight);
				widthOffset += 4;
			}
			Screen.blit(stack, x + widthOffset, y, remainderWidth, boxHeight, 7, 0, remainderWidth, boxHeight, 18, boxHeight);

			Screen.blit(stack, x + boxWidth - 7, y, 7, boxHeight, 11, 0, 7, boxHeight, 18, boxHeight);
		} else {
			// the button is >= 18x18 at this point

			// draw squares
			int squareWidth = boxWidth - 10;
			int squareWidthIterations = squareWidth / 8;
			int remainderSquareWidth = squareWidth % 8;

			int squareHeight = boxHeight - 10;
			int squareHeightIterations = squareHeight / 8;
			int remainderSquareHeight = squareHeight % 8;

			int heightOffset = 5;
			int widthOffset = 5;

			for (int i = 0; i <= squareHeightIterations; i++) {
				int height = i == squareHeightIterations ? remainderSquareHeight : 8;
				for (int j = 0; j < squareWidthIterations; j++) {
					draw(stack, x, y, widthOffset, heightOffset, 5, 5, 8, height);
					widthOffset += 8;
				}
				draw(stack, x, y, widthOffset, heightOffset, 5, 5, remainderSquareWidth, height);
				widthOffset = 5;
				heightOffset += 8;
			}

			// draw tl corner
			draw(stack, x, y, 0, 0, 0, 0, 8, 8);

			// draw top strip

			int stripWidth = boxWidth - 14;
			int stripWidthIterations = stripWidth / 4;
			int remainderStripWidth = stripWidth % 4;

			int stripHeight = boxHeight - 14;
			int stripHeightIterations = stripHeight / 4;
			int remainderStripHeight = stripHeight % 4;

			widthOffset = 7;
			for (int i = 0; i < stripWidthIterations; i++) {
				draw(stack, x, y, widthOffset, 0, 7, 0, 4, 5);
				widthOffset += 4;
			}
			draw(stack, x, y, widthOffset, 0, 7, 0, remainderStripWidth, 5);

			// draw tr corner
			draw(stack, x, y, boxWidth - 8, 0, 10, 0, 8, 8);

			// draw left strip
			heightOffset = 7;
			for (int i = 0; i < stripHeightIterations; i++) {
				draw(stack, x, y, 0, heightOffset, 0, 7, 5, 4);
				heightOffset += 4;
			}
			draw(stack, x, y, 0, heightOffset, 0, 5, 5, remainderStripHeight);

			// draw right strip
			heightOffset = 7;
			widthOffset = boxWidth - 5;
			for (int i = 0; i < stripHeightIterations; i++) {
				draw(stack, x, y, widthOffset, heightOffset, 13, 7, 5, 4);
				heightOffset += 4;
			}
			draw(stack, x, y, widthOffset, heightOffset, 13, 7, 5, remainderStripHeight);

			// draw bl corner
			draw(stack, x, y, 0, boxHeight - 8, 0, 10, 8, 8);

			// draw bottom strip
			heightOffset = boxHeight - 5;
			widthOffset = 7;
			for (int i = 0; i < stripWidthIterations; i++) {
				draw(stack, x, y, widthOffset, heightOffset, 7, 13, 4, 5);
				widthOffset += 4;
			}
			draw(stack, x, y, widthOffset, heightOffset, 7, 13, remainderStripWidth, 5);

			// draw br corner
			draw(stack, x, y, boxWidth - 8, boxHeight - 8, 10, 10, 8, 8);

		}

	}

	private static void draw(PoseStack stack, int x, int y, int widthOffset, int heightOffset, int textXOffset, int textYOffset, int width, int height) {
		Screen.blit(stack, x + widthOffset, y + heightOffset, width, height, textXOffset, textYOffset, width, height, 18, 18);
	}

	public static Predicate<String> getValidator(char[] validChars) {
		return string -> {

			if (string.isEmpty()) {
				return true;
			}

			boolean flag = false;

			for (char character : string.toCharArray()) {
				for (char valid : validChars) {
					if (valid == character) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					return false;
				}
				flag = false;
			}
			return true;
		};
	}

	public enum TextInputTextures implements ITexture {
		TEXT_INPUT_BASE(0, 0, 0, 0, 16, 16, TEXTURE);

		private final int textureWidth;
		private final int textureHeight;
		private final int textureU;
		private final int textureV;
		private final int imageWidth;
		private final int imageHeight;
		private final ResourceLocation loc;

		TextInputTextures(int textureWidth, int textureHeight, int textureU, int textureV, int imageWidth, int imageHeight, ResourceLocation loc) {
			this.textureWidth = textureWidth;
			this.textureHeight = textureHeight;
			this.textureU = textureU;
			this.textureV = textureV;
			this.imageWidth = imageWidth;
			this.imageHeight = imageHeight;
			this.loc = loc;
		}

		@Override
		public ResourceLocation getLocation() {
			return loc;
		}

		@Override
		public int imageHeight() {
			return imageHeight;
		}

		@Override
		public int imageWidth() {
			return imageWidth;
		}

		@Override
		public int textureHeight() {
			return textureHeight;
		}

		@Override
		public int textureU() {
			return textureU;
		}

		@Override
		public int textureV() {
			return textureV;
		}

		@Override
		public int textureWidth() {
			return textureWidth;
		}

	}
}