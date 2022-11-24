package electrodynamics.client.guidebook.utils;

import net.minecraft.network.chat.Component;

public class TextWrapperObject {

	public int xOffset;
	public int yOffset;
	public int color;
	public Component text;

	public TextWrapperObject(int xOffset, int yOffset, int color, Component text) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.color = color;
		this.text = text;
	}

}
