package electrodynamics.common.item.subtype;

import electrodynamics.api.ISubtype;
import electrodynamics.common.tags.ElectrodynamicsTags;
import net.minecraft.item.Item;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

public enum SubtypeRod implements ISubtype {
	steel(ElectrodynamicsTags.Items.ROD_STEEL),
	stainlesssteel(ElectrodynamicsTags.Items.ROD_STAINLESSSTEEL),
	hslasteel(ElectrodynamicsTags.Items.ROD_HSLASTEEL),
	titaniumcarbide(ElectrodynamicsTags.Items.ROD_TITANIUMCARBIDE);

	public final IOptionalNamedTag<Item> tag;

	SubtypeRod(IOptionalNamedTag<Item> tag) {
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
