package electrodynamics.common.fluid.subtype;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import voltaic.api.ISubtype;
import voltaic.common.tags.VoltaicTags;

public enum SubtypeSulfateFluid implements ISubtype {
	copper(VoltaicTags.Fluids.COPPER_SULFATE, () -> ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.copper), VoltaicTags.Items.ORE_COPPER),
	tin(VoltaicTags.Fluids.TIN_SULFATE, () -> ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.tin), VoltaicTags.Items.ORE_TIN),
	silver(VoltaicTags.Fluids.SILVER_SULFATE, () -> ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.silver), VoltaicTags.Items.ORE_SILVER),
	lead(VoltaicTags.Fluids.LEAD_SULFATE, () -> ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.lead), VoltaicTags.Items.ORE_LEAD),
	vanadium(VoltaicTags.Fluids.VANADIUM_SULFATE, () -> ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.vanadium), VoltaicTags.Items.ORE_VANADIUM),
	iron(VoltaicTags.Fluids.IRON_SULFATE, () -> ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.iron), Tags.Items.ORES_IRON),
	gold(VoltaicTags.Fluids.GOLD_SULFATE, () -> ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.gold), ItemTags.GOLD_ORES),
	lithium(VoltaicTags.Fluids.LITHIUM_SULFATE, () -> ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.lithium), VoltaicTags.Items.ORE_LITHIUM),
	molybdenum(VoltaicTags.Fluids.MOLYBDENUM_SULFATE, () -> ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.molybdenum), VoltaicTags.Items.ORE_MOLYBDENUM),
	netherite(VoltaicTags.Fluids.NETHERITE_SULFATE, () -> ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.netherite), Tags.Items.ORES_NETHERITE_SCRAP);

	public final INamedTag<Fluid> tag;
	@Nullable
	public final INamedTag<Item> source;
	@Nullable
	public final Supplier<? extends Item> crystal;

	SubtypeSulfateFluid(INamedTag<Fluid> tag, INamedTag<Item> source) {
		this(tag, null, source);
	}

	SubtypeSulfateFluid(INamedTag<Fluid> tag, Supplier<? extends Item> crystal, INamedTag<Item> source) {
		this.tag = tag;
		this.crystal = crystal;
		this.source = source;
	}

	@Override
	public String tag() {
		return "fluid" + name();
	}

	@Override
	public String forgeTag() {
		return "fluid/" + name();
	}

	@Override
	public boolean isItem() {
		return false;
	}
}
