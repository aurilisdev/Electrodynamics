package electrodynamics.compatibility.jei.utils.gui;

public abstract class ScreenObjectWrapper {

	protected static final String BACKGROUND = "background";
	protected static final String ITEM_SLOTS = "itemslots";
	protected static final String FLUID_GAUGES = "fluidgauges";
	protected static final String ARROWS = "arrows";

	private String texture;

	private int xPos;
	private int yPos;

	private int textX;
	private int textY;
	private int length;
	private int width;

	public ScreenObjectWrapper(String texture, int xStart, int yStart, int textX, int textY, int height, int width) {
		this.texture = texture;

		xPos = xStart;
		yPos = yStart;

		this.textX = textX;
		this.textY = textY;
		length = height;
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

	public int getLength() {
		return length;
	}

	public int getWidth() {
		return width;
	}

	public String getTexture() {
		return "textures/screen/jei/" + texture + ".png";
	}

}
