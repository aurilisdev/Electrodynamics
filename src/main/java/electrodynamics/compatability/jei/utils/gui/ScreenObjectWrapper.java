package electrodynamics.compatability.jei.utils.gui;

public abstract class ScreenObjectWrapper {
	
	protected static final String BACKGROUND = "textures/gui/jei/background.png";
	protected static final String ITEM_SLOTS = "textures/gui/jei/itemslots.png";
	protected static final String FLUID_GAUGES = "textures/gui/jei/fluidgauges.png";
	protected static final String ARROWS = "textures/gui/jei/arrows.png";
	
	private String texture;
	
	private int xPos;
	private int yPos;
	
	private int textX;
	private int textY;
	private int length;
	private int width;
	
	public ScreenObjectWrapper(String texture, int xStart, int yStart, int textX, int textY, int height, int width) {
		this.texture = texture;
		
		this.xPos = xStart;
		this.yPos = yStart;
		
		this.textX = textX;
		this.textY = textY;
		this.length = height;
		this.width = width;
	}
	
	public int getXPos() {
		return this.xPos;
	}
	
	public int getYPos() {
		return this.yPos;
	}
	
	public int getTextX() {
		return this.textX;
	}
	
	public int getTextY() {
		return this.textY;
	}
	
	public int getLength() {
		return this.length;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public String getTexture() {
		return this.texture;
	}
	
}
