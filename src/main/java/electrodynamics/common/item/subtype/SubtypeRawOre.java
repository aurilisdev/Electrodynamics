package electrodynamics.common.item.subtype;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import voltaic.api.ISubtype;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import voltaic.common.tags.VoltaicTags;

public enum SubtypeRawOre implements ISubtype {
	silver(VoltaicTags.Items.RAW_ORE_SILVER, () -> ElectrodynamicsItems.ITEMS_IMPUREDUST.getValue(SubtypeImpureDust.silver), () -> ElectrodynamicsItems.ITEMS_DUST.getValue(SubtypeDust.silver)),
	lead(VoltaicTags.Items.RAW_ORE_LEAD, () -> ElectrodynamicsItems.ITEMS_IMPUREDUST.getValue(SubtypeImpureDust.lead), () -> ElectrodynamicsItems.ITEMS_DUST.getValue(SubtypeDust.lead)),
	tin(VoltaicTags.Items.RAW_ORE_TIN, () -> ElectrodynamicsItems.ITEMS_IMPUREDUST.getValue(SubtypeImpureDust.tin), () -> ElectrodynamicsItems.ITEMS_DUST.getValue(SubtypeDust.tin)),
	chromium(VoltaicTags.Items.RAW_ORE_CHROMIUM, () -> ElectrodynamicsItems.ITEMS_OXIDE.getValue(SubtypeOxide.chromite), null),
	titanium(VoltaicTags.Items.RAW_ORE_TITANIUM, () -> ElectrodynamicsItems.ITEMS_OXIDE.getValue(SubtypeOxide.dititanium), null),
	vanadinite(VoltaicTags.Items.RAW_ORE_VANADIUM, () -> ElectrodynamicsItems.ITEMS_IMPUREDUST.getValue(SubtypeImpureDust.vanadium), () -> ElectrodynamicsItems.ITEMS_DUST.getValue(SubtypeDust.vanadium)),
	lepidolite(VoltaicTags.Items.RAW_ORE_LEPIDOLITE, () -> ElectrodynamicsItems.ITEMS_IMPUREDUST.getValue(SubtypeImpureDust.lithium), null),
	fluorite(VoltaicTags.Items.RAW_ORE_FLUORITE, null, null),
	uranium(VoltaicTags.Items.RAW_ORE_URANIUM, null, null),
	thorium(VoltaicTags.Items.RAW_ORE_THORIUM, null, null);

	public final TagKey<Item> tag;
	@Nullable
	public final Supplier<Item> crushedItem;
	@Nullable
	public final Supplier<Item> grindedItem;

	SubtypeRawOre(TagKey<Item> tag, Supplier<Item> crushedItem, Supplier<Item> grindedItem) {
		this.tag = tag;
		this.crushedItem = crushedItem;
		this.grindedItem = grindedItem;
	}

	@Override
	public String tag() {
		return "rawore" + name();
	}

	@Override
	public String forgeTag() {
		return "rawore/" + name();
	}

	@Override
	public boolean isItem() {
		return true;
	}
}
