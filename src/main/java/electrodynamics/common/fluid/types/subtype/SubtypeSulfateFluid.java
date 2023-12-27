package electrodynamics.common.fluid.types.subtype;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import electrodynamics.api.ISubtype;
import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

public enum SubtypeSulfateFluid implements ISubtype {
	copper(ElectrodynamicsTags.Fluids.COPPER_SULF, () -> ElectrodynamicsItems.getItem(SubtypeCrystal.copper), ElectrodynamicsTags.Items.ORE_COPPER),
	tin(ElectrodynamicsTags.Fluids.TIN_SULF, () -> ElectrodynamicsItems.getItem(SubtypeCrystal.tin), ElectrodynamicsTags.Items.ORE_TIN),
	silver(ElectrodynamicsTags.Fluids.SILVER_SULF, () -> ElectrodynamicsItems.getItem(SubtypeCrystal.silver), ElectrodynamicsTags.Items.ORE_SILVER),
	lead(ElectrodynamicsTags.Fluids.LEAD_SULF, () -> ElectrodynamicsItems.getItem(SubtypeCrystal.lead), ElectrodynamicsTags.Items.ORE_LEAD),
	vanadium(ElectrodynamicsTags.Fluids.VANADIUM_SULF, () -> ElectrodynamicsItems.getItem(SubtypeCrystal.vanadium), ElectrodynamicsTags.Items.ORE_VANADIUM),
	iron(ElectrodynamicsTags.Fluids.IRON_SULF, () -> ElectrodynamicsItems.getItem(SubtypeCrystal.iron), Tags.Items.ORES_IRON),
	gold(ElectrodynamicsTags.Fluids.GOLD_SULF, () -> ElectrodynamicsItems.getItem(SubtypeCrystal.gold), Tags.Items.ORES_GOLD),
	lithium(ElectrodynamicsTags.Fluids.LITHIUM_SULF, () -> ElectrodynamicsItems.getItem(SubtypeCrystal.lithium), ElectrodynamicsTags.Items.ORE_LITHIUM),
	molybdenum(ElectrodynamicsTags.Fluids.MOLYBDENUM_SULF, () -> ElectrodynamicsItems.getItem(SubtypeCrystal.molybdenum), ElectrodynamicsTags.Items.ORE_MOLYBDENUM);

	public final IOptionalNamedTag<Fluid> tag;
	@Nullable
	public final IOptionalNamedTag<Item> source;
	@Nullable
	public final Supplier<Item> crystal;

	SubtypeSulfateFluid(IOptionalNamedTag<Fluid> tag, IOptionalNamedTag<Item> source) {
		this(tag, null, source);
	}

	SubtypeSulfateFluid(IOptionalNamedTag<Fluid> tag, Supplier<Item> crystal, IOptionalNamedTag<Item> source) {
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