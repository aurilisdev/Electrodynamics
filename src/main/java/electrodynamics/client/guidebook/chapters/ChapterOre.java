package electrodynamics.client.guidebook.chapters;

import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.ImageWrapperObject.ImageTextDescriptor;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import electrodynamics.prefab.utilities.TextUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.network.chat.MutableComponent;

public class ChapterOre extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOre.tin));

	public ChapterOre(Module module) {
		super(module);
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return TextUtils.guidebook("chapter.ores");
	}

	@Override
	public void addData() {
		
		//Regular Ores
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOre.aluminum), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.aluminum).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_aluminum"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.aluminum.minY, SubtypeOre.aluminum.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOre.chromite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.chromite).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_chromium"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.chromite.minY, SubtypeOre.chromite.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOre.fluorite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.fluorite).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_fluorite"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.fluorite.minY, SubtypeOre.fluorite.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOre.halite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.halite).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_salt"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.halite.minY, SubtypeOre.halite.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOre.lead), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.lead).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_lead"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.lead.minY, SubtypeOre.lead.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOre.lepidolite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.lepidolite).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_lithium"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.lepidolite.minY, SubtypeOre.lepidolite.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOre.molybdenum), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.molybdenum).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_molybdenum"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.molybdenum.minY, SubtypeOre.molybdenum.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOre.monazite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.monazite).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_monazite"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.monazite.minY, SubtypeOre.monazite.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOre.niter), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.niter).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_niter"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.niter.minY, SubtypeOre.niter.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOre.rutile), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.rutile).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_titanium"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.rutile.minY, SubtypeOre.rutile.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOre.silver), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.silver).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_silver"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.silver.minY, SubtypeOre.silver.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOre.sulfur), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.sulfur).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_sulfur"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.sulfur.minY, SubtypeOre.sulfur.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOre.sylvite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.sylvite).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_sylvite"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.sylvite.minY, SubtypeOre.sylvite.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOre.thorianite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.thorianite).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_thorium"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.thorianite.minY, SubtypeOre.thorianite.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOre.tin), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.tin).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_tin"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.tin.minY, SubtypeOre.tin.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOre.uraninite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.uraninite).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_uranium"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.uraninite.minY, SubtypeOre.uraninite.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOre.vanadinite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.vanadinite).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_vanadium"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.vanadinite.minY, SubtypeOre.vanadinite.maxY))));
		
		//Deep Ores
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.aluminum), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.aluminum).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_aluminum"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.aluminum.minY, SubtypeOreDeepslate.aluminum.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.chromite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.chromite).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_chromium"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.chromite.minY, SubtypeOreDeepslate.chromite.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.fluorite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.fluorite).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_fluorite"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.fluorite.minY, SubtypeOreDeepslate.fluorite.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.halite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.halite).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_salt"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.halite.minY, SubtypeOreDeepslate.halite.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.lead), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.lead).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_lead"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.lead.minY, SubtypeOreDeepslate.lead.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.lepidolite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.lepidolite).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_lithium"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.lepidolite.minY, SubtypeOreDeepslate.lepidolite.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.molybdenum), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.molybdenum).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_molybdenum"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.molybdenum.minY, SubtypeOreDeepslate.molybdenum.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.monazite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.monazite).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_monazite"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.monazite.minY, SubtypeOreDeepslate.monazite.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.niter), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.niter).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_niter"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.niter.minY, SubtypeOreDeepslate.niter.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.rutile), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.rutile).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_titanium"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.rutile.minY, SubtypeOreDeepslate.rutile.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.silver), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.silver).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_silver"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.silver.minY, SubtypeOreDeepslate.silver.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.sulfur), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.sulfur).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_sulfur"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.sulfur.minY, SubtypeOreDeepslate.sulfur.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.sylvite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.sylvite).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_sylvite"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.sylvite.minY, SubtypeOreDeepslate.sylvite.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.thorianite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.thorianite).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_thorium"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.thorianite.minY, SubtypeOreDeepslate.thorianite.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.tin), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.tin).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_tin"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.tin.minY, SubtypeOreDeepslate.tin.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.uraninite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.uraninite).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_uranium"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.uraninite.minY, SubtypeOreDeepslate.uraninite.maxY))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 37, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.vanadinite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.vanadinite).getDescription()), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_vanadium"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.vanadinite.minY, SubtypeOreDeepslate.vanadinite.maxY))));
		
	}

}
