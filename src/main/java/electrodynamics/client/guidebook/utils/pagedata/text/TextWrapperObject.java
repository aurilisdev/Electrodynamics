package electrodynamics.client.guidebook.utils.pagedata.text;

import electrodynamics.client.guidebook.utils.pagedata.AbstractWrapperObject;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.network.chat.Component;

/**
 * A simple wrapper class that contains a segment of text along with basic formatting data for it
 * 
 * @author skip999
 *
 */
public class TextWrapperObject extends AbstractWrapperObject<TextWrapperObject> {

	public static final TextWrapperObject BLANK_LINE = new TextWrapperObject(Component.empty());

	public static final int DEFAULT_COLOR = 4210752;

	public static final int LIGHT_GREY = RenderingUtils.getRGBA(255, 170, 170, 170);

	public int color;
	public Component text;

	public int numberOfIndentions = 0;
	public boolean isSeparateStart = false;
	public boolean center = false;

	public TextWrapperObject(Component text) {
		this(DEFAULT_COLOR, text);
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

}
