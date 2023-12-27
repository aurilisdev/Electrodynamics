package electrodynamics.common.item.subtype;

import electrodynamics.api.ISubtype;
import electrodynamics.common.tags.ElectrodynamicsTags;
import net.minecraft.item.Item;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

public enum SubtypeCircuit implements ISubtype {
	basic(ElectrodynamicsTags.Items.CIRCUITS_BASIC),
	advanced(ElectrodynamicsTags.Items.CIRCUITS_ADVANCED),
	elite(ElectrodynamicsTags.Items.CIRCUITS_ELITE),
	ultimate(ElectrodynamicsTags.Items.CIRCUITS_ULTIMATE);

	public final IOptionalNamedTag<Item> tag;

	SubtypeCircuit(IOptionalNamedTag<Item> tag) {
		this.tag = tag;
	}

	@Override
	public String tag() {
		return "circuit" + name();
	}

	@Override
	public String forgeTag() {
		return "circuits/" + name();
	}

	@Override
	public boolean isItem() {
		return true;
	}
}
