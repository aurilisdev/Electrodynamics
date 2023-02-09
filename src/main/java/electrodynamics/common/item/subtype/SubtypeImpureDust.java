package electrodynamics.common.item.subtype;

import java.util.function.Supplier;

import electrodynamics.api.ISubtype;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public enum SubtypeImpureDust implements ISubtype {
	iron(ElectrodynamicsTags.Items.IMPURE_DUST_IRON, () -> ElectrodynamicsItems.getItem(SubtypeDust.iron)),
	gold(ElectrodynamicsTags.Items.IMPURE_DUST_GOLD, () -> ElectrodynamicsItems.getItem(SubtypeDust.gold)),
	copper(ElectrodynamicsTags.Items.IMPURE_DUST_COPPER, () -> ElectrodynamicsItems.getItem(SubtypeDust.copper)),
	tin(ElectrodynamicsTags.Items.IMPURE_DUST_TIN, () -> ElectrodynamicsItems.getItem(SubtypeDust.tin)),
	silver(ElectrodynamicsTags.Items.IMPURE_DUST_SILVER, () -> ElectrodynamicsItems.getItem(SubtypeDust.silver)),
	lead(ElectrodynamicsTags.Items.IMPURE_DUST_LEAD, () -> ElectrodynamicsItems.getItem(SubtypeDust.lead)),
	vanadium(ElectrodynamicsTags.Items.IMPURE_DUST_VANADIUM, () -> ElectrodynamicsItems.getItem(SubtypeDust.vanadium)),
	lithium(ElectrodynamicsTags.Items.IMPURE_DUST_LITHIUM, () -> ElectrodynamicsItems.getItem(SubtypeDust.lithium)),
	molybdenum(ElectrodynamicsTags.Items.IMPURE_DUST_MOLYBDENUM, () -> ElectrodynamicsItems.getItem(SubtypeDust.molybdenum)),
	netherite(ElectrodynamicsTags.Items.IMPURE_DUST_NETHERITE, () -> ElectrodynamicsItems.getItem(SubtypeDust.netherite));

	public final TagKey<Item> tag;
	public final Supplier<Item> grindedDust;
	
	private SubtypeImpureDust(TagKey<Item> tag, Supplier<Item> grindedDust) {
		this.tag = tag;
		this.grindedDust = grindedDust;
	}
	
	@Override
	public String tag() {
		return "impuredust" + name();
	}

	@Override
	public String forgeTag() {
		return "impuredusts/" + name();
	}

	@Override
	public boolean isItem() {
		return true;
	}
}
