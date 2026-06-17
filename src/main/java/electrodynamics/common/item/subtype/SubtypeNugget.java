package electrodynamics.common.item.subtype;

import java.util.function.Supplier;

import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import voltaic.api.ISubtype;
import voltaic.common.tags.VoltaicTags;

public enum SubtypeNugget implements ISubtype {
    tin(VoltaicTags.Items.NUGGET_TIN, VoltaicTags.Items.INGOT_TIN,
	    () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.tin)),
    copper(VoltaicTags.Items.NUGGET_COPPER, Tags.Items.INGOTS_COPPER, () -> Items.COPPER_INGOT),
    silver(VoltaicTags.Items.NUGGET_SILVER, VoltaicTags.Items.INGOT_SILVER,
	    () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.silver)),
    superconductive(VoltaicTags.Items.NUGGET_SUPERCONDUCTIVE, VoltaicTags.Items.INGOT_SUPERCONDUCTIVE,
	    () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.superconductive)),
    steel(VoltaicTags.Items.NUGGET_STEEL, VoltaicTags.Items.INGOT_STEEL,
	    () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.steel)),
    stainlesssteel(VoltaicTags.Items.NUGGET_STAINLESSSTEEL, VoltaicTags.Items.INGOT_STAINLESSSTEEL,
	    () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.stainlesssteel)),
    hslasteel(VoltaicTags.Items.NUGGET_HSLASTEEL, VoltaicTags.Items.INGOT_HSLASTEEL,
	    () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.hslasteel)),
    titaniumcarbide(VoltaicTags.Items.NUGGET_TITANIUMCARBIDE, VoltaicTags.Items.INGOT_TITANIUMCARBIDE,
	    () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.titaniumcarbide));

    public final TagKey<Item> tag;
    public final TagKey<Item> sourceIngot;
    public final Supplier<Item> productIngot;

    SubtypeNugget(TagKey<Item> tag, TagKey<Item> sourceIngot, Supplier<Item> productIngot) {
	this.tag = tag;
	this.sourceIngot = sourceIngot;
	this.productIngot = productIngot;
    }

    @Override
    public String tag() {
	return "nugget" + name();
    }

    @Override
    public String forgeTag() {
	return "nuggets/" + name();
    }

    @Override
    public boolean isItem() {
	return true;
    }

}
