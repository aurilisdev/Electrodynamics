package electrodynamics.client.guidebook.utils.components;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.client.guidebook.utils.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.components.Page.ChapterPage;
import net.minecraft.network.chat.MutableComponent;

public abstract class Module {

	private List<Chapter> chapters;
	private int startingPageNumber;
	private int endingPageNumber;
	private int chapterPages = 0;

	public static final int CHAPTERS_PER_PAGE = 4;

	public Module() {
		chapters = genChapters();
		chapterPages = (int) Math.ceil((double) chapters.size() / (double) CHAPTERS_PER_PAGE);
	}

	public int setPageNumbers(int startingPageNumber) {
		this.startingPageNumber = startingPageNumber;
		startingPageNumber += chapterPages;
		for (Chapter chapter : chapters) {
			startingPageNumber += chapter.setPageNumbers(startingPageNumber);
		}
		endingPageNumber = startingPageNumber;

		int index = 0;
		for (int i = 0; i < chapterPages; i++) {
			for (int j = 0; j < CHAPTERS_PER_PAGE; j++) {
				if (index < chapters.size()) {
					chapters.get(index).setChapterPageNumber(this.startingPageNumber + i);
					index++;
				} else {
					break;
				}
			}
		}

		return endingPageNumber - this.startingPageNumber;
	}

	public List<Page> getAllPages() {
		List<Page> pages = new ArrayList<>();
		for (int i = 0; i < chapterPages; i++) {
			pages.add(new ChapterPage(i + startingPageNumber));
		}
		for (Chapter chapter : chapters) {
			pages.addAll(chapter.getPages());
		}
		return pages;
	}

	public int getStartingPageNumber() {
		return startingPageNumber;
	}

	public int getEndingPageNumber() {
		return endingPageNumber;
	}

	public boolean isChapterListPage(int pageNumber) {
		return startingPageNumber + chapterPages < pageNumber;
	}

	public boolean isPageInModule(int pageNumber) {
		return pageNumber >= startingPageNumber && pageNumber < endingPageNumber;
	}

	public List<Chapter> getChapters() {
		return chapters;
	}

	public List<Chapter> getChapterSubList(int pageNumber) {
		List<Chapter> chapters = new ArrayList<>();
		for (Chapter chapter : this.chapters) {
			if (chapter.getChapterPageNumber() == pageNumber) {
				chapters.add(chapter);
			}
		}
		return chapters;
	}

	public int getChapterPages() {
		return chapterPages;
	}

	public abstract MutableComponent getTitle();

	public boolean isCat(MutableComponent cat) {
		if (getTitle().getString().equals(cat.getString())) {
			return true;
		}
		return false;
	}

	/**
	 * @return should only return true if module is ModuleElectrodynamics; false otherwise
	 */
	public abstract boolean isFirst();

	public abstract ImageWrapperObject getLogo();

	// this is called at init() and init() only
	protected abstract List<Chapter> genChapters();

}
