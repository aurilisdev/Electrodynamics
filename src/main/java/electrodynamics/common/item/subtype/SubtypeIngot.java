package electrodynamics.common.item.subtype;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import voltaic.api.ISubtype;
import voltaic.common.tags.VoltaicTags;

public enum SubtypeIngot implements ISubtype {

    tin(VoltaicTags.Items.INGOT_TIN, () -> ElectrodynamicsItems.ITEMS_DUST.getValue(SubtypeDust.tin)),
    silver(VoltaicTags.Items.INGOT_SILVER, () -> ElectrodynamicsItems.ITEMS_DUST.getValue(SubtypeDust.silver)),
    steel(VoltaicTags.Items.INGOT_STEEL, () -> ElectrodynamicsItems.ITEMS_DUST.getValue(SubtypeDust.steel)),
    lead(VoltaicTags.Items.INGOT_LEAD, () -> ElectrodynamicsItems.ITEMS_DUST.getValue(SubtypeDust.lead)),
    superconductive(VoltaicTags.Items.INGOT_SUPERCONDUCTIVE,
	    () -> ElectrodynamicsItems.ITEMS_DUST.getValue(SubtypeDust.superconductive)),
    bronze(VoltaicTags.Items.INGOT_BRONZE, () -> ElectrodynamicsItems.ITEMS_DUST.getValue(SubtypeDust.bronze)),
    vanadium(VoltaicTags.Items.INGOT_VANADIUM, () -> ElectrodynamicsItems.ITEMS_DUST.getValue(SubtypeDust.vanadium)),
    lithium(VoltaicTags.Items.INGOT_LITHIUM, () -> ElectrodynamicsItems.ITEMS_DUST.getValue(SubtypeDust.lithium)),
    aluminum(VoltaicTags.Items.INGOT_ALUMINUM),
    chromium(VoltaicTags.Items.INGOT_CHROMIUM, () -> ElectrodynamicsItems.ITEMS_OXIDE.getValue(SubtypeOxide.chromite)),
    stainlesssteel(VoltaicTags.Items.INGOT_STAINLESSSTEEL), vanadiumsteel(VoltaicTags.Items.INGOT_VANADIUMSTEEL),
    hslasteel(VoltaicTags.Items.INGOT_HSLASTEEL), titanium(VoltaicTags.Items.INGOT_TITANIUM),
    molybdenum(VoltaicTags.Items.INGOT_MOLYBDENUM,
	    () -> ElectrodynamicsItems.ITEMS_DUST.getValue(SubtypeDust.molybdenum)),
    titaniumcarbide(VoltaicTags.Items.INGOT_TITANIUMCARBIDE);

    public final TagKey<Item> tag;
    @Nullable
    public final Supplier<Item> grindedDust;

    SubtypeIngot(TagKey<Item> tag) {
	this(tag, null);
    }

    SubtypeIngot(TagKey<Item> tag, Supplier<Item> grindedDust) {
	this.tag = tag;
	this.grindedDust = grindedDust;
    }

    @Override
    public String tag() {
	return "ingot" + name();
    }

    @Override
    public String forgeTag() {
	return "ingots/" + name();
    }

    @Override
    public boolean isItem() {
	return true;
    }
}
