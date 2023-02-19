package electrodynamics.client.guidebook.utils.pagedata;

import net.minecraft.network.chat.Component;

/**
 * A simple wrapper class that contains a segment of text along with basic
 * formatting data for it
 * 
 * @author skip999
 *
 */
public class TextWrapperObject {

	public static final TextWrapperObject BLANK_LINE = new TextWrapperObject(Component.empty());

	public int color;
	public Component text;

	public int numberOfIndentions = 0;
	public boolean isSeparateStart = false;
	public boolean center = false;
	public boolean newPage = false;

	public TextWrapperObject(Component text) {
		this(4210752, text);
	}

	public TextWrapperObject(int color, Component text) {
		this.color = color;
		this.text = text;
	}

	public TextWrapperObject setIndentions(int numOfIndentions) {
		this.numberOfIndentions = numOfIndentions;
		return this;
	}

	public TextWrapperObject setSeparateStart() {
		isSeparateStart = true;
		return this;
	}

	public TextWrapperObject setCentered() {
		center = true;
		return this;
	}

	public TextWrapperObject setNewPage() {
		newPage = true;
		return this;
	}

}
