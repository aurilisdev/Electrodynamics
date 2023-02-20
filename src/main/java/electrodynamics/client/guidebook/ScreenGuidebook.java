package electrodynamics.client.guidebook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.References;
import electrodynamics.api.screen.component.IGuiComponent;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Page;
import electrodynamics.client.guidebook.utils.components.Page.ChapterPage;
import electrodynamics.client.guidebook.utils.components.Page.CoverPage;
import electrodynamics.client.guidebook.utils.components.Page.ImageWrapper;
import electrodynamics.client.guidebook.utils.components.Page.ItemWrapper;
import electrodynamics.client.guidebook.utils.components.Page.ModulePage;
import electrodynamics.client.guidebook.utils.components.Page.TextWrapper;
import electrodynamics.client.guidebook.utils.pagedata.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.TextWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.ImageWrapperObject.ImageTextDescriptor;
import electrodynamics.common.inventory.container.item.ContainerGuidebook;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentGuidebookArrow;
import electrodynamics.prefab.screen.component.ScreenComponentGuidebookArrow.ArrowTextures;
import electrodynamics.prefab.screen.component.button.ButtonGuidebook;
import electrodynamics.prefab.screen.component.button.ButtonModuleSelector;
import electrodynamics.prefab.screen.component.button.ButtonSearchedText;
import electrodynamics.prefab.screen.component.button.ButtonGuidebook.GuidebookButtonType;
import electrodynamics.prefab.screen.component.button.ButtonSpecificPage;
import electrodynamics.prefab.screen.component.editbox.EditBoxSpecificPage;
import electrodynamics.prefab.utilities.RenderingUtils;
import electrodynamics.prefab.utilities.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

/**
 * A basic implementation of a Guidebook that allows for variable length text
 * and images along with some basic formatting options.
 * 
 * @author skip999
 *
 */
public class ScreenGuidebook extends GenericScreen<ContainerGuidebook> {

	private static final List<Module> GUIDEBOOK_MODULES = new ArrayList<>();

	// 0 is defined as the starting page
	private static final int GUIDEBOOK_STARTING_PAGE = 0;

	private static final int MODULES_PER_PAGE = 4;
	private static final int CHAPTERS_PER_PAGE = 4;

	private static final int LEFT_TEXTURE_WIDTH = 171;
	private static final int LEFT_TEXTURE_HEIGHT = 224;

	private static final int RIGHT_TEXTURE_WIDTH = 171;
	private static final int RIGHT_TEXTURE_HEIGHT = 224;

	private static final int OLD_TEXTURE_WIDTH = 171;

	private static final int LEFT_X_SHIFT = -OLD_TEXTURE_WIDTH / 2 + 2;
	private static final int RIGHT_X_SHIFT = OLD_TEXTURE_WIDTH / 2 + 3;

	public static final int TEXT_START_Y = 40;
	public static final int TEXT_END_Y = 180;

	public static final int LINE_HEIGHT = 10;

	public static final int TEXT_START_X = 12;
	public static final int TEXT_END_X = 166;

	public static final int TEXT_WIDTH = TEXT_END_X - TEXT_START_X;
	public static final int LINES_PER_PAGE = (TEXT_END_Y - TEXT_START_Y) / LINE_HEIGHT + 1;// have to add one to get the last line

	public static final int Y_PIXELS_PER_PAGE = LINES_PER_PAGE * LINE_HEIGHT;

	private static final int MODULE_SEPERATION = 40;
	private static final int CHAPTER_SEPERATION = 35;

	public static int currPageNumber = 0;

	private int nextPageNumber = 0;

	private List<Page> pages = new ArrayList<>();

	private static final ResourceLocation PAGE_TEXTURE_LEFT = new ResourceLocation(References.ID, "textures/screen/guidebook/resources/guidebookpageleft.png");
	private static final ResourceLocation PAGE_TEXTURE_RIGHT = new ResourceLocation(References.ID, "textures/screen/guidebook/resources/guidebookpageright.png");

	private PageButton forward;
	private PageButton back;

	private ButtonGuidebook home;
	private ButtonGuidebook chapters;
	private ButtonGuidebook search;

	private EditBoxSpecificPage searchBox;

	private ScreenComponentGuidebookArrow down;
	private ScreenComponentGuidebookArrow up;

	private ButtonModuleSelector caseSensitive;

	private List<ButtonModuleSelector> moduleParameters = new ArrayList<>();

	private List<SearchHit> searches = new ArrayList<>();

	private List<ButtonSearchedText> searchButtons = new ArrayList<>();

	private int lineY = TEXT_START_Y;

	private int lineX = TEXT_START_X;

	private int imagePixelHeightLeft = LINES_PER_PAGE * LINE_HEIGHT;
	private int imagePixelWidthLeft = TEXT_WIDTH;

	private int previousHeight = 0;

	private int color = 0;
	private boolean centered = false;
	private MutableComponent mergedText = Component.empty();

	private boolean previousWasText = false;

	public ScreenGuidebook(ContainerGuidebook screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);
		imageHeight += 58;
		inventoryLabelY += 58;
	}

	@Override
	protected void init() {

		super.init();

		/*
		 * we want to do this every time init is called to deal with the font reloading
		 * 
		 * for all of the chapters, take their lang keys and convert them into formatted
		 * char arrays
		 * 
		 * then split them based upon the page width
		 * 
		 * * we want images to be inserted between the text
		 * 
		 * then assign the split arrays to a page
		 * 
		 * then assign those pages to the chapter
		 */

		sortModules();

		initPageButtons();

		pages.add(getCoverPage());
		nextPageNumber++;

		genModuelPages();

		genPages();

		genSearchPages();

	}

	private void sortModules() {
		List<Module> temp = new ArrayList<>(GUIDEBOOK_MODULES);
		GUIDEBOOK_MODULES.clear();
		GUIDEBOOK_MODULES.add(temp.get(0));
		temp.remove(0);
		List<MutableComponent> cats = new ArrayList<>();
		for (Module mod : temp) {
			cats.add(mod.getTitle());
		}
		Collections.sort(cats, (component1, component2) -> component1.toString().compareToIgnoreCase(component2.toString()));
		for (MutableComponent cat : cats) {
			for (int i = 0; i < temp.size(); i++) {
				Module mod = temp.get(i);
				if (mod.isCat(cat)) {
					GUIDEBOOK_MODULES.add(mod);
					temp.remove(i);
					break;
				}
			}
		}
		for (Module module : GUIDEBOOK_MODULES) {
			module.chapters.clear();
			module.addChapters();
		}
	}

	private void initPageButtons() {
		int guiWidth = (width - imageWidth) / 2;
		int guiHeight = (height - imageHeight) / 2;
		forward = new PageButton(guiWidth + 142 - 45, guiHeight + 200, true, button -> pageForward(), true);
		back = new PageButton(guiWidth + 10 + 44, guiHeight + 200, false, button -> pageBackward(), true);
		home = new ButtonGuidebook(guiWidth + 115 - 186, guiHeight + 202, button -> goToModulePage(), GuidebookButtonType.HOME);
		chapters = new ButtonGuidebook(guiWidth + 50 - 100, guiHeight + 202, button -> goToChapterPage(), GuidebookButtonType.CHAPTERS);
		search = new ButtonGuidebook(guiWidth + 235, guiHeight + 202, button -> goToSearchPage(), GuidebookButtonType.SEARCH);
		addRenderableWidget(forward);
		addRenderableWidget(back);
		addRenderableWidget(home);
		addRenderableWidget(chapters);
		addRenderableWidget(search);
	}

	private void genModuelPages() {
		int guiWidth = (width - imageWidth) / 2;
		int guiHeight = (height - imageHeight) / 2;

		int numPages = (int) Math.ceil((double) GUIDEBOOK_MODULES.size() / (double) MODULES_PER_PAGE);
		int index = 0;
		for (int i = 0; i < numPages; i++) {
			final ModulePage page = new ModulePage(nextPageNumber);

			for (int j = 0; i < MODULES_PER_PAGE; j++) {

				if (index >= GUIDEBOOK_MODULES.size()) {
					break;
				}
				Module module = GUIDEBOOK_MODULES.get(index);
				page.images.add(new ImageWrapper(TEXT_START_X, j * MODULE_SEPERATION + TEXT_START_Y - 2, module.getLogo()));
				int xShift = nextPageNumber % 2 == 0 ? LEFT_X_SHIFT : RIGHT_X_SHIFT - 8;
				addRenderableWidget(new ButtonSpecificPage(guiWidth + 45 + xShift, guiHeight + 43 + j * MODULE_SEPERATION, 120, 20, nextPageNumber, module.getTitle(), button -> setPageNumber(module.getPage())));
				index++;
			}

			pages.add(page);

			nextPageNumber++;
		}

	}

	private void genPages() {

		int guiWidth = (width - imageWidth) / 2;
		int guiHeight = (height - imageHeight) / 2;

		for (Module module : GUIDEBOOK_MODULES) {

			module.setStartPage(nextPageNumber);

			int numPages = (int) Math.ceil((double) module.chapters.size() / (double) CHAPTERS_PER_PAGE);
			int index = 0;

			for (int i = 0; i < numPages; i++) {
				final ChapterPage chapterPage = new ChapterPage(nextPageNumber, module);
				for (int j = 0; j < CHAPTERS_PER_PAGE; j++) {
					if (index >= module.chapters.size()) {
						break;
					}
					final Chapter chapter = module.chapters.get(index);

					if (chapter.getLogo() instanceof ImageWrapperObject image) {
						chapterPage.images.add(new ImageWrapper(TEXT_START_X, j * CHAPTER_SEPERATION + TEXT_START_Y + 10, image));
					} else if (chapter.getLogo() instanceof ItemWrapperObject item) {
						chapterPage.items.add(new ItemWrapper(TEXT_START_X, j * CHAPTER_SEPERATION + TEXT_START_Y + 10, item));
					}

					int xShift = nextPageNumber % 2 == 0 ? LEFT_X_SHIFT : RIGHT_X_SHIFT - 8;

					addRenderableWidget(new ButtonSpecificPage(guiWidth + 45 + xShift, guiHeight + 56 + j * CHAPTER_SEPERATION, 120, 20, nextPageNumber, chapter.getTitle(), button -> setPageNumber(chapter.getStartPage())));

					index++;
				}
				pages.add(chapterPage);
				nextPageNumber++;

			}

			for (Chapter chapter : module.chapters) {

				chapter.setStartPage(nextPageNumber);

				Page currentPage = new Page(nextPageNumber);
				currentPage.associatedChapter = chapter;
				nextPageNumber++;

				for (Object data : chapter.pageData) {

					if (data instanceof TextWrapperObject textWrapper) {

						previousWasText = true;
						lineX = TEXT_START_X;
						imagePixelWidthLeft = TEXT_WIDTH;
						previousHeight = 0;

						if (textWrapper == TextWrapperObject.BLANK_LINE) {

							currentPage = writeCurrentTextToPage(currentPage, chapter);

							imagePixelHeightLeft -= LINE_HEIGHT;

							if (imagePixelHeightLeft <= 0) {
								currentPage = resetToNewPage(currentPage, chapter);
							} else {
								lineY += LINE_HEIGHT;
							}

						} else {

							if (textWrapper.newPage || textWrapper.isSeparateStart) {

								currentPage = writeCurrentTextToPage(currentPage, chapter);

							}

							centered = textWrapper.center;

							color = textWrapper.color;

							MutableComponent indentions = Component.empty();

							for (int i = 0; i < textWrapper.numberOfIndentions; i++) {
								indentions = indentions.append("    ");
							}

							mergedText = mergedText.append(indentions.append(textWrapper.text));

							if (textWrapper.newPage) {

								currentPage = resetToNewPage(currentPage, chapter);

							}
						}

					} else if (data instanceof ImageWrapperObject imageWrapper) {

						if (previousWasText) {
							currentPage = writeCurrentTextToPage(currentPage, chapter);
							centered = false;
							previousWasText = false;
						}

						if (imageWrapper.newPage) {
							currentPage = resetToNewPage(currentPage, chapter);
							previousHeight = 0;
						}

						int trueHeight = imageWrapper.trueHeight - imageWrapper.descriptorTopOffset + imageWrapper.descriptorBottomOffset;

						if (trueHeight > Y_PIXELS_PER_PAGE) {
							throw new UnsupportedOperationException("The image cannot be more than " + (Y_PIXELS_PER_PAGE) + " pixels tall!");
						}

						if (imageWrapper.allowNextToOthers && imageWrapper.width <= imagePixelWidthLeft) {
							imagePixelWidthLeft -= imageWrapper.width;

							currentPage.images.add(new ImageWrapper(lineX, lineY, imageWrapper));

							lineX += imageWrapper.width;

							if (trueHeight > previousHeight) {
								lineY += previousHeight;
								previousHeight = trueHeight;
								lineY -= trueHeight;
							}

						} else {
							lineX = TEXT_START_X;
							imagePixelWidthLeft = TEXT_WIDTH;
							previousHeight = 0;

							if (trueHeight > imagePixelHeightLeft) {

								currentPage = resetToNewPage(currentPage, chapter);
								previousHeight = trueHeight;

							}

							currentPage.images.add(new ImageWrapper(lineX, lineY, imageWrapper));

							lineY += trueHeight;
							imagePixelHeightLeft -= trueHeight;
						}

						if (imagePixelHeightLeft == 0) {
							currentPage = resetToNewPage(currentPage, chapter);
							previousHeight = 0;
						}

					} else if (data instanceof ItemWrapperObject itemWrapper) {

						if (previousWasText) {
							currentPage = writeCurrentTextToPage(currentPage, chapter);
							centered = false;
							previousWasText = false;
						}

						if (itemWrapper.newPage) {
							currentPage = resetToNewPage(currentPage, chapter);
							previousHeight = 0;
						}

						int trueHeight = itemWrapper.height - itemWrapper.descriptorTopOffset + itemWrapper.descriptorBottomOffset;

						if (trueHeight > Y_PIXELS_PER_PAGE) {
							throw new UnsupportedOperationException("The item cannot be more than " + Y_PIXELS_PER_PAGE + " pixels tall!");
						}

						if (itemWrapper.allowNextToOthers && itemWrapper.width <= imagePixelWidthLeft) {

							imagePixelWidthLeft -= itemWrapper.width;

							currentPage.items.add(new ItemWrapper(lineX, lineY, itemWrapper));

							lineX += itemWrapper.width;

							if (trueHeight > previousHeight) {
								lineY += previousHeight;
								previousHeight = trueHeight;
								lineY -= trueHeight;
							}

						} else {

							if (trueHeight > imagePixelHeightLeft) {

								currentPage = resetToNewPage(currentPage, chapter);
								previousHeight = trueHeight;

							}

							currentPage.items.add(new ItemWrapper(lineX, lineY, itemWrapper));

							lineY += trueHeight;
							imagePixelHeightLeft -= trueHeight;

						}

						if (imagePixelHeightLeft == 0) {
							currentPage = resetToNewPage(currentPage, chapter);
							previousHeight = 0;
						}

					}

				}

				currentPage = writeCurrentTextToPage(currentPage, chapter);
				pages.add(currentPage);
				imagePixelHeightLeft = Y_PIXELS_PER_PAGE;
				lineY = TEXT_START_Y;
				lineX = TEXT_START_X;
				imagePixelWidthLeft = TEXT_WIDTH;

			}

		}

		if (pages.size() % 2 == 1) {
			pages.add(new CoverPage(nextPageNumber));
			nextPageNumber++;
		}

	}

	private Page writeCurrentTextToPage(Page currentPage, Chapter chapter) {

		if (mergedText.equals(Component.empty())) {
			return currentPage;
		}

		int remainder = lineY % LINE_HEIGHT;
		if (remainder > 0) {
			int whole = lineY - remainder;
			lineY = whole + LINE_HEIGHT;
			imagePixelHeightLeft = TEXT_END_Y + LINE_HEIGHT - lineY;
		}

		List<FormattedText> text = new ArrayList<>();
		text.addAll(font.getSplitter().splitLines(mergedText, TEXT_WIDTH, Style.EMPTY));
		mergedText = Component.empty();

		while (text.size() > 0) {

			if (imagePixelHeightLeft <= 0) {
				currentPage = resetToNewPage(currentPage, chapter);
			}
			currentPage.text.add(new TextWrapper(lineX, lineY, text.get(0), color, centered));

			text.remove(0);

			lineY += LINE_HEIGHT;

			imagePixelHeightLeft -= LINE_HEIGHT;
		}

		return currentPage;

	}

	private Page resetToNewPage(Page page, Chapter chapter) {
		pages.add(page);
		page = new Page(nextPageNumber);
		page.associatedChapter = chapter;
		nextPageNumber++;

		imagePixelHeightLeft = Y_PIXELS_PER_PAGE;
		lineY = TEXT_START_Y;
		lineX = TEXT_START_X;
		imagePixelWidthLeft = TEXT_WIDTH;

		return page;
	}

	private void genSearchPages() {

		pages.add(getSearchPageLeft());
		nextPageNumber++;

		pages.add(getSeatchPageRight());
		nextPageNumber++;

	}

	@Override
	protected void renderBg(PoseStack stack, float partialTick, int x, int y) {

		int guiWidth = (width - imageWidth) / 2;
		int guiHeight = (height - imageHeight) / 2;

		RenderingUtils.bindTexture(PAGE_TEXTURE_LEFT);
		blit(stack, guiWidth + LEFT_X_SHIFT, guiHeight, 0, 0, LEFT_TEXTURE_WIDTH, LEFT_TEXTURE_HEIGHT);

		RenderingUtils.bindTexture(PAGE_TEXTURE_RIGHT);
		blit(stack, guiWidth + RIGHT_X_SHIFT, guiHeight, 0, 0, RIGHT_TEXTURE_WIDTH, RIGHT_TEXTURE_HEIGHT);

		updatePageArrowVis();

		renderPageBackground(stack, LEFT_X_SHIFT, guiWidth, guiHeight, getCurrentPage());
		renderPageBackground(stack, RIGHT_X_SHIFT - 8, guiWidth, guiHeight, getNextPage());

		int xAxis = x - guiWidth;
		int yAxis = y - guiHeight;

		for (IGuiComponent component : components) {
			component.renderBackground(stack, xAxis, yAxis, guiWidth, guiHeight);
		}

	}

	private void renderPageBackground(PoseStack stack, int xShift, int guiWidth, int guiHeight, Page page) {

		for (ImageWrapper wrapper : page.images) {

			ImageWrapperObject image = wrapper.image();

			RenderingUtils.bindTexture(wrapper.image().location);
			blit(stack, guiWidth + wrapper.x() + image.xOffset + xShift, guiHeight + wrapper.y() + image.yOffset - image.descriptorTopOffset, image.uStart, image.vStart, image.width, image.height, image.imgheight, image.imgwidth);

		}

		for (ItemWrapper wrapper : page.items) {

			ItemWrapperObject item = wrapper.item();

			RenderingUtils.renderItemScaled(item.item, guiWidth + item.xOffset + wrapper.x() + xShift, guiHeight + item.yOffset + wrapper.y(), item.scale);
		}
	}

	@Override
	protected void renderLabels(PoseStack stack, int x, int y) {

		renderPageLabels(stack, LEFT_X_SHIFT, getCurrentPage());

		renderPageLabels(stack, RIGHT_X_SHIFT - 8, getNextPage());

	}

	private void renderPageLabels(PoseStack stack, int xPageShift, Page page) {
		int refX = titleLabelX - 8;
		int refY = titleLabelY - 6;

		if (page instanceof CoverPage) {

			for (TextWrapper text : page.text) {

				if (text.centered()) {
					int xShift = (TEXT_WIDTH - font.width(text.characters())) / 2;
					font.draw(stack, Language.getInstance().getVisualOrder(text.characters()), text.x() + refX + xShift + xPageShift, refY + text.y(), text.color());
				} else {
					font.draw(stack, Language.getInstance().getVisualOrder(text.characters()), text.x() + refX + xPageShift, text.y() + refY, text.color());
				}

			}

			for (ImageWrapper wrapper : page.images) {

				ImageWrapperObject image = wrapper.image();

				for (ImageTextDescriptor descriptor : image.descriptors) {
					font.draw(stack, descriptor.text, refX + wrapper.x() + descriptor.xOffsetFromImage + xPageShift, refY + wrapper.y() + descriptor.yOffsetFromImage, descriptor.color);
				}

			}

			for (ItemWrapper wrapper : page.items) {

				ItemWrapperObject item = wrapper.item();

				for (ImageTextDescriptor descriptor : item.descriptors) {
					font.draw(stack, descriptor.text, refX + wrapper.x() + descriptor.xOffsetFromImage + xPageShift, refY + wrapper.y() + descriptor.yOffsetFromImage, descriptor.color);
				}

			}

		} else if (page instanceof ModulePage) {

			Component modTitle = TextUtils.guidebook("availablemodules").withStyle(ChatFormatting.BOLD);
			int xShift = (TEXT_WIDTH - font.width(modTitle)) / 2;
			font.draw(stack, modTitle, refX + TEXT_START_X + xShift + xPageShift, refY + 16, 4210752);

		} else if (page instanceof ChapterPage chapter) {
			Module currMod = chapter.associatedModule;
			Component moduleTitle = currMod.getTitle().withStyle(ChatFormatting.BOLD);
			int xShift = (TEXT_WIDTH - font.width(moduleTitle)) / 2;
			font.draw(stack, moduleTitle, refX + xShift + TEXT_START_X + xPageShift, refY + 16, 4210752);

			Component chapTitle = TextUtils.guidebook("chapters").withStyle(ChatFormatting.UNDERLINE);
			xShift = (TEXT_WIDTH - font.width(chapTitle)) / 2;
			font.draw(stack, chapTitle, refX + TEXT_START_X + xShift + xPageShift, refY + 31, 4210752);
		} else {
			Module currMod = page.associatedChapter.module;
			Component moduleTitle = currMod.getTitle().withStyle(ChatFormatting.BOLD);
			int xShift = (TEXT_WIDTH - font.width(moduleTitle)) / 2;
			font.draw(stack, moduleTitle, refX + TEXT_START_X + xShift + xPageShift, refY + 16, 4210752);

			Component chapTitle = page.associatedChapter.getTitle().withStyle(ChatFormatting.UNDERLINE);
			xShift = (TEXT_WIDTH - font.width(chapTitle)) / 2;
			font.draw(stack, chapTitle, refX + TEXT_START_X + xShift + xPageShift, refY + 26, 4210752);

			Component pageNumber = Component.literal(page.getPage() + 1 + "");
			xShift = (TEXT_WIDTH - font.width(pageNumber)) / 2;
			font.draw(stack, pageNumber, refX + TEXT_START_X + xShift + xPageShift, refY + 200, 4210752);

			for (TextWrapper text : page.text) {

				if (text.centered()) {
					xShift = (TEXT_WIDTH - font.width(text.characters())) / 2;
					font.draw(stack, Language.getInstance().getVisualOrder(text.characters()), text.x() + refX + xShift + xPageShift, refY + text.y(), text.color());
				} else {
					font.draw(stack, Language.getInstance().getVisualOrder(text.characters()), text.x() + refX + xPageShift, text.y() + refY, text.color());
				}

			}

			for (ImageWrapper wrapper : page.images) {

				ImageWrapperObject image = wrapper.image();

				for (ImageTextDescriptor descriptor : image.descriptors) {
					font.draw(stack, descriptor.text, refX + wrapper.x() + descriptor.xOffsetFromImage + xPageShift, refY + wrapper.y() + descriptor.yOffsetFromImage, descriptor.color);
				}

			}

			for (ItemWrapper wrapper : page.items) {

				ItemWrapperObject item = wrapper.item();

				for (ImageTextDescriptor descriptor : item.descriptors) {
					font.draw(stack, descriptor.text, refX + wrapper.x() + descriptor.xOffsetFromImage + xPageShift, refY + wrapper.y() + descriptor.yOffsetFromImage, descriptor.color);
				}

			}
		}
	}

	private Page getCurrentPage() {

		if (currPageNumber >= pages.size()) {
			currPageNumber = pages.size() - 2;
		}
		return pages.get(currPageNumber);
	}

	private Page getNextPage() {
		if (currPageNumber >= pages.size()) {
			currPageNumber = pages.size() - 2;
		}
		return pages.get(currPageNumber + 1);
	}

	protected void pageForward() {
		if (currPageNumber < pages.size() - 2) {
			movePage(2);
		}
		updatePageArrowVis();
	}

	protected void pageBackward() {
		if (currPageNumber > GUIDEBOOK_STARTING_PAGE + 1) {
			movePage(-2);
		}
		updatePageArrowVis();
	}

	protected void goToChapterPage() {
		Page page = getCurrentPage();
		if (page instanceof ModulePage module) {
			setPageNumber(module.getPage());
		} else if (page instanceof ChapterPage chapter) {
			setPageNumber(chapter.associatedModule.getPage());
		} else {
			setPageNumber(page.associatedChapter.module.getPage());
		}
	}

	protected void goToSearchPage() {
		setPageNumber(pages.size() - 2);
	}

	protected void goToModulePage() {
		setPageNumber(1);
	}

	private void movePage(int number) {
		currPageNumber += number;
		currPageNumber = Mth.clamp(currPageNumber, 0, pages.size() - 4);
	}

	private static void setPageNumber(int number) {
		if (number % 2 == 1) {
			number = number - 1;
		}
		currPageNumber = number;
	}

	private void updatePageArrowVis() {
		forward.visible = currPageNumber < pages.size() - 4;
		back.visible = currPageNumber > GUIDEBOOK_STARTING_PAGE && currPageNumber < pages.size() - 2;
		home.visible = currPageNumber != 0;
		chapters.visible = currPageNumber != 0 && currPageNumber < pages.size() - 4;
		search.visible = currPageNumber < pages.size() - 4;
	}

	public static void addGuidebookModule(Module module) {
		if (module instanceof ModuleElectrodynamics) {
			GUIDEBOOK_MODULES.add(0, module);
		} else {
			GUIDEBOOK_MODULES.add(module);
		}

	}

	private CoverPage getCoverPage() {

		CoverPage page = new CoverPage(nextPageNumber);

		List<FormattedText> split = font.getSplitter().splitLines(TextUtils.guidebook("title").withStyle(ChatFormatting.BOLD), TEXT_WIDTH, Style.EMPTY);

		int y = 16;

		for (FormattedText text : split) {

			page.text.add(new TextWrapper(TEXT_START_X, y, text, TextWrapperObject.DEFAULT_COLOR, true));
			y += LINE_HEIGHT;

		}

		y += 5;

		int xOffset = (TEXT_WIDTH - 74) / 2;

		page.images.add(new ImageWrapper(TEXT_START_X, y, new ImageWrapperObject(xOffset, 0, 0, 0, 74, 100, 74, 100, new ResourceLocation(References.ID, "textures/screen/guidebook/resources/guidebookcover.png"))));

		y += 105;

		split = font.getSplitter().splitLines(TextUtils.guidebook("titlequote"), TEXT_WIDTH, Style.EMPTY);

		for (FormattedText text : split) {

			page.text.add(new TextWrapper(TEXT_START_X, y, text, TextWrapperObject.DEFAULT_COLOR, false));
			y += LINE_HEIGHT;

		}

		return page;

	}

	private CoverPage getSearchPageLeft() {
		CoverPage page = new CoverPage(nextPageNumber);

		List<FormattedText> split = font.getSplitter().splitLines(TextUtils.guidebook("searchparameters").withStyle(ChatFormatting.BOLD), TEXT_WIDTH, Style.EMPTY);

		int y = 16;

		for (FormattedText text : split) {

			page.text.add(new TextWrapper(TEXT_START_X, y, text, TextWrapperObject.DEFAULT_COLOR, true));
			y += LINE_HEIGHT + 2;

		}

		y += 5;

		int guiWidth = (width - imageWidth) / 2;
		int guiHeight = (height - imageHeight) / 2;

		for (Module module : GUIDEBOOK_MODULES) {

			page.text.add(new TextWrapper(TEXT_START_X + 15, y, module.getTitle(), TextWrapperObject.DEFAULT_COLOR, false));

			ButtonModuleSelector selector = new ButtonModuleSelector(guiWidth - 70, guiHeight - 1 + y, nextPageNumber, true);

			moduleParameters.add(selector);

			addRenderableWidget(selector);

			y += LINE_HEIGHT;

		}

		page.text.add(new TextWrapper(TEXT_START_X + 15, 165, TextUtils.guidebook("casesensitive"), TextWrapperObject.DEFAULT_COLOR, false));
		caseSensitive = new ButtonModuleSelector(guiWidth - 70, guiHeight + 165, nextPageNumber, false);
		addRenderableWidget(caseSensitive);

		addRenderableWidget(new ButtonSpecificPage(guiWidth - 71, guiHeight + 180, 75, 20, nextPageNumber, TextUtils.guidebook("selectall"), button -> {
			for (ButtonModuleSelector selector : moduleParameters) {
				selector.setSelected(true);
			}
		}));

		addRenderableWidget(new ButtonSpecificPage(guiWidth + 8, guiHeight + 180, 75, 20, nextPageNumber, TextUtils.guidebook("selectnone"), button -> {
			for (ButtonModuleSelector selector : moduleParameters) {
				selector.setSelected(false);
			}
		}));

		return page;
	}

	private CoverPage getSeatchPageRight() {
		CoverPage page = new CoverPage(nextPageNumber);

		int guiWidth = (width - imageWidth) / 2;
		int guiHeight = (height - imageHeight) / 2;

		searchBox = new EditBoxSpecificPage(font, guiWidth + 92, guiHeight + 10, TEXT_WIDTH, 12, nextPageNumber, Component.empty());
		searchBox.setResponder(this::onTextSearched);
		searchBox.setTextColor(0xFFFFFFFF);
		searchBox.setMaxLength(100);

		addRenderableWidget(searchBox);

		for (int i = 0; i < SEARCH_BUTTON_COUNT; i++) {
			ButtonSearchedText search = new ButtonSearchedText(guiWidth + 92, guiHeight + 35 + 35 * i, TEXT_WIDTH, nextPageNumber, button -> {

			});
			searchButtons.add(search);
			search.setShouldShow(false);
			addRenderableWidget(search);
		}

		components.add(down = new ScreenComponentGuidebookArrow(ArrowTextures.ARROW_DOWN, this, 174, 200, nextPageNumber));

		components.add(up = new ScreenComponentGuidebookArrow(ArrowTextures.ARROW_UP, this, 154, 200, nextPageNumber));

		return page;
	}

	private final int minScroll = 0;
	private int maxScroll = 0;
	private int scrollIndex = 0;

	private static final int SEARCH_BUTTON_COUNT = 5;

	private void onTextSearched(String text) {

		searches.clear();

		if (text.isEmpty() || text.isBlank()) {
			maxScroll = 0;
			scrollIndex = 0;
			resetSearchButtons();
			return;
		}

		List<SearchHit> found = new ArrayList<>();

		List<Module> selectedModules = new ArrayList<>();

		for (int i = 0; i < GUIDEBOOK_MODULES.size(); i++) {
			if (moduleParameters.get(i).isSelected()) {
				selectedModules.add(GUIDEBOOK_MODULES.get(i));
			}
		}

		for (Page page : pages) {

			if (!(page instanceof ChapterPage || page instanceof ModulePage || page instanceof CoverPage)) {

				for (Module module : selectedModules) {
					if (page.associatedChapter.module.isCat(module.getTitle())) {
						for (TextWrapper wrapper : page.text) {

							if (caseSensitive.isSelected() && wrapper.characters().getString().contains(text)) {
								
								found.add(new SearchHit(wrapper.characters(), page.getPage(), page.associatedChapter));
								
							} else if (!caseSensitive.isSelected() && wrapper.characters().getString().toLowerCase().contains(text.toLowerCase())) {
								
								found.add(new SearchHit(wrapper.characters(), page.getPage(), page.associatedChapter));
								
							}

						}
					}
				}
			}

		}
		searches.addAll(found);

		maxScroll = Math.max(0, searches.size() - SEARCH_BUTTON_COUNT);

		if (scrollIndex > maxScroll) {
			scrollIndex = maxScroll;
		}

		updateSearchButtons();

	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double delta) {

		if (searches.size() == 0) {
			resetSearchButtons();
			return super.mouseScrolled(mouseX, mouseY, delta);
		}

		if (currPageNumber >= pages.size() - 2) {

			int scrollDelta = delta > 0 ? -1 : 1;

			scrollIndex = Mth.clamp(scrollIndex + scrollDelta, minScroll, maxScroll);

			updateSearchButtons();

		}
		return super.mouseScrolled(mouseX, mouseY, delta);
	}

	private void updateSearchButtons() {
		up.setShouldRender(scrollIndex > minScroll);
		down.setShouldRender(scrollIndex < maxScroll);
		
		resetSearchButtons();

		if (searches.size() == 0) {
			return;
		}
		
		if (scrollIndex > maxScroll) {
			scrollIndex = maxScroll;
		}

		int listSize = SEARCH_BUTTON_COUNT > searches.size() ? searches.size() : SEARCH_BUTTON_COUNT;
		
		for (int i = 0; i < listSize; i++) {

			ButtonSearchedText searched = searchButtons.get(i);
			SearchHit hit = searches.get(i + scrollIndex);

			searched.setShouldShow(true);
			searched.setChapter(hit.chapter.getTitle());
			searched.setLine(hit.text);

		}

	}

	private void resetSearchButtons() {
		for (ButtonSearchedText button : searchButtons) {
			button.setLine(Component.empty());
			button.setChapter(Component.empty());
			button.setShouldShow(false);
		}
	}

	@Override
	public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
		InputConstants.Key mouseKey = InputConstants.getKey(pKeyCode, pScanCode);
		if (this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey) && searchBox.isVisible() && searchBox.isFocused()) {
			return false;
		}
		return super.keyPressed(pKeyCode, pScanCode, pModifiers);
	}

	private static record SearchHit(FormattedText text, int page, Chapter chapter) {

	}

}
