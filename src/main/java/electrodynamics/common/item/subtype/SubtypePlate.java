package electrodynamics.common.item.subtype;

import voltaic.api.ISubtype;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.Tags;
import voltaic.common.tags.VoltaicTags;

public enum SubtypePlate implements ISubtype {
    copper(VoltaicTags.Items.PLATE_COPPER, Tags.Items.INGOTS_COPPER),
    iron(VoltaicTags.Items.PLATE_IRON, Tags.Items.INGOTS_IRON),
    steel(VoltaicTags.Items.PLATE_STEEL, VoltaicTags.Items.INGOT_STEEL),
    lead(VoltaicTags.Items.PLATE_LEAD, VoltaicTags.Items.INGOT_LEAD),
    bronze(VoltaicTags.Items.PLATE_BRONZE, VoltaicTags.Items.INGOT_BRONZE),
    lithium(VoltaicTags.Items.PLATE_LITHIUM, VoltaicTags.Items.INGOT_LITHIUM),
    stainlesssteel(VoltaicTags.Items.PLATE_STAINLESSSTEEL, VoltaicTags.Items.INGOT_STAINLESSSTEEL),
    vanadiumsteel(VoltaicTags.Items.PLATE_VANADIUMSTEEL, VoltaicTags.Items.INGOT_VANADIUMSTEEL),
    titanium(VoltaicTags.Items.PLATE_TITANIUM, VoltaicTags.Items.INGOT_TITANIUM),
    aluminum(VoltaicTags.Items.PLATE_ALUMINUM, VoltaicTags.Items.INGOT_ALUMINUM),
    hslasteel(VoltaicTags.Items.PLATE_HSLASTEEL, VoltaicTags.Items.INGOT_HSLASTEEL),
    titaniumcarbide(VoltaicTags.Items.PLATE_TITANIUMCARBIDE, VoltaicTags.Items.INGOT_TITANIUMCARBIDE);

    public final TagKey<Item> tag;
    public final TagKey<Item> sourceIngot;

    SubtypePlate(TagKey<Item> tag, TagKey<Item> sourceIngot) {
        this.tag = tag;
        this.sourceIngot = sourceIngot;
    }

    @Override
    public String tag() {
        return "plate" + name();
    }

    @Override
    public String forgeTag() {
        return "plates/" + name();
    }

    @Override
    public boolean isItem() {
        return true;
    }
}
