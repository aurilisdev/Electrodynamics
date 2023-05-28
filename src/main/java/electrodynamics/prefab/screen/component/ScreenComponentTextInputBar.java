package electrodynamics.prefab.screen.component;

import java.util.function.Predicate;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.References;
import electrodynamics.api.screen.ITexture;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
/**
 * @author skip999
 */
public class ScreenComponentTextInputBar extends ScreenComponentGeneric {

	public static final ResourceLocation TEXTURE = new ResourceLocation(References.ID + ":textures/screen/component/textinputbar.png");
	
	public static final char[] POSITIVE_DECIMAL = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
	public static final char[] DECIMAL = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-'};
	
	public static final char[] POSITIVE_INTEGER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	public static final char[] INTEGER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-'};

	public final int width;
	public final int height;

	public ScreenComponentTextInputBar(final GenericScreen<?> gui, final int x, final int y, int width, int height) {
		super(TextInputTextures.TEXT_INPUT_BASE, gui, x, y);
		this.width = width;
		this.height = height;
	}

	@Override
	public void renderBackground(PoseStack stack, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		
		RenderingUtils.bindTexture(texture.getLocation());
		
		drawExpandedBox(stack, this.xLocation + guiWidth, this.yLocation + guiHeight, width, height);
		
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
			
			if(string.isEmpty()) {
				return true;
			}
			
			boolean flag = false;
			
			for(char character : string.toCharArray()) {
				for(char valid : validChars) {
					if(valid == character) {
						flag = true;
						break;
					}
				}
				if(!flag) {
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