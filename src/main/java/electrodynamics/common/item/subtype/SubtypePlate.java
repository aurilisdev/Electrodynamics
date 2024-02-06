package electrodynamics.common.item.subtype;

import electrodynamics.api.ISubtype;
import electrodynamics.common.tags.ElectrodynamicsTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.Tags;

public enum SubtypePlate implements ISubtype {
    copper(ElectrodynamicsTags.Items.PLATE_COPPER, Tags.Items.INGOTS_COPPER),
    iron(ElectrodynamicsTags.Items.PLATE_IRON, Tags.Items.INGOTS_IRON),
    steel(ElectrodynamicsTags.Items.PLATE_STEEL, ElectrodynamicsTags.Items.INGOT_STEEL),
    lead(ElectrodynamicsTags.Items.PLATE_LEAD, ElectrodynamicsTags.Items.INGOT_LEAD),
    bronze(ElectrodynamicsTags.Items.PLATE_BRONZE, ElectrodynamicsTags.Items.INGOT_BRONZE),
    lithium(ElectrodynamicsTags.Items.PLATE_LITHIUM, ElectrodynamicsTags.Items.INGOT_LITHIUM),
    stainlesssteel(ElectrodynamicsTags.Items.PLATE_STAINLESSSTEEL, ElectrodynamicsTags.Items.INGOT_STAINLESSSTEEL),
    vanadiumsteel(ElectrodynamicsTags.Items.PLATE_VANADIUMSTEEL, ElectrodynamicsTags.Items.INGOT_VANADIUMSTEEL),
    titanium(ElectrodynamicsTags.Items.PLATE_TITANIUM, ElectrodynamicsTags.Items.INGOT_TITANIUM),
    aluminum(ElectrodynamicsTags.Items.PLATE_ALUMINUM, ElectrodynamicsTags.Items.INGOT_ALUMINUM),
    hslasteel(ElectrodynamicsTags.Items.PLATE_HSLASTEEL, ElectrodynamicsTags.Items.INGOT_HSLASTEEL),
    titaniumcarbide(ElectrodynamicsTags.Items.PLATE_TITANIUMCARBIDE, ElectrodynamicsTags.Items.INGOT_TITANIUMCARBIDE);

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
