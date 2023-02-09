package electrodynamics.common.item.subtype;

import electrodynamics.api.ISubtype;
import electrodynamics.common.tags.ElectrodynamicsTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public enum SubtypeRod implements ISubtype {
	steel(ElectrodynamicsTags.Items.ROD_STEEL),
	stainlesssteel(ElectrodynamicsTags.Items.ROD_STAINLESSSTEEL),
	hslasteel(ElectrodynamicsTags.Items.ROD_HSLASTEEL),
	titaniumcarbide(ElectrodynamicsTags.Items.ROD_TITANIUMCARBIDE);

	public final TagKey<Item> tag;

	SubtypeRod(TagKey<Item> tag) {
		this.tag = tag;
	}

	@Override
	public String tag() {
		return "rod" + name();
	}

	@Override
	public String forgeTag() {
		return "rods/" + name();
	}

	@Override
	public boolean isItem() {
		return true;
	}

}
