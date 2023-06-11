package electrodynamics.client.guidebook.utils.components;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.client.guidebook.utils.pagedata.OnClick;
import electrodynamics.client.guidebook.utils.pagedata.OnKeyPress;
import electrodynamics.client.guidebook.utils.pagedata.OnTooltip;
import electrodynamics.client.guidebook.utils.pagedata.graphics.AbstractGraphicWrapper;
import electrodynamics.prefab.utilities.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;

public class Page {

	private final int pageNumber;
	public List<TextWrapper> text = new ArrayList<>();
	public List<GraphicWrapper> graphics = new ArrayList<>();
	public Chapter associatedChapter;
	
	public List<TextWrapper> tooltipText = new ArrayList<>();
	public List<GraphicWrapper> tooltipGraphics = new ArrayList<>();
	
	public List<TextWrapper> clickText = new ArrayList<>();
	public List<GraphicWrapper> clickGraphics = new ArrayList<>();
	
	public List<TextWrapper> keyPressText = new ArrayList<>();
	public List<GraphicWrapper> keyPressGraphics = new ArrayList<>();

	public Page(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPage() {
		return pageNumber;
	}
	
	public void renderAdditionalText(PoseStack stack, int refX, int refY, int xPageShift, Font font, int textWidth, int textStartX) {
		
		Module currMod = associatedChapter.module;
		Component moduleTitle = currMod.getTitle().withStyle(ChatFormatting.BOLD);
		int xShift = (textWidth - font.width(moduleTitle)) / 2;
		font.draw(stack, moduleTitle, refX + textStartX + xShift + xPageShift, refY + 16, 4210752);

		Component chapTitle = associatedChapter.getTitle().withStyle(ChatFormatting.UNDERLINE);
		xShift = (textWidth - font.width(chapTitle)) / 2;
		font.draw(stack, chapTitle, refX + textStartX + xShift + xPageShift, refY + 26, 4210752);

		Component pageNumber = Component.literal(getPage() + 1 + "");
		xShift = (textWidth - font.width(pageNumber)) / 2;
		font.draw(stack, pageNumber, refX + textStartX + xShift + xPageShift, refY + 200, 4210752);
		
	}

	public record TextWrapper(int x, int y, FormattedText characters, int color, boolean centered, OnTooltip onTooltip, OnClick onClick, OnKeyPress onKeyPress) {

	}

	public record GraphicWrapper(int x, int y, AbstractGraphicWrapper<?> graphic, OnTooltip onTooltip, OnClick onClick, OnKeyPress onKeyPress) {

	}

	public static class ChapterPage extends Page {

		public Module associatedModule;

		public ChapterPage(int pageNumber, Module module) {
			super(pageNumber);
			associatedModule = module;
		}
		
		@Override
		public void renderAdditionalText(PoseStack stack, int refX, int refY, int xPageShift, Font font, int textWidth, int textStartX) {
			
			Module currMod = associatedModule;
			Component moduleTitle = currMod.getTitle().withStyle(ChatFormatting.BOLD);
			int xShift = (textWidth - font.width(moduleTitle)) / 2;
			font.draw(stack, moduleTitle, refX + xShift + textStartX + xPageShift, refY + 16, 4210752);

			Component chapTitle = TextUtils.guidebook("chapters").withStyle(ChatFormatting.UNDERLINE);
			xShift = (textWidth - font.width(chapTitle)) / 2;
			font.draw(stack, chapTitle, refX + textStartX + xShift + xPageShift, refY + 31, 4210752);
		}

	}

	public static class ModulePage extends Page {

		public ModulePage(int pageNumber) {
			super(pageNumber);
		}
		
		@Override
		public void renderAdditionalText(PoseStack stack, int refX, int refY, int xPageShift, Font font, int textWidth, int textStartX) {
			Component modTitle = TextUtils.guidebook("availablemodules").withStyle(ChatFormatting.BOLD);
			int xShift = (textWidth - font.width(modTitle)) / 2;
			font.draw(stack, modTitle, refX + textStartX + xShift + xPageShift, refY + 16, 4210752);
		}

	}
	
	public static class CoverPage extends Page {

		public CoverPage(int pageNumber) {
			super(pageNumber);
		}
		
		@Override
		public void renderAdditionalText(PoseStack stack, int refX, int refY, int xPageShift, Font font, int textWidth, int textStartX) {
			//Not used as of now
		}
		
	}

}
