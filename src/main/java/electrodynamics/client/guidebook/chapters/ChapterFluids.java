package electrodynamics.client.guidebook.chapters;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.References;
import electrodynamics.client.guidebook.utils.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.TextWrapperObject;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Page;
import electrodynamics.prefab.utilities.TextUtils;
import net.minecraft.network.chat.MutableComponent;

public class ChapterFluids extends Chapter {

	private static final ImageWrapperObject LOGO = new ImageWrapperObject(10, 50, 0, 0, 32, 32, 32, 32, References.ID + ":textures/item/pipe/pipesteel.png");

	@Override
	protected List<Page> genPages() {
		// DO NOT COMPRESS
		List<Page> pages = new ArrayList<>();

		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, TextUtils.guidebook("chapter.fluids.p1l1")), new TextWrapperObject(10, 50, 4210752, TextUtils.guidebook("chapter.fluids.p1l2")), new TextWrapperObject(10, 60, 4210752, TextUtils.guidebook("chapter.fluids.p1l3")), new TextWrapperObject(10, 70, 4210752, TextUtils.guidebook("chapter.fluids.p1l4")), new TextWrapperObject(10, 80, 4210752, TextUtils.guidebook("chapter.fluids.p1l5")), new TextWrapperObject(10, 90, 4210752, TextUtils.guidebook("chapter.fluids.p1l6")), new TextWrapperObject(10, 100, 4210752, TextUtils.guidebook("chapter.fluids.p1l7")), new TextWrapperObject(10, 110, 4210752, TextUtils.guidebook("chapter.fluids.p1l8")), new TextWrapperObject(10, 120, 4210752, TextUtils.guidebook("chapter.fluids.p1l9")), new TextWrapperObject(10, 130, 4210752, TextUtils.guidebook("chapter.fluids.p1l10")), new TextWrapperObject(10, 140, 4210752, TextUtils.guidebook("chapter.fluids.p1l11")), new TextWrapperObject(10, 150, 4210752, TextUtils.guidebook("chapter.fluids.p1l12")), new TextWrapperObject(10, 160, 4210752, TextUtils.guidebook("chapter.fluids.p1l13")), new TextWrapperObject(30, 170, 4210752, TextUtils.guidebook("chapter.fluids.p1l14-1")), new TextWrapperObject(65, 170, 4210752, TextUtils.guidebook("chapter.fluids.p1l14-2")), new TextWrapperObject(30, 180, 4210752, TextUtils.guidebook("chapter.fluids.p1l15-1")), new TextWrapperObject(65, 180, 4210752, TextUtils.guidebook("chapter.fluids.p1l15-2")) }));

		pages.add(new Page(new ImageWrapperObject[] { new ImageWrapperObject(12, 55, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/fluidio.png"), }, new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, TextUtils.guidebook("chapter.fluids.p2l1")), new TextWrapperObject(10, 140, 4210752, TextUtils.guidebook("chapter.fluids.p2l2")), new TextWrapperObject(10, 150, 4210752, TextUtils.guidebook("chapter.fluids.p2l3")), new TextWrapperObject(10, 160, 4210752, TextUtils.guidebook("chapter.fluids.p2l4")), new TextWrapperObject(10, 170, 4210752, TextUtils.guidebook("chapter.fluids.p2l5")), new TextWrapperObject(10, 180, 4210752, TextUtils.guidebook("chapter.fluids.p2l6")), }));

		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(10, 40, 4210752, TextUtils.guidebook("chapter.fluids.p3l1")), new TextWrapperObject(10, 50, 4210752, TextUtils.guidebook("chapter.fluids.p3l2")), new TextWrapperObject(10, 60, 4210752, TextUtils.guidebook("chapter.fluids.p3l3")), new TextWrapperObject(10, 70, 4210752, TextUtils.guidebook("chapter.fluids.p3l4")), new TextWrapperObject(10, 80, 4210752, TextUtils.guidebook("chapter.fluids.p3l5")), new TextWrapperObject(10, 90, 4210752, TextUtils.guidebook("chapter.fluids.p3l6")), new TextWrapperObject(10, 100, 4210752, TextUtils.guidebook("chapter.fluids.p3l7")), new TextWrapperObject(10, 110, 4210752, TextUtils.guidebook("chapter.fluids.p3l8")), new TextWrapperObject(10, 120, 4210752, TextUtils.guidebook("chapter.fluids.p3l9")), new TextWrapperObject(10, 130, 4210752, TextUtils.guidebook("chapter.fluids.p3l10")), new TextWrapperObject(10, 140, 4210752, TextUtils.guidebook("chapter.fluids.p3l11")), new TextWrapperObject(10, 150, 4210752, TextUtils.guidebook("chapter.fluids.p3l12")), new TextWrapperObject(10, 160, 4210752, TextUtils.guidebook("chapter.fluids.p3l13")), new TextWrapperObject(10, 170, 4210752, TextUtils.guidebook("chapter.fluids.p3l14")), new TextWrapperObject(10, 180, 4210752, TextUtils.guidebook("chapter.fluids.p3l15")) }));

		pages.add(new Page(new TextWrapperObject[] { new TextWrapperObject(30, 40, 4210752, TextUtils.guidebook("chapter.fluids.p4l1-1")), new TextWrapperObject(70, 40, 4210752, TextUtils.guidebook("chapter.fluids.p4l1-2")), new TextWrapperObject(30, 50, 4210752, TextUtils.guidebook("chapter.fluids.p4l2-1")), new TextWrapperObject(70, 50, 4210752, TextUtils.guidebook("chapter.fluids.p4l2-2")), new TextWrapperObject(10, 70, 4210752, TextUtils.guidebook("chapter.fluids.p4l3")), new TextWrapperObject(10, 80, 4210752, TextUtils.guidebook("chapter.fluids.p4l4")), new TextWrapperObject(10, 90, 4210752, TextUtils.guidebook("chapter.fluids.p4l5")), new TextWrapperObject(10, 100, 4210752, TextUtils.guidebook("chapter.fluids.p4l6")), new TextWrapperObject(10, 110, 4210752, TextUtils.guidebook("chapter.fluids.p4l7")), new TextWrapperObject(10, 120, 4210752, TextUtils.guidebook("chapter.fluids.p4l8")), new TextWrapperObject(10, 130, 4210752, TextUtils.guidebook("chapter.fluids.p4l9")) }));

		return pages;
	}

	@Override
	public ImageWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return TextUtils.guidebook("chapter.fluids");
	}

}
