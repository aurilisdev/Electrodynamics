package electrodynamics.prefab.screen.component.button;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import electrodynamics.api.screen.ITexture;
import electrodynamics.prefab.screen.component.types.ScreenComponentGeneric;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;

/**
 * A modification of the vanilla button to integrate it with the Electrodynamics system of doing GUI components as the Button
 * class has several annoying issues
 * 
 * @author skip999
 *
 * @param <T>
 */
public class ScreenComponentButton<T extends ScreenComponentButton<?>> extends ScreenComponentGeneric {

	public final boolean isVanillaRender;

	@Nullable
	public OnTooltip onTooltip = null;
	@Nullable
	public OnPress onPress = null;

	@Nullable
	public ITextComponent label = null;

	public SoundEvent pressSound = SoundEvents.UI_BUTTON_CLICK;

	public ScreenComponentButton(ITexture texture, int x, int y) {
		super(texture, x, y);
		isVanillaRender = false;
	}

	public ScreenComponentButton(int x, int y, int width, int height) {
		super(x, y, width, height);
		isVanillaRender = true;
		this.width = width;
		this.height = height;
	}

	public T setLabel(ITextComponent label) {
		this.label = label;
		return (T) this;
	}

	public T setOnPress(OnPress onPress) {
		this.onPress = onPress;
		return (T) this;
	}

	public T setOnTooltip(OnTooltip onTooltip) {
		this.onTooltip = onTooltip;
		return (T) this;
	}

	public T setPressSound(SoundEvent sound) {
		pressSound = sound;
		return (T) this;
	}

	@Override
	public void renderBackground(MatrixStack stack, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		if (isVanillaRender && isVisible()) {
			Minecraft minecraft = Minecraft.getInstance();

			minecraft.getTextureManager().bind(WIDGETS_LOCATION);
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
			int i = this.getYImage(this.isHovered());
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.enableDepthTest();
			blit(stack, this.x + guiWidth, this.y + guiHeight, 0, 46 + i * 20, this.width / 2, this.height, 256, 256);
			blit(stack, this.x + guiWidth + this.width / 2, this.y + guiHeight, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height, 256, 256);

			FontRenderer font = minecraft.font;
			ITextComponent label = getLabel();
			if (label != null) {
				drawCenteredString(stack, font, label, this.x + guiWidth + this.width / 2, this.y + guiHeight + (this.height - 8) / 2, color.color());
			}
			RenderingUtils.resetShaderColor();

		} else {
			super.renderBackground(stack, xAxis, yAxis, guiWidth, guiHeight);
		}
	}

	public int getVanillaYImage(boolean isMouseOver) {
		if (!isVisible()) {
			return 0;
		}
		if (isMouseOver) {
			return 2;
		}

		return 1;
	}

	@Override
	public void renderForeground(MatrixStack stack, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		super.renderForeground(stack, xAxis, yAxis, guiWidth, guiHeight);
		if (isVisible() && isHovered() && onTooltip != null) {
			onTooltip.onTooltip(stack, this, xAxis, yAxis);
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (isActiveAndVisible() && isValidClick(button) && isInClickRegion(mouseX, mouseY)) {

			onMouseClick(mouseX, mouseY);

			return true;
		}
		return false;
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		if (isValidClick(button)) {
			onMouseRelease(mouseX, mouseY);
			return true;
		}
		return false;
	}

	@Override
	public void onMouseClick(double mouseX, double mouseY) {
		if (onPress != null) {
			onPress();
		}
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (!isActiveAndVisible()) {
			return false;
		}
		if (keyCode != 257 && keyCode != 32 && keyCode != 335) {
			return false;
		}
		this.playDownSound(Minecraft.getInstance().getSoundManager());
		this.onPress();
		return true;
	}

	public void onPress() {
		onPress.onPress(this);
		playDownSound(Minecraft.getInstance().getSoundManager());
	}

	public boolean isValidMouseClick(int button) {
		return button == 0;
	}

	public void playDownSound(SoundHandler soundManager) {
		soundManager.play(SimpleSound.forUI(pressSound, 1.0F));
	}

	public ITextComponent getLabel() {
		return label;
	}

	public static interface OnPress {

		public void onPress(ScreenComponentButton<?> button);

	}

	public static interface OnTooltip {

		public void onTooltip(MatrixStack stack, ScreenComponentButton<?> button, int xAxis, int yAxis);

	}

}