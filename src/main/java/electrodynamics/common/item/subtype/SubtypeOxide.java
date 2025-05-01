package electrodynamics.common.item.subtype;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import voltaic.api.ISubtype;
import voltaic.common.tags.VoltaicTags;

public enum SubtypeOxide implements ISubtype {
	vanadium(VoltaicTags.Items.OXIDE_VANADIUM),
	disulfur(VoltaicTags.Items.OXIDE_DISULFUR),
	trisulfur(VoltaicTags.Items.OXIDE_TRISULFUR),
	sulfurdichloride(VoltaicTags.Items.OXIDE_SULFURDICHLORIDE),
	thionylchloride(VoltaicTags.Items.OXIDE_THIONYLCHLORIDE),
	calciumcarbonate(VoltaicTags.Items.OXIDE_CALCIUMCARBONATE),
	chromite(VoltaicTags.Items.OXIDE_CHROMIUM),
	dititanium(VoltaicTags.Items.OXIDE_DITITANIUM),
	sodiumcarbonate(VoltaicTags.Items.OXIDE_SODIUMCARBONATE),
	chromiumdisilicide(VoltaicTags.Items.OXIDE_CHROMIUMDISILICIDE);

	public final TagKey<Item> tag;

	SubtypeOxide(TagKey<Item> tag) {
		this.tag = tag;
	}

	@Override
	public String tag() {
		return "oxide" + name();
	}

	@Override
	public String forgeTag() {
		return "oxide/" + name();
	}

	@Override
	public boolean isItem() {
		return true;
	}
}
