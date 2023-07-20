package electrodynamics.client.guidebook.chapters;

import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Items;

public class ChapterMetricPrefixes extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 32, 32, 32, 2.0F, Items.NAME_TAG);

	public ChapterMetricPrefixes(Module module) {
		super(module);
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return ElectroTextUtils.guidebook("chapter.metricprefixes");
	}

	@Override
	public void addData() {
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.metricprefixes.l1")).setIndentions(1).setSeparateStart());
		blankLine();
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.metricprefixes.pico")).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.metricprefixes.nano")).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.metricprefixes.micro")).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.metricprefixes.mili")).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.metricprefixes.kilo")).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.metricprefixes.mega")).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.metricprefixes.giga")).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.metricprefixes.tera")).setSeparateStart());
		blankLine();
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.metricprefixes.l2")).setSeparateStart());

	}

}
