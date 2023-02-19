package electrodynamics.client.guidebook.chapters;

import electrodynamics.api.References;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.TextWrapperObject;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.prefab.utilities.TextUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class ChapterMachines extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 2.0F, 32, 32, ElectrodynamicsItems.getItem(SubtypeMachine.electricfurnace));

	public ChapterMachines(Module module) {
		super(module);
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return TextUtils.guidebook("chapter.machines");
	}

	@Override
	public void addData() {
		// charger tutorial
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.machines.chargerheader").withStyle(ChatFormatting.UNDERLINE)).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.machines.l1")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.machines.chargeformula")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.machines.l2")).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.machines.l3")).setIndentions(1).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 70, 150, 70, new ResourceLocation(References.ID, "textures/screen/guidebook/chargerbatteryslots.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.machines.l4")).setSeparateStart());

	}

}
