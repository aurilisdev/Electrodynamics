package electrodynamics.client.guidebook;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.References;
import electrodynamics.client.guidebook.chapters.ChapterElectricity;
import electrodynamics.client.guidebook.chapters.ChapterFluids;
import electrodynamics.client.guidebook.chapters.ChapterGettingStarted;
import electrodynamics.client.guidebook.chapters.ChapterMachines;
import electrodynamics.client.guidebook.chapters.ChapterMisc;
import electrodynamics.client.guidebook.chapters.ChapterOre;
import electrodynamics.client.guidebook.chapters.ChapterTips;
import electrodynamics.client.guidebook.utils.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.prefab.utilities.TextUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ModuleElectrodynamics extends Module {

	private static final ImageWrapperObject LOGO = new ImageWrapperObject(10, 38, 0, 0, 32, 32, 32, 32, References.ID + ":textures/screen/guidebook/electrodynamicslogo.png");

	@Override
	public ImageWrapperObject getLogo() {
		return LOGO;
	}
	
	@Override
	protected List<Chapter> genChapters() {
		List<Chapter> chapters = new ArrayList<>();
		chapters.add(new ChapterGettingStarted());
		chapters.add(new ChapterOre());
		chapters.add(new ChapterElectricity());
		chapters.add(new ChapterFluids());
		chapters.add(new ChapterMachines());
		chapters.add(new ChapterMisc());
		chapters.add(new ChapterTips());
		return chapters;
	}

	@Override
	public boolean isFirst() {
		return true;
	}

	@Override
	public MutableComponent getTitle() {
		return TextUtils.gui(References.ID);
	}

}
