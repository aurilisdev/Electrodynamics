package electrodynamics.common.item.subtype;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import electrodynamics.api.ISubtype;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public enum SubtypeDust implements ISubtype {
	iron(ElectrodynamicsTags.Items.DUST_IRON, () -> Items.IRON_INGOT, 200),
	gold(ElectrodynamicsTags.Items.DUST_GOLD, () -> Items.GOLD_INGOT, 200),
	copper(ElectrodynamicsTags.Items.DUST_COPPER, () -> Items.COPPER_INGOT, 200),
	tin(ElectrodynamicsTags.Items.DUST_TIN, () -> ElectrodynamicsItems.getItem(SubtypeIngot.tin), 200),
	silver(ElectrodynamicsTags.Items.DUST_SILVER, () -> ElectrodynamicsItems.getItem(SubtypeIngot.silver), 200),
	steel(ElectrodynamicsTags.Items.DUST_STEEL, () -> ElectrodynamicsItems.getItem(SubtypeIngot.steel), 200),
	lead(ElectrodynamicsTags.Items.DUST_LEAD, () -> ElectrodynamicsItems.getItem(SubtypeIngot.lead), 200),
	bronze(ElectrodynamicsTags.Items.DUST_BRONZE, () -> ElectrodynamicsItems.getItem(SubtypeIngot.bronze), 200),
	superconductive(ElectrodynamicsTags.Items.DUST_SUPERCONDUCTIVE, () -> ElectrodynamicsItems.getItem(SubtypeIngot.superconductive), 200),
	endereye(ElectrodynamicsTags.Items.DUST_ENDEREYE),
	vanadium(ElectrodynamicsTags.Items.DUST_VANADIUM, () -> ElectrodynamicsItems.getItem(SubtypeIngot.vanadium), 200),
	sulfur(ElectrodynamicsTags.Items.DUST_SULFUR),
	niter(ElectrodynamicsTags.Items.DUST_SALTPETER),
	obsidian(ElectrodynamicsTags.Items.DUST_OBSIDIAN),
	lithium(ElectrodynamicsTags.Items.DUST_LITHIUM, () -> ElectrodynamicsItems.getItem(SubtypeIngot.lithium), 200),
	salt(ElectrodynamicsTags.Items.DUST_SALT),
	silica(ElectrodynamicsTags.Items.DUST_SILICA, () -> ElectrodynamicsItems.getItem(SubtypeGlass.clear), 200),
	molybdenum(ElectrodynamicsTags.Items.DUST_MOLYBDENUM, () -> ElectrodynamicsItems.getItem(SubtypeIngot.molybdenum), 200),
	netherite(ElectrodynamicsTags.Items.DUST_NETHERITE, () -> Items.NETHERITE_SCRAP, 200);

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
