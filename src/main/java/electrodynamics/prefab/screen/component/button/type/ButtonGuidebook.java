package electrodynamics.prefab.screen.component.button.type;

import electrodynamics.api.References;
import electrodynamics.api.screen.ITexture;
import electrodynamics.prefab.screen.component.button.ScreenComponentButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

public class ButtonGuidebook extends ScreenComponentButton<ButtonGuidebook> {

	private final GuidebookButtonType type;

	public ButtonGuidebook(GuidebookButtonType type, int x, int y) {
		super(type.off, x, y);
		this.type = type;
		pressSound = SoundEvents.BOOK_PAGE_TURN;
	}

	@Override
	public void renderBackground(GuiGraphics graphics, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		if (isActiveAndVisible() && isHovered()) {
			ITexture on = type.on;
			graphics.blit(on.getLocation(), xLocation + guiWidth, yLocation + guiHeight, on.textureU(), on.textureV(), on.textureWidth(), on.textureHeight(), on.imageWidth(), on.imageHeight());
		} else {
			super.renderBackground(graphics, xAxis, yAxis, guiWidth, guiHeight);
		}

	}

	public static enum GuidebookButtonType {

		LEFT(GuidebookButtonTextures.PAGE_LEFT_OFF, GuidebookButtonTextures.PAGE_LEFT_ON),
		RIGHT(GuidebookButtonTextures.PAGE_RIGHT_OFF, GuidebookButtonTextures.PAGE_RIGHT_ON),
		HOME(GuidebookButtonTextures.HOME_OFF, GuidebookButtonTextures.HOME_ON),
		CHAPTERS(GuidebookButtonTextures.CHAPTERS_OFF, GuidebookButtonTextures.CHAPTERS_ON),
		SEARCH(GuidebookButtonTextures.SEARCH_OFF, GuidebookButtonTextures.SEARCH_ON);

		public final GuidebookButtonTextures off;
		public final GuidebookButtonTextures on;

		GuidebookButtonType(GuidebookButtonTextures off, GuidebookButtonTextures on) {
			this.off = off;
			this.on = on;
		}

	}

	public static enum GuidebookButtonTextures implements ITexture {
		PAGE_LEFT_OFF(19, 10, 2, 207, 256, 256, BookViewScreen.BOOK_LOCATION),
		PAGE_LEFT_ON(19, 10, 25, 207, 256, 256, BookViewScreen.BOOK_LOCATION),
		PAGE_RIGHT_OFF(19, 10, 2, 194, 256, 256, BookViewScreen.BOOK_LOCATION),
		PAGE_RIGHT_ON(19, 10, 25, 194, 256, 256, BookViewScreen.BOOK_LOCATION),
		HOME_OFF(11, 10, 0, 0, 11, 10, "homeoff"),
		HOME_ON(11, 10, 0, 0, 11, 10, "homeon"),
		CHAPTERS_OFF(11, 10, 0, 0, 11, 10, "chaptersoff"),
		CHAPTERS_ON(11, 10, 0, 0, 11, 10, "chapterson"),
		SEARCH_OFF(11, 10, 0, 0, 11, 10, "searchoff"),
		SEARCH_ON(11, 10, 0, 0, 11, 10, "searchon");

		private final int textureWidth;
		private final int textureHeight;
		private final int textureU;
		private final int textureV;
		private final int imageWidth;
		private final int imageHeight;
		private final ResourceLocation loc;

		private GuidebookButtonTextures(int textureWidth, int textureHeight, int textureU, int textureV, int imageWidth, int imageHeight, String name) {
			this(textureWidth, textureHeight, textureU, textureV, imageWidth, imageHeight, new ResourceLocation(References.ID + ":textures/screen/guidebook/buttons/" + name + ".png"));
		}

		private GuidebookButtonTextures(int textureWidth, int textureHeight, int textureU, int textureV, int imageWidth, int imageHeight, ResourceLocation loc) {
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
