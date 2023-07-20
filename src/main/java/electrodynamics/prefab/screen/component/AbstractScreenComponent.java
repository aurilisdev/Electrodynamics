package electrodynamics.prefab.screen.component;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.screen.IScreenWrapper;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;

/**
 * 
 * A modified variant of AbstractWidget aligning it more with the system Electrodynamics uses as AbstractWidget has several issues
 * 
 * @author skip999
 *
 *         Original Author : AurilisDev
 *
 */
public abstract class AbstractScreenComponent extends GuiComponent implements GuiEventListener, Widget, NarratableEntry {

	public int xLocation;
	public int yLocation;

	public int width;
	public int height;

	private boolean isVisible = true;
	private boolean isActive = true;

	private boolean isHovered = false;
	private boolean isFocused = false;

	public IScreenWrapper gui;

	public AbstractScreenComponent(int x, int y, int width, int height) {

		xLocation = x;
		yLocation = y;

		this.width = width;
		this.height = height;
	}

	/*
	 * By separating the GUI definition from the constructor, it allows us to define screen components that can be attached to a screen at a later point. This is especially useful in GUIs that have components that do not change and thus can be made static to improve efficiency
	 * 
	 * Granted, the downside is you need to remember to set the owner screen
	 */
	public AbstractScreenComponent setScreen(IScreenWrapper gui) {
		this.gui = gui;
		return this;
	}

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTick) {
		if (isVisible()) {
			setHovered(isMouseOver(mouseX, mouseY));
			int guiWidth = (int) gui.getGuiWidth();
			int guiHeight = (int) gui.getGuiHeight();
			renderBackground(stack, mouseX - guiWidth, mouseY - guiHeight, guiWidth, guiHeight);
		}

	}

	public void renderBackground(PoseStack stack, int xAxis, int yAxis, int guiWidth, int guiHeight) {

	}

	public void renderForeground(PoseStack stack, int xAxis, int yAxis, int guiWidth, int guiHeight) {

	}

	public void renderScaledText(PoseStack stack, String text, int x, int y, int color, int maxX) {
		int length = gui.getFontRenderer().width(text);

		if (length <= maxX) {
			gui.getFontRenderer().draw(stack, text, x, y, color);
		} else {
			float scale = (float) maxX / length;
			float reverse = 1 / scale;
			float yAdd = 4 - scale * 8 / 2F;

			stack.pushPose();

			stack.scale(scale, scale, scale);
			gui.getFontRenderer().draw(stack, text, (int) (x * reverse), (int) (y * reverse + yAdd), color);

			stack.popPose();
		}
	}

	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		return isPointInRegion(xLocation, yLocation, mouseX - gui.getGuiWidth(), mouseY - gui.getGuiHeight(), width, height);
	}

	/*
	 * @Override public boolean mouseClicked(double mouseX, double mouseY, int button) { if (isActiveAndVisible() && isValidClick(button) && isInClickRegion(mouseX, mouseY)) {
	 * 
	 * onMouseClick(mouseX, mouseY);
	 * 
	 * return true; } return false; }
	 * 
	 * @Override public boolean mouseReleased(double mouseX, double mouseY, int button) { if (isValidClick(button)) { onMouseRelease(mouseX, mouseY); return true; } else { return false; } }
	 * 
	 * @Override public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) { if (isValidClick(button)) { onMouseDrag(mouseX, mouseY, dragX, dragY); return true; } else { return false; } }
	 * 
	 * @Override public boolean mouseScrolled(double mouseX, double mouseY, double delta) { if(isActiveAndVisible()) { onMouseScroll(mouseX, mouseY, delta); return true; } else { return false; } }
	 * 
	 */
	public boolean isInClickRegion(double mouseX, double mouseY) {
		return isMouseOver(mouseX, mouseY);
	}

	protected boolean isPointInRegion(int x, int y, double xAxis, double yAxis, int width, int height) {
		return xAxis >= x && xAxis <= x + width - 1 && yAxis >= y && yAxis <= y + height - 1;
	}

	public void preOnMouseClick(double mouseX, double mouseY, int button) {

	}

	/*
	 * You must return true on mouseClicked() to use
	 */
	public void onMouseClick(double mouseX, double mouseY) {

	}

	/*
	 * You must return true on mouseReleased() to use
	 */
	public void onMouseRelease(double mouseX, double mouseY) {

	}

	/*
	 * You must return true on mouseDragged() to use
	 */
	public void onMouseDrag(double mouseX, double mouseY, double dragX, double dragY) {

	}

	/*
	 * You must return true on mouseScrolled() to use
	 */
	public void onMouseScroll(double mouseX, double mouseY, double delta) {

	}

	public boolean isValidClick(int button) {
		return button == 0;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	public void setHovered(boolean isHovered) {
		this.isHovered = isHovered;
	}

	public boolean isHovered() {
		return isHovered;
	}

	public void setFocused(boolean isFocused) {
		this.isFocused = isFocused;
	}

	public boolean isFocused() {
		return isFocused;
	}

	public boolean isHoveredOrFocused() {
		return isHovered() || isFocused();
	}

	public boolean isActiveAndVisible() {
		return isActive() && isVisible();
	}

	@Override
	public NarrationPriority narrationPriority() {
		if (isFocused()) {
			return NarratableEntry.NarrationPriority.FOCUSED;
		}
		return isHovered() ? NarratableEntry.NarrationPriority.HOVERED : NarratableEntry.NarrationPriority.NONE;
	}

	@Override
	public boolean changeFocus(boolean pFocus) {
		if (isActive() && isVisible()) {
			setFocused(!isFocused());
			onFocusChanged(isFocused());
			return isFocused();
		}
		return false;
	}

	public void onFocusChanged(boolean isFocused) {

	}

	@Override
	public void updateNarration(NarrationElementOutput pNarrationElementOutput) {

	}

}