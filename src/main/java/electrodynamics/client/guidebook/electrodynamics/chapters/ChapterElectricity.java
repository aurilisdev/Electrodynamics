package electrodynamics.client.guidebook.electrodynamics.chapters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import electrodynamics.api.References;
import electrodynamics.client.guidebook.components.Chapter;
import electrodynamics.client.guidebook.components.Page;
import electrodynamics.client.guidebook.utils.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.TextWrapperObject;

public class ChapterElectricity extends Chapter {

	private static final ImageWrapperObject LOGO = new ImageWrapperObject(10, 50, 0, 0, 32, 32, 32, 32, References.ID + ":textures/item/wire/wireinsulatedgold.png");
	
	@Override
	protected List<Page> genPages() {
		//DO NOT COMPRESS
		List<Page> pages = new ArrayList<>();
		pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {
				
		}), Arrays.asList(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.electricity.p1l1"),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.electricity.p1l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.electricity.p1l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.electricity.p1l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.electricity.p1l5"),
			new TextWrapperObject(10, 90, 4210752, "guidebook.electrodynamics.chapter.electricity.p1l6"),
			new TextWrapperObject(10, 100, 4210752, "guidebook.electrodynamics.chapter.electricity.p1l7"),
			new TextWrapperObject(10, 110, 4210752, "guidebook.electrodynamics.chapter.electricity.p1l8"),
			new TextWrapperObject(10, 120, 4210752, "guidebook.electrodynamics.chapter.electricity.p1l9"),
			new TextWrapperObject(10, 130, 4210752, "guidebook.electrodynamics.chapter.electricity.p1l10"),
			new TextWrapperObject(10, 140, 4210752, "guidebook.electrodynamics.chapter.electricity.p1l11"),
			new TextWrapperObject(10, 150, 4210752, "guidebook.electrodynamics.chapter.electricity.p1l12"),
			new TextWrapperObject(10, 160, 4210752, "guidebook.electrodynamics.chapter.electricity.p1l13"),
			new TextWrapperObject(10, 170, 4210752, "guidebook.electrodynamics.chapter.electricity.p1l14"),
			new TextWrapperObject(10, 180, 4210752, "guidebook.electrodynamics.chapter.electricity.p1l15")	
		})));
		
		pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {
				
		}), Arrays.asList(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.electricity.p2l1"),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.electricity.p2l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.electricity.p2l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.electricity.p2l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.electricity.p2l5"),
			new TextWrapperObject(10, 90, 4210752, "guidebook.electrodynamics.chapter.electricity.p2l6"),
			new TextWrapperObject(10, 100, 4210752, "guidebook.electrodynamics.chapter.electricity.p2l7"),
			new TextWrapperObject(10, 110, 4210752, "guidebook.electrodynamics.chapter.electricity.p2l8"),
			new TextWrapperObject(10, 120, 4210752, "guidebook.electrodynamics.chapter.electricity.p2l9"),
			new TextWrapperObject(10, 130, 4210752, "guidebook.electrodynamics.chapter.electricity.p2l10"),
			new TextWrapperObject(10, 140, 4210752, "guidebook.electrodynamics.chapter.electricity.p2l11"),
			new TextWrapperObject(10, 150, 4210752, "guidebook.electrodynamics.chapter.electricity.p2l12"),
			new TextWrapperObject(10, 160, 4210752, "guidebook.electrodynamics.chapter.electricity.p2l13"),
			new TextWrapperObject(10, 170, 4210752, "guidebook.electrodynamics.chapter.electricity.p2l14"),
			new TextWrapperObject(10, 180, 4210752, "guidebook.electrodynamics.chapter.electricity.p2l15")
		})));
		
		pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {
				
		}), Arrays.asList(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.electricity.p3l1"),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.electricity.p3l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.electricity.p3l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.electricity.p3l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.electricity.p3l5"),
			new TextWrapperObject(10, 90, 4210752, "guidebook.electrodynamics.chapter.electricity.p3l6"),
			new TextWrapperObject(10, 100, 4210752, "guidebook.electrodynamics.chapter.electricity.p3l7"),
			new TextWrapperObject(10, 110, 4210752, "guidebook.electrodynamics.chapter.electricity.p3l8"),
			new TextWrapperObject(10, 120, 4210752, "guidebook.electrodynamics.chapter.electricity.p3l9"),
			new TextWrapperObject(10, 130, 4210752, "guidebook.electrodynamics.chapter.electricity.p3l10"),
			new TextWrapperObject(10, 140, 4210752, "guidebook.electrodynamics.chapter.electricity.p3l11"),
			new TextWrapperObject(10, 150, 4210752, "guidebook.electrodynamics.chapter.electricity.p3l12"),
			new TextWrapperObject(10, 160, 4210752, "guidebook.electrodynamics.chapter.electricity.p3l13"),
			new TextWrapperObject(65, 100, 4210752, "guidebook.electrodynamics.chapter.electricity.voltageval", 120),
			new TextWrapperObject(65, 110, 4210752, "guidebook.electrodynamics.chapter.electricity.voltageval", 240),
			new TextWrapperObject(65, 120, 4210752, "guidebook.electrodynamics.chapter.electricity.voltageval", 480),
			new TextWrapperObject(65, 130, 4210752, "guidebook.electrodynamics.chapter.electricity.voltageval", 960)
		})));
		
		pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {
			new ImageWrapperObject(12, 45, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/120vmachines.png"),	
		}), Arrays.asList(new TextWrapperObject[] {
			new TextWrapperObject(10, 130, 4210752, "guidebook.electrodynamics.chapter.electricity.p4l1"),
			new TextWrapperObject(10, 140, 4210752, "guidebook.electrodynamics.chapter.electricity.p4l2"),
			new TextWrapperObject(10, 150, 4210752, "guidebook.electrodynamics.chapter.electricity.p4l3"),
		})));
		
		pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {
			new ImageWrapperObject(12, 45, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/240vmachines.png"),	
		}), Arrays.asList(new TextWrapperObject[] {
			new TextWrapperObject(10, 130, 4210752, "guidebook.electrodynamics.chapter.electricity.p5l1"),
		})));
		
		pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {
			new ImageWrapperObject(12, 45, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/480vmachines.png"),	
		}), Arrays.asList(new TextWrapperObject[] {
			new TextWrapperObject(10, 130, 4210752, "guidebook.electrodynamics.chapter.electricity.p6l1"),
		})));
		
		pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {
			new ImageWrapperObject(12, 45, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/960vmachines.png"),	
		}), Arrays.asList(new TextWrapperObject[] {
			new TextWrapperObject(10, 130, 4210752, "guidebook.electrodynamics.chapter.electricity.p7l1"),
		})));
		
		pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {
				
		}), Arrays.asList(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.electricity.p8l1"),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.electricity.p8l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.electricity.p8l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.electricity.p8l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.electricity.p8l5"),
			new TextWrapperObject(10, 90, 4210752, "guidebook.electrodynamics.chapter.electricity.p8l6"),
			new TextWrapperObject(10, 100, 4210752, "guidebook.electrodynamics.chapter.electricity.p8l7"),
			new TextWrapperObject(10, 110, 4210752, "guidebook.electrodynamics.chapter.electricity.p8l8"),
			new TextWrapperObject(10, 120, 4210752, "guidebook.electrodynamics.chapter.electricity.p8l9"),
			new TextWrapperObject(10, 130, 4210752, "guidebook.electrodynamics.chapter.electricity.p8l10")
		})));
		
		pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {
			new ImageWrapperObject(12, 45, 0, 0, 150, 50, 150, 50, References.ID + ":textures/screen/guidebook/voltagetooltip1.png"),	
			new ImageWrapperObject(12, 100, 0, 0, 150, 55, 150, 55, References.ID + ":textures/screen/guidebook/voltagetooltip2.png")	
		}), Arrays.asList(new TextWrapperObject[] {
			new TextWrapperObject(10, 160, 4210752, "guidebook.electrodynamics.chapter.electricity.p9l1"),
			new TextWrapperObject(10, 170, 4210752, "guidebook.electrodynamics.chapter.electricity.p9l2")
		})));
		
        pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {
				
		}), Arrays.asList(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.electricity.p10l1"),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.electricity.p10l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.electricity.p10l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.electricity.p10l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.electricity.p10l5"),
			new TextWrapperObject(10, 90, 4210752, "guidebook.electrodynamics.chapter.electricity.p10l6"),
			new TextWrapperObject(10, 100, 4210752, "guidebook.electrodynamics.chapter.electricity.p10l7"),
			new TextWrapperObject(10, 110, 4210752, "guidebook.electrodynamics.chapter.electricity.p10l8"),
			new TextWrapperObject(10, 120, 4210752, "guidebook.electrodynamics.chapter.electricity.p10l9"),
			new TextWrapperObject(10, 130, 4210752, "guidebook.electrodynamics.chapter.electricity.p10l10"),
			new TextWrapperObject(10, 140, 4210752, "guidebook.electrodynamics.chapter.electricity.p10l11"),
			new TextWrapperObject(10, 150, 4210752, "guidebook.electrodynamics.chapter.electricity.p10l12"),
			new TextWrapperObject(10, 160, 4210752, "guidebook.electrodynamics.chapter.electricity.p10l13"),
			new TextWrapperObject(10, 170, 4210752, "guidebook.electrodynamics.chapter.electricity.p10l14"),
			new TextWrapperObject(10, 180, 4210752, "guidebook.electrodynamics.chapter.electricity.p10l15")
		})));
        
        pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {
        	new ImageWrapperObject(12, 110, 0, 0, 150, 79, 150, 79, References.ID + ":textures/screen/guidebook/energyio.png"),	
		}), Arrays.asList(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.electricity.p11l1"),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.electricity.p11l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.electricity.p11l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.electricity.p11l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.electricity.p11l5"),
			new TextWrapperObject(10, 90, 4210752, "guidebook.electrodynamics.chapter.electricity.p11l6"),
			new TextWrapperObject(10, 100, 4210752, "guidebook.electrodynamics.chapter.electricity.p11l7")
		})));
        
        pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {
				
		}), Arrays.asList(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.electricity.p12l1"),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.electricity.p12l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.electricity.p12l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.electricity.p12l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.electricity.p12l5"),
			new TextWrapperObject(10, 90, 4210752, "guidebook.electrodynamics.chapter.electricity.p12l6"),
			new TextWrapperObject(10, 100, 4210752, "guidebook.electrodynamics.chapter.electricity.p12l7"),
			new TextWrapperObject(10, 110, 4210752, "guidebook.electrodynamics.chapter.electricity.p12l8"),
			new TextWrapperObject(10, 120, 4210752, "guidebook.electrodynamics.chapter.electricity.p12l9"),
			new TextWrapperObject(10, 130, 4210752, "guidebook.electrodynamics.chapter.electricity.p12l10"),
			new TextWrapperObject(10, 140, 4210752, "guidebook.electrodynamics.chapter.electricity.p12l11"),
			new TextWrapperObject(10, 150, 4210752, "guidebook.electrodynamics.chapter.electricity.p12l12"),
			new TextWrapperObject(10, 160, 4210752, "guidebook.electrodynamics.chapter.electricity.p12l13"),
			new TextWrapperObject(10, 170, 4210752, "guidebook.electrodynamics.chapter.electricity.p12l14"),
			new TextWrapperObject(10, 180, 4210752, "guidebook.electrodynamics.chapter.electricity.p12l15")
		})));
        
        pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {
				
		}), Arrays.asList(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.electricity.p13l1"),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.electricity.p13l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.electricity.p13l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.electricity.p13l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.electricity.p13l5"),
			new TextWrapperObject(10, 90, 4210752, "guidebook.electrodynamics.chapter.electricity.p13l6"),
			new TextWrapperObject(10, 100, 4210752, "guidebook.electrodynamics.chapter.electricity.p13l7"),
			new TextWrapperObject(10, 110, 4210752, "guidebook.electrodynamics.chapter.electricity.p13l8"),
			new TextWrapperObject(10, 120, 4210752, "guidebook.electrodynamics.chapter.electricity.p13l9"),
			new TextWrapperObject(10, 130, 4210752, "guidebook.electrodynamics.chapter.electricity.p13l10"),
			new TextWrapperObject(10, 140, 4210752, "guidebook.electrodynamics.chapter.electricity.p13l11"),
			new TextWrapperObject(10, 150, 4210752, "guidebook.electrodynamics.chapter.electricity.p13l12"),
			new TextWrapperObject(10, 160, 4210752, "guidebook.electrodynamics.chapter.electricity.p13l13"),
			new TextWrapperObject(10, 170, 4210752, "guidebook.electrodynamics.chapter.electricity.p13l14"),
			new TextWrapperObject(10, 180, 4210752, "guidebook.electrodynamics.chapter.electricity.p13l15")
		})));
        
        pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {
				
		}), Arrays.asList(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.electricity.p14l1"),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.electricity.p14l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.electricity.p14l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.electricity.p14l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.electricity.p14l5"),
			new TextWrapperObject(10, 90, 4210752, "guidebook.electrodynamics.chapter.electricity.p14l6"),
			new TextWrapperObject(10, 100, 4210752, "guidebook.electrodynamics.chapter.electricity.p14l7"),
			new TextWrapperObject(10, 110, 4210752, "guidebook.electrodynamics.chapter.electricity.p14l8"),
			new TextWrapperObject(10, 120, 4210752, "guidebook.electrodynamics.chapter.electricity.p14l9"),
			new TextWrapperObject(10, 130, 4210752, "guidebook.electrodynamics.chapter.electricity.p14l10"),
			new TextWrapperObject(10, 140, 4210752, "guidebook.electrodynamics.chapter.electricity.p14l11"),
			new TextWrapperObject(10, 150, 4210752, "guidebook.electrodynamics.chapter.electricity.p14l12"),
			new TextWrapperObject(10, 160, 4210752, "guidebook.electrodynamics.chapter.electricity.p14l13"),
			new TextWrapperObject(10, 170, 4210752, "guidebook.electrodynamics.chapter.electricity.p14l14"),
			new TextWrapperObject(10, 180, 4210752, "guidebook.electrodynamics.chapter.electricity.p14l15")
		})));
		
        pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {
				
		}), Arrays.asList(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.electricity.p15l1"),
			new TextWrapperObject(10, 50, 4210752, "guidebook.electrodynamics.chapter.electricity.p15l2"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.electricity.p15l3"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.electricity.p15l4"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.electricity.p15l5"),
			new TextWrapperObject(10, 90, 4210752, "guidebook.electrodynamics.chapter.electricity.p15l6"),
			new TextWrapperObject(10, 100, 4210752, "guidebook.electrodynamics.chapter.electricity.p15l7"),
			new TextWrapperObject(10, 110, 4210752, "guidebook.electrodynamics.chapter.electricity.p15l8"),
			new TextWrapperObject(10, 120, 4210752, "guidebook.electrodynamics.chapter.electricity.p15l9"),
			new TextWrapperObject(10, 130, 4210752, "guidebook.electrodynamics.chapter.electricity.p15l10"),
			new TextWrapperObject(10, 140, 4210752, "guidebook.electrodynamics.chapter.electricity.p15l11"),
			new TextWrapperObject(10, 150, 4210752, "guidebook.electrodynamics.chapter.electricity.p15l12"),
			new TextWrapperObject(10, 160, 4210752, "guidebook.electrodynamics.chapter.electricity.p15l13"),
			new TextWrapperObject(10, 170, 4210752, "guidebook.electrodynamics.chapter.electricity.p15l14"),
			new TextWrapperObject(10, 180, 4210752, "guidebook.electrodynamics.chapter.electricity.p15l15")
		})));
        
        pages.add(new Page(Arrays.asList(new ImageWrapperObject[] {
				
		}), Arrays.asList(new TextWrapperObject[] {
			new TextWrapperObject(10, 40, 4210752, "guidebook.electrodynamics.chapter.electricity.p16l1"),
			new TextWrapperObject(10, 60, 4210752, "guidebook.electrodynamics.chapter.electricity.p16l2"),
			new TextWrapperObject(10, 70, 4210752, "guidebook.electrodynamics.chapter.electricity.p16l3"),
			new TextWrapperObject(10, 80, 4210752, "guidebook.electrodynamics.chapter.electricity.p16l4"),
			new TextWrapperObject(10, 90, 4210752, "guidebook.electrodynamics.chapter.electricity.p16l5"),
			new TextWrapperObject(10, 100, 4210752, "guidebook.electrodynamics.chapter.electricity.p16l6"),
		})));
        
		return pages;
	}

	@Override
	public ImageWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public String getTitleKey() {
		return "guidebook.electrodynamics.chapter.electricity";
	}


}
