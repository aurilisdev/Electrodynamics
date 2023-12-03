package electrodynamics.common.item.subtype;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import electrodynamics.api.ISubtype;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public enum SubtypeRawOre implements ISubtype {
	silver(ElectrodynamicsTags.Items.RAW_ORE_SILVER, () -> ElectrodynamicsItems.getItem(SubtypeImpureDust.silver), () -> ElectrodynamicsItems.getItem(SubtypeDust.silver)),
	lead(ElectrodynamicsTags.Items.RAW_ORE_LEAD, () -> ElectrodynamicsItems.getItem(SubtypeImpureDust.lead), () -> ElectrodynamicsItems.getItem(SubtypeDust.lead)),
	tin(ElectrodynamicsTags.Items.RAW_ORE_TIN, () -> ElectrodynamicsItems.getItem(SubtypeImpureDust.tin), () -> ElectrodynamicsItems.getItem(SubtypeDust.tin)),
	chromium(ElectrodynamicsTags.Items.RAW_ORE_CHROMIUM, () -> ElectrodynamicsItems.getItem(SubtypeOxide.chromite), null),
	titanium(ElectrodynamicsTags.Items.RAW_ORE_TITANIUM, () -> ElectrodynamicsItems.getItem(SubtypeOxide.dititanium), null),
	vanadinite(ElectrodynamicsTags.Items.RAW_ORE_VANADIUM, () -> ElectrodynamicsItems.getItem(SubtypeImpureDust.vanadium), () -> ElectrodynamicsItems.getItem(SubtypeDust.vanadium)),
	lepidolite(ElectrodynamicsTags.Items.RAW_ORE_LEPIDOLITE, () -> ElectrodynamicsItems.getItem(SubtypeImpureDust.lithium), null),
	fluorite(ElectrodynamicsTags.Items.RAW_ORE_FLUORITE, null, null),
	uranium(ElectrodynamicsTags.Items.RAW_ORE_URANIUM, null, null),
	thorium(ElectrodynamicsTags.Items.RAW_ORE_THORIUM, null, null);

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