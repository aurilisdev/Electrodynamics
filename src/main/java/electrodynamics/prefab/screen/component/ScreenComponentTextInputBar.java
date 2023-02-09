package electrodynamics.prefab.screen.component;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.References;
import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.api.screen.ITexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
/*
 * Class could be condensed to ScreenComponentGeneric, but is left to allow for future potential expansion
 */
public class ScreenComponentTextInputBar extends ScreenComponentGeneric {

	public static final ResourceLocation TEXTURE = new ResourceLocation(References.ID + ":textures/screen/component/textinputbar.png");

	public ScreenComponentTextInputBar(ITexture texture, final IScreenWrapper gui, final int x, final int y) {
		super(texture, gui, x, y);
	}

	@Override
	public void renderBackground(PoseStack stack, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		// TODO variable-width text input bar
		super.renderBackground(stack, xAxis, yAxis, guiWidth, guiHeight);
	}

	public enum TextInputTextures implements ITexture {
		TEXT_INPUT_LARGE(56, 16, 0, 0, 256, 256, TEXTURE),
		TEXT_INPUT_SMALL(49, 16, 0, 16, 256, 256, TEXTURE);

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