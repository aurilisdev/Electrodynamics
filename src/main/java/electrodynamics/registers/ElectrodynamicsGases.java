package electrodynamics.registers;

import java.util.concurrent.ConcurrentHashMap;

import electrodynamics.api.References;
import electrodynamics.api.gas.Gas;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(modid = References.ID, bus = Bus.MOD)
public class ElectrodynamicsGases {
	
	public static final ConcurrentHashMap<Fluid, Gas> MAPPED_GASSES = new ConcurrentHashMap<>();
	
	public static final DeferredRegister<Gas> GASES = DeferredRegister.create(ElectrodynamicsRegistries.GAS_REGISTRY_KEY, References.ID);
	
	public static final RegistryObject<Gas> EMPTY = GASES.register("empty", () -> new Gas(() -> Items.AIR, null, ElectroTextUtils.gas("empty")));
	public static final RegistryObject<Gas> HYDROGEN = GASES.register("hydrogen", () -> new Gas(() -> Items.AIR, ElectrodynamicsTags.Gases.HYDROGEN, ElectroTextUtils.gas("hydrogen"), 33, ElectrodynamicsFluids.fluidHydrogen));
	public static final RegistryObject<Gas> OXYGEN = GASES.register("oxygen", () -> new Gas(() -> Items.AIR, ElectrodynamicsTags.Gases.OXYGEN, ElectroTextUtils.gas("oxygen"), 90, ElectrodynamicsFluids.fluidOxygen));

}
