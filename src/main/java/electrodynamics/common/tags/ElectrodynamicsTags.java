package electrodynamics.common.tags;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.common.fluid.types.FluidEthanol;
import electrodynamics.common.fluid.types.FluidHydrogenFluoride;
import electrodynamics.common.fluid.types.FluidMineral;
import electrodynamics.common.fluid.types.FluidMolybdenum;
import electrodynamics.common.fluid.types.FluidPolyethylene;
import electrodynamics.common.fluid.types.FluidSulfuricAcid;
import electrodynamics.common.item.gear.tools.ItemCanister;
import electrodynamics.common.item.subtype.SubtypeMineralFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class ElectrodynamicsTags {
	
	public static List<Tags.IOptionalNamedTag<Fluid>> FLUID_TAGS = new ArrayList<>();
	
	public static void init() {
		Fluids.init();
	}
	
	public static class Fluids {
		
		public static final Tags.IOptionalNamedTag<Fluid> SULFURIC_ACID = forgeTag(FluidSulfuricAcid.FORGE_TAG);
		public static final Tags.IOptionalNamedTag<Fluid> ETHANOL = forgeTag(FluidEthanol.FORGE_TAG);
		public static final Tags.IOptionalNamedTag<Fluid> HYDROGEN_FLUORIDE = forgeTag(FluidHydrogenFluoride.FORGE_TAG);
		public static final Tags.IOptionalNamedTag<Fluid> MOLYBDENUM = forgeTag(FluidMolybdenum.FORGE_TAG);
		public static final Tags.IOptionalNamedTag<Fluid> POLYETHLYENE = forgeTag(FluidPolyethylene.FORGE_TAG);
		public static final Tags.IOptionalNamedTag<Fluid> COPPER_MF = forgeTag(FluidMineral.FORGE_TAG + SubtypeMineralFluid.copper.name());
		public static final Tags.IOptionalNamedTag<Fluid> TIN_MF = forgeTag(FluidMineral.FORGE_TAG + SubtypeMineralFluid.tin.name());
		public static final Tags.IOptionalNamedTag<Fluid> SILVER_MF = forgeTag(FluidMineral.FORGE_TAG + SubtypeMineralFluid.silver.name());
		public static final Tags.IOptionalNamedTag<Fluid> LEAD_MF = forgeTag(FluidMineral.FORGE_TAG + SubtypeMineralFluid.lead.name());
		public static final Tags.IOptionalNamedTag<Fluid> VANADIUM_MF = forgeTag(FluidMineral.FORGE_TAG + SubtypeMineralFluid.vanadium.name());
		public static final Tags.IOptionalNamedTag<Fluid> IRON_MF = forgeTag(FluidMineral.FORGE_TAG + SubtypeMineralFluid.iron.name());
		public static final Tags.IOptionalNamedTag<Fluid> GOLD_MF = forgeTag(FluidMineral.FORGE_TAG + SubtypeMineralFluid.gold.name());
		public static final Tags.IOptionalNamedTag<Fluid> LITHIUM_MF = forgeTag(FluidMineral.FORGE_TAG + SubtypeMineralFluid.lithium.name());
		public static final Tags.IOptionalNamedTag<Fluid> MOLYBDENUM_MF = forgeTag(FluidMineral.FORGE_TAG + SubtypeMineralFluid.molybdenum.name());
		
		private static void init() {
			FLUID_TAGS.add(SULFURIC_ACID);
			FLUID_TAGS.add(ETHANOL);
			FLUID_TAGS.add(HYDROGEN_FLUORIDE);
			FLUID_TAGS.add(MOLYBDENUM);
			FLUID_TAGS.add(POLYETHLYENE);
			FLUID_TAGS.add(COPPER_MF);
			FLUID_TAGS.add(TIN_MF);
			FLUID_TAGS.add(SILVER_MF);
			FLUID_TAGS.add(LEAD_MF);
			FLUID_TAGS.add(VANADIUM_MF);
			FLUID_TAGS.add(IRON_MF);
			FLUID_TAGS.add(GOLD_MF);
			FLUID_TAGS.add(LITHIUM_MF);
			FLUID_TAGS.add(MOLYBDENUM_MF);
			
			ItemCanister.addTag(SULFURIC_ACID);
			ItemCanister.addTag(ETHANOL);
			ItemCanister.addTag(HYDROGEN_FLUORIDE);
			ItemCanister.addTag(MOLYBDENUM);
			ItemCanister.addTag(POLYETHLYENE);
			ItemCanister.addTag(COPPER_MF);
			ItemCanister.addTag(TIN_MF);
			ItemCanister.addTag(SILVER_MF);
			ItemCanister.addTag(LEAD_MF);
			ItemCanister.addTag(VANADIUM_MF);
			ItemCanister.addTag(IRON_MF);
			ItemCanister.addTag(GOLD_MF);
			ItemCanister.addTag(LITHIUM_MF);
			ItemCanister.addTag(MOLYBDENUM_MF);
		}
		
		private static Tags.IOptionalNamedTag<Fluid> forgeTag(String name){
			return FluidTags.createOptional(new ResourceLocation("forge", name));
		}
		
	}

}
