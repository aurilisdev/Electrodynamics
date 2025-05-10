package electrodynamics.client.guidebook.chapters;

import electrodynamics.Electrodynamics;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TextFormatting;
import voltaic.client.guidebook.utils.components.Chapter;
import voltaic.client.guidebook.utils.components.Module;
import voltaic.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import voltaic.client.guidebook.utils.pagedata.text.TextWrapperObject;

public class ChapterTips extends Chapter {

	private static final ImageWrapperObject LOGO = new ImageWrapperObject(0, 0, 0, 0, 32, 32, 32, 32, Electrodynamics.rl("textures/item/nugget/nuggetsilver.png"));

	public ChapterTips(Module module) {
		super(module);
	}

	@Override
	public ImageWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public IFormattableTextComponent getTitle() {
		return ElectroTextUtils.guidebook("chapter.tips");
	}

	@Override
	public void addData() {

		// Energy storage tip
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.tips.tip", 1).withStyle(TextFormatting.UNDERLINE)).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.tips.tip1")).setIndentions(1).setSeparateStart());

		// Transformer Tip
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.tips.tip", 2).withStyle(TextFormatting.UNDERLINE)).setNewPage());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.tips.tip2")).setIndentions(1).setSeparateStart());

		// Ctrl hover over upgrade slots in GUI
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.tips.tip", 4).withStyle(TextFormatting.UNDERLINE)).setNewPage());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.tips.tip4")).setIndentions(1).setSeparateStart());

	}

}
