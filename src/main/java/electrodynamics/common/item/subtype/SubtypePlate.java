package electrodynamics.common.item.subtype;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraftforge.common.Tags;
import voltaic.api.ISubtype;
import voltaic.common.tags.VoltaicTags;

public enum SubtypePlate implements ISubtype {
	copper(VoltaicTags.Items.PLATE_COPPER, VoltaicTags.Items.INGOT_COPPER),
    iron(VoltaicTags.Items.PLATE_IRON, Tags.Items.INGOTS_IRON),
    steel(VoltaicTags.Items.PLATE_STEEL, VoltaicTags.Items.INGOT_STEEL),
    lead(VoltaicTags.Items.PLATE_LEAD, VoltaicTags.Items.INGOT_LEAD),
    bronze(VoltaicTags.Items.PLATE_BRONZE, VoltaicTags.Items.INGOT_BRONZE),
    lithium(VoltaicTags.Items.PLATE_LITHIUM, VoltaicTags.Items.INGOT_LITHIUM),
    stainlesssteel(VoltaicTags.Items.PLATE_STAINLESSSTEEL, VoltaicTags.Items.INGOT_STAINLESSSTEEL),
    vanadiumsteel(VoltaicTags.Items.PLATE_VANADIUMSTEEL, VoltaicTags.Items.INGOT_VANADIUMSTEEL),
    titanium(VoltaicTags.Items.PLATE_TITANIUM, VoltaicTags.Items.INGOT_TITANIUM),
    aluminum(VoltaicTags.Items.PLATE_ALUMINUM, VoltaicTags.Items.INGOT_ALUMINUM),
    hslasteel(VoltaicTags.Items.PLATE_HSLASTEEL, VoltaicTags.Items.INGOT_HSLASTEEL),
    titaniumcarbide(VoltaicTags.Items.PLATE_TITANIUMCARBIDE, VoltaicTags.Items.INGOT_TITANIUMCARBIDE);

	public final INamedTag<Item> tag;
	public final INamedTag<Item> sourceIngot;

	SubtypePlate(INamedTag<Item> tag, INamedTag<Item> sourceIngot) {
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
