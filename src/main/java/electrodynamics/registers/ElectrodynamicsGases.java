package electrodynamics.registers;

import electrodynamics.Electrodynamics;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import voltaic.api.gas.Gas;
import voltaic.prefab.utilities.math.Color;
import voltaic.registers.VoltaicGases;

public class ElectrodynamicsGases {

    public static final DeferredRegister<Gas> GASES = DeferredRegister.create(VoltaicGases.GAS_REGISTRY_KEY, Electrodynamics.ID);

    public static final DeferredHolder<Gas, Gas> HYDROGEN = GASES.register("hydrogen", () -> new Gas(ElectrodynamicsItems.ITEM_PORTABLECYLINDER, ElectroTextUtils.gas("hydrogen"), 33, Color.WHITE, ElectrodynamicsFluids.FLUID_HYDROGEN));
    public static final DeferredHolder<Gas, Gas> OXYGEN = GASES.register("oxygen", () -> new Gas(ElectrodynamicsItems.ITEM_PORTABLECYLINDER, ElectroTextUtils.gas("oxygen"), 90, Color.WHITE, ElectrodynamicsFluids.FLUID_OXYGEN));
    public static final DeferredHolder<Gas, Gas> STEAM = GASES.register("steam", () -> new Gas(ElectrodynamicsItems.ITEM_PORTABLECYLINDER, ElectroTextUtils.gas("steam"), 373, Color.WHITE, BuiltInRegistries.FLUID.wrapAsHolder(Fluids.WATER)));
    public static final DeferredHolder<Gas, Gas> NITROGEN = GASES.register("nitrogen", () -> new Gas(ElectrodynamicsItems.ITEM_PORTABLECYLINDER, ElectroTextUtils.gas("nitrogen"), Color.WHITE));
    public static final DeferredHolder<Gas, Gas> CARBON_DIOXIDE = GASES.register("carbondioxide", () -> new Gas(ElectrodynamicsItems.ITEM_PORTABLECYLINDER, ElectroTextUtils.gas("carbondioxide"), Color.WHITE));
    public static final DeferredHolder<Gas, Gas> ARGON = GASES.register("argon", () -> new Gas(ElectrodynamicsItems.ITEM_PORTABLECYLINDER, ElectroTextUtils.gas("argon"), Color.WHITE));
    public static final DeferredHolder<Gas, Gas> SULFUR_DIOXIDE = GASES.register("sulfurdioxide", () -> new Gas(ElectrodynamicsItems.ITEM_PORTABLECYLINDER, ElectroTextUtils.gas("sulfurdioxide"), Color.WHITE));
    public static final DeferredHolder<Gas, Gas> AMMONIA = GASES.register("ammonia", () -> new Gas(ElectrodynamicsItems.ITEM_PORTABLECYLINDER, ElectroTextUtils.gas("ammonia"), 239, Color.WHITE, ElectrodynamicsFluids.FLUID_AMMONIA));

}
