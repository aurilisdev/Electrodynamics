package electrodynamics.client.guidebook.chapters;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.References;
import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.OnTooltip;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

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
		return ElectroTextUtils.guidebook("chapter.electricity");
	}

	@Override
	public void addData() {
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l1.1")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.powerformula")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l1.2")).setSeparateStart());

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l2")).setSeparateStart().setIndentions(1));

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l3")).setSeparateStart().setIndentions(1));

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.voltageval", 120, ElectroTextUtils.guidebook("chapter.electricity.yellow"))).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.voltageval", 240, ElectroTextUtils.guidebook("chapter.electricity.blue"))).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.voltageval", 480, ElectroTextUtils.guidebook("chapter.electricity.red"))).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.voltageval", 960, ElectroTextUtils.guidebook("chapter.electricity.purple"))).setSeparateStart().setIndentions(1));

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l4")).setSeparateStart());

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.voltageexample", 120)).setCentered().setNewPage());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, 81, new ResourceLocation(References.ID, "textures/screen/guidebook/120vmachines.png")).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(GuiGraphics graphics, int xAxis, int yAxis, ScreenGuidebook screen) {
				List<FormattedCharSequence> tooltips = new ArrayList<>();
				tooltips.add(ElectroTextUtils.guidebook("chapter.electricity.left", ElectrodynamicsItems.getItem(SubtypeMachine.mineralgrinder).getDescription().copy().withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY).getVisualOrderText());
				tooltips.add(ElectroTextUtils.guidebook("chapter.electricity.middle", ElectrodynamicsItems.getItem(SubtypeMachine.batterybox).getDescription().copy().withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY).getVisualOrderText());
				tooltips.add(ElectroTextUtils.guidebook("chapter.electricity.right", ElectrodynamicsItems.getItem(SubtypeMachine.electricfurnace).getDescription().copy().withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY).getVisualOrderText());

				graphics.renderTooltip(screen.getFontRenderer(), tooltips, xAxis, yAxis);
			}

		}));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.voltageexamplenote")));

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.voltageexample", 240)).setCentered().setNewPage());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, 81, new ResourceLocation(References.ID, "textures/screen/guidebook/240vmachines.png")).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(GuiGraphics graphics, int xAxis, int yAxis, ScreenGuidebook screen) {

				List<FormattedCharSequence> tooltips = new ArrayList<>();
				tooltips.add(ElectroTextUtils.guidebook("chapter.electricity.left", ElectrodynamicsItems.getItem(SubtypeMachine.chemicalmixer).getDescription().copy().withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY).getVisualOrderText());
				tooltips.add(ElectroTextUtils.guidebook("chapter.electricity.middle", ElectrodynamicsItems.getItem(SubtypeMachine.lathe).getDescription().copy().withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY).getVisualOrderText());
				tooltips.add(ElectroTextUtils.guidebook("chapter.electricity.right", ElectrodynamicsItems.getItem(SubtypeMachine.oxidationfurnace).getDescription().copy().withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY).getVisualOrderText());

				graphics.renderTooltip(screen.getFontRenderer(), tooltips, xAxis, yAxis);
			}

		}));

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.voltageexample", 480)).setCentered().setNewPage());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, 81, new ResourceLocation(References.ID, "textures/screen/guidebook/480vmachines.png")).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(GuiGraphics graphics, int xAxis, int yAxis, ScreenGuidebook screen) {
				List<FormattedCharSequence> tooltips = new ArrayList<>();
				tooltips.add(ElectroTextUtils.guidebook("chapter.electricity.left", ElectrodynamicsItems.getItem(SubtypeMachine.mineralwasher).getDescription().copy().withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY).getVisualOrderText());
				tooltips.add(ElectroTextUtils.guidebook("chapter.electricity.middle", ElectrodynamicsItems.getItem(SubtypeMachine.energizedalloyer).getDescription().copy().withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY).getVisualOrderText());
				tooltips.add(ElectroTextUtils.guidebook("chapter.electricity.right", ElectrodynamicsItems.getItem(SubtypeMachine.carbynebatterybox).getDescription().copy().withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY).getVisualOrderText());

				graphics.renderTooltip(screen.getFontRenderer(), tooltips, xAxis, yAxis);
			}

		}));

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.voltageexample", 960)).setCentered().setNewPage());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, 81, new ResourceLocation(References.ID, "textures/screen/guidebook/960vmachines.png")).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(GuiGraphics graphics, int xAxis, int yAxis, ScreenGuidebook screen) {
				List<FormattedCharSequence> tooltips = new ArrayList<>();
				tooltips.add(ElectroTextUtils.guidebook("chapter.electricity.left", ElectrodynamicsItems.getItem(SubtypeMachine.reinforcedalloyer).getDescription().copy().withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY).getVisualOrderText());
				tooltips.add(ElectroTextUtils.guidebook("chapter.electricity.middle", ElectrodynamicsItems.getItem(SubtypeMachine.mineralcrushertriple).getDescription().copy().withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY).getVisualOrderText());

				graphics.renderTooltip(screen.getFontRenderer(), tooltips, xAxis, yAxis);
			}

		}));

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l5")).setNewPage().setIndentions(1));

		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 50, 150, 50, 55, new ResourceLocation(References.ID, "textures/screen/guidebook/voltagetooltip1.png")).setNewPage());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 55, 150, 55, 60, new ResourceLocation(References.ID, "textures/screen/guidebook/voltagetooltip2.png")));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.guivoltagenote")));

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l6")).setNewPage().setIndentions(1));

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l7")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.energyinput")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.energyoutput")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l8")).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, new ResourceLocation(References.ID, "textures/screen/guidebook/energyio.png")).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(GuiGraphics graphics, int xAxis, int yAxis, ScreenGuidebook screen) {
				List<FormattedCharSequence> tooltips = new ArrayList<>();
				tooltips.add(ElectroTextUtils.guidebook("chapter.electricity.left", ElectrodynamicsItems.getItem(SubtypeMachine.batterybox).getDescription().copy().withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY).getVisualOrderText());
				tooltips.add(ElectroTextUtils.guidebook("chapter.electricity.middle", ElectrodynamicsItems.getItem(SubtypeMachine.relay).getDescription().copy().withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY).getVisualOrderText());
				tooltips.add(ElectroTextUtils.guidebook("chapter.electricity.right", ElectrodynamicsItems.getItem(SubtypeMachine.downgradetransformer).getDescription().copy().withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY).getVisualOrderText());

				graphics.renderTooltip(screen.getFontRenderer(), tooltips, xAxis, yAxis);
			}

		}));

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l9")).setNewPage().setIndentions(1));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 72, 150, 72, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/wiretooltip1.png")));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l10")).setSeparateStart());

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l11.1")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEM_RUBBERBOOTS.get().getDescription().copy().withStyle(ChatFormatting.BOLD)));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l11.2")));

		// Wires
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l12.1")).setSeparateStart().setIndentions(1));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, new ResourceLocation(References.ID, "textures/screen/guidebook/coloredwires.png")));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l12.2")).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, new ResourceLocation(References.ID, "textures/screen/guidebook/logisticalwire.png")));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l12.3")).setSeparateStart());

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l13.1")).setSeparateStart().setIndentions(1));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, new ResourceLocation(References.ID, "textures/screen/guidebook/ceramicwire.png")));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l13.2")).setSeparateStart());

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l14")).setSeparateStart().setIndentions(1));

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l15")).setSeparateStart().setIndentions(1));

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l16l1")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.turnsratioformula")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l16l2")).setSeparateStart());

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l17.1")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEM_MULTIMETER.get().getDescription().copy().withStyle(ChatFormatting.BOLD)));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l17.2")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 40, 150, 40, 45, new ResourceLocation(References.ID, "textures/screen/guidebook/multimeterdisplay1.png")));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l17.3")).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.getItem(SubtypeMachine.multimeterblock).getDescription().copy().withStyle(ChatFormatting.BOLD)));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l17.4")));

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l18.1", ElectrodynamicsItems.getItem(SubtypeMachine.relay).getDescription().copy().withStyle(ChatFormatting.BOLD))).setSeparateStart().setIndentions(1));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/relayoff.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/relayon.png")));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l18.2", ElectrodynamicsItems.getItem(SubtypeMachine.relay).getDescription(), ElectrodynamicsItems.getItem(SubtypeMachine.relay).getDescription())).setSeparateStart());

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l19.1", ElectrodynamicsItems.getItem(SubtypeMachine.relay).getDescription(), ElectrodynamicsItems.getItem(SubtypeMachine.circuitbreaker).getDescription().copy().withStyle(ChatFormatting.BOLD), ElectrodynamicsItems.getItem(SubtypeMachine.circuitbreaker).getDescription())).setSeparateStart().setIndentions(1));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/breakeroff.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/breakeron.png")));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l19.2", ElectrodynamicsItems.getItem(SubtypeMachine.circuitbreaker).getDescription())));

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l20.1", ElectrodynamicsItems.getItem(SubtypeMachine.circuitmonitor).getDescription().copy().withStyle(ChatFormatting.BOLD))).setSeparateStart().setIndentions(1));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/circuitmonitor.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/circuitmonitorgui.png")));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l20.2", ElectroTextUtils.gui("networkwattage").withStyle(ChatFormatting.BOLD), ElectroTextUtils.gui("networkvoltage").withStyle(ChatFormatting.BOLD), ElectroTextUtils.gui("networkampacity").withStyle(ChatFormatting.BOLD), ElectroTextUtils.gui("networkminimumvoltage").withStyle(ChatFormatting.BOLD), ElectroTextUtils.gui("networkresistance").withStyle(ChatFormatting.BOLD), ElectroTextUtils.gui("networkload").withStyle(ChatFormatting.BOLD), ElectroTextUtils.gui("networkwattage"))).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l20.3")).setSeparateStart().setIndentions(1));

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.l21")).setSeparateStart().setIndentions(1));

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.symbols").withStyle(ChatFormatting.BOLD)).setNewPage());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.symbvoltage")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.symbcurrent")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.symbresistance")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.symbpower")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.symbenergy")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.symbturnsratio")).setSeparateStart().setIndentions(1));
		blankLine();

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.equations").withStyle(ChatFormatting.BOLD)).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.powerfromenergy")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.powerfromvoltage")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.energytickstoseconds")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.ohmslaw")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.powerfromcurrent")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.electricity.turnsratioformula")).setSeparateStart().setIndentions(1));

	}

}
