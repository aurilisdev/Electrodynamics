package electrodynamics.client.guidebook.utils.components;

import electrodynamics.client.guidebook.utils.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.TextWrapperObject;
import net.minecraft.network.chat.MutableComponent;

public class Page {

	private int pageNumber = 0;

	private ImageWrapperObject[] images = new ImageWrapperObject[] {};
	private TextWrapperObject[] text = new TextWrapperObject[] {};
	private ItemWrapperObject[] items = new ItemWrapperObject[] {};

	private MutableComponent chapterText;

	public Page(ImageWrapperObject[] images, TextWrapperObject[] text, ItemWrapperObject[] items) {
		this.images = images;
		this.text = text;
		this.items = items;
	}

	public Page(ImageWrapperObject[] images, TextWrapperObject[] text) {
		this.images = images;
		this.text = text;
	}

	public Page(TextWrapperObject[] text, ItemWrapperObject[] items) {
		this.text = text;
		this.items = items;
	}

	public Page(ImageWrapperObject[] images, ItemWrapperObject[] items) {
		this.images = images;
		this.items = items;
	}

	public Page(TextWrapperObject[] text) {
		this.text = text;
	}

	public Page(ImageWrapperObject[] images) {
		this.images = images;
	}

	public Page(ItemWrapperObject[] items) {
		this.items = items;
	}

	// For a blank page
	public Page() {
	}

	protected void setPageNumber(int number) {
		pageNumber = number;
	}

	protected void setChapterName(MutableComponent text) {
		chapterText = text;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public MutableComponent getChapterName() {
		return chapterText;
	}

	public TextWrapperObject[] getText() {
		return text;
	}

	public ImageWrapperObject[] getImages() {
		return images;
	}

	public ItemWrapperObject[] getItems() {
		return items;
	}

	// A simple wrapper class for the chapter pages used by Modules
	public static class ChapterPage extends Page {

		public ChapterPage(int pageNumber) {
			setPageNumber(pageNumber);
		}

	}

	// A simple wrapper class for the Module list pages used by @ScreenGuidebook
	public static class ModulePage extends Page {

		public ModulePage(int pageNumber) {
			setPageNumber(pageNumber);
		}

	}
}
