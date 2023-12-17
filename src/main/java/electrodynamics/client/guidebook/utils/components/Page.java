package electrodynamics.client.guidebook.utils.components;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.client.guidebook.utils.pagedata.OnClick;
import electrodynamics.client.guidebook.utils.pagedata.OnKeyPress;
import electrodynamics.client.guidebook.utils.pagedata.OnTooltip;
import electrodynamics.client.guidebook.utils.pagedata.graphics.AbstractGraphicWrapper;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

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

	public void renderAdditionalText(MatrixStack stack, int refX, int refY, int xPageShift, FontRenderer font, int textWidth, int textStartX) {

		Module currMod = associatedChapter.module;
		ITextComponent moduleTitle = currMod.getTitle().withStyle(TextFormatting.BOLD);
		int xShift = (textWidth - font.width(moduleTitle)) / 2;
		font.draw(stack, moduleTitle, refX + textStartX + xShift + xPageShift, refY + 16, 4210752);

		ITextComponent chapTitle = associatedChapter.getTitle().withStyle(TextFormatting.UNDERLINE);
		xShift = (textWidth - font.width(chapTitle)) / 2;
		font.draw(stack, chapTitle, refX + textStartX + xShift + xPageShift, refY + 26, 4210752);

		ITextComponent pageNumber = new StringTextComponent(getPage() + 1 + "");
		xShift = (textWidth - font.width(pageNumber)) / 2;
		font.draw(stack, pageNumber, refX + textStartX + xShift + xPageShift, refY + 200, 4210752);

	}

	public static class TextWrapper {
		
		public final int x;
		public final int y;
		public final ITextProperties characters;
		public final Color color;
		public final boolean centered;
		public final OnTooltip onTooltip;
		public final OnClick onClick;
		public final OnKeyPress onKeyPress;
		
		public TextWrapper(int x, int y, ITextProperties characters, Color color, boolean centered, OnTooltip onTooltip, OnClick onClick, OnKeyPress onKeyPress) {
			this.x = x;
			this.y = y;
			this.characters = characters;
			this.color = color;
			this.centered = centered;
			this.onTooltip = onTooltip;
			this.onClick = onClick;
			this.onKeyPress = onKeyPress;
		}

	}

	public static class GraphicWrapper {
		
		public final int x;
		public final int y;
		public final AbstractGraphicWrapper<?> graphic;
		public final OnTooltip onTooltip;
		public final OnClick onClick;
		public final OnKeyPress onKeyPress;
		
		public GraphicWrapper(int x, int y, AbstractGraphicWrapper<?> graphic, OnTooltip onTooltip, OnClick onClick, OnKeyPress onKeyPress) {
			this.x = x;
			this.y = y;
			this.graphic = graphic;
			this.onTooltip = onTooltip;
			this.onClick = onClick;
			this.onKeyPress = onKeyPress;
		}

	}

	public static class ChapterPage extends Page {

		public Module associatedModule;

		public ChapterPage(int pageNumber, Module module) {
			super(pageNumber);
			associatedModule = module;
		}

		@Override
		public void renderAdditionalText(MatrixStack stack, int refX, int refY, int xPageShift, FontRenderer font, int textWidth, int textStartX) {

			Module currMod = associatedModule;
			ITextComponent moduleTitle = currMod.getTitle().withStyle(TextFormatting.BOLD);
			int xShift = (textWidth - font.width(moduleTitle)) / 2;
			font.draw(stack, moduleTitle, refX + xShift + textStartX + xPageShift, refY + 16, 4210752);

			ITextComponent chapTitle = ElectroTextUtils.guidebook("chapters").withStyle(TextFormatting.UNDERLINE);
			xShift = (textWidth - font.width(chapTitle)) / 2;
			font.draw(stack, chapTitle, refX + textStartX + xShift + xPageShift, refY + 31, 4210752);
		}

	}

	public static class ModulePage extends Page {

		public ModulePage(int pageNumber) {
			super(pageNumber);
		}

		@Override
		public void renderAdditionalText(MatrixStack stack, int refX, int refY, int xPageShift, FontRenderer font, int textWidth, int textStartX) {
			ITextComponent modTitle = ElectroTextUtils.guidebook("availablemodules").withStyle(TextFormatting.BOLD);
			int xShift = (textWidth - font.width(modTitle)) / 2;
			font.draw(stack, modTitle, refX + textStartX + xShift + xPageShift, refY + 16, 4210752);
		}

	}

	public static class CoverPage extends Page {

		public CoverPage(int pageNumber) {
			super(pageNumber);
		}

		@Override
		public void renderAdditionalText(MatrixStack stack, int refX, int refY, int xPageShift, FontRenderer font, int textWidth, int textStartX) {
			// Not used as of now
		}

	}

}