package electrodynamics.registers;

import electrodynamics.Electrodynamics;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import voltaic.api.gas.Gas;
import voltaic.registers.VoltaicRegistries;

public class ElectrodynamicsGases {

	public static final DeferredRegister<Gas> GASES = DeferredRegister.create(VoltaicRegistries.GAS_REGISTRY_KEY, Electrodynamics.ID);

	public static final RegistryObject<Gas> HYDROGEN = GASES.register("hydrogen", () -> new Gas(() -> ElectrodynamicsItems.ITEM_PORTABLECYLINDER.get(), ElectroTextUtils.gas("hydrogen"), 33, () -> ElectrodynamicsFluids.FLUID_HYDROGEN.get()));
    public static final RegistryObject<Gas> OXYGEN = GASES.register("oxygen", () -> new Gas(() -> ElectrodynamicsItems.ITEM_PORTABLECYLINDER.get(), ElectroTextUtils.gas("oxygen"), 90, () -> ElectrodynamicsFluids.FLUID_OXYGEN.get()));
    public static final RegistryObject<Gas> STEAM = GASES.register("steam", () -> new Gas(() -> ElectrodynamicsItems.ITEM_PORTABLECYLINDER.get(), ElectroTextUtils.gas("steam"), 373, () -> Fluids.WATER));
}
