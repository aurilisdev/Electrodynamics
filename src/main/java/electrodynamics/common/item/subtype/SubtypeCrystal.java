package electrodynamics.common.item.subtype;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import electrodynamics.api.ISubtype;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.world.item.Item;

public enum SubtypeCrystal implements ISubtype {
	iron(() -> ElectrodynamicsItems.getItem(SubtypeImpureDust.iron)),
	gold(() -> ElectrodynamicsItems.getItem(SubtypeImpureDust.gold)),
	copper(() -> ElectrodynamicsItems.getItem(SubtypeImpureDust.copper)),
	tin(() -> ElectrodynamicsItems.getItem(SubtypeImpureDust.tin)),
	silver(() -> ElectrodynamicsItems.getItem(SubtypeImpureDust.silver)),
	lead(() -> ElectrodynamicsItems.getItem(SubtypeImpureDust.lead)),
	vanadium(() -> ElectrodynamicsItems.getItem(SubtypeImpureDust.vanadium)),
	lithium(() -> ElectrodynamicsItems.getItem(SubtypeImpureDust.lithium)),
	halite(() -> ElectrodynamicsItems.getItem(SubtypeDust.salt)),
	molybdenum(() -> ElectrodynamicsItems.getItem(SubtypeImpureDust.molybdenum)),
	potassiumchloride(null),
	netherite(() -> ElectrodynamicsItems.getItem(SubtypeImpureDust.netherite));

	@Nullable
	public final Supplier<Item> crushedItem;

	SubtypeCrystal(Supplier<Item> crushedItem) {
		this.crushedItem = crushedItem;
	}

	@Override
	public String tag() {
		return "crystal" + name();
	}

	@Override
	public String forgeTag() {
		return "crystal/" + name();
	}

	@Override
	public boolean isItem() {
		return true;
	}
}
