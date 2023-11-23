package electrodynamics.client.guidebook.utils.components;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.client.guidebook.utils.pagedata.AbstractWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.OnClick;
import electrodynamics.client.guidebook.utils.pagedata.OnKeyPress;
import electrodynamics.client.guidebook.utils.pagedata.OnTooltip;
import electrodynamics.client.guidebook.utils.pagedata.graphics.AbstractGraphicWrapper;
import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import net.minecraft.network.chat.MutableComponent;

/**
 * A simple data-wrapping class that contains a logo, a name, and the content associated with it
 * 
 * @author skip999
 *
 */
public abstract class Chapter {

	private int startPage = 0;
	public List<AbstractWrapperObject<?>> pageData = new ArrayList<>();
	public final Module module;

	public Chapter(Module module) {
		addData();
		this.module = module;
	}

	public void setStartPage(int page) {
		startPage = page;
	}

	public int getStartPage() {
		return startPage;
	}

	public abstract void addData();

	public abstract AbstractGraphicWrapper<?> getLogo();

	public abstract MutableComponent getTitle();

	protected void blankLine() {
		pageData.add(TextWrapperObject.BLANK_LINE);
	}

	public OnTooltip onTooltip() {
		return null;
	}

	public OnClick onClick() {
		return null;
	}

	public OnKeyPress onKeyPress() {
		return null;
	}

}
