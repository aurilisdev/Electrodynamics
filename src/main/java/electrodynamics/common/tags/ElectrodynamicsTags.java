package electrodynamics.common.tags;

import electrodynamics.common.fluid.types.FluidEthanol;
import electrodynamics.common.fluid.types.FluidHydrogenFluoride;
import electrodynamics.common.fluid.types.FluidPolyethylene;
import electrodynamics.common.fluid.types.FluidSulfate;
import electrodynamics.common.fluid.types.FluidSulfuricAcid;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

public class ElectrodynamicsTags {

	public static void init() {
		Items.init();
		Blocks.init();
		Fluids.init();
	}

	public static class Items {

		public static final IOptionalNamedTag<Item> CIRCUITS_BASIC = forgeTag("circuits/basic");
		public static final IOptionalNamedTag<Item> CIRCUITS_ADVANCED = forgeTag("circuits/advanced");
		public static final IOptionalNamedTag<Item> CIRCUITS_ELITE = forgeTag("circuits/elite");
		public static final IOptionalNamedTag<Item> CIRCUITS_ULTIMATE = forgeTag("circuits/ultimate");

		public static final IOptionalNamedTag<Item> DUST_BRONZE = forgeTag("dusts/bronze");
		public static final IOptionalNamedTag<Item> DUST_COPPER = forgeTag("dusts/copper");
		public static final IOptionalNamedTag<Item> DUST_ENDEREYE = forgeTag("dusts/endereye");
		public static final IOptionalNamedTag<Item> DUST_GOLD = forgeTag("dusts/gold");
		public static final IOptionalNamedTag<Item> DUST_IRON = forgeTag("dusts/iron");
		public static final IOptionalNamedTag<Item> DUST_LEAD = forgeTag("dusts/lead");
		public static final IOptionalNamedTag<Item> DUST_LITHIUM = forgeTag("dusts/lithium");
		public static final IOptionalNamedTag<Item> DUST_MOLYBDENUM = forgeTag("dusts/molybdenum");
		public static final IOptionalNamedTag<Item> DUST_OBSIDIAN = forgeTag("dusts/obsidian");
		public static final IOptionalNamedTag<Item> DUST_SALT = forgeTag("dusts/salt");
		public static final IOptionalNamedTag<Item> DUST_SALTPETER = forgeTag("dusts/saltpeter");
		public static final IOptionalNamedTag<Item> DUST_SILICA = forgeTag("dusts/silica");
		public static final IOptionalNamedTag<Item> DUST_SILVER = forgeTag("dusts/silver");
		public static final IOptionalNamedTag<Item> DUST_STEEL = forgeTag("dusts/steel");
		public static final IOptionalNamedTag<Item> DUST_SULFUR = forgeTag("dusts/sulfur");
		public static final IOptionalNamedTag<Item> DUST_SUPERCONDUCTIVE = forgeTag("dusts/superconductive");
		public static final IOptionalNamedTag<Item> DUST_TIN = forgeTag("dusts/tin");
		public static final IOptionalNamedTag<Item> DUST_VANADIUM = forgeTag("dusts/vanadium");

		public static final IOptionalNamedTag<Item> GEAR_BRONZE = forgeTag("gears/bronze");
		public static final IOptionalNamedTag<Item> GEAR_COPPER = forgeTag("gears/copper");
		public static final IOptionalNamedTag<Item> GEAR_IRON = forgeTag("gears/iron");
		public static final IOptionalNamedTag<Item> GEAR_STEEL = forgeTag("gears/steel");
		public static final IOptionalNamedTag<Item> GEAR_TIN = forgeTag("gears/tin");

		public static final IOptionalNamedTag<Item> IMPURE_DUST_COPPER = forgeTag("impuredust/copper");
		public static final IOptionalNamedTag<Item> IMPURE_DUST_GOLD = forgeTag("impuredust/gold");
		public static final IOptionalNamedTag<Item> IMPURE_DUST_IRON = forgeTag("impuredust/iron");
		public static final IOptionalNamedTag<Item> IMPURE_DUST_LEAD = forgeTag("impuredust/lead");
		public static final IOptionalNamedTag<Item> IMPURE_DUST_LITHIUM = forgeTag("impuredust/lithium");
		public static final IOptionalNamedTag<Item> IMPURE_DUST_MOLYBDENUM = forgeTag("impuredust/molybdenum");
		public static final IOptionalNamedTag<Item> IMPURE_DUST_SILVER = forgeTag("impuredust/silver");
		public static final IOptionalNamedTag<Item> IMPURE_DUST_TIN = forgeTag("impuredust/tin");
		public static final IOptionalNamedTag<Item> IMPURE_DUST_VANADIUM = forgeTag("impuredust/vanadium");

		public static final IOptionalNamedTag<Item> INGOT_ALUMINUM = forgeTag("ingots/aluminum");
		public static final IOptionalNamedTag<Item> INGOT_BRONZE = forgeTag("ingots/bronze");
		public static final IOptionalNamedTag<Item> INGOT_CHROMIUM = forgeTag("ingots/chromium");
		public static final IOptionalNamedTag<Item> INGOT_COPPER = forgeTag("ingots/copper");
		public static final IOptionalNamedTag<Item> INGOT_HSLASTEEL = forgeTag("ingots/hslasteel");
		public static final IOptionalNamedTag<Item> INGOT_LEAD = forgeTag("ingots/lead");
		public static final IOptionalNamedTag<Item> INGOT_LITHIUM = forgeTag("ingots/lithium");
		public static final IOptionalNamedTag<Item> INGOT_MOLYBDENUM = forgeTag("ingots/molybdenum");
		public static final IOptionalNamedTag<Item> INGOT_SILVER = forgeTag("ingots/silver");
		public static final IOptionalNamedTag<Item> INGOT_STAINLESSSTEEL = forgeTag("ingots/stainlesssteel");
		public static final IOptionalNamedTag<Item> INGOT_STEEL = forgeTag("ingots/steel");
		public static final IOptionalNamedTag<Item> INGOT_SUPERCONDUCTIVE = forgeTag("ingots/superconductive");
		public static final IOptionalNamedTag<Item> INGOT_TIN = forgeTag("ingots/tin");
		public static final IOptionalNamedTag<Item> INGOT_TITANIUM = forgeTag("ingots/titanium");
		public static final IOptionalNamedTag<Item> INGOT_TITANIUMCARBIDE = forgeTag("ingots/titaniumcarbide");
		public static final IOptionalNamedTag<Item> INGOT_VANADIUM = forgeTag("ingots/vanadium");
		public static final IOptionalNamedTag<Item> INGOT_VANADIUMSTEEL = forgeTag("ingots/vanadiumsteel");

		public static final IOptionalNamedTag<Item> ORE_ALUMINUM = forgeTag("ores/aluminum");
		public static final IOptionalNamedTag<Item> ORE_CHROMIUM = forgeTag("ores/chromium");
		public static final IOptionalNamedTag<Item> ORE_COPPER = forgeTag("ores/copper");
		public static final IOptionalNamedTag<Item> ORE_FLUORITE = forgeTag("ores/fluorite");
		public static final IOptionalNamedTag<Item> ORE_LEAD = forgeTag("ores/lead");
		public static final IOptionalNamedTag<Item> ORE_LITHIUM = forgeTag("ores/lithium");
		public static final IOptionalNamedTag<Item> ORE_MOLYBDENUM = forgeTag("ores/molybdenum");
		public static final IOptionalNamedTag<Item> ORE_MONAZITE = forgeTag("ores/monazite");
		public static final IOptionalNamedTag<Item> ORE_POTASSIUMCHLORIDE = forgeTag("ores/potassiumchloride");
		public static final IOptionalNamedTag<Item> ORE_SALT = forgeTag("ores/salt");
		public static final IOptionalNamedTag<Item> ORE_SALTPETER = forgeTag("ores/saltpeter");
		public static final IOptionalNamedTag<Item> ORE_SILVER = forgeTag("ores/silver");
		public static final IOptionalNamedTag<Item> ORE_SULFUR = forgeTag("ores/sulfur");
		public static final IOptionalNamedTag<Item> ORE_THORIUM = forgeTag("ores/thorium");
		public static final IOptionalNamedTag<Item> ORE_TIN = forgeTag("ores/tin");
		public static final IOptionalNamedTag<Item> ORE_TITANIUM = forgeTag("ores/titanium");
		public static final IOptionalNamedTag<Item> ORE_URANIUM = forgeTag("ores/uranium");
		public static final IOptionalNamedTag<Item> ORE_VANADIUM = forgeTag("ores/vanadium");

		public static final IOptionalNamedTag<Item> OXIDE_CALCIUMCARBONATE = forgeTag("oxide/calciumcarbonate");
		public static final IOptionalNamedTag<Item> OXIDE_CHROMIUM = forgeTag("oxide/chromium");
		public static final IOptionalNamedTag<Item> OXIDE_CHROMIUMDISILICIDE = forgeTag("oxide/chromiumdisilicide");
		public static final IOptionalNamedTag<Item> OXIDE_DISULFUR = forgeTag("oxide/disulfur");
		public static final IOptionalNamedTag<Item> OXIDE_DITITANIUM = forgeTag("oxide/dititanium");
		public static final IOptionalNamedTag<Item> OXIDE_SODIUMCARBONATE = forgeTag("oxide/sodiumcarbonate");
		public static final IOptionalNamedTag<Item> OXIDE_SULFURDICHLORIDE = forgeTag("oxide/sulfurdichloride");
		public static final IOptionalNamedTag<Item> OXIDE_THIONYLCHLORIDE = forgeTag("oxide/thionylchloride");
		public static final IOptionalNamedTag<Item> OXIDE_TRISULFUR = forgeTag("oxide/trisulfur");
		public static final IOptionalNamedTag<Item> OXIDE_VANADIUM = forgeTag("oxide/vanadium");

		public static final IOptionalNamedTag<Item> PLATE_ALUMINUM = forgeTag("plates/aluminum");
		public static final IOptionalNamedTag<Item> PLATE_BRONZE = forgeTag("plates/bronze");
		public static final IOptionalNamedTag<Item> PLATE_HSLASTEEL = forgeTag("plates/hslasteel");
		public static final IOptionalNamedTag<Item> PLATE_IRON = forgeTag("plates/iron");
		public static final IOptionalNamedTag<Item> PLATE_LEAD = forgeTag("plates/lead");
		public static final IOptionalNamedTag<Item> PLATE_LITHIUM = forgeTag("plates/lithium");
		public static final IOptionalNamedTag<Item> PLATE_STAINLESSSTEEL = forgeTag("plates/stainlesssteel");
		public static final IOptionalNamedTag<Item> PLATE_STEEL = forgeTag("plates/steel");
		public static final IOptionalNamedTag<Item> PLATE_TITANIUM = forgeTag("plates/titanium");
		public static final IOptionalNamedTag<Item> PLATE_TITANIUMCARBIDE = forgeTag("plates/titaniumcarbide");
		public static final IOptionalNamedTag<Item> PLATE_VANADIUMSTEEL = forgeTag("plates/vanadiumsteel");

		public static final IOptionalNamedTag<Item> ROD_HSLASTEEL = forgeTag("rods/hslasteel");
		public static final IOptionalNamedTag<Item> ROD_STAINLESSSTEEL = forgeTag("rods/stainlesssteel");
		public static final IOptionalNamedTag<Item> ROD_STEEL = forgeTag("rods/steel");
		public static final IOptionalNamedTag<Item> ROD_TITANIUMCARBIDE = forgeTag("rods/titaniumcarbide");

		public static final IOptionalNamedTag<Item> STORAGE_BLOCK_ALUMINUM = forgeTag("storage_blocks/aluminum");
		public static final IOptionalNamedTag<Item> STORAGE_BLOCK_BRONZE = forgeTag("storage_blocks/bronze");
		public static final IOptionalNamedTag<Item> STORAGE_BLOCK_CHROMIUM = forgeTag("storage_blocks/chromium");
		public static final IOptionalNamedTag<Item> STORAGE_BLOCK_COPPER = forgeTag("storage_blocks/copper");
		public static final IOptionalNamedTag<Item> STORAGE_BLOCK_HSLASTEEL = forgeTag("storage_blocks/hslasteel");
		public static final IOptionalNamedTag<Item> STORAGE_BLOCK_LEAD = forgeTag("storage_blocks/lead");
		public static final IOptionalNamedTag<Item> STORAGE_BLOCK_SILVER = forgeTag("storage_blocks/silver");
		public static final IOptionalNamedTag<Item> STORAGE_BLOCK_STAINLESSSTEEL = forgeTag("storage_blocks/stainlesssteel");
		public static final IOptionalNamedTag<Item> STORAGE_BLOCK_STEEL = forgeTag("storage_blocks/steel");
		public static final IOptionalNamedTag<Item> STORAGE_BLOCK_TIN = forgeTag("storage_blocks/tin");
		public static final IOptionalNamedTag<Item> STORAGE_BLOCK_TITANIUM = forgeTag("storage_blocks/titanium");
		public static final IOptionalNamedTag<Item> STORAGE_BLOCK_TITANIUMCARBIDE = forgeTag("storage_blocks/titaniumcarbide");
		public static final IOptionalNamedTag<Item> STORAGE_BLOCK_VANADIUMSTEEL = forgeTag("storage_blocks/vanadiumsteel");

		public static final IOptionalNamedTag<Item> COAL_COKE = forgeTag("coal_coke");
		public static final IOptionalNamedTag<Item> PLASTIC = forgeTag("plastic");
		public static final IOptionalNamedTag<Item> SLAG = forgeTag("slag");

		public static final IOptionalNamedTag<Item> GEARS = forgeTag("gears");
		public static final IOptionalNamedTag<Item> INGOTS = forgeTag("ingots");
		public static final IOptionalNamedTag<Item> ORES = forgeTag("ores");
		public static final IOptionalNamedTag<Item> INSULATES_PLAYER_FEET = forgeTag("insulates_player_feet");

		private static void init() {
		}

		private static IOptionalNamedTag<Item> forgeTag(String name) {
			return ItemTags.createOptional(new ResourceLocation("forge", name));
		}
	}

	public static class Blocks {

		public static final IOptionalNamedTag<Block> ORE_ALUMINUM = forgeTag("ores/aluminum");
		public static final IOptionalNamedTag<Block> ORE_CHROMIUM = forgeTag("ores/chromium");
		public static final IOptionalNamedTag<Block> ORE_COPPER = forgeTag("ores/copper");
		public static final IOptionalNamedTag<Block> ORE_FLUORITE = forgeTag("ores/fluorite");
		public static final IOptionalNamedTag<Block> ORE_LEAD = forgeTag("ores/lead");
		public static final IOptionalNamedTag<Block> ORE_LITHIUM = forgeTag("ores/lithium");
		public static final IOptionalNamedTag<Block> ORE_MOLYBDENUM = forgeTag("ores/molybdenum");
		public static final IOptionalNamedTag<Block> ORE_MONAZITE = forgeTag("ores/monazite");
		public static final IOptionalNamedTag<Block> ORE_POTASSIUMCHLORIDE = forgeTag("ores/potassiumchloride");
		public static final IOptionalNamedTag<Block> ORE_SALT = forgeTag("ores/salt");
		public static final IOptionalNamedTag<Block> ORE_SALTPETER = forgeTag("ores/saltpeter");
		public static final IOptionalNamedTag<Block> ORE_SILVER = forgeTag("ores/silver");
		public static final IOptionalNamedTag<Block> ORE_SULFUR = forgeTag("ores/sulfur");
		public static final IOptionalNamedTag<Block> ORE_THORIUM = forgeTag("ores/thorium");
		public static final IOptionalNamedTag<Block> ORE_TIN = forgeTag("ores/tin");
		public static final IOptionalNamedTag<Block> ORE_TITANIUM = forgeTag("ores/titanium");
		public static final IOptionalNamedTag<Block> ORE_URANIUM = forgeTag("ores/uranium");
		public static final IOptionalNamedTag<Block> ORE_VANADIUM = forgeTag("ores/vanadium");

		public static final IOptionalNamedTag<Block> STORAGE_BLOCK_ALUMINUM = forgeTag("storage_blocks/aluminum");
		public static final IOptionalNamedTag<Block> STORAGE_BLOCK_BRONZE = forgeTag("storage_blocks/bronze");
		public static final IOptionalNamedTag<Block> STORAGE_BLOCK_CHROMIUM = forgeTag("storage_blocks/chromium");
		public static final IOptionalNamedTag<Block> STORAGE_BLOCK_COPPER = forgeTag("storage_blocks/copper");
		public static final IOptionalNamedTag<Block> STORAGE_BLOCK_HSLASTEEL = forgeTag("storage_blocks/hslasteel");
		public static final IOptionalNamedTag<Block> STORAGE_BLOCK_LEAD = forgeTag("storage_blocks/lead");
		public static final IOptionalNamedTag<Block> STORAGE_BLOCK_SILVER = forgeTag("storage_blocks/silver");
		public static final IOptionalNamedTag<Block> STORAGE_BLOCK_STAINLESSSTEEL = forgeTag("storage_blocks/stainlesssteel");
		public static final IOptionalNamedTag<Block> STORAGE_BLOCK_STEEL = forgeTag("storage_blocks/steel");
		public static final IOptionalNamedTag<Block> STORAGE_BLOCK_TIN = forgeTag("storage_blocks/tin");
		public static final IOptionalNamedTag<Block> STORAGE_BLOCK_TITANIUM = forgeTag("storage_blocks/titanium");
		public static final IOptionalNamedTag<Block> STORAGE_BLOCK_TITANIUMCARBIDE = forgeTag("storage_blocks/titaniumcarbide");
		public static final IOptionalNamedTag<Block> STORAGE_BLOCK_VANADIUMSTEEL = forgeTag("storage_blocks/vanadiumsteel");

		public static final IOptionalNamedTag<Block> ORES = forgeTag("ores");

		private static void init() {
		}

		private static IOptionalNamedTag<Block> forgeTag(String name) {
			return BlockTags.createOptional(new ResourceLocation("forge", name));
		}

	}

	// Only the Tag objects should ever be visible from this class!
	public static class Fluids {
		
		public static final IOptionalNamedTag<Fluid> EMPTY = forgeTag("empty");

		public static final IOptionalNamedTag<Fluid> SULFURIC_ACID = forgeTag(FluidSulfuricAcid.FORGE_TAG);
		public static final IOptionalNamedTag<Fluid> ETHANOL = forgeTag(FluidEthanol.FORGE_TAG);
		public static final IOptionalNamedTag<Fluid> HYDROGEN_FLUORIDE = forgeTag(FluidHydrogenFluoride.FORGE_TAG);
		public static final IOptionalNamedTag<Fluid> POLYETHLYENE = forgeTag(FluidPolyethylene.FORGE_TAG);
		public static final IOptionalNamedTag<Fluid> COPPER_SULF = forgeTag(FluidSulfate.FORGE_TAG + "copper");
		public static final IOptionalNamedTag<Fluid> TIN_SULF = forgeTag(FluidSulfate.FORGE_TAG + "tin");
		public static final IOptionalNamedTag<Fluid> SILVER_SULF = forgeTag(FluidSulfate.FORGE_TAG + "silver");
		public static final IOptionalNamedTag<Fluid> LEAD_SULF = forgeTag(FluidSulfate.FORGE_TAG + "lead");
		public static final IOptionalNamedTag<Fluid> VANADIUM_SULF = forgeTag(FluidSulfate.FORGE_TAG + "vanadium");
		public static final IOptionalNamedTag<Fluid> IRON_SULF = forgeTag(FluidSulfate.FORGE_TAG + "iron");
		public static final IOptionalNamedTag<Fluid> GOLD_SULF = forgeTag(FluidSulfate.FORGE_TAG + "gold");
		public static final IOptionalNamedTag<Fluid> LITHIUM_SULF = forgeTag(FluidSulfate.FORGE_TAG + "lithium");
		public static final IOptionalNamedTag<Fluid> MOLYBDENUM_SULF = forgeTag(FluidSulfate.FORGE_TAG + "molybdenum");

		private static void init() {

		}

		private static IOptionalNamedTag<Fluid> forgeTag(String name) {
			return FluidTags.createOptional(new ResourceLocation("forge", name));
		}

	}

}
