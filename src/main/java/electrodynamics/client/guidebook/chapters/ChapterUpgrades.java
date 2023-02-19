package electrodynamics.client.guidebook.chapters;

import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.TextWrapperObject;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.prefab.utilities.TextUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;

public class ChapterUpgrades extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 2.0F, 32, 32, ElectrodynamicsItems.getItem(SubtypeItemUpgrade.advancedspeed));

	public ChapterUpgrades(Module module) {
		super(module);
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return TextUtils.guidebook("chapter.upgrades");
	}

	@Override
	public void addData() {
		// Injector Upgrade tutorial
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.getItem(SubtypeItemUpgrade.iteminput).getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setSeparateStart());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 2.0F, 32, 30, ElectrodynamicsItems.getItem(SubtypeItemUpgrade.iteminput)));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.upgrades.l1")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.upgrades.default").withStyle(ChatFormatting.BOLD)));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.upgrades.l2")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.upgrades.smart").withStyle(ChatFormatting.BOLD)));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.upgrades.l3")));

		// Ejector Upgrade tutorial
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.getItem(SubtypeItemUpgrade.itemoutput).getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 2.0F, 32, 30, ElectrodynamicsItems.getItem(SubtypeItemUpgrade.itemoutput)));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.upgrades.l4")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.upgrades.default").withStyle(ChatFormatting.BOLD)));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.upgrades.l5")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.upgrades.smart").withStyle(ChatFormatting.BOLD)));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.upgrades.l6")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.upgrades.l7")).setIndentions(1).setSeparateStart());

	}

}
