package electrodynamics.client.guidebook.components;

import java.util.List;

import electrodynamics.client.guidebook.utils.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.TextWrapperObject;

public class Page {

	private int pageNumber = 0;
	
	private List<ImageWrapperObject> images;
	private List<TextWrapperObject> text;
	
	private String chapterKey;
	
	public Page(List<ImageWrapperObject> images, List<TextWrapperObject> text) {
		this.images = images;
		this.text = text;
	}
	
	protected void setPageNumber(int number) {
		pageNumber = number;
	}
	
	protected void setChapterKey(String key) {
		this.chapterKey = key;
	}
	
	public int getPageNumber() {
		return pageNumber;
	}
	
	public String getChapterKey() {
		return chapterKey;
	}
	
	public List<TextWrapperObject> getText(){
		return text;
	}
	
	public List<ImageWrapperObject> getImages(){
		return images;
	}
	
	// A simple wrapper class for the chapter pages used by Modules
	public static class ChapterPage extends Page {

		public ChapterPage(int pageNumber) {
			super(null, null);
			setPageNumber(pageNumber);
		}
		
	}
	
	// A simple wrapper class for the Module list pages used by @ScreenGuidebook
	public static class ModulePage extends Page {

		public ModulePage(int pageNumber) {
			super(null, null);
			setPageNumber(pageNumber);
		}
		
	}
}
