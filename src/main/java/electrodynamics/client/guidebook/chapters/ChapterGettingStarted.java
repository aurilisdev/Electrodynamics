package electrodynamics.client.guidebook.chapters;

import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Items;

public class ChapterGettingStarted extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 32, 32, 32, 2.0F, Items.FILLED_MAP);

	public ChapterGettingStarted(Module module) {
		super(module);
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return ElectroTextUtils.guidebook("chapter.gettingstarted");
	}

	@Override
	public void addData() {
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.gettingstarted.l1")).setIndentions(1).setSeparateStart());
		pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.gettingstarted.l2")).setIndentions(1).setSeparateStart());
	}

}
