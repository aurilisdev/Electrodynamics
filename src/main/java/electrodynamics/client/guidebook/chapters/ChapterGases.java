package electrodynamics.client.guidebook.chapters;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.References;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.gas.Gas;
import electrodynamics.api.gas.GasStack;
import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.OnKeyPress;
import electrodynamics.client.guidebook.utils.pagedata.OnTooltip;
import electrodynamics.client.guidebook.utils.pagedata.graphics.GasWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.graphics.AbstractGraphicWrapper.GraphicTextDescriptor;
import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import electrodynamics.common.block.subtype.SubtypeGasPipe;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.compatibility.jei.JeiBuffer;
import electrodynamics.prefab.utilities.TextUtils;
import electrodynamics.registers.ElectrodynamicsGases;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsRegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.registries.RegistryObject;

public class ChapterGases extends Chapter {

	private static final ImageWrapperObject LOGO = new ImageWrapperObject(0, 0, 0, 0, 32, 32, 32, 32, new ResourceLocation(References.ID, "textures/item/gaspipe/gaspipeuninsulatedsteel.png"));
	
	public ChapterGases(Module module) {
		super(module);
	}

	@Override
	public ImageWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return TextUtils.guidebook("chapter.gases");
	}
	
	@Override
	public void addData() {
		//introduction
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l1.1")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l1.2")).setIndentions(1).setSeparateStart());
		
		for (RegistryObject<Gas> gas : ElectrodynamicsGases.GASES.getEntries()) {
			if(gas.get().isEmpty()) {
				continue;
			}
			pageData.add(new GasWrapperObject(0, 0, 32, 32, 36, gas.get(), new GraphicTextDescriptor(36, 11, gas.get().getDescription()))
					
			.onTooltip(new OnTooltip() {

				@Override
				public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
					if (JeiBuffer.isJeiInstalled()) {
						List<FormattedCharSequence> tooltips = new ArrayList<>();
						tooltips.add(TextUtils.tooltip("guidebookjeirecipe").withStyle(ChatFormatting.GRAY).getVisualOrderText());
						tooltips.add(TextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY).getVisualOrderText());
						screen.displayTooltips(stack, tooltips, xAxis, yAxis);
					}

				}
			}).onKeyPress(new OnKeyPress() {

				@Override
				public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

				}

				@Override
				public Object getJeiLookup() {
					return new GasStack(gas.get(), 1, Gas.ROOM_TEMPERATURE, Gas.PRESSURE_AT_SEA_LEVEL);
				}

			}));
		}
		
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l2")).setIndentions(1).setSeparateStart());
		
		//pressure basics 
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l3", TextUtils.guidebook("chapter.gases.pressure").withStyle(ChatFormatting.BOLD))).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l4.1")).setSeparateStart().setIndentions(1));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 50, 150, 50, 55, new ResourceLocation(References.ID, "textures/screen/guidebook/itemmaxgasvals.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l4.2")).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 50, 150, 50, 55, new ResourceLocation(References.ID, "textures/screen/guidebook/machinepressuretooltip.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l4.3")).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l5")).setIndentions(1).setSeparateStart());
		
		//temperature basics
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l6", TextUtils.guidebook("chapter.gases.temperature").withStyle(ChatFormatting.BOLD))).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l7.1")).setSeparateStart().setIndentions(1));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 50, 150, 50, 55, new ResourceLocation(References.ID, "textures/screen/guidebook/itemmaxgasvals.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l7.2")).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 50, 150, 50, 55, new ResourceLocation(References.ID, "textures/screen/guidebook/machinetemperaturetooltip.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l7.3")).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l8")).setIndentions(1).setSeparateStart());
		//condensed gases
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l9")).setIndentions(1).setSeparateStart());
		for(RegistryObject<Gas> gas : ElectrodynamicsGases.GASES.getEntries()) {
			if(gas.get().isEmpty() || gas.get().getCondensedFluid() == null) {
				continue;
			}
			blankLine();
			pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.gas", gas.get().getDescription()).withStyle(ChatFormatting.ITALIC)).setSeparateStart());
			pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.condensedfluid", gas.get().getCondensedFluid().getFluidType().getDescription())).setSeparateStart());
			pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.condtemp", ChatFormatter.getChatDisplayShort(gas.get().getCondensationTemp(), DisplayUnit.TEMPERATURE_KELVIN))).setSeparateStart());
		}
		blankLine();
		//condensed gas machine handling
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l10.1")).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 60, 150, 60, 65, new ResourceLocation(References.ID, "textures/screen/guidebook/gasmachinecondensedgui.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l10.2")).setSeparateStart());
		
		//gas io 
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l11")).setIndentions(1).setSeparateStart());
		blankLine();
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.input")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.output")).setIndentions(1).setSeparateStart());
		blankLine();
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l12")).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, 81, new ResourceLocation(References.ID, "textures/screen/guidebook/gasio.png")).setNewPage().onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				List<FormattedCharSequence> tooltips = new ArrayList<>();
				tooltips.add(TextUtils.guidebook("chapter.electricity.left", ElectrodynamicsItems.getItem(SubtypeMachine.gasvent).getDescription().copy().withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY).getVisualOrderText());
				tooltips.add(TextUtils.guidebook("chapter.electricity.middle", ElectrodynamicsItems.getItem(SubtypeMachine.electrolyticseparator).getDescription().copy().withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY).getVisualOrderText());
				tooltips.add(TextUtils.guidebook("chapter.electricity.right", ElectrodynamicsItems.ITEM_GASPIPEFILTER.get().getDescription().copy().withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY).getVisualOrderText());
				
				screen.displayTooltips(stack, tooltips, xAxis, yAxis);
			}
			
		}));
		
		//gas pipes
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l13.1")).setIndentions(1).setSeparateStart());
		blankLine();
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.pipecopper").withStyle(ChatFormatting.ITALIC)).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.pipecapacity", ChatFormatter.formatFluidMilibuckets(SubtypeGasPipe.UNINSULATEDCOPPER.maxTransfer))).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.pipepressure", ChatFormatter.getChatDisplayShort(SubtypeGasPipe.UNINSULATEDCOPPER.pipeMaterial.maxPressure, DisplayUnit.PRESSURE_ATM))).setSeparateStart());
		blankLine();
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.pipesteel").withStyle(ChatFormatting.ITALIC)).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.pipecapacity", ChatFormatter.formatFluidMilibuckets(SubtypeGasPipe.UNINSULATEDSTEEL.maxTransfer))).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.pipepressure", ChatFormatter.getChatDisplayShort(SubtypeGasPipe.UNINSULATEDSTEEL.pipeMaterial.maxPressure, DisplayUnit.PRESSURE_ATM))).setSeparateStart());
		blankLine();
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.pipeplastic").withStyle(ChatFormatting.ITALIC)).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.pipecapacity", ChatFormatter.formatFluidMilibuckets(SubtypeGasPipe.UNINSULATEDPLASTIC.maxTransfer))).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.pipepressure", ChatFormatter.getChatDisplayShort(SubtypeGasPipe.UNINSULATEDPLASTIC.pipeMaterial.maxPressure, DisplayUnit.PRESSURE_ATM))).setSeparateStart());
		blankLine();
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l13.2")).setSeparateStart());
		blankLine();
		for(Gas gas : ElectrodynamicsRegistries.gasRegistry().tags().getTag(ElectrodynamicsTags.Gases.IS_CORROSIVE)) {
			pageData.add(new TextWrapperObject(gas.getDescription()).setSeparateStart());
		}
		blankLine();
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l13.3")).setSeparateStart());
		
		//gas network manipulators
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l14.1", ElectrodynamicsItems.ITEM_GASVALVE.get().getDescription().copy().withStyle(ChatFormatting.BOLD), ElectrodynamicsItems.ITEM_GASVALVE.get().getDescription())).setSeparateStart().setIndentions(1));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/gasvalveoff.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/gasvalveon.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l14.2", ElectrodynamicsItems.ITEM_GASVALVE.get().getDescription())).setSeparateStart());
		
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l15.1", ElectrodynamicsItems.ITEM_GASPIPEPUMP.get().getDescription().copy().withStyle(ChatFormatting.BOLD), ElectrodynamicsItems.ITEM_GASPIPEPUMP.get().getDescription())).setSeparateStart().setIndentions(1));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, 85, new ResourceLocation(References.ID, "textures/screen/guidebook/gaspipepump.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l15.2")).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, 85, new ResourceLocation(References.ID, "textures/screen/guidebook/gaspipepumpgui.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l15.3")).setSeparateStart());
		
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l16.1", ElectrodynamicsItems.ITEM_GASPIPEFILTER.get().getDescription().copy().withStyle(ChatFormatting.BOLD), ElectrodynamicsItems.ITEM_GASPIPEFILTER.get().getDescription())).setSeparateStart().setIndentions(1));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, new ResourceLocation(References.ID, "textures/screen/guidebook/gasfilterpipe.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l16.2")).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, new ResourceLocation(References.ID, "textures/screen/guidebook/gasfilterpipegui1.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l16.3", ElectrodynamicsItems.ITEM_GASPIPEFILTER.get().getDescription())).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/gasfilterpipegui2.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/gasfilterpipegui3.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l16.4", ElectrodynamicsItems.ITEM_GASPIPEFILTER.get().getDescription())).setSeparateStart());
		
		//gas storage
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l17")).setIndentions(1).setSeparateStart());
		
		//compressors
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l18")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l19", ElectrodynamicsItems.ITEM_COMPRESSOR.get().getDescription().copy().withStyle(ChatFormatting.BOLD), ElectrodynamicsItems.ITEM_DECOMPRESSOR.get().getDescription().copy().withStyle(ChatFormatting.BOLD), ElectrodynamicsItems.ITEM_COMPRESSOR.get().getDescription(), ElectrodynamicsItems.ITEM_DECOMPRESSOR.get().getDescription())).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l20.1", ElectrodynamicsItems.ITEM_COMPRESSOR.get().getDescription(), ElectrodynamicsItems.ITEM_DECOMPRESSOR.get().getDescription(), ElectrodynamicsItems.ITEM_COMPRESSOR_ADDONTANK.get().getDescription(), ElectrodynamicsItems.ITEM_COMPRESSOR_ADDONTANK.get().getDescription().copy().withStyle(ChatFormatting.BOLD))).setIndentions(1).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, 81, new ResourceLocation(References.ID, "textures/screen/guidebook/pressurizedtankuse.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l20.2")).setSeparateStart());
		
		//thermal manip
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l21.1", ElectrodynamicsItems.ITEM_THERMOELECTRIC_MANIPULATOR.get().getDescription().copy().withStyle(ChatFormatting.BOLD), ElectrodynamicsItems.ITEM_THERMOELECTRIC_MANIPULATOR.get().getDescription())).setIndentions(1).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, 81, new ResourceLocation(References.ID, "textures/screen/guidebook/thermomanipgui.png")));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l21.2", ElectrodynamicsItems.ITEM_THERMOELECTRIC_MANIPULATOR.get().getDescription(), ElectrodynamicsItems.ITEM_COMPRESSOR.get().getDescription(), ElectrodynamicsItems.ITEM_DECOMPRESSOR.get().getDescription(), ElectrodynamicsItems.ITEM_COMPRESSOR_ADDONTANK.get().getDescription())).setSeparateStart());
		
		//portable cylinders
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gases.l22", ElectrodynamicsItems.ITEM_PORTABLECYLINDER.get().getDescription())).setIndentions(1).setSeparateStart());
	}

}
