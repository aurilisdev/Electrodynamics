package electrodynamics.client.guidebook.utils.components;

import java.util.List;

public abstract class Chapter {
	
	private List<Page> pages;
	private int startingPageNumber;
	private int endingPageNumber;
	
	private int chapterPageNumber;
	
	public Chapter() {
		pages = genPages();
	}
	
	public int setPageNumbers(int startingPageNumber) {
		this.startingPageNumber = startingPageNumber;
		int count = 0;
		for(Page page : pages) {
			page.setPageNumber(startingPageNumber);
			page.setChapterKey(getTitleKey());
			startingPageNumber++;
			count++;
		}
		endingPageNumber = startingPageNumber;
		return count;
	}
	
	public List<Page> getPages() {
		return pages;
	}
	
	public int getStartingPageNumber() {
		return startingPageNumber;
	}
	
	public int getEndingPageNumber() {
		return endingPageNumber;
	}
	
	public void setChapterPageNumber(int number) {
		chapterPageNumber = number;
	}
	
	public int getChapterPageNumber() {
		return chapterPageNumber;
	}
	
	public boolean isPageInChapter(int pageNumber) {
		return pageNumber >= startingPageNumber && pageNumber < endingPageNumber;
	}
	
	protected abstract List<Page> genPages();
	
	public abstract Object getLogo();
	
	public abstract String getTitleKey();
	
}
