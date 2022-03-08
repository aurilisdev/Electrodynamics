package electrodynamics.client.guidebook.electrodynamics;

import java.util.ArrayList;
import java.util.List;
import electrodynamics.api.References;
import electrodynamics.client.guidebook.components.Chapter;
import electrodynamics.client.guidebook.components.Module;
import electrodynamics.client.guidebook.electrodynamics.chapters.ChapterElectricity;
import electrodynamics.client.guidebook.electrodynamics.chapters.ChapterFluids;
import electrodynamics.client.guidebook.electrodynamics.chapters.ChapterGettingStarted;
import electrodynamics.client.guidebook.electrodynamics.chapters.ChapterMachines;
import electrodynamics.client.guidebook.electrodynamics.chapters.ChapterMisc;
import electrodynamics.client.guidebook.electrodynamics.chapters.ChapterOre;
import electrodynamics.client.guidebook.electrodynamics.chapters.ChapterTips;
import electrodynamics.client.guidebook.utils.ImageWrapperObject;

public class ModuleElectrodynamics extends Module {

	private static final ImageWrapperObject LOGO = new ImageWrapperObject(10, 38, 0, 0, 32, 32, 32, 32, References.ID + ":textures/screen/guidebook/electrodynamicslogo.png");
	
	@Override
	public ImageWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public String getTitleKey() {
		return "guidebook.electrodynamics.moduletitle";
	}
	
	@Override
	protected List<Chapter> genChapters() {
		List<Chapter> chapters = new ArrayList<>();
		chapters.add(new ChapterOre());
		chapters.add(new ChapterElectricity());
		chapters.add(new ChapterFluids());
		chapters.add(new ChapterMachines());
		chapters.add(new ChapterMisc());
		chapters.add(new ChapterGettingStarted());
		chapters.add(new ChapterTips());
		return chapters;
	}

}
