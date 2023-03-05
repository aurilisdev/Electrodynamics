package electrodynamics.prefab.screen.component.button;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.client.guidebook.ScreenGuidebook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

//Gotta love mojank making it a variable
public class ButtonSpecificPage extends Button {

	public final int page;

	public ButtonSpecificPage(int x, int y, int width, int height, int page, Component message, OnPress onPress) {
		super(x, y, width, height, message, onPress);
		this.page = page;
	}

	@Override
	public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
		if (isVisible()) {
			this.isHovered = pMouseX >= this.x && pMouseY >= this.y && pMouseX < this.x + this.width && pMouseY < this.y + this.height;
			this.renderButton(pPoseStack, pMouseX, pMouseY, pPartialTick);
		}
	}

	@Override
	public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
		if (this.active && isVisible()) {
			if (this.isValidClickButton(pButton)) {
				boolean flag = this.clicked(pMouseX, pMouseY);
				if (flag) {
					this.playDownSound(Minecraft.getInstance().getSoundManager());
					this.onClick(pMouseX, pMouseY);
					return true;
				}
			}

			return false;
		}
		return false;
	}

	@Override
	public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
		if (this.active && isVisible()) {
			if (pKeyCode != 257 && pKeyCode != 32 && pKeyCode != 335) {
				return false;
			}
			this.playDownSound(Minecraft.getInstance().getSoundManager());
			this.onPress();
			return true;
		}
		return false;
	}

	@Override
	public boolean changeFocus(boolean pFocus) {
		if (isVisible()) {
			return super.changeFocus(pFocus);
		}
		return false;
	}

	@Override
	protected boolean clicked(double pMouseX, double pMouseY) {
		return this.active && isVisible() && pMouseX >= this.x && pMouseY >= this.y && pMouseX < this.x + this.width && pMouseY < this.y + this.height;
	}

	@Override
	public boolean isMouseOver(double pMouseX, double pMouseY) {
		return this.active && isVisible() && pMouseX >= this.x && pMouseY >= this.y && pMouseX < this.x + this.width && pMouseY < this.y + this.height;
	}

	@Override
	public boolean isActive() {
		return isVisible() && this.active;
	}

	public boolean isVisible() {
		return page == ScreenGuidebook.currPageNumber || page == ScreenGuidebook.currPageNumber + 1;
	}

}
