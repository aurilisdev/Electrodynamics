package electrodynamics.prefab.screen.component;

import java.util.List;

import javax.annotation.Nonnull;

import electrodynamics.api.References;
import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.api.screen.ITexture;
import electrodynamics.api.screen.component.TextPropertySupplier;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

public class ScreenComponentGuiTab extends AbstractScreenComponentInfo {

	public static final ResourceLocation TEXTURE = new ResourceLocation(References.ID + ":textures/screen/component/screentabs.png");
	
	public ScreenComponentGuiTab(ITexture texture, @Nonnull TextPropertySupplier infoHandler, IScreenWrapper gui, int x, int y) {
		super(texture, infoHandler, gui, x, y);
	}

	@Override
	protected List<? extends FormattedCharSequence> getInfo(List<? extends FormattedCharSequence> list) {
		return infoHandler.getInfo();
	}
	
	public static enum GuiInfoTabTextures implements ITexture {
		ELECTRIC(26, 26, 0, 0, 256, 256, TEXTURE),
		ENCHANTMENT(26, 26, 0, 26, 256, 256, TEXTURE),
		MINING_LOCATION(26, 26, 0, 52, 256, 256, TEXTURE),
		QUARRY_COMPONENTS(26, 26, 0, 78, 256, 256, TEXTURE),
		TEMPERATURE(26, 26, 0, 104, 256, 256, TEXTURE),
		WATER(26, 26, 0, 130, 256, 256, TEXTURE);
		
		private final int textureWidth;
		private final int textureHeight;
		private final int textureU;
		private final int textureV;
		private final int imageWidth;
		private final int imageHeight;
		private final ResourceLocation loc;
		
		private GuiInfoTabTextures(int textureWidth, int textureHeight, int textureU, int textureV, int imageWidth, int imageHeight, ResourceLocation loc) {
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
