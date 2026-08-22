package electrodynamics.common.item.subtype;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import voltaic.api.ISubtype;
import voltaic.common.tags.VoltaicTags;

public enum SubtypeDust implements ISubtype {

    iron(VoltaicTags.Items.DUST_IRON, () -> Items.IRON_INGOT, 200),
    gold(VoltaicTags.Items.DUST_GOLD, () -> Items.GOLD_INGOT, 200),
    copper(VoltaicTags.Items.DUST_COPPER, () -> Items.COPPER_INGOT, 200),
    tin(VoltaicTags.Items.DUST_TIN, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.tin), 200),
    silver(VoltaicTags.Items.DUST_SILVER, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.silver), 200),
    steel(VoltaicTags.Items.DUST_STEEL, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.steel), 200),
    lead(VoltaicTags.Items.DUST_LEAD, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.lead), 200),
    bronze(VoltaicTags.Items.DUST_BRONZE, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.bronze), 200),
    superconductive(VoltaicTags.Items.DUST_SUPERCONDUCTIVE,
	    () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.superconductive), 200),
    endereye(VoltaicTags.Items.DUST_ENDEREYE),
    vanadium(VoltaicTags.Items.DUST_VANADIUM, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.vanadium),
	    200),
    sulfur(VoltaicTags.Items.DUST_SULFUR), niter(VoltaicTags.Items.DUST_SALTPETER),
    obsidian(VoltaicTags.Items.DUST_OBSIDIAN),
    lithium(VoltaicTags.Items.DUST_LITHIUM, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.lithium), 200),
    salt(VoltaicTags.Items.DUST_SALT),
    silica(VoltaicTags.Items.DUST_SILICA, () -> ElectrodynamicsItems.ITEMS_CUSTOMGLASS.getValue(SubtypeGlass.clear),
	    200),
    molybdenum(VoltaicTags.Items.DUST_MOLYBDENUM,
	    () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.molybdenum), 200),
    netherite(VoltaicTags.Items.DUST_NETHERITE_SCRAP, () -> Items.NETHERITE_SCRAP, 200);

    public final TagKey<Item> tag;
    @Nullable
    public final Supplier<Item> smeltedItem;
    public final int smeltTime;

    SubtypeDust(TagKey<Item> tag) {
	this(tag, null, 0);
    }

    SubtypeDust(TagKey<Item> tag, Supplier<Item> smeltedItem, int smeltTime) {
	this.tag = tag;
	this.smeltedItem = smeltedItem;
	this.smeltTime = smeltTime;
    }

    @Override
    public String tag() {
	return "dust" + name();
    }

    @Override
    public String forgeTag() {
	return "dusts/" + name();
    }

    @Override
    public boolean isItem() {
	return true;
    }
}
