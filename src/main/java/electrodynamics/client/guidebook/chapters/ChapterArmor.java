package electrodynamics.client.guidebook.chapters;

import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.TextWrapperObject;
import electrodynamics.common.item.subtype.SubtypeCeramic;
import electrodynamics.prefab.utilities.TextUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;

public class ChapterArmor extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 2.0F, 32, 32, ElectrodynamicsItems.ITEM_COMPOSITEHELMET.get());

	public ChapterArmor(Module module) {
		super(module);
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return TextUtils.guidebook("chapter.armor");
	}

	@Override
	public void addData() {

		// Jetpack
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEM_JETPACK.get().getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setSeparateStart());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 10, 2.0F, 32, 32, ElectrodynamicsItems.ITEM_JETPACK.get()));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.armor.jetpack")).setSeparateStart());

		// Hydraulic Boots
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEM_HYDRAULICBOOTS.get().getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 10, 2.0F, 32, 32, ElectrodynamicsItems.ITEM_HYDRAULICBOOTS.get()));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.armor.hydraulicboots")).setSeparateStart());

		// Combat Chestplate
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get().getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 10, 2.0F, 32, 32, ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get()));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.armor.combatchestplate")).setSeparateStart());

		// Combat Boots
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEM_COMBATBOOTS.get().getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 10, 2.0F, 32, 32, ElectrodynamicsItems.ITEM_COMBATBOOTS.get()));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.armor.combatboots")).setSeparateStart());

		// Ceramic Plate Protection
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.armor.ceramicheader").withStyle(ChatFormatting.BOLD)).setCentered().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 10, 2.0F, 32, 32, ElectrodynamicsItems.getItem(SubtypeCeramic.plate)));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.armor.ceramicl1")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.armor.ceramicl2")).setIndentions(1).setSeparateStart());

	}

}
