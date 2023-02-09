package electrodynamics.common.item.subtype;

import electrodynamics.api.ISubtype;
import electrodynamics.common.tags.ElectrodynamicsTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public enum SubtypeOxide implements ISubtype {
	vanadium(ElectrodynamicsTags.Items.OXIDE_VANADIUM),
	disulfur(ElectrodynamicsTags.Items.OXIDE_DISULFUR),
	trisulfur(ElectrodynamicsTags.Items.OXIDE_TRISULFUR),
	sulfurdichloride(ElectrodynamicsTags.Items.OXIDE_SULFURDICHLORIDE),
	thionylchloride(ElectrodynamicsTags.Items.OXIDE_THIONYLCHLORIDE),
	calciumcarbonate(ElectrodynamicsTags.Items.OXIDE_CALCIUMCARBONATE),
	chromite(ElectrodynamicsTags.Items.OXIDE_CHROMIUM),
	dititanium(ElectrodynamicsTags.Items.OXIDE_DITITANIUM),
	sodiumcarbonate(ElectrodynamicsTags.Items.OXIDE_SODIUMCARBONATE),
	chromiumdisilicide(ElectrodynamicsTags.Items.OXIDE_CHROMIUMDISILICIDE);

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
