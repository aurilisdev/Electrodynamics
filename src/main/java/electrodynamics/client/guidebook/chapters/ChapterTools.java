package electrodynamics.client.guidebook.chapters;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.OnKeyPress;
import electrodynamics.client.guidebook.utils.pagedata.OnTooltip;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import electrodynamics.common.item.subtype.SubtypeRod;
import electrodynamics.compatibility.jei.JeiBuffer;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class ChapterTools extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 32, 32, 32, 2.0F, ElectrodynamicsItems.ITEM_ELECTRICDRILL.get());

	public ChapterTools(Module module) {
		super(module);
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public IFormattableTextComponent getTitle() {
		return ElectroTextUtils.guidebook("chapter.tools");
	}

	@Override
	public void addData() {

		// Kinetic Railgun
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEM_KINETICRAILGUN.get().getDescription().copy().withStyle(TextFormatting.BOLD)).setCentered().setSeparateStart());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 30, 30, 2.0F, ElectrodynamicsItems.ITEM_KINETICRAILGUN.get()).onTooltip(new OnTooltip() {

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
				return new ItemStack(ElectrodynamicsItems.ITEM_KINETICRAILGUN.get());
			}

		}));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.tools.ammo")).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.getItem(SubtypeRod.steel).getDescription()).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.getItem(SubtypeRod.stainlesssteel).getDescription()).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.getItem(SubtypeRod.hslasteel).getDescription()).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.tools.damage")).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.tools.roddamage", ElectroTextUtils.guidebook("chapter.tools.steel"), 16)).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.tools.roddamage", ElectroTextUtils.guidebook("chapter.tools.stainless"), 20)).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.tools.roddamage", ElectroTextUtils.guidebook("chapter.tools.steel"), new StringTextComponent("" + 4).append(ElectroTextUtils.guidebook("chapter.tools.ap")))).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.tools.apnote").withStyle(TextFormatting.ITALIC)).setSeparateStart());
		blankLine();
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.tools.kineticl1")).setNewPage());

		// Plasma Railgun
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEM_PLASMARAILGUN.get().getDescription().copy().withStyle(TextFormatting.BOLD)).setCentered().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 30, 30, 2.0F, ElectrodynamicsItems.ITEM_PLASMARAILGUN.get()).onTooltip(new OnTooltip() {

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
				return new ItemStack(ElectrodynamicsItems.ITEM_KINETICRAILGUN.get());
			}

		}));
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.tools.ammo")).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.tools.energy")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.tools.damage")).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.tools.roddamage", ElectroTextUtils.guidebook("chapter.tools.initial"), new StringTextComponent("" + 40).append(ElectroTextUtils.guidebook("chapter.tools.ap")))).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.tools.roddamage", ElectroTextUtils.guidebook("chapter.tools.after", "1s"), new StringTextComponent("" + 20).append(ElectroTextUtils.guidebook("chapter.tools.ap")))).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.tools.apnote").withStyle(TextFormatting.ITALIC)).setSeparateStart());

	}

}