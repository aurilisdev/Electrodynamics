package electrodynamics.common.item.subtype;

import java.util.function.Supplier;

import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag.INamedTag;
import voltaic.api.ISubtype;
import voltaic.common.tags.VoltaicTags;

public enum SubtypeNugget implements ISubtype {
	tin(VoltaicTags.Items.NUGGET_TIN, VoltaicTags.Items.INGOT_TIN, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.tin)),
	copper(VoltaicTags.Items.NUGGET_COPPER, VoltaicTags.Items.INGOT_COPPER, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.copper)),
	silver(VoltaicTags.Items.NUGGET_SILVER, VoltaicTags.Items.INGOT_SILVER, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.silver)),
	superconductive(VoltaicTags.Items.NUGGET_SUPERCONDUCTIVE, VoltaicTags.Items.INGOT_SUPERCONDUCTIVE, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.superconductive)),
	steel(VoltaicTags.Items.NUGGET_STEEL, VoltaicTags.Items.INGOT_STEEL, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.steel)),
	stainlesssteel(VoltaicTags.Items.NUGGET_STAINLESSSTEEL, VoltaicTags.Items.INGOT_STAINLESSSTEEL, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.stainlesssteel)),
	hslasteel(VoltaicTags.Items.NUGGET_HSLASTEEL, VoltaicTags.Items.INGOT_HSLASTEEL, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.hslasteel)),
	titaniumcarbide(VoltaicTags.Items.NUGGET_TITANIUMCARBIDE, VoltaicTags.Items.INGOT_TITANIUMCARBIDE, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.titaniumcarbide));

	public final INamedTag<Item> tag;
	public final INamedTag<Item> sourceIngot;
	public final Supplier<Item> productIngot;

	SubtypeNugget(INamedTag<Item> tag, INamedTag<Item> sourceIngot, Supplier<Item> productIngot) {
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
