package electrodynamics.common.fluid.subtype;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import voltaic.api.ISubtype;
import voltaic.common.tags.VoltaicTags;
import voltaic.prefab.utilities.math.Color;

public enum SubtypePureMineralFluid implements ISubtype {
    copper(VoltaicTags.Fluids.PURE_COPPER, () -> ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.copper), Color.WHITE),
    tin(VoltaicTags.Fluids.PURE_TIN, () -> ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.tin), Color.WHITE),
    silver(VoltaicTags.Fluids.PURE_SILVER, () -> ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.silver), Color.WHITE),
    lead(VoltaicTags.Fluids.PURE_LEAD, () -> ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.lead), Color.WHITE),
    vanadium(VoltaicTags.Fluids.PURE_VANADIUM, () -> ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.vanadium), Color.WHITE),
    iron(VoltaicTags.Fluids.PURE_IRON, () -> ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.iron), Color.WHITE),
    gold(VoltaicTags.Fluids.PURE_GOLD, () -> ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.gold), Color.WHITE),
    lithium(VoltaicTags.Fluids.PURE_LITHIUM, () -> ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.lithium), Color.WHITE),
    molybdenum(VoltaicTags.Fluids.PURE_MOLYBDENUM, () -> ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.molybdenum), Color.WHITE),
    netherite(VoltaicTags.Fluids.PURE_NETHERITE, () -> ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.netherite), Color.WHITE),
    aluminum(VoltaicTags.Fluids.PURE_ALUMINUM, () -> ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.aluminum), Color.WHITE),
    titanium(VoltaicTags.Fluids.PURE_TITANIUM, () -> ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.titanium), Color.WHITE),
    chromium(VoltaicTags.Fluids.PURE_CHROMIUM, () -> ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.chromium), Color.WHITE);

    public final TagKey<Fluid> tag;
    @Nullable
    public final Supplier<Item> result;
    public final Color color;

    SubtypePureMineralFluid(TagKey<Fluid> tag, Color color) {
        this(tag, null, color);
    }

    SubtypePureMineralFluid(TagKey<Fluid> tag, Supplier<Item> result, Color color) {
        this.tag = tag;
        this.result = result;
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
