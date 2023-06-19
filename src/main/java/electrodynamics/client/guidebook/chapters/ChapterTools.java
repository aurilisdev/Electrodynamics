package electrodynamics.client.guidebook.chapters;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.OnKeyPress;
import electrodynamics.client.guidebook.utils.pagedata.OnTooltip;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import electrodynamics.common.item.subtype.SubtypeDrillHead;
import electrodynamics.common.item.subtype.SubtypeRod;
import electrodynamics.compatibility.jei.JeiBuffer;
import electrodynamics.prefab.utilities.TextUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;

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
	public MutableComponent getTitle() {
		return TextUtils.guidebook("chapter.tools");
	}

	@Override
	public void addData() {

		// Kinetic Railgun
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEM_KINETICRAILGUN.get().getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setSeparateStart());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 30, 30, 2.0F, ElectrodynamicsItems.ITEM_KINETICRAILGUN.get()).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					List<FormattedCharSequence> tooltips = new ArrayList<>();
					tooltips.add(TextUtils.tooltip("guidebookjeirecipe").withStyle(ChatFormatting.GRAY).getVisualOrderText());
					screen.displayTooltips(stack, tooltips, xAxis, yAxis);
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
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.ammo")).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.getItem(SubtypeRod.steel).getDescription()).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.getItem(SubtypeRod.stainlesssteel).getDescription()).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.getItem(SubtypeRod.hslasteel).getDescription()).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.damage")).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.roddamage", TextUtils.guidebook("chapter.tools.steel"), 16)).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.roddamage", TextUtils.guidebook("chapter.tools.stainless"), 20)).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.roddamage", TextUtils.guidebook("chapter.tools.steel"), Component.literal("" + 4).append(TextUtils.guidebook("chapter.tools.ap")))).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.apnote").withStyle(ChatFormatting.ITALIC)).setSeparateStart());
		blankLine();
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.kineticl1")).setSeparateStart());

		// Kinetic Railgun
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEM_PLASMARAILGUN.get().getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 30, 30, 2.0F, ElectrodynamicsItems.ITEM_PLASMARAILGUN.get()).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					List<FormattedCharSequence> tooltips = new ArrayList<>();
					tooltips.add(TextUtils.tooltip("guidebookjeirecipe").withStyle(ChatFormatting.GRAY).getVisualOrderText());
					screen.displayTooltips(stack, tooltips, xAxis, yAxis);
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
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.ammo")).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.energy")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.damage")).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.roddamage", TextUtils.guidebook("chapter.tools.initial"), Component.literal("" + 40).append(TextUtils.guidebook("chapter.tools.ap")))).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.roddamage", TextUtils.guidebook("chapter.tools.after", "1s"), Component.literal("" + 20).append(TextUtils.guidebook("chapter.tools.ap")))).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.apnote").withStyle(ChatFormatting.ITALIC)).setSeparateStart());

		// Seismic Scanner
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEM_SEISMICSCANNER.get().getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 5, 32, 30, 30, 2.0F, ElectrodynamicsItems.ITEM_SEISMICSCANNER.get()).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					List<FormattedCharSequence> tooltips = new ArrayList<>();
					tooltips.add(TextUtils.tooltip("guidebookjeirecipe").withStyle(ChatFormatting.GRAY).getVisualOrderText());
					screen.displayTooltips(stack, tooltips, xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.ITEM_SEISMICSCANNER.get());
			}

		}));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.seismicl1")).setIndentions(1).setSeparateStart());

		// Electric Drill
		pageData.add(new TextWrapperObject(ElectrodynamicsItems.ITEM_ELECTRICDRILL.get().getDescription().copy().withStyle(ChatFormatting.BOLD)).setCentered().setNewPage());
		pageData.add(new ItemWrapperObject(7 + ScreenGuidebook.TEXT_WIDTH / 2 - 16, 10, 32, 32, 32, 2.0F, ElectrodynamicsItems.ITEM_ELECTRICDRILL.get()).onTooltip(new OnTooltip() {

			@Override
			public void onTooltip(PoseStack stack, int xAxis, int yAxis, ScreenGuidebook screen) {
				if (JeiBuffer.isJeiInstalled()) {
					List<FormattedCharSequence> tooltips = new ArrayList<>();
					tooltips.add(TextUtils.tooltip("guidebookjeirecipe").withStyle(ChatFormatting.GRAY).getVisualOrderText());
					screen.displayTooltips(stack, tooltips, xAxis, yAxis);
				}

			}
		}).onKeyPress(new OnKeyPress() {

			@Override
			public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) {

			}

			@Override
			public Object getJeiLookup() {
				return new ItemStack(ElectrodynamicsItems.ITEM_ELECTRICDRILL.get());
			}

		}));
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.electricdrilll1")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.quarry.drillhead", TextUtils.guidebook("chapter.quarry.steelhead"), ChatFormatter.getChatDisplayShort(SubtypeDrillHead.steel.speedBoost * 100, DisplayUnit.PERCENTAGE))).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.quarry.drillhead", TextUtils.guidebook("chapter.quarry.stainlesshead"), ChatFormatter.getChatDisplayShort(SubtypeDrillHead.stainlesssteel.speedBoost * 100, DisplayUnit.PERCENTAGE))).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.quarry.drillhead", TextUtils.guidebook("chapter.quarry.hslahead"), ChatFormatter.getChatDisplayShort(SubtypeDrillHead.hslasteel.speedBoost * 100, DisplayUnit.PERCENTAGE))).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.quarry.drillhead", TextUtils.guidebook("chapter.quarry.titaniumhead"), ChatFormatter.getChatDisplayShort(SubtypeDrillHead.titanium.speedBoost * 100, DisplayUnit.PERCENTAGE))).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.quarry.drillhead", TextUtils.guidebook("chapter.quarry.carbidehead"), ChatFormatter.getChatDisplayShort(SubtypeDrillHead.titaniumcarbide.speedBoost * 100, DisplayUnit.PERCENTAGE))).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.electricdrilll2")).setSeparateStart());
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.tools.electricdrilll3")).setIndentions(1).setSeparateStart());

	}

}
