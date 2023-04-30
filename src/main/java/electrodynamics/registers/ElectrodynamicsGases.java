package electrodynamics.registers;

import electrodynamics.api.References;
import electrodynamics.api.gas.Gas;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.prefab.utilities.TextUtils;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ElectrodynamicsGases {
	
	public static final DeferredRegister<Gas> GASES = DeferredRegister.create(ElectrodynamicsRegistries.GAS_REGISTRY_KEY, References.ID);
	
	public static final RegistryObject<Gas> EMPTY = GASES.register("empty", () -> new Gas(() -> Items.AIR, null, TextUtils.gas("empty")));
	public static final RegistryObject<Gas> HYDROGEN = GASES.register("hydrogen", () -> new Gas(() -> Items.AIR, ElectrodynamicsTags.Gases.HYDROGEN, TextUtils.gas("hydrogen"), 33, ElectrodynamicsFluids.fluidHydrogen));
	public static final RegistryObject<Gas> OXYGEN = GASES.register("oxygen", () -> new Gas(() -> Items.AIR, ElectrodynamicsTags.Gases.OXYGEN, TextUtils.gas("oxygen"), 90, ElectrodynamicsFluids.fluidOxygen));

}
