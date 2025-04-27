package electrodynamics.client.guidebook.chapters;

import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Items;
import voltaic.client.guidebook.utils.components.Chapter;
import voltaic.client.guidebook.utils.components.Module;
import voltaic.client.guidebook.utils.pagedata.graphics.ItemWrapperObject;
import voltaic.client.guidebook.utils.pagedata.text.TextWrapperObject;

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
