package electrodynamics.client.guidebook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.References;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Page;
import electrodynamics.client.guidebook.utils.components.Page.ChapterPage;
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
import electrodynamics.prefab.screen.component.button.ButtonGuidebook;
import electrodynamics.prefab.screen.component.button.ButtonGuidebook.ButtonType;
import electrodynamics.prefab.screen.component.button.ButtonSpecificPage;
import electrodynamics.prefab.utilities.RenderingUtils;
import electrodynamics.prefab.utilities.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
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

	private static final int TEXTURE_WIDTH = 176;
	private static final int TEXTURE_HEIGHT = 224;

	public static final int TEXT_START_Y = 40;
	public static final int TEXT_END_Y = 180;

	public static final int LINE_HEIGHT = 10;

	public static final int TEXT_START_X = 10;
	public static final int TEXT_END_X = 158;

	public static final int TEXT_WIDTH = TEXT_END_X - TEXT_START_X;
	public static final int LINES_PER_PAGE = (TEXT_END_Y - TEXT_START_Y) / LINE_HEIGHT + 1;// have to add one to get the last line

	public static final int Y_PIXELS_PER_PAGE = LINES_PER_PAGE * LINE_HEIGHT;

	private static final int MODULE_SEPERATION = 40;
	private static final int CHAPTER_SEPERATION = 35;

	public static int currPageNumber = 0;

	private int nextPageNumber = 0;

	private List<Page> pages = new ArrayList<>();

	private static final ResourceLocation PAGE_TEXTURE = new ResourceLocation(References.ID + ":textures/screen/guidebook/resources/guidebookpage.png");

	private PageButton forward;
	private PageButton back;

	private ButtonGuidebook home;
	private ButtonGuidebook chapters;

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

		genModuelPages();

		genPages();

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
		forward = new PageButton(guiWidth + 142, guiHeight + 200, true, button -> pageForward(), true);
		back = new PageButton(guiWidth + 10, guiHeight + 200, false, button -> pageBackward(), true);
		home = new ButtonGuidebook(guiWidth + 115, guiHeight + 202, button -> goToModulePage(), ButtonType.HOME);
		chapters = new ButtonGuidebook(guiWidth + 50, guiHeight + 202, button -> goToChapterPage(), ButtonType.CHAPTERS);
		addRenderableWidget(forward);
		addRenderableWidget(back);
		addRenderableWidget(home);
		addRenderableWidget(chapters);
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
				addRenderableWidget(new ButtonSpecificPage(guiWidth + 45, guiHeight + 43 + j * MODULE_SEPERATION, 120, 20, nextPageNumber, module.getTitle(), button -> setPageNumber(module.getPage())));
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

					addRenderableWidget(new ButtonSpecificPage(guiWidth + 45, guiHeight + 56 + j * CHAPTER_SEPERATION, 120, 20, nextPageNumber, chapter.getTitle(), button -> setPageNumber(chapter.getStartPage())));

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
								pages.add(currentPage);
								currentPage = new Page(nextPageNumber);
								currentPage.associatedChapter = chapter;
								nextPageNumber++;

								imagePixelHeightLeft = Y_PIXELS_PER_PAGE;
								lineY = TEXT_START_Y;
								lineX = TEXT_START_X;
								imagePixelWidthLeft = TEXT_WIDTH;
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

								pages.add(currentPage);
								currentPage = new Page(nextPageNumber);
								currentPage.associatedChapter = chapter;
								nextPageNumber++;

								imagePixelHeightLeft = Y_PIXELS_PER_PAGE;
								lineY = TEXT_START_Y;

							}
						}

					} else if (data instanceof ImageWrapperObject imageWrapper) {

						if (previousWasText) {
							currentPage = writeCurrentTextToPage(currentPage, chapter);
							centered = false;
							previousWasText = false;
						}

						if (imageWrapper.newPage) {
							pages.add(currentPage);
							currentPage = new Page(nextPageNumber);
							currentPage.associatedChapter = chapter;
							nextPageNumber++;

							imagePixelHeightLeft = Y_PIXELS_PER_PAGE;
							lineY = TEXT_START_Y;
							lineX = TEXT_START_X;
							imagePixelWidthLeft = TEXT_WIDTH;
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

								pages.add(currentPage);
								currentPage = new Page(nextPageNumber);
								currentPage.associatedChapter = chapter;
								nextPageNumber++;

								imagePixelHeightLeft = Y_PIXELS_PER_PAGE;
								lineY = TEXT_START_Y;
								lineX = TEXT_START_X;
								imagePixelWidthLeft = TEXT_WIDTH;
								previousHeight = 0;

							}

							currentPage.images.add(new ImageWrapper(lineX, lineY, imageWrapper));

							lineY += trueHeight;
							imagePixelHeightLeft -= trueHeight;
						}

						if (imagePixelHeightLeft == 0) {
							pages.add(currentPage);
							currentPage = new Page(nextPageNumber);
							currentPage.associatedChapter = chapter;
							nextPageNumber++;

							imagePixelHeightLeft = Y_PIXELS_PER_PAGE;
							lineY = TEXT_START_Y;
							lineX = TEXT_START_X;
							imagePixelWidthLeft = TEXT_WIDTH;
						}

					} else if (data instanceof ItemWrapperObject itemWrapper) {

						if (previousWasText) {
							currentPage = writeCurrentTextToPage(currentPage, chapter);
							centered = false;
							previousWasText = false;
						}

						if (itemWrapper.newPage) {
							pages.add(currentPage);
							currentPage = new Page(nextPageNumber);
							currentPage.associatedChapter = chapter;
							nextPageNumber++;

							imagePixelHeightLeft = Y_PIXELS_PER_PAGE;
							lineY = TEXT_START_Y;
							lineX = TEXT_START_X;
							imagePixelWidthLeft = TEXT_WIDTH;
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

								pages.add(currentPage);
								currentPage = new Page(nextPageNumber);
								currentPage.associatedChapter = chapter;
								nextPageNumber++;

								imagePixelHeightLeft = Y_PIXELS_PER_PAGE;
								lineY = TEXT_START_Y;
								lineX = TEXT_START_X;
								imagePixelWidthLeft = TEXT_WIDTH;

							}

							currentPage.items.add(new ItemWrapper(lineX, lineY, itemWrapper));

							lineY += trueHeight;
							imagePixelHeightLeft -= trueHeight;

						}

						if (imagePixelHeightLeft == 0) {
							pages.add(currentPage);
							currentPage = new Page(nextPageNumber);
							currentPage.associatedChapter = chapter;
							nextPageNumber++;

							imagePixelHeightLeft = Y_PIXELS_PER_PAGE;
							lineY = TEXT_START_Y;
							lineX = TEXT_START_X;
							imagePixelWidthLeft = TEXT_WIDTH;
						}

					}

				}

				currentPage = writeCurrentTextToPage(currentPage, chapter);
				pages.add(currentPage);
				imagePixelHeightLeft = Y_PIXELS_PER_PAGE;
				lineY = TEXT_START_Y;
				lineX = TEXT_START_X;
				imagePixelWidthLeft = TEXT_WIDTH;
				// nextPageNumber++;

			}

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

		List<FormattedCharSequence> text = new ArrayList<>();
		text.addAll(font.split(mergedText, TEXT_WIDTH));
		mergedText = Component.empty();

		while (text.size() > 0) {

			if (imagePixelHeightLeft <= 0) {
				imagePixelHeightLeft = Y_PIXELS_PER_PAGE;
				lineY = TEXT_START_Y;
				pages.add(currentPage);
				currentPage = new Page(nextPageNumber);
				currentPage.associatedChapter = chapter;
				nextPageNumber++;
			}

			currentPage.text.add(new TextWrapper(lineX, lineY, text.get(0), color, centered));

			text.remove(0);

			lineY += LINE_HEIGHT;

			imagePixelHeightLeft -= LINE_HEIGHT;
		}

		return currentPage;

	}

	@Override
	protected void renderBg(PoseStack stack, float partialTick, int x, int y) {

		int guiWidth = (width - imageWidth) / 2;
		int guiHeight = (height - imageHeight) / 2;
		RenderingUtils.bindTexture(PAGE_TEXTURE);
		blit(stack, guiWidth, guiHeight, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT);

		Page page = getCurrentPage();

		updatePageArrowVis();

		for (ImageWrapper wrapper : page.images) {

			ImageWrapperObject image = wrapper.image();

			RenderingUtils.bindTexture(wrapper.image().location);
			blit(stack, guiWidth + wrapper.x() + image.xOffset, guiHeight + wrapper.y() + image.yOffset - image.descriptorTopOffset, image.uStart, image.vStart, image.width, image.height, image.imgheight, image.imgwidth);

		}

		for (ItemWrapper wrapper : page.items) {

			ItemWrapperObject item = wrapper.item();

			RenderingUtils.renderItemScaled(item.item, guiWidth + item.xOffset + wrapper.x(), guiHeight + item.yOffset + wrapper.y(), item.scale);
		}

	}

	@Override
	protected void renderLabels(PoseStack stack, int x, int y) {
		int refX = titleLabelX - 8;
		int refY = titleLabelY - 6;

		Page page = getCurrentPage();

		if (page instanceof ModulePage) {

			Component modTitle = TextUtils.guidebook("availablemodules").withStyle(ChatFormatting.BOLD);
			int xShift = (TEXTURE_WIDTH - font.width(modTitle)) / 2;
			font.draw(stack, modTitle, refX + xShift, refY + 16, 4210752);

		} else if (page instanceof ChapterPage) {
			Module currMod = getCurrentModule();
			if (currMod != null) {
				Component moduleTitle = currMod.getTitle().withStyle(ChatFormatting.BOLD);
				int xShift = (TEXTURE_WIDTH - font.width(moduleTitle)) / 2;
				font.draw(stack, moduleTitle, refX + xShift, refY + 16, 4210752);

				Component chapTitle = TextUtils.guidebook("chapters").withStyle(ChatFormatting.UNDERLINE);
				xShift = (TEXTURE_WIDTH - font.width(chapTitle)) / 2;
				font.draw(stack, chapTitle, refX + xShift, refY + 31, 4210752);
			}
		} else {
			Module currMod = getCurrentModule();
			if (currMod != null) {
				Component moduleTitle = currMod.getTitle().withStyle(ChatFormatting.BOLD);
				int xShift = (TEXTURE_WIDTH - font.width(moduleTitle)) / 2;
				font.draw(stack, moduleTitle, refX + xShift, refY + 16, 4210752);

				Component chapTitle = page.associatedChapter.getTitle().withStyle(ChatFormatting.UNDERLINE);
				xShift = (TEXTURE_WIDTH - font.width(chapTitle)) / 2;
				font.draw(stack, chapTitle, refX + xShift, refY + 26, 4210752);

				Component pageNumber = Component.literal(getCurrentPageNumber() + "");
				xShift = (TEXTURE_WIDTH - font.width(pageNumber)) / 2;
				font.draw(stack, pageNumber, refX + xShift, refY + 200, 4210752);

				for (TextWrapper text : page.text) {

					if (text.centered()) {
						xShift = (TEXT_WIDTH - font.width(text.characters())) / 2;
						font.draw(stack, text.characters(), text.x() + refX + xShift, refY + text.y(), text.color());
					} else {
						font.draw(stack, text.characters(), text.x() + refX, text.y() + refY, text.color());
					}

				}

				for (ImageWrapper wrapper : page.images) {

					ImageWrapperObject image = wrapper.image();

					for (ImageTextDescriptor descriptor : image.descriptors) {
						font.draw(stack, descriptor.text, refX + wrapper.x() + descriptor.xOffsetFromImage, refY + wrapper.y() + descriptor.yOffsetFromImage, descriptor.color);
					}

				}

				for (ItemWrapper wrapper : page.items) {

					ItemWrapperObject item = wrapper.item();

					for (ImageTextDescriptor descriptor : item.descriptors) {
						font.draw(stack, descriptor.text, refX + wrapper.x() + descriptor.xOffsetFromImage, refY + wrapper.y() + descriptor.yOffsetFromImage, descriptor.color);
					}

				}

			}
		}
	}

	private Module getCurrentModule() {

		Page page = getCurrentPage();

		if (page instanceof ChapterPage chapter) {
			return chapter.associatedModule;
		} else if (page.associatedChapter != null) {
			return page.associatedChapter.module;
		}

		return null;
	}

	private Page getCurrentPage() {

		if (currPageNumber >= pages.size()) {
			currPageNumber = pages.size() - 1;
		}
		return pages.get(currPageNumber);
	}

	protected void pageForward() {
		if (getCurrentPageNumber() < getPageCount() - 1) {
			movePage(1);
		}
		updatePageArrowVis();
	}

	protected void pageBackward() {
		if (getCurrentPageNumber() > GUIDEBOOK_STARTING_PAGE) {
			movePage(-1);
		}
		updatePageArrowVis();
	}

	protected void goToChapterPage() {
		Module module = getCurrentModule();
		if (module != null) {
			setPageNumber(module.getPage());
		}
	}

	protected void goToModulePage() {
		setPageNumber(0);
	}

	private int getPageCount() {
		return pages.size();
	}

	private static int getCurrentPageNumber() {
		return currPageNumber;
	}

	private void movePage(int number) {
		currPageNumber += number;
		currPageNumber = Mth.clamp(currPageNumber, 0, pages.size() - 1);
	}

	private static void setPageNumber(int number) {
		currPageNumber = number;
	}

	private void updatePageArrowVis() {
		forward.visible = getCurrentPageNumber() < getPageCount() - 1;
		back.visible = currPageNumber > GUIDEBOOK_STARTING_PAGE;
	}

	public static void addGuidebookModule(Module module) {
		if (module instanceof ModuleElectrodynamics) {
			GUIDEBOOK_MODULES.add(0, module);
		} else {
			GUIDEBOOK_MODULES.add(module);
		}

	}

}
