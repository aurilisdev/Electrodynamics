package electrodynamics.common.item.subtype;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraftforge.common.Tags;
import voltaic.api.ISubtype;
import voltaic.common.tags.VoltaicTags;

public enum SubtypeGear implements ISubtype {
	iron(VoltaicTags.Items.GEAR_IRON, Tags.Items.INGOTS_IRON),
	copper(VoltaicTags.Items.GEAR_COPPER, VoltaicTags.Items.INGOT_COPPER),
	tin(VoltaicTags.Items.GEAR_TIN, VoltaicTags.Items.INGOT_TIN),
	steel(VoltaicTags.Items.GEAR_STEEL, VoltaicTags.Items.INGOT_STEEL),
	bronze(VoltaicTags.Items.GEAR_BRONZE, VoltaicTags.Items.INGOT_BRONZE);

	public final INamedTag<Item> tag;
	public final INamedTag<Item> sourceIngot;

	SubtypeGear(INamedTag<Item> tag, INamedTag<Item> sourceIngot) {
		this.tag = tag;
		this.sourceIngot = sourceIngot;
	}

	@Override
	public String tag() {
		return "gear" + name();
	}

	@Override
	public String forgeTag() {
		return "gears/" + name();
	}

	@Override
	public boolean isItem() {
		return true;
	}
}
