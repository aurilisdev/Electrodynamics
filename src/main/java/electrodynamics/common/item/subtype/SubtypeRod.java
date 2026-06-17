package electrodynamics.common.item.subtype;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import voltaic.api.ISubtype;
import voltaic.common.tags.VoltaicTags;

public enum SubtypeRod implements ISubtype {
    steel(VoltaicTags.Items.ROD_STEEL), stainlesssteel(VoltaicTags.Items.ROD_STAINLESSSTEEL),
    hslasteel(VoltaicTags.Items.ROD_HSLASTEEL), titaniumcarbide(VoltaicTags.Items.ROD_TITANIUMCARBIDE);

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
