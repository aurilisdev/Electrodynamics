package electrodynamics.client.guidebook.chapters;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.References;
import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.OnKeyPress;
import electrodynamics.client.guidebook.utils.pagedata.OnTooltip;
import electrodynamics.client.guidebook.utils.pagedata.graphics.AbstractGraphicWrapper.GraphicTextDescriptor;
import electrodynamics.client.guidebook.utils.pagedata.graphics.FluidWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.compatibility.jei.JeiBuffer;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsFluids;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.RegistryObject;

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
	public IFormattableTextComponent getTitle() {
		return ElectroTextUtils.guidebook("chapter.fluids");
	}

	@Override
	public void addData() {
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l1")).setIndentions(1).setSeparateStart());

		blankLine();
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.topic.header", 1, ElectroTextUtils.guidebook("chapter.fluids.topic.fluidlist"))).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.topic.header", 2, ElectroTextUtils.guidebook("chapter.fluids.topic.fluidio"))).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.topic.header", 3, ElectroTextUtils.guidebook("chapter.fluids.topic.fluidpipes"))).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.topic.header", 4, ElectroTextUtils.guidebook("chapter.fluids.topic.fluidtools"))).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.topic.header", 5, ElectroTextUtils.guidebook("chapter.fluids.topic.fluidgui"))).setSeparateStart());

		/* Fluid List */

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.topic.fluidlist").withStyle(TextFormatting.BOLD, TextFormatting.UNDERLINE)).setNewPage());

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l2.1")).setIndentions(1).setSeparateStart());

		for (RegistryObject<Fluid> fluid : ElectrodynamicsFluids.FLUIDS.getEntries()) {
			pageData.add(new FluidWrapperObject(0, 0, 32, 32, 36, fluid.get(), new GraphicTextDescriptor(36, 11, new TranslationTextComponent(fluid.get().getAttributes().getTranslationKey())))

					.onTooltip(new OnTooltip() {

						@Override
						public void onTooltip(MatrixStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
							if (JeiBuffer.isJeiInstalled()) {
								List<IReorderingProcessor> tooltips = new ArrayList<>();
								tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(TextFormatting.GRAY).getVisualOrderText());
								tooltips.add(ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(TextFormatting.GRAY).getVisualOrderText());
								screen.renderTooltip(stack, tooltips, xAxis, yAxis);
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
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l2.2")).setSeparateStart());

		/* Fluid IO */

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.topic.fluidio").withStyle(TextFormatting.BOLD, TextFormatting.UNDERLINE)).setNewPage());

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l3.1")).setIndentions(1).setSeparateStart());
		blankLine();
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.fluidinput")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.fluidoutput")).setIndentions(1).setSeparateStart());
		blankLine();
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l3.2")).setSeparateStart());
		pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 79, 150, 79, 81, new ResourceLocation(References.ID, "textures/screen/guidebook/fluidio.png")).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(MatrixStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				List<IReorderingProcessor> tooltips = new ArrayList<>();
				tooltips.add(ElectroTextUtils.guidebook("chapter.electricity.left", ElectrodynamicsItems.getItem(SubtypeMachine.mineralwasher).getDescription().copy().withStyle(TextFormatting.DARK_GRAY)).withStyle(TextFormatting.GRAY).getVisualOrderText());
				tooltips.add(ElectroTextUtils.guidebook("chapter.electricity.middle", ElectrodynamicsItems.getItem(SubtypeMachine.chemicalcrystallizer).getDescription().copy().withStyle(TextFormatting.DARK_GRAY)).withStyle(TextFormatting.GRAY).getVisualOrderText());
				tooltips.add(ElectroTextUtils.guidebook("chapter.electricity.right", ElectrodynamicsItems.getItem(SubtypeMachine.electricpump).getDescription().copy().withStyle(TextFormatting.DARK_GRAY)).withStyle(TextFormatting.GRAY).getVisualOrderText());

				screen.renderTooltip(stack, tooltips, xAxis, yAxis);
			}

		}));

		/* Fluid Pipes */

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.topic.fluidpipes").withStyle(TextFormatting.BOLD, TextFormatting.UNDERLINE)).setNewPage());

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l4", ElectroTextUtils.guidebook("chapter.fluids.pipes").withStyle(TextFormatting.BOLD))).setSeparateStart().setIndentions(1));
		blankLine();
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.pipecapacity", ElectroTextUtils.guidebook("chapter.fluids.pipesteel"), SubtypeFluidPipe.steel.maxTransfer)).setSeparateStart().setIndentions(1));

		/* Fluid Tools */

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.topic.fluidtools").withStyle(TextFormatting.BOLD, TextFormatting.UNDERLINE)).setNewPage());

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l5.1")).setSeparateStart().setIndentions(1));

		// Reinforced Canister

		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get().getDescription().copy().withStyle(TextFormatting.BOLD)).setCentered().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 30, 30, 2.0F, ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get()).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(MatrixStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					List<IReorderingProcessor> tooltips = new ArrayList<>();
					tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(TextFormatting.GRAY).getVisualOrderText());
					screen.renderTooltip(stack, tooltips, xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get());
			}

		}));

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l5.reinforcedcanister.1")).setSeparateStart().setIndentions(1));

		// Fluid Tanks

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.fluidtanks").withStyle(TextFormatting.BOLD)).setCentered().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 30, 30, 2.0F, ElectrodynamicsItems.getItem(SubtypeMachine.tankhsla)).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(MatrixStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					List<IReorderingProcessor> tooltips = new ArrayList<>();
					tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(TextFormatting.GRAY).getVisualOrderText());
					screen.renderTooltip(stack, tooltips, xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.getItem(SubtypeMachine.tankhsla));
			}

		}));

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l5.fluidtank.1")).setSeparateStart().setIndentions(1));

		/* Fluid GUIs */

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.topic.fluidgui").withStyle(TextFormatting.BOLD, TextFormatting.UNDERLINE)).setNewPage());

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.l6")).setSeparateStart().setIndentions(1));

	}

}