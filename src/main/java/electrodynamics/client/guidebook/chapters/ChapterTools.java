package electrodynamics.client.guidebook.chapters;

import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.TextWrapperObject;
import electrodynamics.common.item.subtype.SubtypeRod;
import electrodynamics.prefab.utilities.TextUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ChapterTools extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 2.0F, 32, 32, ElectrodynamicsItems.ITEM_ELECTRICDRILL.get());

	public ChapterTools(Module module) {
		super(module);
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return TextUtils.guidebook("chapter.tools");
	}

	@Override
	public void addData() {

		// Kinetic Railgun
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEM_KINETICRAILGUN.get().getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setSeparateStart());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 2.0F, 32, 30, ElectrodynamicsItems.ITEM_KINETICRAILGUN.get()));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.ammo")).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.getItem(SubtypeRod.steel).getDescription()).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.getItem(SubtypeRod.stainlesssteel).getDescription()).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.getItem(SubtypeRod.hslasteel).getDescription()).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.damage")).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.roddamage", TextUtils.guidebook("chapter.tools.steel"), 16)).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.roddamage", TextUtils.guidebook("chapter.tools.stainless"), 20)).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.roddamage", TextUtils.guidebook("chapter.tools.steel"), Component.literal("" + 4).append(TextUtils.guidebook("chapter.tools.ap")))).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.apnote").withStyle(ChatFormatting.ITALIC)).setSeparateStart());
		blankLine();
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.kineticl1")).setSeparateStart());

		// Kinetic Railgun
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEM_PLASMARAILGUN.get().getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 2.0F, 32, 30, ElectrodynamicsItems.ITEM_PLASMARAILGUN.get()));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.ammo")).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.energy")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.damage")).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.roddamage", TextUtils.guidebook("chapter.tools.initial"), Component.literal("" + 40).append(TextUtils.guidebook("chapter.tools.ap")))).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.roddamage", TextUtils.guidebook("chapter.tools.after", "1s"), Component.literal("" + 20).append(TextUtils.guidebook("chapter.tools.ap")))).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.apnote").withStyle(ChatFormatting.ITALIC)).setSeparateStart());

		// Seismic Scanner
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEM_SEISMICSCANNER.get().getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 2.0F, 32, 30, ElectrodynamicsItems.ITEM_SEISMICSCANNER.get()));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.seismicl1")).setIndentions(1).setSeparateStart());

	}

}
