package electrodynamics.client.guidebook.chapters;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.References;
import electrodynamics.client.guidebook.utils.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.TextWrapperObject;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Page;
import net.minecraft.ChatFormatting;

public class ChapterMisc extends Chapter {

	private static final ImageWrapperObject LOGO = new ImageWrapperObject(10, 50, 0, 0, 32, 32, 32, 32, References.ID + ":textures/item/coil.png");
	
	@Override
	protected List<Page> genPages() {
		//DO NOT COMPRESS
		List<Page> pages = new ArrayList<>();
		
		pages.add(new Page(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.misc.p1l1").setTextStyles(ChatFormatting.UNDERLINE),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.misc.p1l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.misc.p1l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.misc.p1l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.misc.p1l5"),
			new TextWrapperObject(10, 90, 4210752, "guidebook.electrodynamics.chapter.misc.p1l6"),
			new TextWrapperObject(30, 100, 4210752, "guidebook.electrodynamics.chapter.misc.p1l7-1"),
			new TextWrapperObject(85, 100, 4210752, "guidebook.electrodynamics.chapter.misc.p1l7-2"),
			new TextWrapperObject(30, 110, 4210752, "guidebook.electrodynamics.chapter.misc.p1l8-1"),
			new TextWrapperObject(85, 110, 4210752, "guidebook.electrodynamics.chapter.misc.p1l8-2"),
			new TextWrapperObject(30, 120, 4210752, "guidebook.electrodynamics.chapter.misc.p1l9-1"),
			new TextWrapperObject(85, 120, 4210752, "guidebook.electrodynamics.chapter.misc.p1l9-2"),
			new TextWrapperObject(10, 130, 4210752, "guidebook.electrodynamics.chapter.misc.p1l10").setTextStyles(ChatFormatting.ITALIC),
			new TextWrapperObject(10, 140, 4210752, "guidebook.electrodynamics.chapter.misc.p1l11"),
			new TextWrapperObject(10, 150, 4210752, "guidebook.electrodynamics.chapter.misc.p1l12"),
			new TextWrapperObject(10, 160, 4210752, "guidebook.electrodynamics.chapter.misc.p1l13"),
			new TextWrapperObject(10, 170, 4210752, "guidebook.electrodynamics.chapter.misc.p1l14")
		}));
		
		pages.add(new Page(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.misc.p2l1").setTextStyles(ChatFormatting.UNDERLINE),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.misc.p2l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.misc.p2l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.misc.p2l4"),
			new TextWrapperObject(30, 80, 4210752, "guidebook.electrodynamics.chapter.misc.p2l5-1"),
			new TextWrapperObject(85, 80, 4210752, "guidebook.electrodynamics.chapter.misc.p2l5-2"),
			new TextWrapperObject(30, 90, 4210752, "guidebook.electrodynamics.chapter.misc.p2l6-1"),
			new TextWrapperObject(85, 90, 4210752, "guidebook.electrodynamics.chapter.misc.p2l6-2"),
			new TextWrapperObject(10, 100, 4210752, "guidebook.electrodynamics.chapter.misc.p2l7").setTextStyles(ChatFormatting.ITALIC),
		}));
		
		pages.add(new Page(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.misc.p3l1").setTextStyles(ChatFormatting.UNDERLINE),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.misc.p3l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.misc.p3l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.misc.p3l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.misc.p3l5"),
			new TextWrapperObject(10, 90, 4210752, "guidebook.electrodynamics.chapter.misc.p3l6"),
			new TextWrapperObject(10, 100, 4210752, "guidebook.electrodynamics.chapter.misc.p3l7"),
			new TextWrapperObject(10, 110, 4210752, "guidebook.electrodynamics.chapter.misc.p3l8"),
			new TextWrapperObject(10, 120, 4210752, "guidebook.electrodynamics.chapter.misc.p3l9"),
			new TextWrapperObject(10, 130, 4210752, "guidebook.electrodynamics.chapter.misc.p3l10"),
			new TextWrapperObject(10, 140, 4210752, "guidebook.electrodynamics.chapter.misc.p3l11"),
			new TextWrapperObject(10, 150, 4210752, "guidebook.electrodynamics.chapter.misc.p3l12"),
			new TextWrapperObject(10, 160, 4210752, "guidebook.electrodynamics.chapter.misc.p3l13"),
			new TextWrapperObject(10, 170, 4210752, "guidebook.electrodynamics.chapter.misc.p3l14"),
			new TextWrapperObject(10, 180, 4210752, "guidebook.electrodynamics.chapter.misc.p3l15")
		}));
		
		pages.add(new Page(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.misc.p4l1"),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.misc.p4l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.misc.p4l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.misc.p4l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.misc.p4l5"),
			new TextWrapperObject(10, 90, 4210752, "guidebook.electrodynamics.chapter.misc.p4l6"),
			new TextWrapperObject(10, 100, 4210752, "guidebook.electrodynamics.chapter.misc.p4l7"),
			new TextWrapperObject(10, 110, 4210752, "guidebook.electrodynamics.chapter.misc.p4l8"),
			new TextWrapperObject(10, 120, 4210752, "guidebook.electrodynamics.chapter.misc.p4l9"),
			new TextWrapperObject(10, 130, 4210752, "guidebook.electrodynamics.chapter.misc.p4l10")
		}));
		
		pages.add(new Page(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.misc.p5l1").setTextStyles(ChatFormatting.UNDERLINE),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.misc.p5l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.misc.p5l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.misc.p5l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.misc.p5l5"),
			new TextWrapperObject(10, 90, 4210752, "guidebook.electrodynamics.chapter.misc.p5l6"),
			new TextWrapperObject(10, 100, 4210752, "guidebook.electrodynamics.chapter.misc.p5l7"),
			new TextWrapperObject(10, 110, 4210752, "guidebook.electrodynamics.chapter.misc.p5l8"),
			new TextWrapperObject(10, 120, 4210752, "guidebook.electrodynamics.chapter.misc.p5l9"),
			new TextWrapperObject(10, 130, 4210752, "guidebook.electrodynamics.chapter.misc.p5l10"),
			new TextWrapperObject(10, 140, 4210752, "guidebook.electrodynamics.chapter.misc.p5l11"),
			new TextWrapperObject(10, 150, 4210752, "guidebook.electrodynamics.chapter.misc.p5l12"),
			new TextWrapperObject(10, 160, 4210752, "guidebook.electrodynamics.chapter.misc.p5l13"),
			new TextWrapperObject(10, 170, 4210752, "guidebook.electrodynamics.chapter.misc.p5l14"),
			new TextWrapperObject(10, 180, 4210752, "guidebook.electrodynamics.chapter.misc.p5l15")
		}));
		
		pages.add(new Page(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.misc.p6l1"),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.misc.p6l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.misc.p6l3")
		}));

		return pages;
	}

	@Override
	public ImageWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public String getTitleKey() {
		return "guidebook.electrodynamics.chapter.misc";
	}

}
