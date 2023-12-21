package electrodynamics.common.item.subtype;

import electrodynamics.api.ISubtype;
import electrodynamics.common.tags.ElectrodynamicsTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;

public enum SubtypeGear implements ISubtype {
	iron(ElectrodynamicsTags.Items.GEAR_IRON, Tags.Items.INGOTS_IRON),
	copper(ElectrodynamicsTags.Items.GEAR_COPPER, Tags.Items.INGOTS_COPPER),
	tin(ElectrodynamicsTags.Items.GEAR_TIN, ElectrodynamicsTags.Items.INGOT_TIN),
	steel(ElectrodynamicsTags.Items.GEAR_STEEL, ElectrodynamicsTags.Items.INGOT_STEEL),
	bronze(ElectrodynamicsTags.Items.GEAR_BRONZE, ElectrodynamicsTags.Items.INGOT_BRONZE);

	public final TagKey<Item> tag;
	public final TagKey<Item> sourceIngot;

	SubtypeGear(TagKey<Item> tag, TagKey<Item> sourceIngot) {
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