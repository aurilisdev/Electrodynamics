package electrodynamics.common.tags;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.common.fluid.types.gas.FluidHydrogen;
import electrodynamics.common.fluid.types.gas.FluidOxygen;
import electrodynamics.common.fluid.types.liquid.FluidClay;
import electrodynamics.common.fluid.types.liquid.FluidConcrete;
import electrodynamics.common.fluid.types.liquid.FluidEthanol;
import electrodynamics.common.fluid.types.liquid.FluidHydrogenFluoride;
import electrodynamics.common.fluid.types.liquid.FluidPolyethylene;
import electrodynamics.common.fluid.types.liquid.FluidSulfate;
import electrodynamics.common.fluid.types.liquid.FluidSulfuricAcid;
import electrodynamics.common.fluid.types.subtype.SubtypeSulfateFluid;
import electrodynamics.common.item.gear.tools.ItemCanister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.Tags;

public class ElectrodynamicsTags {

	public static List<Tags.IOptionalNamedTag<Fluid>> FLUID_TAGS = new ArrayList<>();

	public static void init() {
		Fluids.init();
	}

	public static List<Tags.IOptionalNamedTag<Fluid>> getFluidTags() {
		return FLUID_TAGS;
	}

	// Only the Tag objects should ever be visible from this class!
	public static class Fluids {

		public static final Tags.IOptionalNamedTag<Fluid> SULFURIC_ACID = forgeTag(FluidSulfuricAcid.FORGE_TAG);
		public static final Tags.IOptionalNamedTag<Fluid> ETHANOL = forgeTag(FluidEthanol.FORGE_TAG);
		public static final Tags.IOptionalNamedTag<Fluid> HYDROGEN_FLUORIDE = forgeTag(FluidHydrogenFluoride.FORGE_TAG);
		public static final Tags.IOptionalNamedTag<Fluid> POLYETHLYENE = forgeTag(FluidPolyethylene.FORGE_TAG);
		public static final Tags.IOptionalNamedTag<Fluid> COPPER_SULF = forgeTag(FluidSulfate.FORGE_TAG + SubtypeSulfateFluid.copper.name());
		public static final Tags.IOptionalNamedTag<Fluid> TIN_SULF = forgeTag(FluidSulfate.FORGE_TAG + SubtypeSulfateFluid.tin.name());
		public static final Tags.IOptionalNamedTag<Fluid> SILVER_SULF = forgeTag(FluidSulfate.FORGE_TAG + SubtypeSulfateFluid.silver.name());
		public static final Tags.IOptionalNamedTag<Fluid> LEAD_SULF = forgeTag(FluidSulfate.FORGE_TAG + SubtypeSulfateFluid.lead.name());
		public static final Tags.IOptionalNamedTag<Fluid> VANADIUM_SULF = forgeTag(FluidSulfate.FORGE_TAG + SubtypeSulfateFluid.vanadium.name());
		public static final Tags.IOptionalNamedTag<Fluid> IRON_SULF = forgeTag(FluidSulfate.FORGE_TAG + SubtypeSulfateFluid.iron.name());
		public static final Tags.IOptionalNamedTag<Fluid> GOLD_SULF = forgeTag(FluidSulfate.FORGE_TAG + SubtypeSulfateFluid.gold.name());
		public static final Tags.IOptionalNamedTag<Fluid> LITHIUM_SULF = forgeTag(FluidSulfate.FORGE_TAG + SubtypeSulfateFluid.lithium.name());
		public static final Tags.IOptionalNamedTag<Fluid> MOLYBDENUM_SULF = forgeTag(FluidSulfate.FORGE_TAG + SubtypeSulfateFluid.molybdenum.name());
		public static final Tags.IOptionalNamedTag<Fluid> CLAY = forgeTag(FluidClay.FORGE_TAG);
		public static final Tags.IOptionalNamedTag<Fluid> CONCRETE = forgeTag(FluidConcrete.FORGE_TAG);
		public static final Tags.IOptionalNamedTag<Fluid> OXYGEN = forgeTag(FluidOxygen.FORGE_TAG);
		public static final Tags.IOptionalNamedTag<Fluid> HYDROGEN = forgeTag(FluidHydrogen.FORGE_TAG);

		private static void init() {
			FLUID_TAGS.add(SULFURIC_ACID);
			FLUID_TAGS.add(ETHANOL);
			FLUID_TAGS.add(HYDROGEN_FLUORIDE);
			FLUID_TAGS.add(POLYETHLYENE);
			FLUID_TAGS.add(COPPER_SULF);
			FLUID_TAGS.add(TIN_SULF);
			FLUID_TAGS.add(SILVER_SULF);
			FLUID_TAGS.add(LEAD_SULF);
			FLUID_TAGS.add(VANADIUM_SULF);
			FLUID_TAGS.add(IRON_SULF);
			FLUID_TAGS.add(GOLD_SULF);
			FLUID_TAGS.add(LITHIUM_SULF);
			FLUID_TAGS.add(MOLYBDENUM_SULF);
			FLUID_TAGS.add(CLAY);
			FLUID_TAGS.add(CONCRETE);
			FLUID_TAGS.add(OXYGEN);
			FLUID_TAGS.add(HYDROGEN);

			ItemCanister.addTag(SULFURIC_ACID);
			ItemCanister.addTag(ETHANOL);
			ItemCanister.addTag(HYDROGEN_FLUORIDE);
			ItemCanister.addTag(POLYETHLYENE);
			ItemCanister.addTag(COPPER_SULF);
			ItemCanister.addTag(TIN_SULF);
			ItemCanister.addTag(SILVER_SULF);
			ItemCanister.addTag(LEAD_SULF);
			ItemCanister.addTag(VANADIUM_SULF);
			ItemCanister.addTag(IRON_SULF);
			ItemCanister.addTag(GOLD_SULF);
			ItemCanister.addTag(LITHIUM_SULF);
			ItemCanister.addTag(MOLYBDENUM_SULF);
			ItemCanister.addTag(CLAY);
			ItemCanister.addTag(CONCRETE);
			ItemCanister.addTag(OXYGEN);
			ItemCanister.addTag(HYDROGEN);
		}

		private static Tags.IOptionalNamedTag<Fluid> forgeTag(String name) {
			return FluidTags.createOptional(new ResourceLocation("forge", name));
		}

	}

}
