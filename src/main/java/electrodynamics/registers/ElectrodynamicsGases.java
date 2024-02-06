package electrodynamics.registers;

import java.util.concurrent.ConcurrentHashMap;

import electrodynamics.api.References;
import electrodynamics.api.gas.Gas;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ElectrodynamicsGases {

	public static final ConcurrentHashMap<Fluid, Gas> MAPPED_GASSES = new ConcurrentHashMap<>();

	public static final DeferredRegister<Gas> GASES = DeferredRegister.create(ElectrodynamicsRegistries.GAS_REGISTRY_KEY, References.ID);

	public static final DeferredHolder<Gas, Gas> EMPTY = GASES.register("empty", () -> new Gas(() -> Items.AIR, null, ElectroTextUtils.gas("empty")));
	public static final DeferredHolder<Gas, Gas> HYDROGEN = GASES.register("hydrogen", () -> new Gas(() -> ElectrodynamicsItems.ITEM_PORTABLECYLINDER.get(), ElectrodynamicsTags.Gases.HYDROGEN, ElectroTextUtils.gas("hydrogen"), 33, ElectrodynamicsFluids.fluidHydrogen));
	public static final DeferredHolder<Gas, Gas> OXYGEN = GASES.register("oxygen", () -> new Gas(() -> ElectrodynamicsItems.ITEM_PORTABLECYLINDER.get(), ElectrodynamicsTags.Gases.OXYGEN, ElectroTextUtils.gas("oxygen"), 90, ElectrodynamicsFluids.fluidOxygen));
	public static final DeferredHolder<Gas, Gas> STEAM = GASES.register("steam", () -> new Gas(() -> ElectrodynamicsItems.ITEM_PORTABLECYLINDER.get(), ElectrodynamicsTags.Gases.STEAM, ElectroTextUtils.gas("steam"), 373, Fluids.WATER));

}
