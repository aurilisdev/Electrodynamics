package electrodynamics.common.item.subtype;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import voltaic.api.ISubtype;
import voltaic.common.tags.VoltaicTags;

public enum SubtypeCircuit implements ISubtype {
	basic(VoltaicTags.Items.CIRCUITS_BASIC),
	advanced(VoltaicTags.Items.CIRCUITS_ADVANCED),
	elite(VoltaicTags.Items.CIRCUITS_ELITE),
	ultimate(VoltaicTags.Items.CIRCUITS_ULTIMATE);

	public final TagKey<Item> tag;

	SubtypeCircuit(TagKey<Item> tag) {
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
