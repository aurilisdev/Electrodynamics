package electrodynamics.common.fluid.subtype;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import electrodynamics.registers.ElectrodynamicsFluids;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.Tags;
import voltaic.api.ISubtype;
import voltaic.common.tags.VoltaicTags;
import voltaic.prefab.utilities.math.Color;

public enum SubtypeSulfateFluid implements ISubtype {
    copper(VoltaicTags.Fluids.COPPER_SULFATE,
	    () -> ElectrodynamicsFluids.FLUIDS_PUREMINERAL.getValue(SubtypePureMineralFluid.copper),
	    ItemTags.COPPER_ORES, Color.WHITE),
    tin(VoltaicTags.Fluids.TIN_SULFATE,
	    () -> ElectrodynamicsFluids.FLUIDS_PUREMINERAL.getValue(SubtypePureMineralFluid.tin),
	    VoltaicTags.Items.ORE_TIN, Color.WHITE),
    silver(VoltaicTags.Fluids.SILVER_SULFATE,
	    () -> ElectrodynamicsFluids.FLUIDS_PUREMINERAL.getValue(SubtypePureMineralFluid.silver),
	    VoltaicTags.Items.ORE_SILVER, Color.WHITE),
    lead(VoltaicTags.Fluids.LEAD_SULFATE,
	    () -> ElectrodynamicsFluids.FLUIDS_PUREMINERAL.getValue(SubtypePureMineralFluid.lead),
	    VoltaicTags.Items.ORE_LEAD, Color.WHITE),
    vanadium(VoltaicTags.Fluids.VANADIUM_SULFATE,
	    () -> ElectrodynamicsFluids.FLUIDS_PUREMINERAL.getValue(SubtypePureMineralFluid.vanadium),
	    VoltaicTags.Items.ORE_VANADIUM, Color.WHITE),
    iron(VoltaicTags.Fluids.IRON_SULFATE,
	    () -> ElectrodynamicsFluids.FLUIDS_PUREMINERAL.getValue(SubtypePureMineralFluid.iron), ItemTags.IRON_ORES,
	    Color.WHITE),
    gold(VoltaicTags.Fluids.GOLD_SULFATE,
	    () -> ElectrodynamicsFluids.FLUIDS_PUREMINERAL.getValue(SubtypePureMineralFluid.gold), ItemTags.GOLD_ORES,
	    Color.WHITE),
    lithium(VoltaicTags.Fluids.LITHIUM_SULFATE,
	    () -> ElectrodynamicsFluids.FLUIDS_PUREMINERAL.getValue(SubtypePureMineralFluid.lithium),
	    VoltaicTags.Items.ORE_LITHIUM, Color.WHITE),
    molybdenum(VoltaicTags.Fluids.MOLYBDENUM_SULFATE,
	    () -> ElectrodynamicsFluids.FLUIDS_PUREMINERAL.getValue(SubtypePureMineralFluid.molybdenum),
	    VoltaicTags.Items.ORE_MOLYBDENUM, Color.WHITE),
    netherite(VoltaicTags.Fluids.NETHERITE_SULFATE,
	    () -> ElectrodynamicsFluids.FLUIDS_PUREMINERAL.getValue(SubtypePureMineralFluid.netherite),
	    Tags.Items.ORES_NETHERITE_SCRAP, Color.WHITE);

    public final TagKey<Fluid> tag;
    @Nullable
    public final TagKey<Item> source;
    @Nullable
    public final Supplier<Fluid> result;
    public final Color color;

    SubtypeSulfateFluid(TagKey<Fluid> tag, TagKey<Item> source, Color color) {
	this(tag, null, source, color);
    }

    SubtypeSulfateFluid(TagKey<Fluid> tag, Supplier<Fluid> result, TagKey<Item> source, Color color) {
	this.tag = tag;
	this.result = result;
	this.source = source;
	this.color = color;
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
