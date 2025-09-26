package electrodynamics.client.guidebook.chapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.reloadlistener.CoalGeneratorFuelRegister;
import electrodynamics.common.reloadlistener.CombustionFuelRegister;
import electrodynamics.common.reloadlistener.ThermoelectricGeneratorHeatRegister;
import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.object.CombustionFuelSource;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.ForgeRegistries;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.client.guidebook.ScreenGuidebook;
import voltaic.client.guidebook.utils.components.Chapter;
import voltaic.client.guidebook.utils.components.Module;
import voltaic.client.guidebook.utils.pagedata.OnKeyPress;
import voltaic.client.guidebook.utils.pagedata.OnTooltip;
import voltaic.client.guidebook.utils.pagedata.graphics.ItemWrapperObject;
import voltaic.client.guidebook.utils.pagedata.text.TextWrapperObject;
import voltaic.common.item.subtype.SubtypeItemUpgrade;
import voltaic.compatibility.jei.JeiBuffer;
import voltaic.registers.VoltaicItems;

public class ChapterGenerators extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 32, 32, 32, 2.0F, ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.combustionchamber));

	public ChapterGenerators(Module module) {
		super(module);
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public IFormattableTextComponent getTitle() {
		return ElectroTextUtils.guidebook("chapter.generators");
	}

	@Override
	public void addData() {

		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.l1")));

		// Thermoelectric Generator
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.thermoelectricgenerator).getDescription().copy().withStyle(TextFormatting.BOLD)).setCentered().setSeparateStart().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 30, 30, 2.0F, ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.thermoelectricgenerator)).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(MatrixStack poseStack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					List<IReorderingProcessor> tooltips = new ArrayList<>();
					tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(TextFormatting.GRAY).getVisualOrderText());
					screen.renderTooltip(poseStack, tooltips, xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.thermoelectricgenerator));
			}

		}));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.generationbase", ChatFormatter.getChatDisplayShort(ElectroConstants.THERMOELECTRICGENERATOR_AMPERAGE * 120, DisplayUnits.WATT), 120)).setSeparateStart());
		blankLine();
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.use").withStyle(TextFormatting.UNDERLINE)).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.thermoelectricgeneratoruse")).setSeparateStart());
		blankLine();
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.heatsource").withStyle(TextFormatting.UNDERLINE)).setSeparateStart());

		ThermoelectricGeneratorHeatRegister.INSTANCE.getHeatSources().forEach((fluid, multiplier) -> {
			if (ForgeRegistries.FLUIDS.getKey(fluid).toString().toLowerCase(Locale.ROOT).contains("flow")) {
				return;
			}
			pageData.add(new TextWrapperObject(new TranslationTextComponent(fluid.getAttributes().getTranslationKey())).setSeparateStart());
			pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.multiplier", multiplier)).setSeparateStart().setIndentions(1));
		});

		// Solar Panel
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.solarpanel).getDescription().copy().withStyle(TextFormatting.BOLD)).setCentered().setSeparateStart().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 32, 32, 2.0F, ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.solarpanel)).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(MatrixStack poseStack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					List<IReorderingProcessor> tooltips = new ArrayList<>();
					tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(TextFormatting.GRAY).getVisualOrderText());
					screen.renderTooltip(poseStack, tooltips, xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.solarpanel));
			}

		}));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.generationbase", ChatFormatter.getChatDisplayShort(ElectroConstants.SOLARPANEL_AMPERAGE * 120, DisplayUnits.WATT), 120)).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.generationupgrade", ChatFormatter.getChatDisplayShort(ElectroConstants.SOLARPANEL_AMPERAGE * 120 * 2.25, DisplayUnits.WATT), 120)).setSeparateStart());
		blankLine();
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.tips").withStyle(TextFormatting.UNDERLINE)).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.tipsolarweather")).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.tipsolartemperature")).setSeparateStart());
		blankLine();
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.upgrades").withStyle(TextFormatting.UNDERLINE)).setSeparateStart());
		pageData.add(new TextWrapperObject(VoltaicItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.improvedsolarcell).getDescription()).setIndentions(1).setSeparateStart());

		// Advanced Solar Panel
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.advancedsolarpanel).getDescription().copy().withStyle(TextFormatting.BOLD)).setCentered().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 32, 32, 2.0F, ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.advancedsolarpanel)).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(MatrixStack poseStack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					List<IReorderingProcessor> tooltips = new ArrayList<>();
					tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(TextFormatting.GRAY).getVisualOrderText());
					screen.renderTooltip(poseStack, tooltips, xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.advancedsolarpanel));
			}

		}));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.generationbase", ChatFormatter.getChatDisplayShort(ElectroConstants.ADVANCEDSOLARPANEL_AMPERAGE * 240, DisplayUnits.WATT), 240)).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.generationupgrade", ChatFormatter.getChatDisplayShort(ElectroConstants.ADVANCEDSOLARPANEL_AMPERAGE * 240 * 2.25, DisplayUnits.WATT), 240)).setSeparateStart());
		blankLine();
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.tips").withStyle(TextFormatting.UNDERLINE)).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.tipsolarweather")).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.tipsolartemperature")).setSeparateStart());
		blankLine();
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.upgrades").withStyle(TextFormatting.UNDERLINE)).setSeparateStart());
		pageData.add(new TextWrapperObject(VoltaicItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.improvedsolarcell).getDescription()).setIndentions(1).setSeparateStart());

		// Windmill
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.windmill).getDescription().copy().withStyle(TextFormatting.BOLD)).setCentered().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 15, 32, 32, 32, 2.0F, ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.windmill)).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(MatrixStack poseStack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					List<IReorderingProcessor> tooltips = new ArrayList<>();
					tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(TextFormatting.GRAY).getVisualOrderText());
					screen.renderTooltip(poseStack, tooltips, xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.windmill));
			}

		}));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.generationbase", ChatFormatter.getChatDisplayShort(ElectroConstants.WINDMILL_MAX_AMPERAGE * 120, DisplayUnits.WATT), 120)).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.generationupgrade", ChatFormatter.getChatDisplayShort(ElectroConstants.WINDMILL_MAX_AMPERAGE * 120 * 2.25, DisplayUnits.WATT), 120)).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.tipminy", 0)).setSeparateStart());
		blankLine();
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.tips").withStyle(TextFormatting.UNDERLINE)).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.tipidealy", 319)).setIndentions(1).setSeparateStart());
		blankLine();
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.upgrades").withStyle(TextFormatting.UNDERLINE)).setSeparateStart());
		pageData.add(new TextWrapperObject(VoltaicItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.stator).getDescription()).setIndentions(1).setSeparateStart());

		// Coal Generator
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.coalgenerator).getDescription().copy().withStyle(TextFormatting.BOLD)).setCentered().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 10, 32, 32, 32, 2.0F, ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.coalgenerator)).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(MatrixStack poseStack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					List<IReorderingProcessor> tooltips = new ArrayList<>();
					tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(TextFormatting.GRAY).getVisualOrderText());
					screen.renderTooltip(poseStack, tooltips, xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.coalgenerator));
			}

		}));
		blankLine();
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.generationbase", ChatFormatter.getChatDisplayShort(ElectroConstants.COALGENERATOR_AMPERAGE * 120, DisplayUnits.WATT), 120)).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.tiptemprange", 27, 2500)).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.tipidealtemp", 2500)).setSeparateStart());
		blankLine();
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.fuels").withStyle(TextFormatting.UNDERLINE)).setSeparateStart());
		for (Item item : CoalGeneratorFuelRegister.INSTANCE.getFuels()) {
			pageData.add(new TextWrapperObject(item.getDescription()).setSeparateStart());
			pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.burntime", ChatFormatter.getChatDisplayShort(ForgeHooks.getBurnTime(new ItemStack(item), null) / 20.0, DisplayUnits.TIME_SECONDS))).setIndentions(1).setSeparateStart());
		}

		// Hydroelectric Generator
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.hydroelectricgenerator).getDescription().copy().withStyle(TextFormatting.BOLD)).setCentered().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 32, 32, 2.0F, ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.hydroelectricgenerator)).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(MatrixStack poseStack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					List<IReorderingProcessor> tooltips = new ArrayList<>();
					tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(TextFormatting.GRAY).getVisualOrderText());
					screen.renderTooltip(poseStack, tooltips, xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.hydroelectricgenerator));
			}

		}));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.generationbase", ChatFormatter.getChatDisplayShort(ElectroConstants.HYDROELECTRICGENERATOR_AMPERAGE * 120, DisplayUnits.WATT), 120)).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.generationupgrade", ChatFormatter.getChatDisplayShort(ElectroConstants.HYDROELECTRICGENERATOR_AMPERAGE * 120 * 2.25, DisplayUnits.WATT), 120)).setSeparateStart());
		blankLine();
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.upgrades").withStyle(TextFormatting.UNDERLINE)).setSeparateStart());
		pageData.add(new TextWrapperObject(VoltaicItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.stator).getDescription()).setIndentions(1).setSeparateStart());

		// Combustion Chamber
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.combustionchamber).getDescription().copy().withStyle(TextFormatting.BOLD)).setCentered().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 32, 32, 2.0F, ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.combustionchamber)).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(MatrixStack poseStack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					List<IReorderingProcessor> tooltips = new ArrayList<>();
					tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(TextFormatting.GRAY).getVisualOrderText());
					screen.renderTooltip(poseStack, tooltips, xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.combustionchamber));
			}

		}));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.generationbase", ChatFormatter.getChatDisplayShort(ElectroConstants.COMBUSTIONCHAMBER_JOULES_PER_TICK * 20, DisplayUnits.WATT), 120)).setSeparateStart());
		blankLine();
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.fuels").withStyle(TextFormatting.UNDERLINE)).setSeparateStart());
		for (CombustionFuelSource fuel : CombustionFuelRegister.INSTANCE.getFuels()) {
			pageData.add(new TextWrapperObject(new TranslationTextComponent(fuel.getFuels().get(0).getFluid().getAttributes().getTranslationKey())).setSeparateStart());
			pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.combustionchamberburn", fuel.getFuelUsage())).setIndentions(1).setSeparateStart());
			pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.generators.multiplier", fuel.getPowerMultiplier())).setIndentions(1).setSeparateStart());
		}

	}

}
