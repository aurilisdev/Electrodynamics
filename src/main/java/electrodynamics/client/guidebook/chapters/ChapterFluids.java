package electrodynamics.client.guidebook.chapters;

import electrodynamics.api.References;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.TextWrapperObject;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.prefab.utilities.TextUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class ChapterFluids extends Chapter {

	private static final ImageWrapperObject LOGO = new ImageWrapperObject(0, 0, 0, 0, 32, 32, 32, 32, new ResourceLocation(References.ID, "textures/item/pipe/pipesteel.png"));

	public ChapterFluids(Module module) {
		super(module);
	}

	@Override
	public ImageWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return TextUtils.guidebook("chapter.fluids");
	}

	@Override
	public void addData() {
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.fluids.l1")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.fluids.l2")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.fluids.fluidinput")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.fluids.fluidoutput")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.fluids.l3")).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, 81, new ResourceLocation(References.ID, "textures/screen/guidebook/fluidio.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.fluids.l4")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.fluids.pipecapacity", TextUtils.guidebook("chapter.fluids.pipecopper"), SubtypeFluidPipe.copper.maxTransfer)).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.fluids.pipecapacity", TextUtils.guidebook("chapter.fluids.pipesteel"), SubtypeFluidPipe.steel.maxTransfer)).setSeparateStart().setIndentions(1));
		blankLine();
		
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.fluids.l5.1", ElectrodynamicsItems.ITEM_FLUIDVALVE.get().getDescription().copy().withStyle(ChatFormatting.BOLD), ElectrodynamicsItems.ITEM_FLUIDVALVE.get().getDescription())).setSeparateStart().setIndentions(1));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/fluidvalveoff.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/fluidvalveon.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.fluids.l5.2", ElectrodynamicsItems.ITEM_FLUIDVALVE.get().getDescription())).setSeparateStart());
		
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.fluids.l6.1", ElectrodynamicsItems.ITEM_FLUIDPIPEPUMP.get().getDescription().copy().withStyle(ChatFormatting.BOLD), ElectrodynamicsItems.ITEM_FLUIDPIPEPUMP.get().getDescription())).setSeparateStart().setIndentions(1));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, new ResourceLocation(References.ID, "textures/screen/guidebook/fluidpipepump.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.fluids.l6.2")).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, new ResourceLocation(References.ID, "textures/screen/guidebook/fluidpipepumpgui.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.fluids.l6.3")).setSeparateStart());
		
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.fluids.l7.1", ElectrodynamicsItems.ITEM_FLUIDPIPEFILTER.get().getDescription().copy().withStyle(ChatFormatting.BOLD), ElectrodynamicsItems.ITEM_FLUIDPIPEFILTER.get().getDescription())).setSeparateStart().setIndentions(1));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, new ResourceLocation(References.ID, "textures/screen/guidebook/fluidfilterpipe.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.fluids.l7.2")).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, new ResourceLocation(References.ID, "textures/screen/guidebook/fluidfilterpipegui1.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.fluids.l7.3", ElectrodynamicsItems.ITEM_FLUIDPIPEFILTER.get().getDescription())).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/fluidfilterpipegui2.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/fluidfilterpipegui3.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.fluids.l7.4", ElectrodynamicsItems.ITEM_FLUIDPIPEFILTER.get().getDescription())).setSeparateStart());
		
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.fluids.l8")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.fluids.l9")).setSeparateStart().setIndentions(1));

	}

}
