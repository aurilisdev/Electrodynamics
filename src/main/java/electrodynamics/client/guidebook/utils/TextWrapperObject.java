package electrodynamics.client.guidebook.utils;

import net.minecraft.ChatFormatting;

public class TextWrapperObject {

	public int xOffset;
	public int yOffset;
	public int color;
	public String textKey;
	public Object[] componentInfo;
	public ChatFormatting[] formats;
	
	public TextWrapperObject(int xOffset, int yOffset, int color, String textKey, Object...componentInfo) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.color = color;
		this.textKey = textKey;
		this.componentInfo = componentInfo;
	}
	
	public TextWrapperObject setTextStyles(ChatFormatting...formats) {
		this.formats = formats;
		return this;
	}
	
}
