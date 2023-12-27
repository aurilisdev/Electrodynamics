package electrodynamics.client.guidebook.chapters;

import electrodynamics.api.References;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TextFormatting;

public class ChapterTips extends Chapter {

	private static final ImageWrapperObject LOGO = new ImageWrapperObject(0, 0, 0, 0, 32, 32, 32, 32, new ResourceLocation(References.ID, "textures/item/crystal/crystalsilver.png"));

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
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.tips.tip", 3).withStyle(TextFormatting.UNDERLINE)).setNewPage());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.tips.tip3")).setIndentions(1).setSeparateStart());

	}

}