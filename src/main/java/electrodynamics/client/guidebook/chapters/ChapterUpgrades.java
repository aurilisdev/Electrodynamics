package electrodynamics.client.guidebook.chapters;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TextFormatting;
import voltaic.client.guidebook.ScreenGuidebook;
import voltaic.client.guidebook.utils.components.Chapter;
import voltaic.client.guidebook.utils.components.Module;
import voltaic.client.guidebook.utils.pagedata.OnKeyPress;
import voltaic.client.guidebook.utils.pagedata.OnTooltip;
import voltaic.client.guidebook.utils.pagedata.graphics.ItemWrapperObject;
import voltaic.client.guidebook.utils.pagedata.text.TextWrapperObject;
import voltaic.common.item.subtype.SubtypeItemUpgrade;
import voltaic.compatibility.jei.JeiBuffer;

public class ChapterUpgrades extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 32, 32, 32, 2.0F, ElectrodynamicsItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.advancedspeed));

	public ChapterUpgrades(Module module) {
		super(module);
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public IFormattableTextComponent getTitle() {
		return ElectroTextUtils.guidebook("chapter.upgrades");
	}

	@Override
	public void addData() {
		// Injector Upgrade tutorial
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.iteminput).getDescription().copy().withStyle(TextFormatting.BOLD)).setCentered().setSeparateStart());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 30, 30, 2.0F, ElectrodynamicsItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.iteminput)).onTooltip(new OnTooltip() {

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
				return new ItemStack(ElectrodynamicsItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.iteminput));
			}

		}));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.upgrades.l1")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.upgrades.default").withStyle(TextFormatting.BOLD)));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.upgrades.l2")));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.upgrades.smart").withStyle(TextFormatting.BOLD)));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.upgrades.l3")));

		// Ejector Upgrade tutorial
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.itemoutput).getDescription().copy().withStyle(TextFormatting.BOLD)).setCentered().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 30, 30, 2.0F, ElectrodynamicsItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.itemoutput)).onTooltip(new OnTooltip() {

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
				return new ItemStack(ElectrodynamicsItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.itemoutput));
			}

		}));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.upgrades.l4")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.upgrades.default").withStyle(TextFormatting.BOLD)));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.upgrades.l5")));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.upgrades.smart").withStyle(TextFormatting.BOLD)));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.upgrades.l6")));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.upgrades.l7")).setIndentions(1).setSeparateStart());

	}

}
