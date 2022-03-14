package electrodynamics.client.guidebook.chapters;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.References;
import electrodynamics.client.guidebook.utils.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.TextWrapperObject;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Page;
import net.minecraft.ChatFormatting;

public class ChapterTips extends Chapter {

	private static final ImageWrapperObject LOGO = new ImageWrapperObject(10, 50, 0, 0, 32, 32, 32, 32, References.ID + ":textures/item/nugget/nuggetsilver.png");
	
	@Override
	protected List<Page> genPages() {
		List<Page> pages = new ArrayList<>();
		
		pages.add(new Page(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.tips.p1l1").setTextStyles(ChatFormatting.UNDERLINE),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.tips.p1l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.tips.p1l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.tips.p1l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.tips.p1l5"),
			new TextWrapperObject(10, 90, 4210752, "guidebook.electrodynamics.chapter.tips.p1l6"),
			new TextWrapperObject(10, 100, 4210752, "guidebook.electrodynamics.chapter.tips.p1l7"),
			new TextWrapperObject(10, 110, 4210752, "guidebook.electrodynamics.chapter.tips.p1l8"),
			new TextWrapperObject(10, 120, 4210752, "guidebook.electrodynamics.chapter.tips.p1l9")
		}));
		
		pages.add(new Page(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.tips.p2l1").setTextStyles(ChatFormatting.UNDERLINE),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.tips.p2l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.tips.p2l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.tips.p2l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.tips.p2l5"),
			new TextWrapperObject(10, 90, 4210752, "guidebook.electrodynamics.chapter.tips.p2l6"),
			new TextWrapperObject(10, 100, 4210752, "guidebook.electrodynamics.chapter.tips.p2l7"),
			new TextWrapperObject(10, 110, 4210752, "guidebook.electrodynamics.chapter.tips.p2l8"),
			new TextWrapperObject(10, 120, 4210752, "guidebook.electrodynamics.chapter.tips.p2l9"),
			new TextWrapperObject(10, 130, 4210752, "guidebook.electrodynamics.chapter.tips.p2l10"),
			new TextWrapperObject(10, 140, 4210752, "guidebook.electrodynamics.chapter.tips.p2l11")
		}));
		
		pages.add(new Page(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.tips.p3l1").setTextStyles(ChatFormatting.UNDERLINE),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.tips.p3l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.tips.p3l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.tips.p3l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.tips.p3l5"),
			new TextWrapperObject(10, 90, 4210752, "guidebook.electrodynamics.chapter.tips.p3l6"),
			new TextWrapperObject(10, 100, 4210752, "guidebook.electrodynamics.chapter.tips.p3l7"),
			new TextWrapperObject(10, 110, 4210752, "guidebook.electrodynamics.chapter.tips.p3l8"),
			new TextWrapperObject(10, 120, 4210752, "guidebook.electrodynamics.chapter.tips.p3l9"),
			new TextWrapperObject(10, 130, 4210752, "guidebook.electrodynamics.chapter.tips.p3l10"),
			new TextWrapperObject(10, 140, 4210752, "guidebook.electrodynamics.chapter.tips.p3l11")
		}));
		
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
