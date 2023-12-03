package electrodynamics.common.item.subtype;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import electrodynamics.api.ISubtype;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public enum SubtypeIngot implements ISubtype {

	tin(ElectrodynamicsTags.Items.INGOT_TIN, () -> ElectrodynamicsItems.getItem(SubtypeDust.tin)),
	silver(ElectrodynamicsTags.Items.INGOT_SILVER, () -> ElectrodynamicsItems.getItem(SubtypeDust.silver)),
	steel(ElectrodynamicsTags.Items.INGOT_STEEL, () -> ElectrodynamicsItems.getItem(SubtypeDust.steel)),
	lead(ElectrodynamicsTags.Items.INGOT_LEAD, () -> ElectrodynamicsItems.getItem(SubtypeDust.lead)),
	superconductive(ElectrodynamicsTags.Items.INGOT_SUPERCONDUCTIVE, () -> ElectrodynamicsItems.getItem(SubtypeDust.superconductive)),
	bronze(ElectrodynamicsTags.Items.INGOT_BRONZE, () -> ElectrodynamicsItems.getItem(SubtypeDust.bronze)),
	vanadium(ElectrodynamicsTags.Items.INGOT_VANADIUM, () -> ElectrodynamicsItems.getItem(SubtypeDust.vanadium)),
	lithium(ElectrodynamicsTags.Items.INGOT_LITHIUM, () -> ElectrodynamicsItems.getItem(SubtypeDust.lithium)),
	aluminum(ElectrodynamicsTags.Items.INGOT_ALUMINUM),
	chromium(ElectrodynamicsTags.Items.INGOT_CHROMIUM, () -> ElectrodynamicsItems.getItem(SubtypeOxide.chromite)),
	stainlesssteel(ElectrodynamicsTags.Items.INGOT_STAINLESSSTEEL),
	vanadiumsteel(ElectrodynamicsTags.Items.INGOT_VANADIUMSTEEL),
	hslasteel(ElectrodynamicsTags.Items.INGOT_HSLASTEEL),
	titanium(ElectrodynamicsTags.Items.INGOT_TITANIUM),
	molybdenum(ElectrodynamicsTags.Items.INGOT_MOLYBDENUM, () -> ElectrodynamicsItems.getItem(SubtypeDust.molybdenum)),
	titaniumcarbide(ElectrodynamicsTags.Items.INGOT_TITANIUMCARBIDE);

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