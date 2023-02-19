package electrodynamics.client.guidebook.chapters;

import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.ItemWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.TextWrapperObject;
import electrodynamics.prefab.utilities.TextUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Items;

public class ChapterGettingStarted extends Chapter {

	private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 2.0F, 32, 32, Items.FILLED_MAP);

	public ChapterGettingStarted(Module module) {
		super(module);
	}

	@Override
	public ItemWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return TextUtils.guidebook("chapter.gettingstarted");
	}

	@Override
	public void addData() {
		pageData.add(new TextWrapperObject(TextUtils.guidebook("chapter.gettingstarted.l1")).setIndentions(1).setSeparateStart());
	}

}
