package electrodynamics.client.guidebook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.References;
import electrodynamics.client.guidebook.utils.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.TextWrapperObject;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.components.Page;
import electrodynamics.client.guidebook.utils.components.Page.ChapterPage;
import electrodynamics.client.guidebook.utils.components.Page.ModulePage;
import electrodynamics.common.inventory.container.item.ContainerGuidebook;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.button.ButtonGuidebook;
import electrodynamics.prefab.screen.component.button.ButtonGuidebook.ButtonType;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class ScreenGuidebook extends GenericScreen<ContainerGuidebook> {

	private static List<Module> GUIDEBOOK_MODULES = new ArrayList<>();
	private static List<Page> GUIDEBOOK_PAGES = new ArrayList<>();
	private static boolean hasInitHappened = false;
	private static int modulePageOffset = 0;

	// index represents page number; 0 indexed
	private static List<List<Button>> MODULE_BUTTONS = new ArrayList<>();
	private static List<List<Button>> CHAPTER_BUTTONS = new ArrayList<>();

	private static int currPageNumber = 0;

	// 0 is defined as the starting page
	private static final int GUIDEBOOK_STARTING_PAGE = 0;

	private static final int MODULES_PER_PAGE = 4;

	private static final int TEXTURE_WIDTH = 176;
	private static final int TEXTURE_HEIGHT = 224;

	private static final ResourceLocation PAGE_TEXTURE = new ResourceLocation(References.ID + ":textures/screen/guidebook/resources/guidebookpage.png");

	private PageButton forward;
	private PageButton back;

	private ButtonGuidebook home;
	private ButtonGuidebook chapters;

	public ScreenGuidebook(ContainerGuidebook screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);
		imageHeight += 58;
		inventoryLabelY += 58;
	}

	@Override
	protected void init() {
		if (!hasInitHappened) {
			sortModules();
			int pageOffset = GUIDEBOOK_STARTING_PAGE + (int) Math.ceil((double) GUIDEBOOK_MODULES.size() / (double) MODULES_PER_PAGE);
			modulePageOffset = pageOffset;
			for (Module module : GUIDEBOOK_MODULES) {
				pageOffset += module.setPageNumbers(pageOffset);
			}
			for (int i = 0; i < modulePageOffset; i++) {
				GUIDEBOOK_PAGES.add(new ModulePage(i));
			}
			for (Module module : GUIDEBOOK_MODULES) {
				GUIDEBOOK_PAGES.addAll(module.getAllPages());
			}
			hasInitHappened = true;
		}
		initChapterButtons();
		initModuleButtons();
		super.init();
		initPageButtons();
		for (List<Button> buttons : MODULE_BUTTONS) {
			for (Button button : buttons) {
				addRenderableWidget(button);
			}
		}
		for (List<Button> buttons : CHAPTER_BUTTONS) {
			for (Button button : buttons) {
				addRenderableWidget(button);
			}
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

	private void initModuleButtons() {
		MODULE_BUTTONS.clear();
		int guiWidth = (width - imageWidth) / 2;
		int guiHeight = (height - imageHeight) / 2;
		List<Module> subModules;
		for (int i = 0; i < modulePageOffset; i++) {
			subModules = getModuleSubset(i * MODULES_PER_PAGE);
			List<Button> pageButtons = new ArrayList<>();
			for (int j = 0; j < subModules.size(); j++) {
				Module module = subModules.get(j);
				pageButtons.add(new Button(guiWidth + 45, guiHeight + 43 + j * MODULE_SEPERATION, 120, 20, new TranslatableComponent(module.getTitle()), button -> setPageNumber(module.getStartingPageNumber())));
			}
			MODULE_BUTTONS.add(pageButtons);
		}
	}

	private void initChapterButtons() {
		CHAPTER_BUTTONS.clear();
		int guiWidth = (width - imageWidth) / 2;
		int guiHeight = (height - imageHeight) / 2;
		List<Chapter> currChapters;
		for (Module module : GUIDEBOOK_MODULES) {
			currChapters = module.getChapters();
			Chapter chapter;
			int index = 0;
			List<Button> pageButtons = new ArrayList<>();
			for (int i = 0; i < module.getChapterPages(); i++) {
				for (int j = 0; j < Module.CHAPTERS_PER_PAGE; j++) {
					if (index < currChapters.size()) {
						chapter = currChapters.get(index);
						int chapterNumber = chapter.getStartingPageNumber();
						pageButtons.add(new Button(guiWidth + 45, guiHeight + 56 + j * CHAPTER_SEPERATION, 120, 20, new TranslatableComponent(chapter.getTitleKey()), button -> setPageNumber(chapterNumber)));
						index++;
					} else {
						break;
					}
				}
			}
			CHAPTER_BUTTONS.add(pageButtons);
		}
	}

	private static final int MODULE_SEPERATION = 40;
	private static final int CHAPTER_SEPERATION = 35;

	@Override
	protected void renderBg(PoseStack stack, float partialTick, int x, int y) {
		Page page = getCurrentPage();
		updatePageArrowVis();
		updateModuleButtonVis();
		// this one may need to be reworked if it lags
		updateChapterButtonVis();
		RenderingUtils.bindTexture(PAGE_TEXTURE);
		int guiWidth = (width - imageWidth) / 2;
		int guiHeight = (height - imageHeight) / 2;
		blit(stack, guiWidth, guiHeight, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT);

		if (page instanceof ModulePage) {
			List<Module> pageModules = getModuleSubset(getCurrentPageNumber() * MODULES_PER_PAGE);

			for (int i = 0; i < pageModules.size(); i++) {
				Module module = pageModules.get(i);
				ImageWrapperObject image = module.getLogo();
				RenderingUtils.bindTexture(new ResourceLocation(image.location));
				blit(stack, guiWidth + image.xOffset, guiHeight + image.yOffet + i * MODULE_SEPERATION, image.uStart, image.vStart, image.width, image.height, image.imgheight, image.imgwidth);
			}
		} else if (page instanceof ChapterPage) {
			Module currentModule = getCurrentModule();
			List<Chapter> chaptersForPage = currentModule.getChapterSubList(getCurrentPageNumber());
			for (int i = 0; i < chaptersForPage.size(); i++) {
				Chapter chapter = chaptersForPage.get(i);
				Object object = chapter.getLogo();
				if (object instanceof ImageWrapperObject image) {
					RenderingUtils.bindTexture(new ResourceLocation(image.location));
					blit(stack, guiWidth + image.xOffset, guiHeight + image.yOffet + i * CHAPTER_SEPERATION, image.uStart, image.vStart, image.width, image.height, image.imgwidth, image.imgheight);
				} else if (object instanceof ItemWrapperObject item) {
					RenderingUtils.renderItemScaled(item.item, guiWidth + item.xOffset, guiHeight + item.yOffset + i * CHAPTER_SEPERATION, item.scale);
				}

			}
		} else {
			for (ImageWrapperObject image : page.getImages()) {
				RenderingUtils.bindTexture(new ResourceLocation(image.location));
				blit(stack, guiWidth + image.xOffset, guiHeight + image.yOffet, getBlitOffset(), image.uStart, image.vStart, image.width, image.height, image.imgheight, image.imgwidth);
			}
			for (ItemWrapperObject item : page.getItems()) {
				RenderingUtils.renderItemScaled(item.item, guiWidth + item.xOffset, guiHeight + item.yOffset, item.scale);
			}
		}

	}

	@Override
	protected void renderLabels(PoseStack stack, int x, int y) {
		int refX = titleLabelX - 8;
		int refY = titleLabelY - 6;

		Page page = getCurrentPage();

		if (page instanceof ModulePage) {
			Component modTitle = new TranslatableComponent("guidebook.availablemodules").withStyle(ChatFormatting.BOLD);
			int xShift = (TEXTURE_WIDTH - font.width(modTitle)) / 2;
			font.draw(stack, modTitle, refX + xShift, refY + 16, 4210752);
		} else if (page instanceof ChapterPage) {
			Module currMod = getCurrentModule();
			if (currMod != null) {
				Component moduleTitle = new TranslatableComponent(currMod.getTitle()).withStyle(ChatFormatting.BOLD);
				int xShift = (TEXTURE_WIDTH - font.width(moduleTitle)) / 2;
				font.draw(stack, moduleTitle, refX + xShift, refY + 16, 4210752);

				Component chapTitle = new TranslatableComponent("guidebook.chapters").withStyle(ChatFormatting.UNDERLINE);
				xShift = (TEXTURE_WIDTH - font.width(chapTitle)) / 2;
				font.draw(stack, chapTitle, refX + xShift, refY + 31, 4210752);
			}
		} else {
			Module currMod = getCurrentModule();
			if (currMod != null) {
				Component moduleTitle = new TranslatableComponent(currMod.getTitle()).withStyle(ChatFormatting.BOLD);
				int xShift = (TEXTURE_WIDTH - font.width(moduleTitle)) / 2;
				font.draw(stack, moduleTitle, refX + xShift, refY + 16, 4210752);

				Component chapTitle = new TranslatableComponent(page.getChapterKey()).withStyle(ChatFormatting.UNDERLINE);
				xShift = (TEXTURE_WIDTH - font.width(chapTitle)) / 2;
				font.draw(stack, chapTitle, refX + xShift, refY + 26, 4210752);

				Component pageNumber = new TextComponent(getCurrentPageNumber() + "");
				xShift = (TEXTURE_WIDTH - font.width(pageNumber)) / 2;
				font.draw(stack, pageNumber, refX + xShift, refY + 200, 4210752);

				for (TextWrapperObject text : page.getText()) {
					Component component;
					if (text.componentInfo == null) {
						if (text.formats == null) {
							component = new TranslatableComponent(text.textKey);
						} else {
							component = new TranslatableComponent(text.textKey).withStyle(text.formats);
						}
					} else if (text.formats == null) {
						component = new TranslatableComponent(text.textKey, text.componentInfo);
					} else {
						component = new TranslatableComponent(text.textKey, text.componentInfo).withStyle(text.formats);
					}
					font.draw(stack, component, refX + text.xOffset, refY + text.yOffset, text.color);
				}
			}
		}
	}

	private static int getCurrentModuleNumber() {
		for (int i = 0; i < GUIDEBOOK_MODULES.size(); i++) {
			Module module = GUIDEBOOK_MODULES.get(i);
			if (module.isPageInModule(currPageNumber)) {
				return i;
			}
		}
		return -1;
	}

	private static Module getCurrentModule() {
		int mod = getCurrentModuleNumber();
		if (mod > -1) {
			return GUIDEBOOK_MODULES.get(mod);
		}
		return null;
	}

	private static Page getCurrentPage() {
		return GUIDEBOOK_PAGES.get(currPageNumber);
	}

	public static void addGuidebookModule(Module module) {
		if (!module.isFirst() || GUIDEBOOK_MODULES.isEmpty()) {
			GUIDEBOOK_MODULES.add(module);
		} else {
			List<Module> temp = new ArrayList<>(GUIDEBOOK_MODULES);
			GUIDEBOOK_MODULES = new ArrayList<>();
			GUIDEBOOK_MODULES.add(module);
			GUIDEBOOK_MODULES.addAll(temp);
		}
	}

	// You are really fucking around if you get this to crash...
	private static void sortModules() {
		List<Module> temp = new ArrayList<>(GUIDEBOOK_MODULES);
		GUIDEBOOK_MODULES = new ArrayList<>();
		GUIDEBOOK_MODULES.add(temp.get(0));
		temp.remove(0);
		List<String> cats = new ArrayList<>();
		for (Module mod : temp) {
			cats.add(mod.getTitleCat());
		}
		Collections.sort(cats);
		for (String cat : cats) {
			for (int i = 0; i < temp.size(); i++) {
				Module mod = temp.get(i);
				if (mod.isCat(cat)) {
					GUIDEBOOK_MODULES.add(mod);
					temp.remove(i);
					break;
				}
			}
		}
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
			setPageNumber(module.getStartingPageNumber());
		}
	}

	protected void goToModulePage() {
		setPageNumber(0);
	}

	private static int getPageCount() {
		return GUIDEBOOK_PAGES.size();
	}

	private static int getCurrentPageNumber() {
		return currPageNumber;
	}

	private static void movePage(int number) {
		currPageNumber += number;
		currPageNumber = Mth.clamp(currPageNumber, 0, GUIDEBOOK_PAGES.size() - 1);
	}

	private static void setPageNumber(int number) {
		currPageNumber = number;
	}

	private static List<Module> getModuleSubset(int offset) {
		int end = offset + MODULES_PER_PAGE;
		int sizeCheck = GUIDEBOOK_MODULES.size() - 1;
		return GUIDEBOOK_MODULES.subList(offset, end > sizeCheck ? sizeCheck + 1 : end);
	}

	private void updatePageArrowVis() {
		forward.visible = getCurrentPageNumber() < getPageCount() - 1;
		back.visible = currPageNumber > GUIDEBOOK_STARTING_PAGE;
	}

	private static void updateModuleButtonVis() {
		for (List<Button> buttons : MODULE_BUTTONS) {
			for (Button button : buttons) {
				button.visible = false;
			}
		}
		if (getCurrentPage() instanceof ModulePage) {
			for (Button button : MODULE_BUTTONS.get(currPageNumber)) {
				button.visible = true;
			}
		}
	}

	private static void updateChapterButtonVis() {
		for (List<Button> buttons : CHAPTER_BUTTONS) {
			for (Button button : buttons) {
				button.visible = false;
			}
		}
		if (getCurrentPage() instanceof ChapterPage) {
			int currModNumber = getCurrentModuleNumber();
			if (currModNumber > -1) {
				Module currentModule = GUIDEBOOK_MODULES.get(currModNumber);
				List<Chapter> currChaps = currentModule.getChapters();
				List<Button> chapButtons = CHAPTER_BUTTONS.get(currModNumber);
				for (int i = 0; i < currChaps.size(); i++) {
					if (currChaps.get(i).getChapterPageNumber() == getCurrentPageNumber()) {
						chapButtons.get(i).visible = true;
					}
				}
			}
		}
	}
}
