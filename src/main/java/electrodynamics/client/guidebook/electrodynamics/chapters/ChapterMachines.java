package electrodynamics.client.guidebook.electrodynamics.chapters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import electrodynamics.api.References;
import electrodynamics.client.guidebook.components.Chapter;
import electrodynamics.client.guidebook.components.Page;
import electrodynamics.client.guidebook.utils.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.TextWrapperObject;
import net.minecraft.ChatFormatting;

public class ChapterMachines extends Chapter {
	
	private static final ImageWrapperObject LOGO = new ImageWrapperObject(10, 50, 0, 0, 32, 32, 32, 32, References.ID + ":textures/item/motor.png");
	
	@Override
	protected List<Page> genPages() {
		//DO NOT COMPRESS
		List<Page> pages = new ArrayList<>();
		
		pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {
			new ImageWrapperObject(12, 110, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/markerring.png"),		
		}), Arrays.asList(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.machines.p1l1").setTextStyles(ChatFormatting.UNDERLINE),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.machines.p1l2-1").setTextStyles(ChatFormatting.BOLD),
			new TextWrapperObject(55, 50, 4210752, "guidebook.electrodynamics.chapter.machines.p1l2-2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.machines.p1l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.machines.p1l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.machines.p1l5"),
			new TextWrapperObject(10, 90, 4210752, "guidebook.electrodynamics.chapter.machines.p1l6"),
			new TextWrapperObject(10, 100, 4210752, "guidebook.electrodynamics.chapter.machines.p1l7"),
		})));
		
		pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {
			new ImageWrapperObject(12, 110, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/seismicrelay1.png")
		}), Arrays.asList(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.machines.p2l1-1").setTextStyles(ChatFormatting.BOLD),
			new TextWrapperObject(55, 40, 4210752, "guidebook.electrodynamics.chapter.machines.p2l1-2"),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.machines.p2l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.machines.p2l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.machines.p2l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.machines.p2l5"),
			new TextWrapperObject(10, 90, 4210752, "guidebook.electrodynamics.chapter.machines.p2l6"),
			new TextWrapperObject(10, 100, 4210752, "guidebook.electrodynamics.chapter.machines.p2l7"),
		})));
		
		pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {
			new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/seismicrelay2.png"),
			new ImageWrapperObject(12, 117, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/seismicrelay3.png")
		}), Arrays.asList(new TextWrapperObject[] {
			
		})));
		
		pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {
			new ImageWrapperObject(12, 110, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/quarryplacement.png")
		}), Arrays.asList(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.machines.p4l1-1").setTextStyles(ChatFormatting.BOLD),
			new TextWrapperObject(55, 40, 4210752, "guidebook.electrodynamics.chapter.machines.p4l1-2"),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.machines.p4l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.machines.p4l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.machines.p4l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.machines.p4l5")
		})));
		
		pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {
			new ImageWrapperObject(12, 110, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/coolantresplacement.png")
		}), Arrays.asList(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.machines.p5l1-1").setTextStyles(ChatFormatting.BOLD),
			new TextWrapperObject(55, 40, 4210752, "guidebook.electrodynamics.chapter.machines.p5l1-2"),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.machines.p5l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.machines.p5l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.machines.p5l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.machines.p5l5"),
			new TextWrapperObject(10, 90, 4210752, "guidebook.electrodynamics.chapter.machines.p5l6")
		})));
		
		pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {
				
		}), Arrays.asList(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.machines.p6l1-1").setTextStyles(ChatFormatting.BOLD),
			new TextWrapperObject(55, 40, 4210752, "guidebook.electrodynamics.chapter.machines.p6l1-2"),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.machines.p6l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.machines.p6l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.machines.p6l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.machines.p6l5"),
			new TextWrapperObject(10, 90, 4210752, "guidebook.electrodynamics.chapter.machines.p6l6"),
			new TextWrapperObject(10, 100, 4210752, "guidebook.electrodynamics.chapter.machines.p6l7"),
			new TextWrapperObject(10, 110, 4210752, "guidebook.electrodynamics.chapter.machines.p6l8"),
			new TextWrapperObject(10, 120, 4210752, "guidebook.electrodynamics.chapter.machines.p6l9"),
			new TextWrapperObject(10, 130, 4210752, "guidebook.electrodynamics.chapter.machines.p6l10"),
			new TextWrapperObject(10, 140, 4210752, "guidebook.electrodynamics.chapter.machines.p6l11"),
			new TextWrapperObject(10, 150, 4210752, "guidebook.electrodynamics.chapter.machines.p6l12"),
			new TextWrapperObject(10, 160, 4210752, "guidebook.electrodynamics.chapter.machines.p6l13"),
			new TextWrapperObject(10, 170, 4210752, "guidebook.electrodynamics.chapter.machines.p6l14")
		})));
		
		pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {
			new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/motorcomplex1.png"),
			new ImageWrapperObject(12, 117, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/motorcomplex2.png")
		}), Arrays.asList(new TextWrapperObject[] {
			
		})));
		
		pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {

		}), Arrays.asList(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.machines.p8l1-1").setTextStyles(ChatFormatting.BOLD),
			new TextWrapperObject(55, 40, 4210752, "guidebook.electrodynamics.chapter.machines.p8l1-2"),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.machines.p8l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.machines.p8l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.machines.p8l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.machines.p8l5"),
			new TextWrapperObject(10, 90, 4210752, "guidebook.electrodynamics.chapter.machines.p8l6"),
			new TextWrapperObject(10, 100, 4210752, "guidebook.electrodynamics.chapter.machines.p8l7"),
			new TextWrapperObject(10, 110, 4210752, "guidebook.electrodynamics.chapter.machines.p8l8"),
			new TextWrapperObject(10, 120, 4210752, "guidebook.electrodynamics.chapter.machines.p8l9")
		})));
		
		pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {
			new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/quarrypower1.png"),
			new ImageWrapperObject(12, 117, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/quarrypower2.png")
		}), Arrays.asList(new TextWrapperObject[] {
			
		})));
		
		pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {
			new ImageWrapperObject(12, 38, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/quarrypower3.png")
		}), Arrays.asList(new TextWrapperObject[] {
			
		})));
		
		pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {

		}), Arrays.asList(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.machines.p11l1-1").setTextStyles(ChatFormatting.BOLD),
			new TextWrapperObject(55, 40, 4210752, "guidebook.electrodynamics.chapter.machines.p11l1-2"),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.machines.p11l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.machines.p11l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.machines.p11l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.machines.p11l5"),
			new TextWrapperObject(30, 90, 4210752, "guidebook.electrodynamics.chapter.machines.p11l6"),
			new TextWrapperObject(85, 90, 4210752, "guidebook.electrodynamics.chapter.machines.durability", 200),
			new TextWrapperObject(30, 100, 4210752, "guidebook.electrodynamics.chapter.machines.p11l7"),
			new TextWrapperObject(85, 100, 4210752, "guidebook.electrodynamics.chapter.machines.durability", 400),
			new TextWrapperObject(30, 110, 4210752, "guidebook.electrodynamics.chapter.machines.p11l8"),
			new TextWrapperObject(85, 110, 4210752, "guidebook.electrodynamics.chapter.machines.durability", 600),
			new TextWrapperObject(30, 120, 4210752, "guidebook.electrodynamics.chapter.machines.p11l9"),
			new TextWrapperObject(85, 120, 4210752, "guidebook.electrodynamics.chapter.machines.durability", 1000),
			new TextWrapperObject(30, 130, 4210752, "guidebook.electrodynamics.chapter.machines.p11l10"),
			new TextWrapperObject(85, 130, 4210752, "guidebook.electrodynamics.chapter.machines.durability", "Infinite"),
			new TextWrapperObject(10, 140, 4210752, "guidebook.electrodynamics.chapter.machines.p11l11"),
			new TextWrapperObject(10, 150, 4210752, "guidebook.electrodynamics.chapter.machines.p11l12"),
			new TextWrapperObject(10, 160, 4210752, "guidebook.electrodynamics.chapter.machines.p11l13"),
			new TextWrapperObject(10, 170, 4210752, "guidebook.electrodynamics.chapter.machines.p11l14"),
			new TextWrapperObject(10, 180, 4210752, "guidebook.electrodynamics.chapter.machines.p11l15"),
		})));
		
		pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {

		}), Arrays.asList(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.machines.p12l1"),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.machines.p12l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.machines.p12l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.machines.p12l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.machines.p12l5"),
			new TextWrapperObject(10, 90, 4210752, "guidebook.electrodynamics.chapter.machines.p12l6"),
			new TextWrapperObject(10, 100, 4210752, "guidebook.electrodynamics.chapter.machines.p12l7"),
			new TextWrapperObject(10, 110, 4210752, "guidebook.electrodynamics.chapter.machines.p12l8"),
			new TextWrapperObject(10, 120, 4210752, "guidebook.electrodynamics.chapter.machines.p12l9-1"),
			new TextWrapperObject(31, 120, 4210752, "guidebook.electrodynamics.chapter.machines.p12l9-2").setTextStyles(ChatFormatting.BOLD),
			new TextWrapperObject(58, 120, 4210752, "guidebook.electrodynamics.chapter.machines.p12l9-3"),
			new TextWrapperObject(10, 130, 4210752, "guidebook.electrodynamics.chapter.machines.p12l10"),
			new TextWrapperObject(10, 140, 4210752, "guidebook.electrodynamics.chapter.machines.p12l11"),
			new TextWrapperObject(10, 150, 4210752, "guidebook.electrodynamics.chapter.machines.p12l12"),
			new TextWrapperObject(10, 160, 4210752, "guidebook.electrodynamics.chapter.machines.p12l13"),
			new TextWrapperObject(10, 170, 4210752, "guidebook.electrodynamics.chapter.machines.p12l14"),
			new TextWrapperObject(10, 180, 4210752, "guidebook.electrodynamics.chapter.machines.p12l15"),
		})));
		
		pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {

		}), Arrays.asList(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.machines.p13l1-1").setTextStyles(ChatFormatting.BOLD),
			new TextWrapperObject(60, 40, 4210752, "guidebook.electrodynamics.chapter.machines.p13l1-2"),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.machines.p13l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.machines.p13l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.machines.p13l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.machines.p13l5"),
			new TextWrapperObject(10, 90, 4210752, "guidebook.electrodynamics.chapter.machines.p13l6"),
			new TextWrapperObject(10, 100, 4210752, "guidebook.electrodynamics.chapter.machines.p13l7-1"),
			new TextWrapperObject(80, 100, 4210752, "guidebook.electrodynamics.chapter.machines.p13l7-2").setTextStyles(ChatFormatting.BOLD),
			new TextWrapperObject(10, 110, 4210752, "guidebook.electrodynamics.chapter.machines.p13l8"),
			new TextWrapperObject(10, 120, 4210752, "guidebook.electrodynamics.chapter.machines.p13l9"),
			new TextWrapperObject(10, 130, 4210752, "guidebook.electrodynamics.chapter.machines.p13l10"),
			new TextWrapperObject(10, 140, 4210752, "guidebook.electrodynamics.chapter.machines.p13l11"),
			new TextWrapperObject(10, 150, 4210752, "guidebook.electrodynamics.chapter.machines.p13l12"),
			new TextWrapperObject(10, 160, 4210752, "guidebook.electrodynamics.chapter.machines.p13l13"),
			new TextWrapperObject(10, 170, 4210752, "guidebook.electrodynamics.chapter.machines.p13l14"),
			new TextWrapperObject(10, 180, 4210752, "guidebook.electrodynamics.chapter.machines.p13l15"),
		})));
		
		pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {

		}), Arrays.asList(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.machines.p14l1-1").setTextStyles(ChatFormatting.BOLD),
			new TextWrapperObject(70, 40, 4210752, "guidebook.electrodynamics.chapter.machines.p14l1-2"),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.machines.p14l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.machines.p14l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.machines.p14l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.machines.p14l5"),
			new TextWrapperObject(10, 90, 4210752, "guidebook.electrodynamics.chapter.machines.p14l6"),
		})));
		
		return pages;
	}

	@Override
	public ImageWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public String getTitleKey() {
		return "guidebook.electrodynamics.chapter.machines";
	}

}
