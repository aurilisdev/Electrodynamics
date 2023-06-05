package electrodynamics.client.guidebook.chapters;

import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.ImageWrapperObject.ImageTextDescriptor;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import electrodynamics.prefab.utilities.TextUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.ChatFormatting;
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
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOre.aluminum), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.aluminum).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_aluminum"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.aluminum.minY, SubtypeOre.aluminum.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.aluminum.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.aluminum.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.aluminum.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOre.chromite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.chromite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_chromium"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.chromite.minY, SubtypeOre.chromite.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.chromite.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.chromite.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.chromite.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOre.fluorite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.fluorite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_fluorite"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.fluorite.minY, SubtypeOre.fluorite.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.fluorite.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.fluorite.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.fluorite.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOre.halite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.halite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_salt"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.halite.minY, SubtypeOre.halite.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.halite.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.halite.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.halite.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOre.lead), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.lead).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_lead"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.lead.minY, SubtypeOre.lead.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.lead.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.lead.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.lead.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOre.lepidolite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.lepidolite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_lithium"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.lepidolite.minY, SubtypeOre.lepidolite.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.lepidolite.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.lepidolite.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.lepidolite.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOre.molybdenum), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.molybdenum).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_molybdenum"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.molybdenum.minY, SubtypeOre.molybdenum.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.molybdenum.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.molybdenum.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.molybdenum.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOre.monazite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.monazite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_monazite"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.monazite.minY, SubtypeOre.monazite.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.monazite.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.monazite.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.monazite.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOre.niter), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.niter).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_niter"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.niter.minY, SubtypeOre.niter.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.niter.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.niter.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.niter.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOre.rutile), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.rutile).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_titanium"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.rutile.minY, SubtypeOre.rutile.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.rutile.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.rutile.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.rutile.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOre.silver), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.silver).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_silver"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.silver.minY, SubtypeOre.silver.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.silver.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.silver.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.silver.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOre.sulfur), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.sulfur).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_sulfur"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.sulfur.minY, SubtypeOre.sulfur.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.sulfur.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.sulfur.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.sulfur.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOre.sylvite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.sylvite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_sylvite"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.sylvite.minY, SubtypeOre.sylvite.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.sylvite.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.sylvite.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.sylvite.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOre.thorianite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.thorianite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_thorium"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.thorianite.minY, SubtypeOre.thorianite.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.thorianite.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.thorianite.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.thorianite.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOre.tin), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.tin).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_tin"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.tin.minY, SubtypeOre.tin.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.tin.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.tin.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.tin.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOre.uraninite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.uraninite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_uranium"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.uraninite.minY, SubtypeOre.uraninite.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.uraninite.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.uraninite.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.uraninite.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOre.vanadinite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOre.vanadinite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_vanadium"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOre.vanadinite.minY, SubtypeOre.vanadinite.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOre.vanadinite.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOre.vanadinite.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOre.vanadinite.harvestLevel))));
		
		//Deep Ores
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.aluminum), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.aluminum).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_aluminum"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.aluminum.minY, SubtypeOreDeepslate.aluminum.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.aluminum.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.aluminum.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.aluminum.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.chromite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.chromite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_chromium"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.chromite.minY, SubtypeOreDeepslate.chromite.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.chromite.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.chromite.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.chromite.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.fluorite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.fluorite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_fluorite"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.fluorite.minY, SubtypeOreDeepslate.fluorite.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.fluorite.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.fluorite.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.fluorite.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.halite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.halite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_salt"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.halite.minY, SubtypeOreDeepslate.halite.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.halite.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.halite.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.halite.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.lead), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.lead).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_lead"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.lead.minY, SubtypeOreDeepslate.lead.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.lead.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.lead.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.lead.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.lepidolite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.lepidolite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_lithium"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.lepidolite.minY, SubtypeOreDeepslate.lepidolite.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.lepidolite.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.lepidolite.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.lepidolite.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.molybdenum), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.molybdenum).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_molybdenum"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.molybdenum.minY, SubtypeOreDeepslate.molybdenum.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.molybdenum.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.molybdenum.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.molybdenum.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.monazite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.monazite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_monazite"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.monazite.minY, SubtypeOreDeepslate.monazite.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.monazite.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.monazite.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.monazite.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.niter), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.niter).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_niter"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.niter.minY, SubtypeOreDeepslate.niter.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.niter.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.niter.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.niter.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.rutile), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.rutile).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_titanium"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.rutile.minY, SubtypeOreDeepslate.rutile.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.rutile.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.rutile.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.rutile.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.silver), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.silver).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_silver"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.silver.minY, SubtypeOreDeepslate.silver.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.silver.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.silver.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.silver.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.sulfur), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.sulfur).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_sulfur"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.sulfur.minY, SubtypeOreDeepslate.sulfur.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.sulfur.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.sulfur.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.sulfur.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.sylvite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.sylvite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_sylvite"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.sylvite.minY, SubtypeOreDeepslate.sylvite.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.sylvite.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.sylvite.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.sylvite.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.thorianite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.thorianite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_thorium"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.thorianite.minY, SubtypeOreDeepslate.thorianite.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.thorianite.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.thorianite.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.thorianite.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.tin), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.tin).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_tin"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.tin.minY, SubtypeOreDeepslate.tin.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.tin.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.tin.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.tin.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.uraninite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.uraninite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_uranium"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.uraninite.minY, SubtypeOreDeepslate.uraninite.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.uraninite.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.uraninite.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.uraninite.harvestLevel))));
		
		pageData.add(new ItemWrapperObject(7, 5, 2.0F, 32, 75, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.vanadinite), 
				new ImageTextDescriptor(40, 0, ElectrodynamicsItems.getItem(SubtypeOreDeepslate.vanadinite).getDescription().copy().withStyle(ChatFormatting.ITALIC)), 
				new ImageTextDescriptor(40, 10, TextUtils.guidebook("chapter.ores.material", TextUtils.guidebook("chapter.ores.material_vanadium"))),
				new ImageTextDescriptor(40, 20, TextUtils.guidebook("chapter.ores.spawnrange", SubtypeOreDeepslate.vanadinite.minY, SubtypeOreDeepslate.vanadinite.maxY)),
				new ImageTextDescriptor(40, 30, TextUtils.guidebook("chapter.ores.veinsperchunk", SubtypeOreDeepslate.vanadinite.veinsPerChunk)),
				new ImageTextDescriptor(40, 40, TextUtils.guidebook("chapter.ores.veinsize", SubtypeOreDeepslate.vanadinite.veinSize)),
				new ImageTextDescriptor(40, 50, TextUtils.guidebook("chapter.ores.miningteir", SubtypeOreDeepslate.vanadinite.harvestLevel))));
		
	}

}
