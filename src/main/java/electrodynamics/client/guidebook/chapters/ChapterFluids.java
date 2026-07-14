package electrodynamics.client.guidebook.chapters;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsFluids;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.RegistryObject;
import voltaic.client.guidebook.ScreenGuidebook;
import voltaic.client.guidebook.utils.components.Chapter;
import voltaic.client.guidebook.utils.components.Module;
import voltaic.client.guidebook.utils.pagedata.OnKeyPress;
import voltaic.client.guidebook.utils.pagedata.graphics.AbstractGraphicWrapper;
import voltaic.client.guidebook.utils.pagedata.graphics.FluidWrapperObject;
import voltaic.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import voltaic.client.guidebook.utils.pagedata.graphics.ItemWrapperObject;
import voltaic.client.guidebook.utils.pagedata.text.TextWrapperObject;
import voltaic.compatibility.jei.JeiBuffer;

public class ChapterFluids extends Chapter {

    private static final ImageWrapperObject LOGO = new ImageWrapperObject(0, 0, 0, 0, 32, 32, 32, 32,
	    Electrodynamics.rl("textures/item/pipe/pipesteel.png"));

    public ChapterFluids(Module module) {
	super(module);
    }

    @Override
    public ImageWrapperObject getLogo() {
	return LOGO;
    }

    @Override
    public IFormattableTextComponent getTitle() {
	return ElectroTextUtils.guidebook("chapter.fluids");
    }

    @Override
    public void addData() {
	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l1")).setIndentions(1)
		.setSeparateStart());

	blankLine();
	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.topic.header", 1,
		ElectroTextUtils.guidebook("chapter.fluids.topic.fluidlist"))).setSeparateStart());
	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.topic.header", 2,
		ElectroTextUtils.guidebook("chapter.fluids.topic.fluidio"))).setSeparateStart());
	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.topic.header", 3,
		ElectroTextUtils.guidebook("chapter.fluids.topic.fluidpipes"))).setSeparateStart());
	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.topic.header", 4,
		ElectroTextUtils.guidebook("chapter.fluids.topic.fluidtools"))).setSeparateStart());
	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.topic.header", 5,
		ElectroTextUtils.guidebook("chapter.fluids.topic.fluidgui"))).setSeparateStart());

	/* Fluid List */

	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.topic.fluidlist")
		.withStyle(TextFormatting.BOLD, TextFormatting.UNDERLINE)).setNewPage());

	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l2.1")).setIndentions(1)
		.setSeparateStart());

	for (RegistryObject<Fluid> fluid : ElectrodynamicsFluids.FLUIDS.getEntries()) {
	    pageData.add(new FluidWrapperObject(0, 0, 32, 32, 36, fluid.get(),
		    new AbstractGraphicWrapper.GraphicTextDescriptor(36, 11,
			    new TranslationTextComponent(fluid.get().getAttributes().getTranslationKey())))

		    .onTooltip((poseStack, xAxis, yAxis, screen) -> {
			if (JeiBuffer.isJeiInstalled()) {
			    List<IReorderingProcessor> tooltips = new ArrayList<>();
			    tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(TextFormatting.GRAY)
				    .getVisualOrderText());
			    tooltips.add(ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(TextFormatting.GRAY)
				    .getVisualOrderText());
			    screen.renderTooltip(poseStack, tooltips, xAxis, yAxis);
			}

		    }).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis,
				int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
			    return new FluidStack(fluid.get(), 1);
			}

		    }));
	}
	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l2.2")).setSeparateStart());

	/* Fluid IO */

	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.topic.fluidio")
		.withStyle(TextFormatting.BOLD, TextFormatting.UNDERLINE)).setNewPage());

	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l3.1")).setIndentions(1)
		.setSeparateStart());
	blankLine();
	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.fluidinput")).setIndentions(1)
		.setSeparateStart());
	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.fluidoutput")).setIndentions(1)
		.setSeparateStart());
	blankLine();
	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l3.2")).setSeparateStart());
	pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, 81,
		Electrodynamics.rl("textures/screen/guidebook/fluidio.png"))
		.onTooltip((poseStack, xAxis, yAxis, screen) -> {
		    List<IReorderingProcessor> tooltips = new ArrayList<>();
		    tooltips.add(ElectroTextUtils
			    .guidebook("chapter.electricity.left",
				    ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralwasher)
					    .getDescription().copy().withStyle(TextFormatting.DARK_GRAY))
			    .withStyle(TextFormatting.GRAY).getVisualOrderText());
		    tooltips.add(ElectroTextUtils
			    .guidebook("chapter.electricity.middle",
				    ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.chemicalcrystallizer)
					    .getDescription().copy().withStyle(TextFormatting.DARK_GRAY))
			    .withStyle(TextFormatting.GRAY).getVisualOrderText());
		    tooltips.add(ElectroTextUtils
			    .guidebook("chapter.electricity.right",
				    ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricpump)
					    .getDescription().copy().withStyle(TextFormatting.DARK_GRAY))
			    .withStyle(TextFormatting.GRAY).getVisualOrderText());

		    screen.renderTooltip(poseStack, tooltips, xAxis, yAxis);
		}));

	/* Fluid Pipes */

	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.topic.fluidpipes")
		.withStyle(TextFormatting.BOLD, TextFormatting.UNDERLINE)).setNewPage());

	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l4",
		ElectroTextUtils.guidebook("chapter.fluids.pipes").withStyle(TextFormatting.BOLD))).setSeparateStart()
		.setIndentions(1));
	blankLine();
	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.pipecapacity",
		ElectroTextUtils.guidebook("chapter.fluids.pipecopper"), SubtypeFluidPipe.copper.getMaxTransfer()))
		.setSeparateStart().setIndentions(1));
	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.pipecapacity",
		ElectroTextUtils.guidebook("chapter.fluids.pipesteel"), SubtypeFluidPipe.steel.getMaxTransfer()))
		.setSeparateStart().setIndentions(1));

	/* Fluid Tools */

	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.topic.fluidtools")
		.withStyle(TextFormatting.BOLD, TextFormatting.UNDERLINE)).setNewPage());

	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l5.1")).setSeparateStart()
		.setIndentions(1));

	// Reinforced Canister

	pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get().getDescription().copy()
		.withStyle(TextFormatting.BOLD)).setCentered().setNewPage());
	pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 30, 30, 2.0F,
		ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get()).onTooltip((poseStack, xAxis, yAxis, screen) -> {
		    if (JeiBuffer.isJeiInstalled()) {
			List<IReorderingProcessor> tooltips = new ArrayList<>();
			tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(TextFormatting.GRAY)
				.getVisualOrderText());
			screen.renderTooltip(poseStack, tooltips, xAxis, yAxis);
		    }

		}).onKeyPress(new OnKeyPress() {

		    @Override
		    public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis,
			    ScreenGuidebook screen) {

		    }

		    @Override
		    public Object getJeiLookup() {
			return new ItemStack(ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get());
		    }

		}));

	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l5.reinforcedcanister.1"))
		.setSeparateStart().setIndentions(1));

	// Fluid Valve

	pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.fluidvalve)
		.getDescription().copy().withStyle(TextFormatting.BOLD)).setCentered().setNewPage());
	pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 30, 30, 2.0F,
		ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.fluidvalve))
		.onTooltip((poseStack, xAxis, yAxis, screen) -> {
		    if (JeiBuffer.isJeiInstalled()) {
			List<IReorderingProcessor> tooltips = new ArrayList<>();
			tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(TextFormatting.GRAY)
				.getVisualOrderText());
			screen.renderTooltip(poseStack, tooltips, xAxis, yAxis);
		    }

		}).onKeyPress(new OnKeyPress() {

		    @Override
		    public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis,
			    ScreenGuidebook screen) {

		    }

		    @Override
		    public Object getJeiLookup() {
			return new ItemStack(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.fluidvalve));
		    }

		}));

	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l5.fluidvalve.1"))
		.setSeparateStart().setIndentions(1));
	pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75,
		Electrodynamics.rl("textures/screen/guidebook/fluidvalveoff.png")));
	pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75,
		Electrodynamics.rl("textures/screen/guidebook/fluidvalveon.png")));
	pageData.add(
		new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l5.fluidvalve.2")).setSeparateStart());

	// Fluid Pipe Pump

	pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.fluidpipepump)
		.getDescription().copy().withStyle(TextFormatting.BOLD)).setCentered().setNewPage());
	pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 30, 30, 2.0F,
		ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.fluidpipepump))
		.onTooltip((poseStack, xAxis, yAxis, screen) -> {
		    if (JeiBuffer.isJeiInstalled()) {
			List<IReorderingProcessor> tooltips = new ArrayList<>();
			tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(TextFormatting.GRAY)
				.getVisualOrderText());
			screen.renderTooltip(poseStack, tooltips, xAxis, yAxis);
		    }

		}).onKeyPress(new OnKeyPress() {

		    @Override
		    public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis,
			    ScreenGuidebook screen) {

		    }

		    @Override
		    public Object getJeiLookup() {
			return new ItemStack(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.fluidpipepump));
		    }

		}));

	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l5.fluidpipepump.1"))
		.setSeparateStart().setIndentions(1));
	pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79,
		Electrodynamics.rl("textures/screen/guidebook/fluidpipepump.png")));
	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l5.fluidpipepump.2"))
		.setSeparateStart());
	pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79,
		Electrodynamics.rl("textures/screen/guidebook/fluidpipepumpgui.png")));
	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l5.fluidpipepump.3"))
		.setSeparateStart());

	// Fluid Pipe Filter

	pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.fluidpipefilter)
		.getDescription().copy().withStyle(TextFormatting.BOLD)).setCentered().setNewPage());
	pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 30, 30, 2.0F,
		ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.fluidpipefilter))
		.onTooltip((poseStack, xAxis, yAxis, screen) -> {
		    if (JeiBuffer.isJeiInstalled()) {
			List<IReorderingProcessor> tooltips = new ArrayList<>();
			tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(TextFormatting.GRAY)
				.getVisualOrderText());
			screen.renderTooltip(poseStack, tooltips, xAxis, yAxis);
		    }

		}).onKeyPress(new OnKeyPress() {

		    @Override
		    public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis,
			    ScreenGuidebook screen) {

		    }

		    @Override
		    public Object getJeiLookup() {
			return new ItemStack(
				ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.fluidpipefilter));
		    }

		}));

	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l5.fluidfilterpipe.1"))
		.setSeparateStart().setIndentions(1));
	pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79,
		Electrodynamics.rl("textures/screen/guidebook/fluidfilterpipe.png")));
	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l5.fluidfilterpipe.2"))
		.setSeparateStart());
	pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79,
		Electrodynamics.rl("textures/screen/guidebook/fluidfilterpipegui1.png")));
	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l5.fluidfilterpipe.3",
		ElectroTextUtils.guidebook("chapter.fluids.blacklist").withStyle(TextFormatting.BOLD),
		ElectroTextUtils.guidebook("chapter.fluids.whitelist").withStyle(TextFormatting.BOLD)))
		.setSeparateStart());
	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l5.fluidfilterpipe.4"))
		.setSeparateStart().setIndentions(1));
	pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75,
		Electrodynamics.rl("textures/screen/guidebook/fluidfilterpipegui2.png")));
	pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75,
		Electrodynamics.rl("textures/screen/guidebook/fluidfilterpipegui3.png")));
	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l5.fluidfilterpipe.5"))
		.setSeparateStart());

	// Fluid Void

	pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.fluidvoid)
		.getDescription().copy().withStyle(TextFormatting.BOLD)).setCentered().setNewPage());
	pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 30, 30, 2.0F,
		ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.fluidvoid))
		.onTooltip((poseStack, xAxis, yAxis, screen) -> {
		    if (JeiBuffer.isJeiInstalled()) {
			List<IReorderingProcessor> tooltips = new ArrayList<>();
			tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(TextFormatting.GRAY)
				.getVisualOrderText());
			screen.renderTooltip(poseStack, tooltips, xAxis, yAxis);
		    }

		}).onKeyPress(new OnKeyPress() {

		    @Override
		    public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis,
			    ScreenGuidebook screen) {

		    }

		    @Override
		    public Object getJeiLookup() {
			return new ItemStack(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.fluidvoid));
		    }

		}));

	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l5.fluidvoid.1"))
		.setSeparateStart().setIndentions(1));

	// Fluid Tanks

	pageData.add(new TextWrapperObject(
		ElectroTextUtils.guidebook("chapter.fluids.fluidtanks").withStyle(TextFormatting.BOLD)).setCentered()
		.setNewPage());
	pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 30, 30, 2.0F,
		ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.tankhsla))
		.onTooltip((poseStack, xAxis, yAxis, screen) -> {
		    if (JeiBuffer.isJeiInstalled()) {
			List<IReorderingProcessor> tooltips = new ArrayList<>();
			tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(TextFormatting.GRAY)
				.getVisualOrderText());
			screen.renderTooltip(poseStack, tooltips, xAxis, yAxis);
		    }

		}).onKeyPress(new OnKeyPress() {

		    @Override
		    public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis,
			    ScreenGuidebook screen) {

		    }

		    @Override
		    public Object getJeiLookup() {
			return new ItemStack(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.tankhsla));
		    }

		}));

	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l5.fluidtank.1"))
		.setSeparateStart().setIndentions(1));

	/* Fluid GUIs */

	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.topic.fluidgui")
		.withStyle(TextFormatting.BOLD, TextFormatting.UNDERLINE)).setNewPage());

	pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l6")).setSeparateStart()
		.setIndentions(1));

    }

}
