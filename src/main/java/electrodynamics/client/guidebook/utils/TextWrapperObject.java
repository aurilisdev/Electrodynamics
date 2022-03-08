package electrodynamics.client.guidebook.utils;

public class TextWrapperObject {

	public int xOffset;
	public int yOffset;
	public int color;
	public String textKey;
	public Object[] componentInfo;
	
	public TextWrapperObject(int xOffset, int yOffset, int color, String textKey, Object...componentInfo) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.color = color;
		this.textKey = textKey;
		this.componentInfo = componentInfo;
	}
	
}
