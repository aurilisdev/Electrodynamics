package electrodynamics.client.guidebook.chapters;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.References;
import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.OnKeyPress;
import electrodynamics.client.guidebook.utils.pagedata.OnTooltip;
import electrodynamics.client.guidebook.utils.pagedata.graphics.AbstractGraphicWrapper.GraphicTextDescriptor;
import electrodynamics.client.guidebook.utils.pagedata.graphics.FluidWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.compatibility.jei.JeiBuffer;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsFluids;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.RegistryObject;

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
		return ElectroTextUtils.guidebook("chapter.fluids");
	}

	@Override
	public void addData() {
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l1.1")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l1.2")).setIndentions(1).setSeparateStart());

		for (RegistryObject<Fluid> fluid : ElectrodynamicsFluids.FLUIDS.getEntries()) {
			pageData.add(new FluidWrapperObject(0, 0, 32, 32, 36, fluid.get(), new GraphicTextDescriptor(36, 11, fluid.get().getFluidType().getDescription()))

					.onTooltip(new OnTooltip() {

						@Override
						public void onTooltip(GuiGraphics graphics, int xAxis, int yAxis, ScreenGuidebook screen) {
							if (JeiBuffer.isJeiInstalled()) {
								List<FormattedCharSequence> tooltips = new ArrayList<>();
								tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(ChatFormatting.GRAY).getVisualOrderText());
								tooltips.add(ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY).getVisualOrderText());
								graphics.renderTooltip(screen.getFontRenderer(), tooltips, xAxis, yAxis);
							}

						}
					}).onKeyPress(new OnKeyPress() {

						@Override
						public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

						}

						@Override
						public Object getJeiLookup() {
							return new FluidStack(fluid.get(), 1);
						}

					}));
		}

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l2")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.fluidinput")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.fluidoutput")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l3")).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, 81, new ResourceLocation(References.ID, "textures/screen/guidebook/fluidio.png")).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(GuiGraphics graphics, int xAxis, int yAxis, ScreenGuidebook screen) {
				List<FormattedCharSequence> tooltips = new ArrayList<>();
				tooltips.add(ElectroTextUtils.guidebook("chapter.electricity.left", ElectrodynamicsItems.getItem(SubtypeMachine.mineralwasher).getDescription().copy().withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY).getVisualOrderText());
				tooltips.add(ElectroTextUtils.guidebook("chapter.electricity.middle", ElectrodynamicsItems.getItem(SubtypeMachine.chemicalcrystallizer).getDescription().copy().withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY).getVisualOrderText());
				tooltips.add(ElectroTextUtils.guidebook("chapter.electricity.right", ElectrodynamicsItems.getItem(SubtypeMachine.electricpump).getDescription().copy().withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY).getVisualOrderText());

				graphics.renderTooltip(screen.getFontRenderer(), tooltips, xAxis, yAxis);
			}

		}));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l4")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.pipecapacity", ElectroTextUtils.guidebook("chapter.fluids.pipecopper"), SubtypeFluidPipe.copper.maxTransfer)).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.pipecapacity", ElectroTextUtils.guidebook("chapter.fluids.pipesteel"), SubtypeFluidPipe.steel.maxTransfer)).setSeparateStart().setIndentions(1));
		blankLine();

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l5.1", ElectrodynamicsItems.ITEM_FLUIDVALVE.get().getDescription().copy().withStyle(ChatFormatting.BOLD), ElectrodynamicsItems.ITEM_FLUIDVALVE.get().getDescription())).setSeparateStart().setIndentions(1));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/fluidvalveoff.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/fluidvalveon.png")));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l5.2", ElectrodynamicsItems.ITEM_FLUIDVALVE.get().getDescription())).setSeparateStart());

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l6.1", ElectrodynamicsItems.ITEM_FLUIDPIPEPUMP.get().getDescription().copy().withStyle(ChatFormatting.BOLD), ElectrodynamicsItems.ITEM_FLUIDPIPEPUMP.get().getDescription())).setSeparateStart().setIndentions(1));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, new ResourceLocation(References.ID, "textures/screen/guidebook/fluidpipepump.png")));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l6.2")).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, new ResourceLocation(References.ID, "textures/screen/guidebook/fluidpipepumpgui.png")));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l6.3")).setSeparateStart());

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l7.1", ElectrodynamicsItems.ITEM_FLUIDPIPEFILTER.get().getDescription().copy().withStyle(ChatFormatting.BOLD), ElectrodynamicsItems.ITEM_FLUIDPIPEFILTER.get().getDescription())).setSeparateStart().setIndentions(1));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, new ResourceLocation(References.ID, "textures/screen/guidebook/fluidfilterpipe.png")));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l7.2")).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, new ResourceLocation(References.ID, "textures/screen/guidebook/fluidfilterpipegui1.png")));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l7.3", ElectrodynamicsItems.ITEM_FLUIDPIPEFILTER.get().getDescription())).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/fluidfilterpipegui2.png")));
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, new ResourceLocation(References.ID, "textures/screen/guidebook/fluidfilterpipegui3.png")));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l7.4", ElectrodynamicsItems.ITEM_FLUIDPIPEFILTER.get().getDescription())).setSeparateStart());

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l8")).setSeparateStart().setIndentions(1));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l9")).setSeparateStart().setIndentions(1));

	}

}
