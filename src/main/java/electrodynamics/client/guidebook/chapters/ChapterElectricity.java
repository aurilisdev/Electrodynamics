package electrodynamics.client.guidebook.chapters;

import electrodynamics.api.References;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.TextWrapperObject;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.prefab.utilities.TextUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class ChapterElectricity extends Chapter {

	private static final ImageWrapperObject LOGO = new ImageWrapperObject(2, 2, 0, 0, 28, 28, 28, 28, new ResourceLocation(References.ID, "textures/item/wire/wiregold.png"));

	public ChapterElectricity(Module module) {
		super(module);
	}

	@Override
	public ImageWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return TextUtils.guidebook("chapter.electricity");
	}

	@Override
	public void addData() {
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l1")).setIndentions(1).setSeparateStart());

		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l2")).setSeparateStart().setIndentions(1));

		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l3")).setSeparateStart().setIndentions(1));

		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.voltageval", 120, TextUtils.guidebook("chapter.electricity.yellow"))).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.voltageval", 240, TextUtils.guidebook("chapter.electricity.blue"))).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.voltageval", 480, TextUtils.guidebook("chapter.electricity.red"))).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.voltageval", 960, TextUtils.guidebook("chapter.electricity.purple"))).setSeparateStart().setIndentions(1));

		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l4")).setSeparateStart());

		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.voltageexample", 120)).setCentered().setNewPage());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, 81, new ResourceLocation(References.ID, "textures/screen/guidebook/120vmachines.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.voltageexamplenote")));

		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.voltageexample", 240)).setCentered().setNewPage());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, 81, new ResourceLocation(References.ID, "textures/screen/guidebook/240vmachines.png")));

		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.voltageexample", 480)).setCentered().setNewPage());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, 81, new ResourceLocation(References.ID, "textures/screen/guidebook/480vmachines.png")));

		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.voltageexample", 960)).setCentered().setNewPage());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, 81, new ResourceLocation(References.ID, "textures/screen/guidebook/960vmachines.png")));

		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l5")).setNewPage().setIndentions(1));

		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 50, 150, 50, 55, new ResourceLocation(References.ID, "textures/screen/guidebook/voltagetooltip1.png")).setNewPage());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 55, 150, 55, 60, new ResourceLocation(References.ID, "textures/screen/guidebook/voltagetooltip2.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.guivoltagenote")));

		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l6")).setNewPage().setIndentions(1));

		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l7")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.energyinput")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.energyoutput")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l8")).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, new ResourceLocation(References.ID, "textures/screen/guidebook/energyio.png")));

		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l9")).setNewPage().setIndentions(1));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 72, 150, 72, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/wiretooltip1.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l10")).setSeparateStart());
		
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l11.1")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEM_RUBBERBOOTS.get().getDescription().copy().withStyle(ChatFormatting.BOLD)));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l11.2")));
		
		//Wires
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l12.1")).setSeparateStart().setIndentions(1));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, new ResourceLocation(References.ID, "textures/screen/guidebook/coloredwires.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l12.2")).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, new ResourceLocation(References.ID, "textures/screen/guidebook/logisticalwire.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l12.3")).setSeparateStart());
		
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l13.1")).setSeparateStart().setIndentions(1));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, new ResourceLocation(References.ID, "textures/screen/guidebook/ceramicwire.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l13.2")).setSeparateStart());
		
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l14")).setSeparateStart().setIndentions(1));
		
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l15")).setSeparateStart().setIndentions(1));
		
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l16.1")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEM_MULTIMETER.get().getDescription().copy().withStyle(ChatFormatting.BOLD)));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l16.2")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 40, 150, 40, 45, new ResourceLocation(References.ID, "textures/screen/guidebook/multimeterdisplay1.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l16.3")).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.getItem(SubtypeMachine.multimeterblock).getDescription().copy().withStyle(ChatFormatting.BOLD)));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l16.4")));
		
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l17.1", ElectrodynamicsItems.getItem(SubtypeMachine.relay).getDescription().copy().withStyle(ChatFormatting.BOLD))).setSeparateStart().setIndentions(1));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/relayoff.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/relayon.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l17.2", ElectrodynamicsItems.getItem(SubtypeMachine.relay).getDescription(), ElectrodynamicsItems.getItem(SubtypeMachine.relay).getDescription())).setSeparateStart());
		
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l18.1", ElectrodynamicsItems.getItem(SubtypeMachine.relay).getDescription(), ElectrodynamicsItems.getItem(SubtypeMachine.circuitbreaker).getDescription().copy().withStyle(ChatFormatting.BOLD), ElectrodynamicsItems.getItem(SubtypeMachine.circuitbreaker).getDescription())).setSeparateStart().setIndentions(1));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/breakeroff.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/breakeron.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l18.2", ElectrodynamicsItems.getItem(SubtypeMachine.circuitbreaker).getDescription())));
		
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.l19")).setSeparateStart().setIndentions(1));
		
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.powerfromenergy")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.powerfromvoltage")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.energytickstoseconds")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.voltagefromresistance")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.electricity.powerfromcurrent")).setSeparateStart().setIndentions(1));

	}

}
