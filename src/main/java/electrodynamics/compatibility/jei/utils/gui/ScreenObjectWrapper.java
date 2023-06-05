package electrodynamics.compatibility.jei.utils.gui;

import electrodynamics.api.References;
import net.minecraft.resources.ResourceLocation;

public abstract class ScreenObjectWrapper {

	protected static final ResourceLocation BACKGROUND = new ResourceLocation(References.ID, "textures/screen/jei/background.png");
	protected static final ResourceLocation ITEM_SLOTS = new ResourceLocation(References.ID, "textures/screen/jei/itemslots.png");
	protected static final ResourceLocation FLUID_GAUGES = new ResourceLocation(References.ID, "textures/screen/jei/fluidgauges.png");
	protected static final ResourceLocation ARROWS = new ResourceLocation(References.ID, "textures/screen/jei/arrows.png");
	protected static final ResourceLocation GAS_GAUGES = new ResourceLocation(References.ID, "textures/screen/jei/gasgauges.png");

	private ResourceLocation texture;

	private int xPos;
	private int yPos;

	private int textX;
	private int textY;
	private int height;
	private int width;

	public ScreenObjectWrapper(ResourceLocation texture, int xStart, int yStart, int textX, int textY, int width, int height) {
		this.texture = texture;

		xPos = xStart;
		yPos = yStart;

		this.textX = textX;
		this.textY = textY;
		this.height = height;
		this.width = width;
	}

	public int getXPos() {
		return xPos;
	}

	public int getYPos() {
		return yPos;
	}

	public int getTextX() {
		return textX;
	}

	public int getTextY() {
		return textY;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public ResourceLocation getTexture() {
		return texture;
	}

}
