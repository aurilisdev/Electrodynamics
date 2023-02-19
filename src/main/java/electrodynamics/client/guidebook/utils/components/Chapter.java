package electrodynamics.client.guidebook.utils.components;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.client.guidebook.utils.pagedata.TextWrapperObject;
import net.minecraft.network.chat.MutableComponent;

/**
 * A simple data-wrapping class that contains a logo, a name, and the content
 * associated with it
 * 
 * @author skip999
 *
 */
public abstract class Chapter {

	private int startPage = 0;
	public List<Object> pageData = new ArrayList<>();
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

	public abstract Object getLogo();

	public abstract MutableComponent getTitle();

	protected void blankLine() {
		pageData.add(TextWrapperObject.BLANK_LINE);
	}

}
