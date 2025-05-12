package electrodynamics.common.item.subtype;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag.INamedTag;
import voltaic.api.ISubtype;
import voltaic.common.tags.VoltaicTags;

public enum SubtypeRod implements ISubtype {
	steel(VoltaicTags.Items.ROD_STEEL),
	stainlesssteel(VoltaicTags.Items.ROD_STAINLESSSTEEL),
	hslasteel(VoltaicTags.Items.ROD_HSLASTEEL),
	titaniumcarbide(VoltaicTags.Items.ROD_TITANIUMCARBIDE);

	public final INamedTag<Item> tag;

	SubtypeRod(INamedTag<Item> tag) {
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
