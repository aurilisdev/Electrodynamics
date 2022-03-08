package electrodynamics.client.guidebook.electrodynamics.chapters;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.References;
import electrodynamics.client.guidebook.components.Chapter;
import electrodynamics.client.guidebook.components.Page;
import electrodynamics.client.guidebook.utils.ImageWrapperObject;

public class ChapterTips extends Chapter {

	private static final ImageWrapperObject LOGO = new ImageWrapperObject(10, 50, 0, 0, 32, 32, 32, 32, References.ID + ":textures/item/nugget/nuggetsilver.png");
	
	@Override
	protected List<Page> genPages() {
		List<Page> pages = new ArrayList<>();
		return pages;
	}

	@Override
	public ImageWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public String getTitleKey() {
		return "guidebook.electrodynamics.chapter.tips";
	}
	
}
