package electrodynamics.client.guidebook.chapters;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.api.item.ItemUtils;
import electrodynamics.client.guidebook.utils.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.TextWrapperObject;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Page;
import electrodynamics.common.block.subtype.SubtypeOre;

public class ChapterOre extends Chapter {

	// private static final ImageWrapperObject LOGO = new ImageWrapperObject(10, 50, 0, 0, 32, 32, 32, 32, References.ID + ":textures/block/ore/oretin.png");
	private static final ItemWrapperObject LOGO = new ItemWrapperObject(17, 60, 2.0F, ItemUtils.fromBlock(DeferredRegisters.getSafeBlock(SubtypeOre.tin)));

	@Override
	protected List<Page> genPages() {
		// FOR SANITY'S SAKE, PLEASE LEAVE THIS SPACED OUT!!!!!!!!!!!!!!!!!!
		List<Page> pages = new ArrayList<>();

		pages.add(new Page(new ImageWrapperObject[] { new ImageWrapperObject(10, 45, 0, 0, 32, 32, 32, 32, References.ID + ":textures/block/ore/orealuminum.png"), new ImageWrapperObject(10, 80, 0, 0, 32, 32, 32, 32, References.ID + ":textures/block/ore/orechromite.png"), new ImageWrapperObject(10, 115, 0, 0, 32, 32, 32, 32, References.ID + ":textures/block/ore/orefluorite.png"), new ImageWrapperObject(10, 150, 0, 0, 32, 32, 32, 32, References.ID + ":textures/block/ore/orehalite.png") }, new TextWrapperObject[] { new TextWrapperObject(45, 47, 4210752, "guidebook.electrodynamics.chapter.ores.aluminumname"), new TextWrapperObject(45, 57, 4210752, "guidebook.electrodynamics.chapter.ores.aluminummaterial"), new TextWrapperObject(45, 67, 4210752, "guidebook.electrodynamics.chapter.ores.spawnrange", SubtypeOre.aluminum.maxY, SubtypeOre.aluminum.minY), new TextWrapperObject(45, 82, 4210752, "guidebook.electrodynamics.chapter.ores.chromitename"), new TextWrapperObject(45, 92, 4210752, "guidebook.electrodynamics.chapter.ores.chromitematerial"), new TextWrapperObject(45, 102, 4210752, "guidebook.electrodynamics.chapter.ores.spawnrange", SubtypeOre.chromite.maxY, SubtypeOre.chromite.minY), new TextWrapperObject(45, 117, 4210752, "guidebook.electrodynamics.chapter.ores.fluoritename"), new TextWrapperObject(45, 127, 4210752, "guidebook.electrodynamics.chapter.ores.fluoritematerial"), new TextWrapperObject(45, 137, 4210752, "guidebook.electrodynamics.chapter.ores.spawnrange", SubtypeOre.fluorite.maxY, SubtypeOre.fluorite.minY), new TextWrapperObject(45, 152, 4210752, "guidebook.electrodynamics.chapter.ores.halitename"), new TextWrapperObject(45, 162, 4210752, "guidebook.electrodynamics.chapter.ores.halitematerial"), new TextWrapperObject(45, 172, 4210752, "guidebook.electrodynamics.chapter.ores.spawnrange", SubtypeOre.halite.maxY, SubtypeOre.halite.minY) }));

		pages.add(new Page(new ImageWrapperObject[] { new ImageWrapperObject(10, 45, 0, 0, 32, 32, 32, 32, References.ID + ":textures/block/ore/orelead.png"), new ImageWrapperObject(10, 80, 0, 0, 32, 32, 32, 32, References.ID + ":textures/block/ore/orelepidolite.png"), new ImageWrapperObject(10, 115, 0, 0, 32, 32, 32, 32, References.ID + ":textures/block/ore/oremolybdenum.png"), new ImageWrapperObject(10, 150, 0, 0, 32, 32, 32, 32, References.ID + ":textures/block/ore/oremonazite.png") }, new TextWrapperObject[] { new TextWrapperObject(45, 47, 4210752, "guidebook.electrodynamics.chapter.ores.leadname"), new TextWrapperObject(45, 57, 4210752, "guidebook.electrodynamics.chapter.ores.leadmaterial"), new TextWrapperObject(45, 67, 4210752, "guidebook.electrodynamics.chapter.ores.spawnrange", SubtypeOre.lead.maxY, SubtypeOre.lead.minY), new TextWrapperObject(45, 82, 4210752, "guidebook.electrodynamics.chapter.ores.lepidolitename"), new TextWrapperObject(45, 92, 4210752, "guidebook.electrodynamics.chapter.ores.lepidolitematerial"), new TextWrapperObject(45, 102, 4210752, "guidebook.electrodynamics.chapter.ores.spawnrange", SubtypeOre.lepidolite.maxY, SubtypeOre.lepidolite.minY), new TextWrapperObject(45, 117, 4210752, "guidebook.electrodynamics.chapter.ores.molybdenumname"), new TextWrapperObject(45, 127, 4210752, "guidebook.electrodynamics.chapter.ores.molybdenummaterial"), new TextWrapperObject(45, 137, 4210752, "guidebook.electrodynamics.chapter.ores.spawnrange", SubtypeOre.molybdenum.maxY, SubtypeOre.molybdenum.minY), new TextWrapperObject(45, 152, 4210752, "guidebook.electrodynamics.chapter.ores.monazitename"), new TextWrapperObject(45, 162, 4210752, "guidebook.electrodynamics.chapter.ores.monazitematerial"), new TextWrapperObject(45, 172, 4210752, "guidebook.electrodynamics.chapter.ores.spawnrange", SubtypeOre.monazite.maxY, SubtypeOre.monazite.minY) }));

		pages.add(new Page(new ImageWrapperObject[] { new ImageWrapperObject(10, 45, 0, 0, 32, 32, 32, 32, References.ID + ":textures/block/ore/oreniter.png"), new ImageWrapperObject(10, 80, 0, 0, 32, 32, 32, 32, References.ID + ":textures/block/ore/orerutile.png"), new ImageWrapperObject(10, 115, 0, 0, 32, 32, 32, 32, References.ID + ":textures/block/ore/oresilver.png"), new ImageWrapperObject(10, 150, 0, 0, 32, 32, 32, 32, References.ID + ":textures/block/ore/oresulfur.png") }, new TextWrapperObject[] { new TextWrapperObject(45, 47, 4210752, "guidebook.electrodynamics.chapter.ores.nitername"), new TextWrapperObject(45, 57, 4210752, "guidebook.electrodynamics.chapter.ores.nitermaterial"), new TextWrapperObject(45, 67, 4210752, "guidebook.electrodynamics.chapter.ores.spawnrange", SubtypeOre.niter.maxY, SubtypeOre.niter.minY), new TextWrapperObject(45, 82, 4210752, "guidebook.electrodynamics.chapter.ores.rutilename"), new TextWrapperObject(45, 92, 4210752, "guidebook.electrodynamics.chapter.ores.rutilematerial"), new TextWrapperObject(45, 102, 4210752, "guidebook.electrodynamics.chapter.ores.spawnrange", SubtypeOre.rutile.maxY, SubtypeOre.rutile.minY), new TextWrapperObject(45, 117, 4210752, "guidebook.electrodynamics.chapter.ores.silvername"), new TextWrapperObject(45, 127, 4210752, "guidebook.electrodynamics.chapter.ores.silvermaterial"), new TextWrapperObject(45, 137, 4210752, "guidebook.electrodynamics.chapter.ores.spawnrange", SubtypeOre.silver.maxY, SubtypeOre.silver.minY), new TextWrapperObject(45, 152, 4210752, "guidebook.electrodynamics.chapter.ores.sulfurname"), new TextWrapperObject(45, 162, 4210752, "guidebook.electrodynamics.chapter.ores.sulfurmaterial"), new TextWrapperObject(45, 172, 4210752, "guidebook.electrodynamics.chapter.ores.spawnrange", SubtypeOre.sulfur.maxY, SubtypeOre.sulfur.minY) }));

		pages.add(new Page(new ImageWrapperObject[] { new ImageWrapperObject(10, 45, 0, 0, 32, 32, 32, 32, References.ID + ":textures/block/ore/oresylvite.png"), new ImageWrapperObject(10, 80, 0, 0, 32, 32, 32, 32, References.ID + ":textures/block/ore/orethorianite.png"), new ImageWrapperObject(10, 115, 0, 0, 32, 32, 32, 32, References.ID + ":textures/block/ore/oretin.png"), new ImageWrapperObject(10, 150, 0, 0, 32, 32, 32, 32, References.ID + ":textures/block/ore/oreuraninite.png") }, new TextWrapperObject[] { new TextWrapperObject(45, 47, 4210752, "guidebook.electrodynamics.chapter.ores.sylvitename"), new TextWrapperObject(45, 57, 4210752, "guidebook.electrodynamics.chapter.ores.sylvitematerial"), new TextWrapperObject(45, 67, 4210752, "guidebook.electrodynamics.chapter.ores.spawnrange", SubtypeOre.sylvite.maxY, SubtypeOre.sylvite.minY), new TextWrapperObject(45, 82, 4210752, "guidebook.electrodynamics.chapter.ores.thorianitename"), new TextWrapperObject(45, 92, 4210752, "guidebook.electrodynamics.chapter.ores.thorianitematerial"), new TextWrapperObject(45, 102, 4210752, "guidebook.electrodynamics.chapter.ores.spawnrange", SubtypeOre.thorianite.maxY, SubtypeOre.thorianite.minY), new TextWrapperObject(45, 117, 4210752, "guidebook.electrodynamics.chapter.ores.tinname"), new TextWrapperObject(45, 127, 4210752, "guidebook.electrodynamics.chapter.ores.tinmaterial"), new TextWrapperObject(45, 137, 4210752, "guidebook.electrodynamics.chapter.ores.spawnrange", SubtypeOre.tin.maxY, SubtypeOre.tin.minY), new TextWrapperObject(45, 152, 4210752, "guidebook.electrodynamics.chapter.ores.uraninitename"), new TextWrapperObject(45, 162, 4210752, "guidebook.electrodynamics.chapter.ores.uraninitematerial"), new TextWrapperObject(45, 172, 4210752, "guidebook.electrodynamics.chapter.ores.spawnrange", SubtypeOre.uraninite.maxY, SubtypeOre.uraninite.minY) }));

		pages.add(new Page(new ImageWrapperObject[] { new ImageWrapperObject(10, 45, 0, 0, 32, 32, 32, 32, References.ID + ":textures/block/ore/orevanadinite.png"), }, new TextWrapperObject[] { new TextWrapperObject(45, 47, 4210752, "guidebook.electrodynamics.chapter.ores.vanadinitename"), new TextWrapperObject(45, 57, 4210752, "guidebook.electrodynamics.chapter.ores.vanadinitematerial"), new TextWrapperObject(45, 67, 4210752, "guidebook.electrodynamics.chapter.ores.spawnrange", SubtypeOre.vanadinite.maxY, SubtypeOre.vanadinite.minY), }));

		return pages;
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public String getTitleKey() {
		return "guidebook.electrodynamics.chapter.ores";
	}

}
