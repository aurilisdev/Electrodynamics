package electrodynamics.compatability.jei.utils.label;

public class GenericLabelWrapper {
	
	protected static final String POWER = "power";
	
	private int COLOR;
	private int Y_POS;
	private int END_X_POS;
	private String NAME;
	
	public GenericLabelWrapper(int color, int yPos, int endXPos, String name) {
		COLOR = color;
		Y_POS = yPos;
		END_X_POS = endXPos;
		NAME = name;
	}
	
	public int getColor() {
		return COLOR;
	}
	
	public int getYPos() {
		return Y_POS;
	}
	
	public int getEndXPos() {
		return END_X_POS;
	}
	
	public String getName() {
		return NAME;
	}

}
